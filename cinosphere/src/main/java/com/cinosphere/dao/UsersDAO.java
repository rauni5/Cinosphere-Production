package com.cinosphere.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.cinosphere.model.UsersModel;
import com.cinosphere.utils.DBconfig;

/**
 * DAO class responsible for all user-related database operations.
 */
public class UsersDAO {
	/**
	 * Inserts a new user record into the database.
	 *
	 * @param firstName
	 * @param lastName
	 * @param username
	 * @param email
	 * @param dateOfBirth
	 * @param gender
	 * @param hashPassword
	 * @param userRole
	 * @return true if inserted successfully
	 * @throws Exception if database operation fails
	 */
	public boolean insert(String firstName, String lastName, String username, String email,LocalDate dateOfBirth, String gender,String hashPassword,String userRole) throws Exception {
		String sql = "INSERT INTO users (first_name, last_name, username, email, date_of_birth, gender, hash_password, registration_date, is_active, user_role) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		Connection con = DBconfig.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);

			ps.setString (1,  firstName);
			ps.setString (2,  lastName);
			ps.setString (3,  username);
			ps.setString (4,  email);
			ps.setDate   (5,  Date.valueOf(dateOfBirth));
			ps.setString (6,  gender);
			ps.setString (7,  hashPassword);
			ps.setDate   (8,  Date.valueOf(LocalDate.now())); 
			ps.setBoolean(9, false);                          
			ps.setString (10, userRole);

			return ps.executeUpdate() > 0;
}
	/**
	 * Retrieves user details using username.
	 *
	 * @param username
	 * @return user record
	 * @throws Exception if database operation fails
	 */
	public UsersModel findByUsername(String username) throws Exception {
		UsersModel user = null;
        String sql = "SELECT * FROM users WHERE username = ?";
        Connection con = DBconfig.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
                if(rs.next()) {
                	user = createUserModel(rs);
                }
            rs.close();
            ps.close();
            con.close();
            return user;
        }
	
