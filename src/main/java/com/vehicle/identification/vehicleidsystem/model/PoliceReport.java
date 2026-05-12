package com.vehicle.identification.vehicleidsystem.model;

import java.time.LocalDate;

public class PoliceReport extends BaseEntity {
    private int vehicleId;
    private LocalDate reportDate;
    private String reportType;
    private String description;
    private String officerName;

    public PoliceReport() {}

    public PoliceReport(int vehicleId, String reportType, String description, String officerName) {
        this.vehicleId = vehicleId;
        this.reportDate = LocalDate.now();
        this.reportType = reportType;
        this.description = description;
        this.officerName = officerName;
    }

    // Getters and Setters
    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOfficerName() {
        return officerName;
    }

    public void setOfficerName(String officerName) {
        this.officerName = officerName;
    }
}