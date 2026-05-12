package com.vehicle.identification.vehicleidsystem.controller;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Modern Login Controller with animations and validation
 */
public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox rememberMeCheckbox;

    @FXML
    private Button togglePasswordButton;

    @FXML
    private ProgressIndicator loadingIndicator;

    @FXML
    private Label errorMessage;

    private boolean isPasswordVisible = false;
    private TextField passwordTextField;

    @FXML
    public void initialize() {
        // Add enter key listener
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                handleLogin();
            }
        });

        usernameField.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                passwordField.requestFocus();
            }
        });

        // Load saved credentials if "Remember Me" was checked
        loadSavedCredentials();

        // Animate form entrance
        animateFormEntrance();
    }

    /**
     * Toggle password visibility
     */
    @FXML
    private void togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible;

        if (isPasswordVisible) {
            // Show password
            passwordTextField = new TextField();
            passwordTextField.setText(passwordField.getText());
            passwordTextField.setStyle(passwordField.getStyle());
            passwordTextField.setPromptText("Enter your password");

            // Replace password field with text field
            var parent = (javafx.scene.layout.HBox) passwordField.getParent();
            int index = parent.getChildren().indexOf(passwordField);
            parent.getChildren().set(index, passwordTextField);

            togglePasswordButton.setText("🙈");
        } else {
            // Hide password
            PasswordField newPasswordField = new PasswordField();
            newPasswordField.setText(passwordTextField.getText());
            newPasswordField.setStyle(passwordTextField.getStyle());
            newPasswordField.setPromptText("Enter your password");

            // Replace text field with password field
            var parent = (javafx.scene.layout.HBox) passwordTextField.getParent();
            int index = parent.getChildren().indexOf(passwordTextField);
            parent.getChildren().set(index, newPasswordField);

            passwordField = newPasswordField;
            togglePasswordButton.setText("👁️");
        }
    }

    /**
     * Handle login button click
     */
    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = isPasswordVisible ?
                passwordTextField.getText() : passwordField.getText();

        // Validate inputs
        if (username.isEmpty() || password.isEmpty()) {
            showError("Please enter both username and password");
            shakeAnimation();
            return;
        }

        // Show loading
        setLoading(true);

        // Simulate authentication (replace with actual database check)
        new Thread(() -> {
            try {
                Thread.sleep(1500); // Simulate network delay

                // Simple authentication (replace with actual database validation)
                if (authenticateUser(username, password)) {
                    // Save credentials if "Remember Me" is checked
                    if (rememberMeCheckbox.isSelected()) {
                        saveCredentials(username, password);
                    } else {
                        clearSavedCredentials();
                    }

                    // Load main application
                    javafx.application.Platform.runLater(() -> {
                        loadMainApplication();
                    });
                } else {
                    javafx.application.Platform.runLater(() -> {
                        showError("Invalid username or password");
                        shakeAnimation();
                        setLoading(false);
                    });
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Authenticate user (replace with actual database query)
     */
    private boolean authenticateUser(String username, String password) {
        // TODO: Replace with actual database authentication
        // For demo purposes, accept any username/password with min 4 chars
        return username.length() >= 4 && password.length() >= 4;

        // Example of real authentication:
        // String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        // try (PreparedStatement pstmt = DBConnection.getConnection().prepareStatement(query)) {
        //     pstmt.setString(1, username);
        //     pstmt.setString(2, hashPassword(password)); // Always hash passwords!
        //     ResultSet rs = pstmt.executeQuery();
        //     return rs.next();
        // }
    }

    /**
     * Load main application after successful login
     */
    private void loadMainApplication() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 700));
            stage.setTitle("Vehicle Identification System");

            // Fade transition
            FadeTransition fade = new FadeTransition(Duration.millis(500), root);
            fade.setFromValue(0);
            fade.setToValue(1);
            fade.play();

        } catch (IOException e) {
            showError("Failed to load application: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handle forgot password
     */
    @FXML
    private void handleForgotPassword() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Password Reset");
        alert.setHeaderText("Reset Password");
        alert.setContentText("Please contact your administrator to reset your password.\n\n" +
                "Email: support@vehicleid.com\n" +
                "Phone: +1-555-0123");
        alert.showAndWait();
    }

    /**
     * Handle sign up
     */
    @FXML
    private void handleSignUp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sign Up");
        alert.setHeaderText("Create Account");
        alert.setContentText("Sign up functionality will be available soon!\n\n" +
                "Please contact your administrator to create an account.");
        alert.showAndWait();
    }

    /**
     * Show error message with animation
     */
    private void showError(String message) {
        errorMessage.setText(message);
        errorMessage.setVisible(true);

        // Fade in animation
        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), errorMessage);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        // Auto-hide after 5 seconds
        new Timeline(new KeyFrame(Duration.seconds(5), e -> {
            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), errorMessage);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(event -> errorMessage.setVisible(false));
            fadeOut.play();
        })).play();
    }

    /**
     * Shake animation for errors
     */
    private void shakeAnimation() {
        TranslateTransition shake = new TranslateTransition(Duration.millis(500), errorMessage.getParent());
        shake.setFromX(-10);
        shake.setToX(10);
        shake.setCycleCount(4);
        shake.setAutoReverse(true);
        shake.play();
    }

    /**
     * Animate form entrance
     */
    private void animateFormEntrance() {
        var form = errorMessage.getParent();
        form.setOpacity(0);
        form.setTranslateY(50);

        FadeTransition fade = new FadeTransition(Duration.millis(800), form);
        fade.setFromValue(0);
        fade.setToValue(1);

        TranslateTransition translate = new TranslateTransition(Duration.millis(800), form);
        translate.setFromY(50);
        translate.setToY(0);

        ParallelTransition parallel = new ParallelTransition(fade, translate);
        parallel.play();
    }

    /**
     * Set loading state
     */
    private void setLoading(boolean loading) {
        loadingIndicator.setVisible(loading);
        usernameField.setDisable(loading);
        if (isPasswordVisible) {
            passwordTextField.setDisable(loading);
        } else {
            passwordField.setDisable(loading);
        }
        rememberMeCheckbox.setDisable(loading);
    }

    /**
     * Save credentials (simple implementation - use secure storage in production)
     */
    private void saveCredentials(String username, String password) {
        // TODO: Use Java Preferences API or encrypted file storage
        System.setProperty("saved.username", username);
        System.setProperty("saved.password", password); // ⚠️ Never store plain passwords in production!
    }

    /**
     * Load saved credentials
     */
    private void loadSavedCredentials() {
        String savedUsername = System.getProperty("saved.username");
        String savedPassword = System.getProperty("saved.password");

        if (savedUsername != null && savedPassword != null) {
            usernameField.setText(savedUsername);
            if (!isPasswordVisible) {
                passwordField.setText(savedPassword);
            } else {
                passwordTextField.setText(savedPassword);
            }
            rememberMeCheckbox.setSelected(true);
        }
    }

    /**
     * Clear saved credentials
     */
    private void clearSavedCredentials() {
        System.clearProperty("saved.username");
        System.clearProperty("saved.password");
    }
}