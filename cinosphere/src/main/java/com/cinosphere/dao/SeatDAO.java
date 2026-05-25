package com.cinosphere.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cinosphere.model.SeatModel;
import com.cinosphere.utils.DBconfig;
/**
 * DAO class responsible for all seat-related database operations.
 */
public class SeatDAO {
	/**
	 * Inserts a new seat record into the database.
	 *
	 * @param screenId
	 * @param seatNumber
	 * @param rowNumber
	 * @param seatType
	 * @param seatStatus
	 * @return true if inserted successfully
	 * @throws Exception if database operation fails
	 */
	public boolean insert(int screenId, int seatNumber, String rowNumber,String seatType, String seatStatus) throws Exception {
			String sql = "INSERT INTO seat (screen_id, seat_number, row_number, seat_type, seat_status) "
			     + "VALUES (?, ?, ?, ?, ?)";
			Connection con = DBconfig.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt   (1, screenId);
			ps.setInt(2, seatNumber);
			ps.setString(3, rowNumber);
			ps.setString(4, seatType);
			ps.setString(5, seatStatus);
			return ps.executeUpdate() > 0;
		}
	
	/**
	 * Retrieves all seats for a screen.
	 *
	 * @param screenId
	 * @return list of seat records
	 * @throws Exception if database operation fails
	 */
	public List<SeatModel> findByScreenId(int screenId) throws Exception{
		List<SeatModel> seats = new ArrayList<>();
		String sql = "SELECT * FROM seat WHERE screen_id = ?";
        Connection con = DBconfig.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, screenId);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
        	seats.add(createSeatModel(rs));
        }
        rs.close();
        ps.close();
        con.close();
		return seats;
	}
	/**
	 * Retrieves seat details using seat ID.
	 *
	 * @param seatId
	 * @return seat record
	 * @throws Exception if database operation fails
	 */
	public SeatModel findBySeatId(int seatId) throws Exception{
		SeatModel seat = null;
		String sql = "SELECT * FROM seat WHERE seat_id = ?";
        Connection con = DBconfig.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, seatId);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
        	seat=createSeatModel(rs);
        }
        rs.close();
        ps.close();
        con.close();
		return seat;
	}
	/**
	 * Updates seat status.
	 *
	 * @param seatId
	 * @param seatStatus
	 * @return true if updated successfully
	 * @throws Exception if database operation fails
	 */
	public boolean updateSeatStatus(int seatId, String seatStatus) throws Exception {
		String sql = "UPDATE seat SET seat_status=? WHERE seat_id=?";
		Connection con = DBconfig.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, seatStatus);
		ps.setInt   (2, seatId);
		return ps.executeUpdate() > 0;
	}
	
	/**
	 * Creates a SeatModel object from the result set.
	 *
	 * @param rs
	 * @return populated SeatModel object
	 * @throws SQLException if result set processing fails
	 */
	public SeatModel createSeatModel(ResultSet rs) throws SQLException {
		SeatModel seat = new SeatModel();
		seat.setSeatId(rs.getInt("seat_id"));
		seat.setScreenId(rs.getInt("screen_id"));
		seat.setRowNumber(rs.getString("row_number"));
		seat.setSeatNumber(rs.getInt("seat_number"));
		seat.setSeatType(rs.getString("seat_type"));
		seat.setSeatStatus(rs.getString("seat_status"));
		return seat;
	}

}
