package com.vehicle.identification.vehicleidsystem.dao;

import com.vehicle.identification.vehicleidsystem.model.PoliceReport;
import com.vehicle.identification.vehicleidsystem.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PoliceReportDAO implements BaseDAO<PoliceReport> {

    @Override
    public void create(PoliceReport report) {
        String sql = "INSERT INTO PoliceReport(vehicle_id, report_date, report_type, description, officer_name) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, report.getVehicleId());
            pstmt.setObject(2, report.getReportDate());
            pstmt.setString(3, report.getReportType());
            pstmt.setString(4, report.getDescription());
            pstmt.setString(5, report.getOfficerName());

            pstmt.executeUpdate();
            System.out.println("✅ Police report created successfully.");

        } catch (SQLException e) {
            System.err.println("❌ Error creating police report: " + e.getMessage());
        }
    }

    @Override
    public List<PoliceReport> findAll() {
        List<PoliceReport> reports = new ArrayList<>();
        String sql = "SELECT * FROM PoliceReport";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                PoliceReport report = new PoliceReport();
                report.setId(rs.getInt("report_id"));
                report.setVehicleId(rs.getInt("vehicle_id"));
                report.setReportDate(rs.getObject("report_date", LocalDate.class));
                report.setReportType(rs.getString("report_type"));
                report.setDescription(rs.getString("description"));
                report.setOfficerName(rs.getString("officer_name"));
                reports.add(report);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error fetching police reports: " + e.getMessage());
        }
        return reports;
    }

    @Override
    public PoliceReport findById(int id) {
        String sql = "SELECT * FROM PoliceReport WHERE report_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                PoliceReport report = new PoliceReport();
                report.setId(rs.getInt("report_id"));
                report.setVehicleId(rs.getInt("vehicle_id"));
                report.setReportDate(rs.getObject("report_date", LocalDate.class));
                report.setReportType(rs.getString("report_type"));
                report.setDescription(rs.getString("description"));
                report.setOfficerName(rs.getString("officer_name"));
                return report;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error finding police report: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean update(PoliceReport report) {
        String sql = "UPDATE PoliceReport SET vehicle_id=?, report_date=?, report_type=?, description=?, officer_name=? WHERE report_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, report.getVehicleId());
            pstmt.setObject(2, report.getReportDate());
            pstmt.setString(3, report.getReportType());
            pstmt.setString(4, report.getDescription());
            pstmt.setString(5, report.getOfficerName());
            pstmt.setInt(6, report.getId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error updating police report: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM PoliceReport WHERE report_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error deleting police report: " + e.getMessage());
            return false;
        }
    }
}