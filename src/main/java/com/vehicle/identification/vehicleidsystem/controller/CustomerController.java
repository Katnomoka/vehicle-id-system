package com.vehicle.identification.vehicleidsystem.controller;

import com.vehicle.identification.vehicleidsystem.dao.CustomerDAO;
import com.vehicle.identification.vehicleidsystem.dao.CustomerQueryDAO;
import com.vehicle.identification.vehicleidsystem.model.Customer;
import com.vehicle.identification.vehicleidsystem.model.CustomerQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.util.List;

public class CustomerController {

    @FXML
    private TableView<CustomerQuery> queryTable;

    @FXML
    private TableColumn<CustomerQuery, Integer> colQueryId;

    @FXML
    private TableColumn<CustomerQuery, LocalDateTime> colQueryDate;

    @FXML
    private TableColumn<CustomerQuery, String> colQueryText;

    @FXML
    private TableColumn<CustomerQuery, String> colResponse;

    @FXML
    private TextField customerIdField;

    @FXML
    private TextField vehicleIdField;

    @FXML
    private TextArea queryTextArea;

    @FXML
    private TextArea responseTextArea;

    private CustomerDAO customerDAO;
    private CustomerQueryDAO queryDAO;
    private ObservableList<CustomerQuery> queryData;

    @FXML
    public void initialize() {
        customerDAO = new CustomerDAO();
        queryDAO = new CustomerQueryDAO();
        queryData = FXCollections.observableArrayList();

        // Setup table columns
        colQueryId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colQueryDate.setCellValueFactory(new PropertyValueFactory<>("queryDate"));
        colQueryText.setCellValueFactory(new PropertyValueFactory<>("queryText"));
        colResponse.setCellValueFactory(new PropertyValueFactory<>("responseText"));

        queryTable.setItems(queryData);
        loadQueries();
    }

    private void loadQueries() {
        List<CustomerQuery> queries = queryDAO.findAll();
        queryData.setAll(queries);
    }

    @FXML
    private void handleSubmitQuery() {
        try {
            int customerId = Integer.parseInt(customerIdField.getText());
            int vehicleId = Integer.parseInt(vehicleIdField.getText());
            String queryText = queryTextArea.getText();

            if (queryText.isEmpty()) {
                showAlert("Error", "Please enter your query");
                return;
            }

            CustomerQuery query = new CustomerQuery();
            query.setCustomerId(customerId);
            query.setVehicleId(vehicleId);
            query.setQueryDate(LocalDateTime.now());
            query.setQueryText(queryText);
            query.setResponseText("Pending response...");

            queryDAO.create(query);
            loadQueries();
            clearFields();

            showAlert("Success", "Query submitted successfully!");

        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter valid Customer ID and Vehicle ID");
        } catch (Exception e) {
            showAlert("Error", "Error submitting query: " + e.getMessage());
        }
    }

    @FXML
    private void handleRefresh() {
        loadQueries();
    }

    private void clearFields() {
        customerIdField.clear();
        vehicleIdField.clear();
        queryTextArea.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}