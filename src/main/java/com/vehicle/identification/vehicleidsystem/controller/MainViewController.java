package com.vehicle.identification.vehicleidsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main View Controller - Handles MenuBar actions and tab management
 */
public class MainViewController {

    @FXML
    private TabPane tabPane;

    /**
     * Exit the application
     */
    @FXML
    private void handleExit() {
        Stage stage = (Stage) tabPane.getScene().getWindow();
        stage.close();
    }

    /**
     * Show Dashboard tab
     */
    @FXML
    private void showDashboard() {
        // Find and select Dashboard tab
        for (Tab tab : tabPane.getTabs()) {
            if ("Dashboard".equals(tab.getText())) {
                tabPane.getSelectionModel().select(tab);
                break;
            }
        }
    }

    /**
     * Show Progress tab
     */
    @FXML
    private void showProgress() {
        // Find and select Progress tab
        for (Tab tab : tabPane.getTabs()) {
            if ("Progress".equals(tab.getText())) {
                tabPane.getSelectionModel().select(tab);
                break;
            }
        }
    }

    /**
     * Show Admin Module in a new window
     */
    @FXML
    private void showAdmin() {
        openModuleWindow("Admin Module", "/fxml/AdminPanel.fxml", 1200, 700);
    }

    /**
     * Show Workshop Module in a new window
     */
    @FXML
    private void showWorkshop() {
        openModuleWindow("Workshop Module", "/fxml/WorkshopPanel.fxml", 1200, 700);
    }

    /**
     * Show Customer Module in a new window
     */
    @FXML
    private void showCustomer() {
        openModuleWindow("Customer Module", "/fxml/CustomerPanel.fxml", 1200, 700);
    }

    /**
     * Show Insurance Module in a new window
     */
    @FXML
    private void showInsurance() {
        openModuleWindow("Insurance Module", "/fxml/InsurancePanel.fxml", 1200, 700);
    }

    /**
     * Show Police Module in a new window
     */
    @FXML
    private void showPolice() {
        openModuleWindow("Police Module", "/fxml/PolicePanel.fxml", 1200, 700);
    }

    /**
     * Show About dialog
     */
    @FXML
    private void showAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Vehicle Identification System");
        alert.setContentText("Version 1.0\n\nDeveloped for OOP2 Course\n© 2026\n\nFeatures:\n" +
                "• Vehicle Management\n" +
                "• Service Records\n" +
                "• Insurance Tracking\n" +
                "• Police Reports\n" +
                "• Customer Queries\n" +
                "• Admin Controls");
        alert.showAndWait();
    }

    /**
     * Helper method to open a module in a new window
     * This demonstrates proper window management and FXML loading
     */
    private void openModuleWindow(String title, String fxmlPath, double width, double height) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root, width, height));

            // ✅ ENABLE RESIZING FOR MODULES
            stage.setResizable(true);
            stage.setMinWidth(800);
            stage.setMinHeight(600);

            stage.show();
        } catch (IOException e) {
            // ... error handling
        }
    }
}