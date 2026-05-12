package com.vehicle.identification.vehicleidsystem.dao;

import com.vehicle.identification.vehicleidsystem.model.Vehicle;
import com.vehicle.identification.vehicleidsystem.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO implements BaseDAO<Vehicle> {

    // 1. Create Vehicle
    @Override
    public void create(Vehicle vehicle) {
        String sql = "INSERT INTO Vehicle(registration_number, make, model, year, owner_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, vehicle.getRegistrationNumber());
            pstmt.setString(2, vehicle.getMake());
            pstmt.setString(3, vehicle.getModel());
            pstmt.setInt(4, vehicle.getYear());
            pstmt.setInt(5, vehicle.getOwnerId());

            pstmt.executeUpdate();
            System.out.println("✅ Vehicle created successfully.");

        } catch (SQLException e) {
            System.err.println("❌ Error creating vehicle: " + e.getMessage());
        }
    }

    // 2. Find All Vehicles
    @Override
    public List<Vehicle> findAll() {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM Vehicle";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Vehicle v = new Vehicle();
                v.setId(rs.getInt("vehicle_id"));
                v.setRegistrationNumber(rs.getString("registration_number"));
                v.setMake(rs.getString("make"));
                v.setModel(rs.getString("model"));
                v.setYear(rs.getInt("year"));
                v.setOwnerId(rs.getInt("owner_id"));
                vehicles.add(v);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error fetching vehicles: " + e.getMessage());
        }
        return vehicles;
    }

    // 3. Find By ID
    @Override
    public Vehicle findById(int id) {
        String sql = "SELECT * FROM Vehicle WHERE vehicle_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Vehicle v = new Vehicle();
                v.setId(rs.getInt("vehicle_id"));
                v.setRegistrationNumber(rs.getString("registration_number"));
                v.setMake(rs.getString("make"));
                v.setModel(rs.getString("model"));
                v.setYear(rs.getInt("year"));
                v.setOwnerId(rs.getInt("owner_id"));
                return v;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error finding vehicle: " + e.getMessage());
        }
        return null;
    }

    // 4. Update Vehicle
    @Override
    public boolean update(Vehicle vehicle) {
        String sql = "UPDATE Vehicle SET registration_number=?, make=?, model=?, year=?, owner_id=? WHERE vehicle_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, vehicle.getRegistrationNumber());
            pstmt.setString(2, vehicle.getMake());
            pstmt.setString(3, vehicle.getModel());
            pstmt.setInt(4, vehicle.getYear());
            pstmt.setInt(5, vehicle.getOwnerId());
            pstmt.setInt(6, vehicle.getId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error updating vehicle: " + e.getMessage());
            return false;
        }
    }

    // 5. Delete Vehicle
    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM Vehicle WHERE vehicle_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error deleting vehicle: " + e.getMessage());
            return false;
        }
    }
}