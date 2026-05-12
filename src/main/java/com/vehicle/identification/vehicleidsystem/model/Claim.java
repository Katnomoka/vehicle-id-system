package com.vehicle.identification.vehicleidsystem.model;

import java.time.LocalDate;

public class Claim extends BaseEntity {
    private int policyId;
    private LocalDate claimDate;
    private double claimAmount;
    private String status;

    public Claim() {}

    public Claim(int policyId, double claimAmount) {
        this.policyId = policyId;
        this.claimDate = LocalDate.now();
        this.claimAmount = claimAmount;
        this.status = "Pending";
    }

    // Getters and Setters
    public int getPolicyId() {
        return policyId;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public LocalDate getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(LocalDate claimDate) {
        this.claimDate = claimDate;
    }

    public double getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(double claimAmount) {
        this.claimAmount = claimAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}