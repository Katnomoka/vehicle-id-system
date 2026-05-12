package com.vehicle.identification.vehicleidsystem.dao;
import java.time.LocalDate;
import com.vehicle.identification.vehicleidsystem.model.ServiceRecord;
import com.vehicle.identification.vehicleidsystem.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceRecordDAO implements BaseDAO<ServiceRecord> {

    @Override
    public void create(ServiceRecord serviceRecord) {
        String sql = "INSERT INTO ServiceRecord(vehicle_id, service_date, service_type, description, cost) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, serviceRecord.getVehicleId());
            pstmt.setObject(2, serviceRecord.getServiceDate());
            pstmt.setString(3, serviceRecord.getServiceType());
            pstmt.setString(4, serviceRecord.getDescription());
            pstmt.setDouble(5, serviceRecord.getCost());

            pstmt.executeUpdate();
            System.out.println("✅ Service record created successfully.");

        } catch (SQLException e) {
            System.err.println("❌ Error creating service record: " + e.getMessage());
        }
    }

    @Override
    public List<ServiceRecord> findAll() {
        List<ServiceRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM ServiceRecord";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ServiceRecord record = new ServiceRecord();
                record.setId(rs.getInt("service_id"));
                record.setVehicleId(rs.getInt("vehicle_id"));
                record.setServiceDate(rs.getObject("service_date", LocalDate.class));
                record.setServiceType(rs.getString("service_type"));
                record.setDescription(rs.getString("description"));
                record.setCost(rs.getDouble("cost"));
                records.add(record);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error fetching service records: " + e.getMessage());
        }
        return records;
    }

    @Override
    public ServiceRecord findById(int id) {
        String sql = "SELECT * FROM ServiceRecord WHERE service_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                ServiceRecord record = new ServiceRecord();
                record.setId(rs.getInt("service_id"));
                record.setVehicleId(rs.getInt("vehicle_id"));
                record.setServiceDate(rs.getObject("service_date", LocalDate.class));
                record.setServiceType(rs.getString("service_type"));
                record.setDescription(rs.getString("description"));
                record.setCost(rs.getDouble("cost"));
                return record;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error finding service record: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean update(ServiceRecord serviceRecord) {
        String sql = "UPDATE ServiceRecord SET vehicle_id=?, service_date=?, service_type=?, description=?, cost=? WHERE service_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, serviceRecord.getVehicleId());
            pstmt.setObject(2, serviceRecord.getServiceDate());
            pstmt.setString(3, serviceRecord.getServiceType());
            pstmt.setString(4, serviceRecord.getDescription());
            pstmt.setDouble(5, serviceRecord.getCost());
            pstmt.setInt(6, serviceRecord.getId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error updating service record: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM ServiceRecord WHERE service_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error deleting service record: " + e.getMessage());
            return false;
        }
    }
}