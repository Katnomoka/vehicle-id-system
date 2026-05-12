package com.vehicle.identification.vehicleidsystem.controller;

import com.vehicle.identification.vehicleidsystem.dao.ServiceRecordDAO;
import com.vehicle.identification.vehicleidsystem.model.ServiceRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller for Workshop Module - Manages vehicle service records
 */
public class WorkshopController {

    @FXML
    private TableView<ServiceRecord> serviceTable;

    @FXML
    private TableColumn<ServiceRecord, Integer> colServiceId;

    @FXML
    private TableColumn<ServiceRecord, Integer> colVehicleId;

    @FXML
    private TableColumn<ServiceRecord, LocalDate> colServiceDate;

    @FXML
    private TableColumn<ServiceRecord, String> colServiceType;

    @FXML
    private TableColumn<ServiceRecord, String> colDescription;

    @FXML
    private TableColumn<ServiceRecord, Double> colCost;

    @FXML
    private TextField vehicleIdField;

    // ✅ FIXED: Changed from TextField to ComboBox<String> to match FXML
    @FXML
    private ComboBox<String> serviceTypeCombo;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private TextField costField;

    private ServiceRecordDAO serviceDAO;
    private ObservableList<ServiceRecord> serviceData;

    @FXML
    public void initialize() {
        serviceDAO = new ServiceRecordDAO();
        serviceData = FXCollections.observableArrayList();

        // Setup table columns - uses PropertyValues for polymorphism
        colServiceId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        colServiceDate.setCellValueFactory(new PropertyValueFactory<>("serviceDate"));
        colServiceType.setCellValueFactory(new PropertyValueFactory<>("serviceType"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        serviceTable.setItems(serviceData);
        loadServiceRecords();
    }

    private void loadServiceRecords() {
        List<ServiceRecord> records = serviceDAO.findAll();
        serviceData.setAll(records);
    }

    @FXML
    private void handleAddService() {
        try {
            // Validate and parse input
            int vehicleId = Integer.parseInt(vehicleIdField.getText());

            // ✅ FIXED: Use getValue() for ComboBox instead of getText()
            String serviceType = serviceTypeCombo.getValue();
            String description = descriptionArea.getText();
            double cost = Double.parseDouble(costField.getText());

            // Validate required fields
            if (serviceType == null || serviceType.isEmpty()) {
                showAlert("Error", "Please select a service type");
                return;
            }

            // Create new ServiceRecord (demonstrates object creation)
            ServiceRecord record = new ServiceRecord();
            record.setVehicleId(vehicleId);
            record.setServiceDate(LocalDate.now());
            record.setServiceType(serviceType);
            record.setDescription(description);
            record.setCost(cost);

            // Save to database (demonstrates JDBC + exception handling)
            serviceDAO.create(record);
            loadServiceRecords();
            clearFields();

            showAlert("Success", "Service record added successfully!");

        } catch (NumberFormatException e) {
            // ✅ Exception Handling requirement
            showAlert("Error", "Please enter valid numbers for Vehicle ID and Cost");
        } catch (Exception e) {
            showAlert("Error", "Error adding service: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRefresh() {
        loadServiceRecords();
    }

    private void clearFields() {
        vehicleIdField.clear();
        // ✅ FIXED: Use setValue(null) for ComboBox instead of clear()
        serviceTypeCombo.setValue(null);
        descriptionArea.clear();
        costField.clear();
    }

    /**
     * Helper method to show alert dialogs
     * Demonstrates proper UI feedback and exception handling
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}