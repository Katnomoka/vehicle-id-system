package com.vehicle.identification.vehicleidsystem.dao;

import com.vehicle.identification.vehicleidsystem.model.Claim;
import com.vehicle.identification.vehicleidsystem.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for managing Insurance Claims.
 * Implements BaseDAO for Polymorphism requirements.
 */
public class ClaimDAO implements BaseDAO<Claim> {

    // 1. Create Claim
    @Override
    public void create(Claim claim) {
        String sql = "INSERT INTO Claim(policy_id, claim_date, claim_amount, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, claim.getPolicyId());
            pstmt.setObject(2, claim.getClaimDate());
            pstmt.setDouble(3, claim.getClaimAmount());
            pstmt.setString(4, claim.getStatus());

            pstmt.executeUpdate();
            System.out.println("✅ Claim created successfully.");

        } catch (SQLException e) {
            System.err.println("❌ Error creating claim: " + e.getMessage());
        }
    }

    // 2. Find All Claims
    @Override
    public List<Claim> findAll() {
        List<Claim> claims = new ArrayList<>();
        String sql = "SELECT * FROM Claim";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Claim claim = new Claim();
                claim.setId(rs.getInt("claim_id"));
                claim.setPolicyId(rs.getInt("policy_id"));
                claim.setClaimDate(rs.getObject("claim_date", LocalDate.class));
                claim.setClaimAmount(rs.getDouble("claim_amount"));
                claim.setStatus(rs.getString("status"));
                claims.add(claim);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error fetching claims: " + e.getMessage());
        }
        return claims;
    }

    // 3. Find By ID
    @Override
    public Claim findById(int id) {
        String sql = "SELECT * FROM Claim WHERE claim_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Claim claim = new Claim();
                claim.setId(rs.getInt("claim_id"));
                claim.setPolicyId(rs.getInt("policy_id"));
                claim.setClaimDate(rs.getObject("claim_date", LocalDate.class));
                claim.setClaimAmount(rs.getDouble("claim_amount"));
                claim.setStatus(rs.getString("status"));
                return claim;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error finding claim: " + e.getMessage());
        }
        return null;
    }

    // 4. Update Claim
    @Override
    public boolean update(Claim claim) {
        String sql = "UPDATE Claim SET policy_id=?, claim_date=?, claim_amount=?, status=? WHERE claim_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, claim.getPolicyId());
            pstmt.setObject(2, claim.getClaimDate());
            pstmt.setDouble(3, claim.getClaimAmount());
            pstmt.setString(4, claim.getStatus());
            pstmt.setInt(5, claim.getId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error updating claim: " + e.getMessage());
            return false;
        }
    }

    // 5. Delete Claim
    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM Claim WHERE claim_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error deleting claim: " + e.getMessage());
            return false;
        }
    }
}