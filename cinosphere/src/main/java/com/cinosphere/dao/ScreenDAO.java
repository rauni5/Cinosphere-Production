package com.cinosphere.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cinosphere.model.ScreenModel;
import com.cinosphere.utils.DBconfig;
/**
 * DAO class responsible for all screen-related database operations.
 */
public class ScreenDAO {
	/**
	 * Inserts a new screen record into the database.
	 *
	 * @param theatreId
	 * @param screenName
	 * @param screenType
	 * @param screenStatus
	 * @param capacity
	 * @param basePrice
	 * @return true if inserted successfully
	 * @throws Exception if database operation fails
	 */
	public boolean insert(int theatreId,String screenName, String screenType,String screenStatus,int capacity,double basePrice) throws Exception {
        String sql = "INSERT INTO screen (theatre_id, screen_name, screen_type, screen_status, total_capacity, base_price) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        Connection con = DBconfig.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt   (1, theatreId);
            ps.setString(2, screenName);
            ps.setString(3, screenType);
            ps.setString(4, screenStatus);
            ps.setInt   (5, capacity);
            ps.setBigDecimal(6,BigDecimal.valueOf(basePrice));
            return ps.executeUpdate() > 0;
    }
	/**
	 * Updates screen status.
	 *
	 * @param screenId
	 * @param screenStatus
	 * @return true if updated successfully
	 * @throws Exception if database operation fails
	 */
	public boolean updateScreenStatus(int screenId, String screenStatus) throws Exception {
		String sql = "UPDATE screen SET screen_status = ? WHERE screen_id = ?";
		Connection con = DBconfig.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString (1, screenStatus);
		ps.setInt (2,  screenId);
		return ps.executeUpdate() > 0;
	}
	/**
	 * Updates screen base price.
	 *
	 * @param screenId
	 * @param basePrice
	 * @return true if updated successfully
	 * @throws Exception if database operation fails
	 */
	public boolean updateScreenBasePrice(int screenId, double basePrice) throws Exception {
		String sql = "UPDATE screen SET base_price = ? WHERE screen_id = ?";
		Connection con = DBconfig.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setBigDecimal (1, BigDecimal.valueOf(basePrice));
		ps.setInt (2,  screenId);
		return ps.executeUpdate() > 0;
	}
	/**
	 * Retrieves all screens for a theatre.
	 *
	 * @param theatreId
	 * @return list of screen records
	 * @throws Exception if database operation fails
	 */

	public List<ScreenModel> findBytheatreId(int theatreId) throws Exception{
		List<ScreenModel> screens = new ArrayList<>();
		String sql = "SELECT * FROM screen WHERE theatre_id = ?";
        Connection con = DBconfig.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, theatreId);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
        	screens.add(createScreenModel(rs));
        }
        rs.close();
        ps.close();
        con.close();
        return  screens;
	}
	/**
	 * Retrieves all screen records from the database.
	 *
	 * @return list of screen records
	 * @throws Exception if database operation fails
	 */
	public List<ScreenModel> getAllScreen() throws Exception{
		List<ScreenModel> screens = new ArrayList<>();
		String sql = "SELECT * FROM screen";
        Connection con = DBconfig.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
        	screens.add(createScreenModel(rs));
        }
        rs.close();
        ps.close();
        con.close();
        return screens;
	}
	/**
	 * Retrieves screen details using screen ID.
	 *
	 * @param screenId
	 * @return screen record
	 * @throws Exception if database operation fails
	 */
	public ScreenModel findByScreenId(int screenId) throws Exception{
		ScreenModel screen = null;
		String sql = "SELECT * FROM screen WHERE screen_id = ?";
        Connection con = DBconfig.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, screenId);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
        	screen = createScreenModel(rs);
        }
        rs.close();
        ps.close();
        con.close();
        return  screen;
	}
	/**
	 * Creates a ScreenModel object from the result set.
	 *
	 * @param rs
	 * @return populated ScreenModel object
	 * @throws SQLException if result set processing fails
	 */
	public ScreenModel createScreenModel(ResultSet rs) throws SQLException{
		ScreenModel screen = new ScreenModel();
		screen.setScreenId(rs.getInt("screen_id"));
		screen.setTheatreId(rs.getInt("theatre_id"));
		screen.setScreenName(rs.getString("screen_name"));
		screen.setScreenType(rs.getString("screen_type"));
		screen.setScreenStatus(rs.getString("screen_status"));
		screen.setTotalCapacity(rs.getInt("total_capacity"));
		screen.setBasePrice(rs.getDouble("base_price"));
		return screen;
	}

}
