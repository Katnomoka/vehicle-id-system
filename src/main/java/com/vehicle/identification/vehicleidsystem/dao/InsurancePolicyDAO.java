package com.vehicle.identification.vehicleidsystem.dao;

import com.vehicle.identification.vehicleidsystem.model.InsurancePolicy;
import com.vehicle.identification.vehicleidsystem.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InsurancePolicyDAO implements BaseDAO<InsurancePolicy> {

    @Override
    public void create(InsurancePolicy policy) {
        String sql = "INSERT INTO InsurancePolicy(vehicle_id, insurance_company, policy_number, start_date, end_date, coverage_details) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, policy.getVehicleId());
            pstmt.setString(2, policy.getInsuranceCompany());
            pstmt.setString(3, policy.getPolicyNumber());
            pstmt.setObject(4, policy.getStartDate());
            pstmt.setObject(5, policy.getEndDate());
            pstmt.setString(6, policy.getCoverageDetails());

            pstmt.executeUpdate();
            System.out.println("✅ Insurance policy created successfully.");

        } catch (SQLException e) {
            System.err.println("❌ Error creating insurance policy: " + e.getMessage());
        }
    }

    @Override
    public List<InsurancePolicy> findAll() {
        List<InsurancePolicy> policies = new ArrayList<>();
        String sql = "SELECT * FROM InsurancePolicy";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                InsurancePolicy policy = new InsurancePolicy();
                policy.setId(rs.getInt("policy_id"));
                policy.setVehicleId(rs.getInt("vehicle_id"));
                policy.setInsuranceCompany(rs.getString("insurance_company"));
                policy.setPolicyNumber(rs.getString("policy_number"));
                policy.setStartDate(rs.getObject("start_date", LocalDate.class));
                policy.setEndDate(rs.getObject("end_date", LocalDate.class));
                policy.setCoverageDetails(rs.getString("coverage_details"));
                policies.add(policy);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error fetching insurance policies: " + e.getMessage());
        }
        return policies;
    }

    @Override
    public InsurancePolicy findById(int id) {
        String sql = "SELECT * FROM InsurancePolicy WHERE policy_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                InsurancePolicy policy = new InsurancePolicy();
                policy.setId(rs.getInt("policy_id"));
                policy.setVehicleId(rs.getInt("vehicle_id"));
                policy.setInsuranceCompany(rs.getString("insurance_company"));
                policy.setPolicyNumber(rs.getString("policy_number"));
                policy.setStartDate(rs.getObject("start_date", LocalDate.class));
                policy.setEndDate(rs.getObject("end_date", LocalDate.class));
                policy.setCoverageDetails(rs.getString("coverage_details"));
                return policy;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error finding insurance policy: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean update(InsurancePolicy policy) {
        String sql = "UPDATE InsurancePolicy SET vehicle_id=?, insurance_company=?, policy_number=?, start_date=?, end_date=?, coverage_details=? WHERE policy_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, policy.getVehicleId());
            pstmt.setString(2, policy.getInsuranceCompany());
            pstmt.setString(3, policy.getPolicyNumber());
            pstmt.setObject(4, policy.getStartDate());
            pstmt.setObject(5, policy.getEndDate());
            pstmt.setString(6, policy.getCoverageDetails());
            pstmt.setInt(7, policy.getId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error updating insurance policy: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM InsurancePolicy WHERE policy_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error deleting insurance policy: " + e.getMessage());
            return false;
        }
    }
}