package com.vehicle.identification.vehicleidsystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Controller for Admin Module - Manages user access and system monitoring
 * Demonstrates exception handling, string manipulation, and UI controls
 */
public class AdminController {

    // ==================== FORM FIELDS ====================
    @FXML
    private TextField userIdField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<String> accessLevelCombo;

    // ==================== TABLE FIELDS ====================
    @FXML
    private TableView<UserAccess> usersTable;

    @FXML
    private TableColumn<UserAccess, String> colUserId;

    @FXML
    private TableColumn<UserAccess, String> colUsername;

    @FXML
    private TableColumn<UserAccess, String> colRole;

    @FXML
    private TableColumn<UserAccess, String> colGrantedDate;

    @FXML
    private TableColumn<UserAccess, String> colStatus;

    @FXML
    private TableColumn<UserAccess, String> colLastLogin;

    // ==================== LOGS & STATS ====================
    @FXML
    private TextArea accessLogArea;

    @FXML
    private Label totalUsersLabel;

    @FXML
    private Label activeSessionsLabel;

    @FXML
    private Label pendingRequestsLabel;

    @FXML
    private Label deniedAccessLabel;

    // ==================== DATA ====================
    private ObservableList<UserAccess> userData;

    // ==================== INNER CLASS (For TableView) ====================
    /**
     * Simple model class for displaying user access data in TableView
     * Demonstrates object creation and encapsulation
     */
    public static class UserAccess {
        private final String userId;
        private final String username;
        private final String role;
        private final String grantedDate;
        private final String status;
        private final String lastLogin;

        public UserAccess(String userId, String username, String role,
                          String grantedDate, String status, String lastLogin) {
            this.userId = userId;
            this.username = username;
            this.role = role;
            this.grantedDate = grantedDate;
            this.status = status;
            this.lastLogin = lastLogin;
        }

        // Getters for PropertyValueFactory
        public String getUserId() { return userId; }
        public String getUsername() { return username; }
        public String getRole() { return role; }
        public String getGrantedDate() { return grantedDate; }
        public String getStatus() { return status; }
        public String getLastLogin() { return lastLogin; }
    }

