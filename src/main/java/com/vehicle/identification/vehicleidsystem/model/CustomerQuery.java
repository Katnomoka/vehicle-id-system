package com.vehicle.identification.vehicleidsystem.model;

import java.time.LocalDateTime;

public class CustomerQuery extends BaseEntity {
    private int customerId;
    private int vehicleId;
    private LocalDateTime queryDate;
    private String queryText;
    private String responseText;

    public CustomerQuery() {}

    public CustomerQuery(int customerId, int vehicleId, String queryText) {
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.queryDate = LocalDateTime.now();
        this.queryText = queryText;
        this.responseText = "Pending";
    }

    // Getters and Setters
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public LocalDateTime getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(LocalDateTime queryDate) {
        this.queryDate = queryDate;
    }

    public String getQueryText() {
        return queryText;
    }

    public void setQueryText(String queryText) {
        this.queryText = queryText;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }
}