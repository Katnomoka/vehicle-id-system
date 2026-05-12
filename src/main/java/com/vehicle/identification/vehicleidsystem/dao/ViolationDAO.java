package com.vehicle.identification.vehicleidsystem.dao;

import com.vehicle.identification.vehicleidsystem.model.Violation;
import com.vehicle.identification.vehicleidsystem.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ViolationDAO implements BaseDAO<Violation> {

    @Override
    public void create(Violation violation) {
        String sql = "INSERT INTO Violation(vehicle_id, violation_date, violation_type, fine_amount, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, violation.getVehicleId());
            pstmt.setObject(2, violation.getViolationDate());
            pstmt.setString(3, violation.getViolationType());
            pstmt.setDouble(4, violation.getFineAmount());
            pstmt.setString(5, violation.getStatus());

            pstmt.executeUpdate();
            System.out.println("✅ Violation created successfully.");

        } catch (SQLException e) {
            System.err.println("❌ Error creating violation: " + e.getMessage());
        }
    }

    @Override
    public List<Violation> findAll() {
        List<Violation> violations = new ArrayList<>();
        String sql = "SELECT * FROM Violation";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Violation violation = new Violation();
                violation.setId(rs.getInt("violation_id"));
                violation.setVehicleId(rs.getInt("vehicle_id"));
                violation.setViolationDate(rs.getObject("violation_date", LocalDate.class));
                violation.setViolationType(rs.getString("violation_type"));
                violation.setFineAmount(rs.getDouble("fine_amount"));
                violation.setStatus(rs.getString("status"));
                violations.add(violation);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error fetching violations: " + e.getMessage());
        }
        return violations;
    }

    @Override
    public Violation findById(int id) {
        String sql = "SELECT * FROM Violation WHERE violation_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Violation violation = new Violation();
                violation.setId(rs.getInt("violation_id"));
                violation.setVehicleId(rs.getInt("vehicle_id"));
                violation.setViolationDate(rs.getObject("violation_date", LocalDate.class));
                violation.setViolationType(rs.getString("violation_type"));
                violation.setFineAmount(rs.getDouble("fine_amount"));
                violation.setStatus(rs.getString("status"));
                return violation;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error finding violation: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean update(Violation violation) {
        String sql = "UPDATE Violation SET vehicle_id=?, violation_date=?, violation_type=?, fine_amount=?, status=? WHERE violation_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, violation.getVehicleId());
            pstmt.setObject(2, violation.getViolationDate());
            pstmt.setString(3, violation.getViolationType());
            pstmt.setDouble(4, violation.getFineAmount());
            pstmt.setString(5, violation.getStatus());
            pstmt.setInt(6, violation.getId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error updating violation: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM Violation WHERE violation_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error deleting violation: " + e.getMessage());
            return false;
        }
    }
}