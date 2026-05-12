package com.vehicle.identification.vehicleidsystem.dao;

import com.vehicle.identification.vehicleidsystem.model.CustomerQuery;
import com.vehicle.identification.vehicleidsystem.util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CustomerQueryDAO implements BaseDAO<CustomerQuery> {

    @Override
    public void create(CustomerQuery query) {
        String sql = "INSERT INTO CustomerQuery(customer_id, vehicle_id, query_date, query_text, response_text) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, query.getCustomerId());
            pstmt.setInt(2, query.getVehicleId());
            pstmt.setObject(3, query.getQueryDate());
            pstmt.setString(4, query.getQueryText());
            pstmt.setString(5, query.getResponseText());

            pstmt.executeUpdate();
            System.out.println("✅ Customer query created successfully.");

        } catch (SQLException e) {
            System.err.println("❌ Error creating customer query: " + e.getMessage());
        }
    }

    @Override
    public List<CustomerQuery> findAll() {
        List<CustomerQuery> queries = new ArrayList<>();
        String sql = "SELECT * FROM CustomerQuery";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                CustomerQuery query = new CustomerQuery();
                query.setId(rs.getInt("query_id"));
                query.setCustomerId(rs.getInt("customer_id"));
                query.setVehicleId(rs.getInt("vehicle_id"));
                query.setQueryDate(rs.getObject("query_date", LocalDateTime.class));
                query.setQueryText(rs.getString("query_text"));
                query.setResponseText(rs.getString("response_text"));
                queries.add(query);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error fetching customer queries: " + e.getMessage());
        }
        return queries;
    }

    @Override
    public CustomerQuery findById(int id) {
        String sql = "SELECT * FROM CustomerQuery WHERE query_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                CustomerQuery query = new CustomerQuery();
                query.setId(rs.getInt("query_id"));
                query.setCustomerId(rs.getInt("customer_id"));
                query.setVehicleId(rs.getInt("vehicle_id"));
                query.setQueryDate(rs.getObject("query_date", LocalDateTime.class));
                query.setQueryText(rs.getString("query_text"));
                query.setResponseText(rs.getString("response_text"));
                return query;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error finding customer query: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean update(CustomerQuery query) {
        String sql = "UPDATE CustomerQuery SET customer_id=?, vehicle_id=?, query_date=?, query_text=?, response_text=? WHERE query_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, query.getCustomerId());
            pstmt.setInt(2, query.getVehicleId());
            pstmt.setObject(3, query.getQueryDate());
            pstmt.setString(4, query.getQueryText());
            pstmt.setString(5, query.getResponseText());
            pstmt.setInt(6, query.getId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error updating customer query: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM CustomerQuery WHERE query_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error deleting customer query: " + e.getMessage());
            return false;
        }
    }
}