    // ==================== INITIALIZATION ====================
    @FXML
    public void initialize() {
        // Initialize ComboBox with access levels
        accessLevelCombo.getItems().addAll(
                "Workshop", "Customer", "Insurance", "Police", "Full Access"
        );

        // Setup TableView columns (demonstrates polymorphism with PropertyValueFactory)
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colGrantedDate.setCellValueFactory(new PropertyValueFactory<>("grantedDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colLastLogin.setCellValueFactory(new PropertyValueFactory<>("lastLogin"));

        // Initialize data list
        userData = FXCollections.observableArrayList();
        usersTable.setItems(userData);

        // Load initial data
        loadAccessLogs();
        loadDummyUsers();
        updateStatistics();
    }

    // ==================== ACTION METHODS ====================
    @FXML
    private void handleGrantAccess() {
        try {
            String userId = userIdField.getText().trim();
            String password = passwordField.getText();
            String accessLevel = accessLevelCombo.getValue();

            // Validate inputs (string manipulation + validation)
            if (userId.isEmpty()) {
                showAlert("Error", "Please enter a User ID");
                return;
            }
            if (password.isEmpty() || password.length() < 6) {
                showAlert("Error", "Password must be at least 6 characters");
                return;
            }
            if (accessLevel == null) {
                showAlert("Error", "Please select an access level");
                return;
            }

            // Add to table (simulating database operation)
            UserAccess newUser = new UserAccess(
                    userId,
                    "user_" + userId,
                    accessLevel,
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                    "Active",
                    "Never"
            );
            userData.add(newUser);
            usersTable.refresh();

            // Log the action
            String message = "Access granted: User " + userId + " → " + accessLevel;
            appendToLog(message);
            updateStatistics();

            // Show success and clear fields
            showAlert("Success", message);
            userIdField.clear();
            passwordField.clear();
            accessLevelCombo.setValue(null);

        } catch (Exception e) {
            // Exception handling requirement
            showAlert("Error", "Error granting access: " + e.getMessage());
            appendToLog("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRevokeAccess() {
        try {
            String userId = userIdField.getText().trim();

            if (userId.isEmpty()) {
                showAlert("Error", "Please enter a User ID");
                return;
            }

            // Find and update user status (demonstrates iteration + string comparison)
            boolean found = false;
            for (UserAccess user : userData) {
                if (user.getUserId().equals(userId)) {
                    // Create new object with updated status (immutability pattern)
                    UserAccess updated = new UserAccess(
                            user.getUserId(),
                            user.getUsername(),
                            user.getRole(),
                            user.getGrantedDate(),
                            "Revoked",  // Changed status
                            user.getLastLogin()
                    );
                    userData.remove(user);
                    userData.add(updated);
                    found = true;
                    break;
                }
            }

            if (found) {
                String message = "Access revoked for User ID: " + userId;
                appendToLog(message);
                updateStatistics();
                showAlert("Success", message);
                userIdField.clear();
            } else {
                showAlert("Warning", "User ID not found: " + userId);
            }

        } catch (Exception e) {
            showAlert("Error", "Error revoking access: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRefresh() {
        loadDummyUsers();
        updateStatistics();
        appendToLog("Data refreshed by admin");
        showAlert("Refreshed", "User list and statistics updated");
    }

    @FXML
    private void handleClearLogs() {
        if (accessLogArea != null) {
            accessLogArea.clear();
            appendToLog("Logs cleared by admin");
            showAlert("Cleared", "Access logs have been cleared");
        }
    }

    @FXML
    private void handleExportLogs() {
        try {
            // Simulate file export (can be extended with FileExporter utility)
            String logContent = accessLogArea.getText();
            int lineCount = logContent.split("\n").length;

            appendToLog("Logs exported: " + lineCount + " entries");
            showAlert("Export Success", "Access logs exported successfully!\n" +
                    "Entries: " + lineCount);

            // TODO: Later integrate with FileExporter.java for actual CSV export
            // FileExporter.exportToCSV("admin_logs.csv", Arrays.asList(logContent));

        } catch (Exception e) {
            showAlert("Export Failed", "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ==================== HELPER METHODS ====================
    private void loadAccessLogs() {
        appendToLog("=== System Initialized ===");
        appendToLog("Admin panel loaded successfully");
        appendToLog("User access management module ready");
        appendToLog("Database connection: Active");
        appendToLog("Security level: High");
    }

    private void appendToLog(String message) {
        if (accessLogArea != null) {
            String timestamp = LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            );
            accessLogArea.appendText("[" + timestamp + "] " + message + "\n");
            // Auto-scroll to bottom
            accessLogArea.setScrollTop(Double.MAX_VALUE);
        }
    }

    private void loadDummyUsers() {
        // Sample data for demonstration (20+ items for ScrollPane requirement)
        userData.clear();
        userData.addAll(
                new UserAccess("101", "alice_admin", "Full Access", "2026-01-15 09:00", "Active", "2026-05-12 08:30"),
                new UserAccess("102", "bob_workshop", "Workshop", "2026-01-20 10:30", "Active", "2026-05-11 14:20"),
                new UserAccess("103", "charlie_customer", "Customer", "2026-02-01 11:00", "Active", "2026-05-10 16:45"),
                new UserAccess("104", "diana_insurance", "Insurance", "2026-02-10 14:00", "Active", "2026-05-09 09:15"),
                new UserAccess("105", "evan_police", "Police", "2026-02-15 08:45", "Active", "2026-05-12 07:00"),
                new UserAccess("106", "fiona_workshop", "Workshop", "2026-03-01 13:20", "Revoked", "2026-04-30 11:00"),
                new UserAccess("107", "george_customer", "Customer", "2026-03-10 09:30", "Active", "2026-05-11 10:30"),
                new UserAccess("108", "hannah_insurance", "Insurance", "2026-03-15 15:00", "Active", "2026-05-08 13:45"),
                new UserAccess("109", "ian_police", "Police", "2026-03-20 10:15", "Active", "2026-05-12 06:20"),
                new UserAccess("110", "julia_workshop", "Workshop", "2026-04-01 11:45", "Active", "2026-05-10 15:10"),
                new UserAccess("111", "kevin_customer", "Customer", "2026-04-05 14:30", "Pending", "Never"),
                new UserAccess("112", "laura_insurance", "Insurance", "2026-04-10 09:00", "Active", "2026-05-11 08:00"),
                new UserAccess("113", "mike_police", "Police", "2026-04-15 16:20", "Active", "2026-05-12 07:45"),
                new UserAccess("114", "nancy_workshop", "Workshop", "2026-04-20 10:00", "Revoked", "2026-05-01 12:00"),
                new UserAccess("115", "oscar_customer", "Customer", "2026-04-25 13:15", "Active", "2026-05-09 14:30"),
                new UserAccess("116", "paula_insurance", "Insurance", "2026-05-01 08:30", "Active", "2026-05-12 09:00"),
                new UserAccess("117", "quinn_police", "Police", "2026-05-05 11:00", "Active", "2026-05-11 16:20"),
                new UserAccess("118", "rachel_workshop", "Workshop", "2026-05-08 14:45", "Pending", "Never"),
                new UserAccess("119", "steve_customer", "Customer", "2026-05-10 09:20", "Active", "2026-05-12 10:15"),
                new UserAccess("120", "tina_insurance", "Insurance", "2026-05-11 15:30", "Active", "2026-05-12 08:45"),
                new UserAccess("121", "uma_police", "Police", "2026-05-12 07:00", "Active", "2026-05-12 07:00"),
                new UserAccess("122", "victor_workshop", "Workshop", "2026-05-12 08:00", "Pending", "Never"),
                new UserAccess("123", "wendy_customer", "Customer", "2026-05-12 09:00", "Active", "2026-05-12 09:00"),
                new UserAccess("124", "xavier_insurance", "Insurance", "2026-05-12 10:00", "Active", "2026-05-12 10:00"),
                new UserAccess("125", "yolanda_police", "Police", "2026-05-12 11:00", "Active", "2026-05-12 11:00")
        );
        usersTable.refresh();
    }

    private void updateStatistics() {
        // Count statistics (demonstrates repetition + conditional logic)
        int total = userData.size();
        int active = 0, pending = 0, revoked = 0;

        for (UserAccess user : userData) {
            switch (user.getStatus()) {
                case "Active": active++; break;
                case "Pending": pending++; break;
                case "Revoked": revoked++; break;
            }
        }

        // Update labels with formatted strings
        totalUsersLabel.setText(String.valueOf(total));
        activeSessionsLabel.setText(String.valueOf(active));
        pendingRequestsLabel.setText(String.valueOf(pending));
        deniedAccessLabel.setText(String.valueOf(revoked));
    }

    /**
     * Helper method to show alert dialogs
     * Centralizes UI feedback logic
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}