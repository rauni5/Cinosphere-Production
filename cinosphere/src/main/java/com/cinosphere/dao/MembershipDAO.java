package com.cinosphere.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cinosphere.model.MembershipModel;
import com.cinosphere.utils.DBconfig;
/**
 * DAO class responsible for all membership-related database operations.
 */
public class MembershipDAO {
	/**
	 * Inserts a new membership record into the database.
	 *
	 * @param userId
	 * @param membershipType
	 * @param membershipStatus
	 * @param totalLoyaltyPoints
	 * @param discountPercent
	 * @return true if inserted successfully
	 * @throws SQLException if database operation fails
	 */
	public boolean insert(int userId,String membershipType, String membershipStatus, int totalLoyaltyPoints, double discountPercent) throws SQLException {
        String sql = "INSERT INTO membership (user_id, membership_type, membership_status, "
                   + "total_loyalty_points, discount_percentage) VALUES (?, ?, ?, ?, ?)";
        Connection con = DBconfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt        (1, userId);
            ps.setString     (2, membershipType);
            ps.setString     (3, membershipStatus);
            ps.setInt        (4, totalLoyaltyPoints);
            ps.setDouble (5, discountPercent);
            return ps.executeUpdate() > 0;
        }
	/**
	 * Retrieves membership details for a specific user.
	 *
	 * @param userId
	 * @return membership record
	 * @throws Exception if database operation fails
	 */
	public MembershipModel findByUserId(int userId) throws Exception {
        MembershipModel membership = null;
		String sql = "SELECT * FROM membership WHERE user_id = ?";
        Connection con = DBconfig.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
        	membership = createMembershipModel(rs);
        }
        rs.close();
        ps.close();
        con.close();
        return membership;
    }
	/**
	 * Updates membership type for a user.
	 *
	 * @param userId
	 * @param membershipType
	 * @return true if updated successfully
	 * @throws Exception if database operation fails
	 */
	public boolean updateMembershipType(int userId, String membershipType)throws Exception {
		String sql = "UPDATE membership SET membership_type = ? WHERE user_id = ?";
		Connection con = DBconfig.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString (1, membershipType);
		ps.setInt (2,  userId);
		return ps.executeUpdate() > 0;
	}
	/**
	 * Updates loyalty points for a user's membership.
	 *
	 * @param userId
	 * @param loyaltyPoints
	 * @return true if updated successfully
	 * @throws Exception if database operation fails
	 */
	public boolean updateMembershipLoyaltyPoints(int userId, int loyaltyPoints) throws Exception {
		String sql = "UPDATE membership SET total_loyalty_points = ? WHERE user_id = ?";
		Connection con = DBconfig.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt (1, loyaltyPoints);
		ps.setInt (2,  userId);
		return ps.executeUpdate() > 0;
	}
	/**
	 * Updates membership discount percentage for a user.
	 *
	 * @param userId
	 * @param discountPercent
	 * @return true if updated successfully
	 * @throws Exception if database operation fails
	 */
	public boolean updateMembershipDiscount(int userId, int discountPercent) throws Exception {
		String sql = "UPDATE membership SET discount_percentage = ? WHERE user_id = ?";
		Connection con = DBconfig.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt (1, discountPercent);
		ps.setInt (2,  userId);
		return ps.executeUpdate() > 0;
	}
	/**
	 * Updates membership status for a user.
	 *
	 * @param userId
	 * @param membershipStatus
	 * @return true if updated successfully
	 * @throws Exception if database operation fails
	 */
	public boolean updateMembershipStatus(int userId, String membershipStatus) throws Exception {
		String sql = "UPDATE membership SET membership_status = ? WHERE user_id = ?";
		Connection con = DBconfig.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString (1, membershipStatus);
		ps.setInt (2,  userId);
		return ps.executeUpdate() > 0;
	}
	
	
	/**
	 * Creates a MembershipModel object from the result set.
	 *
	 * @param rs
	 * @return populated MembershipModel object
	 * @throws SQLException if result set processing fails
	 */
	
	public MembershipModel createMembershipModel(ResultSet rs) throws SQLException {
		MembershipModel membership = new MembershipModel();
		membership.setMembershipId(rs.getInt("membership_id"));
		membership.setUserId(rs.getInt("user_id"));
		membership.setMembershipType(rs.getString("membership_type"));
		membership.setMembershipStatus(rs.getString("membership_status"));
		membership.setTotalLoyaltyPoints(rs.getInt("total_loyalty_points"));
		membership.setDiscountPercentage(rs.getDouble("discount_percentage"));
		return membership;
	}
    }
