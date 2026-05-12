package com.vehicle.identification.vehicleidsystem.controller;

import com.vehicle.identification.vehicleidsystem.dao.InsurancePolicyDAO;
import com.vehicle.identification.vehicleidsystem.model.InsurancePolicy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;

public class InsuranceController {

    @FXML
    private TableView<InsurancePolicy> policyTable;

    @FXML
    private TableColumn<InsurancePolicy, Integer> colPolicyId;

    @FXML
    private TableColumn<InsurancePolicy, Integer> colVehicleId;

    @FXML
    private TableColumn<InsurancePolicy, String> colCompany;

    @FXML
    private TableColumn<InsurancePolicy, String> colPolicyNumber;

    @FXML
    private TableColumn<InsurancePolicy, LocalDate> colStartDate;

    @FXML
    private TableColumn<InsurancePolicy, LocalDate> colEndDate;

    @FXML
    private TableColumn<InsurancePolicy, String> colCoverage;

    @FXML
    private TextField vehicleIdField;

    @FXML
    private TextField companyField;

    @FXML
    private TextField policyNumberField;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextArea coverageArea;

    private InsurancePolicyDAO policyDAO;
    private ObservableList<InsurancePolicy> policyData;

    @FXML
    public void initialize() {
        policyDAO = new InsurancePolicyDAO();
        policyData = FXCollections.observableArrayList();

        // Setup table columns
        colPolicyId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        colCompany.setCellValueFactory(new PropertyValueFactory<>("insuranceCompany"));
        colPolicyNumber.setCellValueFactory(new PropertyValueFactory<>("policyNumber"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        colCoverage.setCellValueFactory(new PropertyValueFactory<>("coverageDetails"));

        policyTable.setItems(policyData);
        loadPolicies();
    }

    private void loadPolicies() {
        List<InsurancePolicy> policies = policyDAO.findAll();
        policyData.setAll(policies);
    }

    @FXML
    private void handleAddPolicy() {
        try {
            int vehicleId = Integer.parseInt(vehicleIdField.getText());
            String company = companyField.getText();
            String policyNumber = policyNumberField.getText();
            LocalDate startDate = startDatePicker.getValue();
            LocalDate endDate = endDatePicker.getValue();
            String coverage = coverageArea.getText();

            if (company.isEmpty() || policyNumber.isEmpty()) {
                showAlert("Error", "Please fill all required fields");
                return;
            }

            InsurancePolicy policy = new InsurancePolicy();
            policy.setVehicleId(vehicleId);
            policy.setInsuranceCompany(company);
            policy.setPolicyNumber(policyNumber);
            policy.setStartDate(startDate);
            policy.setEndDate(endDate);
            policy.setCoverageDetails(coverage);

            policyDAO.create(policy);
            loadPolicies();
            clearFields();

            showAlert("Success", "Insurance policy added successfully!");

        } catch (Exception e) {
            showAlert("Error", "Error adding policy: " + e.getMessage());
        }
    }

    @FXML
    private void handleRefresh() {
        loadPolicies();
    }

    private void clearFields() {
        vehicleIdField.clear();
        companyField.clear();
        policyNumberField.clear();
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        coverageArea.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}