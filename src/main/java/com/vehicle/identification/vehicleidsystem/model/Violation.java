package com.vehicle.identification.vehicleidsystem.model;

import java.time.LocalDate;

public class Violation extends BaseEntity {
    private int vehicleId;
    private LocalDate violationDate;
    private String violationType;
    private double fineAmount;
    private String status;

    public Violation() {}

    public Violation(int vehicleId, String violationType, double fineAmount, String status) {
        this.vehicleId = vehicleId;
        this.violationDate = LocalDate.now();
        this.violationType = violationType;
        this.fineAmount = fineAmount;
        this.status = status;
    }

    // Getters and Setters
    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public LocalDate getViolationDate() {
        return violationDate;
    }

    public void setViolationDate(LocalDate violationDate) {
        this.violationDate = violationDate;
    }

    public String getViolationType() {
        return violationType;
    }

    public void setViolationType(String violationType) {
        this.violationType = violationType;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}