	/**
	 * Retrieves users matching username pattern.
	 *
	 * @param username
	 * @return list of user records
	 * @throws Exception if database operation fails
	 */
	public List<UsersModel> findByUsernames(String username) throws Exception {
		List<UsersModel> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE username LIKE ?";
        Connection con = DBconfig.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1,"%"+ username+"%");
        ResultSet rs = ps.executeQuery();
                while(rs.next()) {
                	users.add(createUserModel(rs));
                }
            rs.close();
            ps.close();
            con.close();
            return users;
	}

	
	/**
	 * Retrieves user details using email.
	 *
	 * @param email
	 * @return user record
	 * @throws SQLException if database operation fails
	 */
	public UsersModel findByEmail(String email) throws SQLException {
		UsersModel user = null;
        String sql = "SELECT * FROM users WHERE email = ?";
        Connection con = DBconfig.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
                if(rs.next()) {
                	user = createUserModel(rs);
                }
            rs.close();
            ps.close();
            con.close();
            return user;
        }
	/**
	 * Retrieves user details using user ID.
	 *
	 * @param userId
	 * @return user record
	 * @throws SQLException if database operation fails
	 */
	public UsersModel findByUserId(int userId) throws SQLException {
		UsersModel user = null;
        String sql = "SELECT * FROM users WHERE user_id = ?";
        Connection con = DBconfig.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
                if(rs.next()) {
                	user = createUserModel(rs);
                }
            rs.close();
            ps.close();
            con.close();
            return user;
        }
	/**
	 * Retrieves all user records from the database.
	 *
	 * @return list of user records
	 * @throws Exception if database operation fails
	 */
	public List<UsersModel> getAllUser() throws Exception {
		
	    List<UsersModel> users = new ArrayList<>();
	    Connection con = DBconfig.getConnection();
	    
	    String sql = "SELECT * FROM users";
	    PreparedStatement ps = con.prepareStatement(sql);
	    ResultSet rs = ps.executeQuery();
	
	    while (rs.next()) {
	        users.add(createUserModel(rs));
	    }
	    
	    rs.close();
	    ps.close();
	    con.close();
	    return users;
	}
	/**
	 * Retrieves users based on account status.
	 *
	 * @param isActive
	 * @return list of user records
	 * @throws Exception if database operation fails
	 */
	public List<UsersModel> getUserByStatus(boolean isActive) throws Exception{
		List<UsersModel> users = new ArrayList<>();
	    Connection con = DBconfig.getConnection();
	    
	    String sql = "SELECT * FROM users WHERE is_active=?";
	    PreparedStatement ps = con.prepareStatement(sql);
	    ps.setBoolean(1, isActive);
	    ResultSet rs = ps.executeQuery();
	
	    while (rs.next()) {
	        users.add(createUserModel(rs));
	    }
	    
	    rs.close();
	    ps.close();
	    con.close();
	    return users;
	}
	/**
	 * Soft deletes a user account by setting is_active to false.
	 *
	 * @param userId
	 * @return true if updated successfully
	 * @throws Exception if database operation fails
	 */
	public boolean deleteUser(int userId) throws Exception{
        String sql = "UPDATE users SET is_active = ? WHERE user_id = ? ";
        Connection con = DBconfig.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setBoolean(1,false);
        ps.setInt(2, userId);
        
        return ps.executeUpdate() > 0;
	}
	/**
	 * Activates a user account by setting is_active to true.
	 *
	 * @param userId
	 * @return true if updated successfully
	 * @throws Exception if database operation fails
	 */
	public boolean ActivateUser(int userId) throws Exception{
        String sql = "UPDATE users SET is_active = ? WHERE user_id = ? ";
        Connection con = DBconfig.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setBoolean(1,true);
        ps.setInt(2,userId);
        
        return ps.executeUpdate() > 0;
	}
	
	/**
	 * Updates user profile details.
	 *
	 * @param userId
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param dateOfBirth
	 * @return true if updated successfully
	 * @throws Exception if database operation fails
	 */
	public boolean UpdateUser(int userId,String firstName, String lastName, String email,LocalDate dateOfBirth) throws Exception {
		
		String sql = "UPDATE users SET first_name =?, last_name=?, email=?, date_of_birth=? WHERE user_id = ? ";

		Connection con = DBconfig.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString (1,  firstName);
		ps.setString (2,  lastName);
		ps.setString (3,  email);
		ps.setDate (4,  Date.valueOf(dateOfBirth));
		ps.setInt(5, userId);
		return ps.executeUpdate() > 0;
	}
	/**
	 * Updates user password.
	 *
	 * @param userId
	 * @param password
	 * @return true if updated successfully
	 * @throws Exception if database operation fails
	 */
	public boolean UpdateUserPassword(int userId,String password) throws Exception {
		
		String sql = "UPDATE users SET hash_password =? WHERE user_id = ? ";

		Connection con = DBconfig.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString (1,  password);
		ps.setInt(2, userId);
		return ps.executeUpdate() > 0;
	}
	/**
	 * Retrieves total number of users registered on a specific date.
	 *
	 * @param date
	 * @return total new users count
	 * @throws Exception if database operation fails
	 */
	public int getNewUsers(LocalDate date) throws Exception{
		int total = 0;
	    String query = " SELECT COUNT(*) FROM users WHERE DATE(registration_date) = ?";
	    Connection con = DBconfig.getConnection();
	    PreparedStatement ps = con.prepareStatement(query);
	    ps.setDate(1, Date.valueOf(date));
	    ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	          total = rs.getInt(1);
	        }
	    return total;
	}
	
	/**
	 * Creates a UsersModel object from the result set.
	 *
	 * @param rs
	 * @return populated UsersModel object
	 * @throws SQLException if result set processing fails
	 */
	public UsersModel createUserModel(ResultSet rs) throws SQLException {
		UsersModel user = new UsersModel();
		user.setUserId(rs.getInt("user_id"));
		user.setFirstName(rs.getString("first_name"));
		user.setLastName(rs.getString("last_name"));
		user.setUsername(rs.getString("username"));
		user.setEmail(rs.getString("email"));
		user.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
		user.setGender(rs.getString("gender"));
		user.setHashPassword(rs.getString("hash_password"));
		user.setRegistrationDate(rs.getDate("registration_date").toLocalDate());
		user.setisActive(rs.getBoolean("is_active"));
		user.setUserRole(rs.getString("user_role"));
		return user;
	}

}
