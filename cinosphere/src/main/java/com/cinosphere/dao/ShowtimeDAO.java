package com.cinosphere.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


import com.cinosphere.model.ShowtimeModel;
import com.cinosphere.utils.DBconfig;

/**
 * DAO class responsible for all showtime-related database operations.
 */
public class ShowtimeDAO {
	/**
	 * Inserts a new showtime record into the database.
	 *
	 * @param screenId
	 * @param movieId
	 * @param showDate
	 * @param startTime
	 * @param endTime
	 * @param showStatus
	 * @param showType
	 * @return true if inserted successfully
	 * @throws Exception if database operation fails
	 */
	public boolean insert(int screenId, int movieId, LocalDate showDate, LocalTime startTime, LocalTime endTime, String showStatus, String showType) throws Exception {
		String sql = "INSERT INTO showtime (screen_id,movie_id,show_date,start_time,end_time,show_status,show_type)"
				+"VALUES (?,?,?,?,?,?,?)";
		Connection con = DBconfig.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setInt(1, screenId);
		ps.setInt(2, movieId);
		ps.setDate(3, Date.valueOf(showDate));
		ps.setTime(4, Time.valueOf(startTime));
		ps.setTime(5, Time.valueOf(endTime));
		ps.setString(6, showStatus);
		ps.setString(7, showType);
		
		return ps.executeUpdate() > 0;		
	}
	/**
	 * Retrieves all active showtime records.
	 *
	 * @return list of active showtime records
	 * @throws Exception if database operation fails
	 */
	public List<ShowtimeModel> findAllActive() throws Exception {
		List<ShowtimeModel> shows = new ArrayList<>();
		String sql = "SELECT * FROM showtime WHERE show_status = 'ACTIVE' ORDER BY show_date, movie_id, start_time";
		Connection con = DBconfig.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			shows.add(createShowtimeModel(rs));
		}
		rs.close();
		ps.close();
		con.close();
		return shows;
	}
	
	
	/**
	 * Retrieves showtimes using movie ID.
	 *
	 * @param movieId
	 * @return list of showtime records
	 * @throws Exception if database operation fails
	 */
	public List<ShowtimeModel> findByMovieId(int movieId) throws Exception{
		List<ShowtimeModel> shows = new ArrayList<>();
		Connection con = DBconfig.getConnection();
		
		String sql = "SELECT * FROM showtime WHERE movie_id = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, movieId);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			shows.add(createShowtimeModel(rs));
		}
		rs.close();
		ps.close();
		return shows;
	}
	/**
	 * Retrieves showtimes using screen ID.
	 *
	 * @param screenId
	 * @return list of showtime records
	 * @throws Exception if database operation fails
	 */
	public List<ShowtimeModel> findByScreenId(int screenId) throws Exception{
		List<ShowtimeModel> shows = new ArrayList<>();
		Connection con = DBconfig.getConnection();
		
		String sql = "SELECT * FROM showtime WHERE screen_id = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, screenId);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			shows.add(createShowtimeModel(rs));
		}
		rs.close();
		ps.close();
		return shows;
	}
	/**
	 * Retrieves showtimes using show date.
	 *
	 * @param showDate
	 * @return list of showtime records
	 * @throws Exception if database operation fails
	 */
	public List<ShowtimeModel> findByDate(LocalDate showDate) throws Exception {
		List<ShowtimeModel> shows = new ArrayList<>();
		Connection con = DBconfig.getConnection();
		
		String sql = "SELECT * FROM showtime WHERE show_date = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setDate(1, Date.valueOf(showDate));
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			shows.add(createShowtimeModel(rs));
		}
		rs.close();
		ps.close();
		return shows;
	}
	
	/**
	 * Retrieves showtime details using showtime ID.
	 *
	 * @param showtimeId
	 * @return showtime record
	 * @throws Exception if database operation fails
	 */
	public ShowtimeModel findByShowtimeId(int showtimeId) throws Exception{
		ShowtimeModel show = null;
		Connection con = DBconfig.getConnection();
		
		String sql = "SELECT * FROM showtime WHERE showtime_id = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, showtimeId);
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			show = createShowtimeModel(rs);
		}
		rs.close();
		ps.close();
		return show;
	}
	/**
	 * Updates showtime status.
	 *
	 * @param showtimeId
	 * @param showtimeStatus
	 * @return true if updated successfully
	 * @throws Exception if database operation fails
	 */
	public boolean updateShowtimeStatus(int showtimeId,String showtimeStatus) throws Exception{
		Connection con = DBconfig.getConnection();
		
		String sql = "UPDATE showtime SET showtime_status=? WHERE showtime_id=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, showtimeStatus);
		ps.setInt(2, showtimeId);
		return ps.executeUpdate()>0;
	}
	/**
	 * Updates showtime type.
	 *
	 * @param showtimeId
	 * @param showtimeType
	 * @return true if updated successfully
	 * @throws Exception if database operation fails
	 */
	public boolean updateShowtimeType(int showtimeId,String showtimeType) throws Exception{
		Connection con = DBconfig.getConnection();
		
		String sql = "UPDATE showtime SET showtime_type=? WHERE showtime_id=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, showtimeType);
		ps.setInt(2, showtimeId);
		return ps.executeUpdate()>0;
	}
	/**
	 * Deletes all showtimes for a movie.
	 *
	 * @param movieId
	 * @return true if deleted successfully
	 * @throws Exception if database operation fails
	 */
	public boolean deleteByMovieId(int movieId) throws Exception {
	    String sql = "DELETE FROM showtime WHERE movie_id = ?";
	    Connection con = DBconfig.getConnection();
	    PreparedStatement ps = con.prepareStatement(sql);
	    ps.setInt(1, movieId);
	    return ps.executeUpdate() > 0;
	}
	
	
	/**
	 * Creates a ShowtimeModel object from the result set.
	 *
	 * @param rs
	 * @return populated ShowtimeModel object
	 * @throws SQLException if result set processing fails
	 */
	public ShowtimeModel createShowtimeModel(ResultSet rs) throws SQLException {
		ShowtimeModel show = new ShowtimeModel();
		show.setShowtimeId(rs.getInt("showtime_id"));
		show.setScreenId(rs.getInt("screen_id"));
		show.setMovieId(rs.getInt("movie_id"));
		show.setShowDate(rs.getDate("show_date").toLocalDate());
		show.setStartTime(rs.getTime("start_time").toLocalTime());
		show.setEndTime(rs.getTime("end_time").toLocalTime());
		show.setShowStatus(rs.getString("show_status"));
		show.setShowType(rs.getString("show_type"));
		return show;
	}


	
}
