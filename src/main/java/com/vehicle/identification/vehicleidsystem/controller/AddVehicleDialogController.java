package com.vehicle.identification.vehicleidsystem.controller;

import com.vehicle.identification.vehicleidsystem.dao.VehicleDAO;
import com.vehicle.identification.vehicleidsystem.model.Vehicle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for Add Vehicle Dialog
 * Handles user input and validates data before saving to database
 */
public class AddVehicleDialogController {

    @FXML
    private TextField regNumberField;

    @FXML
    private TextField makeField;

    @FXML
    private TextField modelField;

    @FXML
    private TextField yearField;

    @FXML
    private TextField ownerIdField;

    private VehicleDAO vehicleDAO;
    private Runnable onVehicleAdded;

    @FXML
    public void initialize() {
        vehicleDAO = new VehicleDAO();
    }

    /**
     * Set callback to refresh parent table after adding vehicle
     * @param callback Runnable to execute after successful vehicle addition
     */
    public void setOnVehicleAdded(Runnable callback) {
        this.onVehicleAdded = callback;
    }

    @FXML
    private void handleAdd() {
        try {
            // Get and validate input values (string manipulation)
            String regNumber = regNumberField.getText().trim();
            String make = makeField.getText().trim();
            String model = modelField.getText().trim();
            String yearText = yearField.getText().trim();
            String ownerIdText = ownerIdField.getText().trim();

            // Validate required fields
            if (regNumber.isEmpty() || make.isEmpty() || model.isEmpty() ||
                    yearText.isEmpty() || ownerIdText.isEmpty()) {
                showAlert("Error", "Please fill all fields");
                return;
            }

            // Parse and validate numeric fields (exception handling)
            int year;
            int ownerId;

            try {
                year = Integer.parseInt(yearText);
                ownerId = Integer.parseInt(ownerIdText);
            } catch (NumberFormatException e) {
                showAlert("Error", "Please enter valid numbers for Year and Owner ID");
                return;
            }

            // Validate year range
            if (year < 1990 || year > 2026) {
                showAlert("Error", "Please enter a valid year (1990-2026)");
                return;
            }

            // Validate owner ID
            if (ownerId <= 0) {
                showAlert("Error", "Owner ID must be a positive number");
                return;
            }

            // Create new Vehicle object (object creation requirement)
            Vehicle vehicle = new Vehicle(regNumber, make, model, year, ownerId);

            // Save to database using DAO (JDBC + exception handling)
            vehicleDAO.create(vehicle);

            // Show success message
            String successMessage = String.format(
                    "Vehicle added successfully!\n\n" +
                            "Registration: %s\n" +
                            "Make: %s\n" +
                            "Model: %s\n" +
                            "Year: %d\n" +
                            "Owner ID: %d",
                    regNumber, make, model, year, ownerId
            );

            showAlert("Success", successMessage);

            // Close dialog window
            Stage stage = (Stage) regNumberField.getScene().getWindow();
            stage.close();

            // Refresh parent table (callback)
            if (onVehicleAdded != null) {
                onVehicleAdded.run();
            }

        } catch (Exception e) {
            // Exception handling requirement
            showAlert("Error", "Error adding vehicle: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel() {
        // Close dialog without saving
        Stage stage = (Stage) regNumberField.getScene().getWindow();
        stage.close();
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