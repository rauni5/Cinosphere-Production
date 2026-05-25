package com.cinosphere.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cinosphere.model.BookingModel;
import com.cinosphere.utils.DBconfig;

/**
 * DAO class responsible for all booking-related database operations.
 */
public class BookingDAO {
/**
 * Inserts a new booking record into the database.
 * 
 * @param userId
 * @param bookingDate
 * @param bookingTime
 * @param bookingStatus
 * @param totalAmount
 * @param bookingChannel
 * @param loyaltyPointsEarned
 * @return true if inserted successfully
 * @throws Exception if database operation fails
 */
	public boolean insert(int userId, LocalDate bookingDate, LocalTime bookingTime,String bookingStatus, double totalAmount,String bookingChannel, int loyaltyPointsEarned) throws Exception {
		String sql = "INSERT INTO booking  (user_id, booking_date, booking_time, booking_status,total_amount, booking_channel, loyalty_points_earned) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		Connection con = DBconfig.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt       (1, userId);
		ps.setDate      (2, Date.valueOf(bookingDate));
		ps.setTime    (3, Time.valueOf(bookingTime)); 
		ps.setString    (4, bookingStatus);
		ps.setBigDecimal(5, BigDecimal.valueOf(totalAmount));
		ps.setString    (6, bookingChannel);
		ps.setInt       (7, loyaltyPointsEarned);
		return ps.executeUpdate()>0;
	}
	/**
	 * Inserts a new booking and returns the generated booking ID.
	 * 
	 * @param userId
	 * @param bookingDate
	 * @param bookingTime
	 * @param bookingStatus
	 * @param totalAmount
	 * @param bookingChannel
	 * @param loyaltyPointsEarned
	 * @return generated booking ID, or -1 if insertion fails
	 * @throws Exception if database operation fails
	 */
	public int insertAndGetId(int userId, LocalDate bookingDate, LocalTime bookingTime,String bookingStatus, double totalAmount,String bookingChannel, int loyaltyPointsEarned) throws Exception {
		String sql = "INSERT INTO booking  (user_id, booking_date, booking_time, booking_status,total_amount, booking_channel, loyalty_points_earned) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		Connection con = DBconfig.getConnection();
		PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ps.setInt       (1, userId);
		ps.setDate      (2, Date.valueOf(bookingDate));
		ps.setTime    (3, Time.valueOf(bookingTime)); 
		ps.setString    (4, bookingStatus);
		ps.setBigDecimal(5, BigDecimal.valueOf(totalAmount));
		ps.setString    (6, bookingChannel);
		ps.setInt       (7, loyaltyPointsEarned);
		int rows = ps.executeUpdate();
		if (rows == 0) { ps.close(); con.close(); return -1; }
		ResultSet keys = ps.getGeneratedKeys();
		int newId = keys.next() ? keys.getInt(1) : -1;
		keys.close(); ps.close(); con.close();
		return newId;
	}
	/**
	 * Retrieves all bookings for a specific user.
	 *
	 * @param userId
	 * @return list of bookings
	 * @throws Exception if database operation fails
	 */
	public List<BookingModel> findByUserId(int userId) throws Exception {
        List<BookingModel> bookings = new ArrayList<>();
		String sql = "SELECT * FROM booking WHERE user_id = ?";
        Connection con = DBconfig.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
        	bookings.add(createBookingModel(rs));
        }
        rs.close();
        ps.close();
        con.close();
        return bookings;
    }
	/**
	 * Retrieves bookings by booking status.
	 *
	 * @param bookingStatus
	 * @return list of bookings
	 * @throws Exception if database operation fails
	 */
	public List<BookingModel> findByBookingStatus(String bookingStatus) throws Exception {
        List<BookingModel> bookings = new ArrayList<>();
		String sql = "SELECT * FROM booking WHERE booking_status = ?";
        Connection con = DBconfig.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, bookingStatus);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
        	bookings.add(createBookingModel(rs));
        }
        rs.close();
        ps.close();
        con.close();
        return bookings;
    }
	/**
	 * Retrieves all bookings from the database.
	 *
	 * @return list of all bookings
	 * @throws Exception if database operation fails
	 */
	public List<BookingModel> getAllBookings() throws Exception {
        List<BookingModel> bookings = new ArrayList<>();
		String sql = "SELECT * FROM booking";
        Connection con = DBconfig.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
        	bookings.add(createBookingModel(rs));
        }
        rs.close();
        ps.close();
        con.close();
        return bookings;
    }
	/**
	 * Retrieves a booking using booking ID.
	 *
	 * @param bookingId
	 * @return booking object if found, otherwise null
	 * @throws Exception if database operation fails
	 */
	public BookingModel findByBookingId(int bookingId) throws Exception {
       BookingModel booking = null;
		String sql = "SELECT * FROM booking WHERE booking_id = ?";
        Connection con = DBconfig.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, bookingId);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
        	booking = createBookingModel(rs);
        }
        rs.close();
        ps.close();
        con.close();
        return booking;
    }
	/**
	 * Retrieves bookings for a user filtered by booking status.
	 *
	 * @param userId
	 * @param bookingStatus
	 * @return list of matching bookings
	 * @throws Exception if database operation fails
	 */
	public List<BookingModel> findByUserId(int userId, String bookingStatus) throws Exception {
        List<BookingModel> bookings = new ArrayList<>();
		String sql = "SELECT * FROM booking WHERE user_id = ? AND booking_status = ?";
        Connection con = DBconfig.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, userId);
        ps.setString(2, bookingStatus);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
        	bookings.add(createBookingModel(rs));
        }
        rs.close();
        ps.close();
        con.close();
        return bookings;
    }
	/**
	 * 
	 * @param userId
	 * @param bookingStatus
	 * @return
	 * @throws Exception
	 */
	public boolean updateBookingStatus(int bookingId, String bookingStatus)throws Exception {
		String sql = "UPDATE booking SET booking_status = ? WHERE booking_id = ?";
		Connection con = DBconfig.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString (1, bookingStatus);
		ps.setInt (2,  bookingId);
		return ps.executeUpdate() > 0;
	}
	/**
	 * Updates booking status.
	 *
	 * @param bookingId
	 * @param bookingStatus
	 * @return true if updated successfully
	 * @throws Exception if database operation fails
	 */
	public int getTotalBookings() throws Exception {
		int booking = 0;
		String sql = "SELECT COUNT(*) FROM booking";
        Connection con = DBconfig.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
        	booking = rs.getInt(1);
        }
        rs.close();
        ps.close();
        con.close();
        return booking;
	}
	/**
	 * Retrieves total number of bookings.
	 *
	 * @return total booking count
	 * @throws Exception if database operation fails
	 */
	public int findTotalBookingByUserId(int userId) throws Exception{
		int booking = 0;
		String sql = "SELECT COUNT(*) FROM booking WHERE user_id = ?";
        Connection con = DBconfig.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
        	booking = rs.getInt(1);
        }
        rs.close();
        ps.close();
        con.close();
        return booking;
	}
	/**
	 * Retrieves total bookings made by a specific user.
	 *
	 * @param userId
	 * @return total booking count
	 * @throws Exception if database operation fails
	 */
	public int findTotalUpcomingByUserId(int userId) throws Exception {
		int count = 0;
		String sql = "SELECT COUNT(DISTINCT b.booking_id) FROM booking b JOIN ticket t ON t.booking_id = b.booking_id "
			+ "JOIN showtime s ON s.showtime_id = t.showtime_id WHERE b.user_id = ? AND s.show_date >= CURDATE()";
		Connection con = DBconfig.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, userId);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			count = rs.getInt(1);
		}
		rs.close();
		ps.close();
		con.close();
		return count;
	}
	/**
	 * Retrieves total upcoming bookings for a user.
	 *
	 * @param userId
	 * @return upcoming booking count
	 * @throws Exception if database operation fails
	 */
	public BookingModel findLatestComingByUserId(int userId) throws Exception {
		BookingModel booking = null;
		String sql = "SELECT DISTINCT b.* FROM booking b JOIN ticket t ON t.booking_id = b.booking_id "
				+ "JOIN showtime s ON s.showtime_id = t.showtime_id WHERE b.user_id = ? AND s.show_date >= CURDATE() "
				+ "ORDER BY s.show_date ASC LIMIT 1";
			Connection con = DBconfig.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				booking= createBookingModel(rs);
			}
			rs.close();
			ps.close();
			con.close();
			return booking;
	}
	/**
	 * Retrieves upcoming bookings for a specific user.
	 *
	 * @param userId
	 * @return list of upcoming bookings
	 * @throws Exception if database operation fails
	 */
	public List<BookingModel> findUpcomingByUserId(int userId) throws Exception {
		List<BookingModel> bookings = new ArrayList<>();
		String sql = "SELECT DISTINCT b.* FROM booking b JOIN ticket t ON t.booking_id = b.booking_id "
			+ "JOIN showtime s ON s.showtime_id = t.showtime_id WHERE b.user_id = ? AND s.show_date >= CURDATE() "
			+ "ORDER BY s.show_date ASC";
		Connection con = DBconfig.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, userId);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			bookings.add(createBookingModel(rs));
		}
		rs.close();
		ps.close();
		con.close();
		return bookings;
	}
	/**
	 * Retrieves the latest booking made by a user.
	 *
	 * @param userId
	 * @return latest booking record
	 * @throws Exception if database operation fails
	 */
	public BookingModel findLatestConfirmedByUserId(int userId) throws Exception {
		BookingModel booking = null;
		String sql = "SELECT * FROM booking WHERE user_id=? ORDER BY booking_date DESC LIMIT 1;";
        Connection con = DBconfig.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
        	booking = createBookingModel(rs);
        }
        rs.close();
        ps.close();
        con.close();
        return booking;
	}
	/**
	 * Retrieves confirmed seat IDs for a specific showtime.
	 *
	 * @param showtimeId
	 * @return set of confirmed seat IDs
	 * @throws Exception if database operation fails
	 */
	public Set<Integer> getConfirmedSeatIdsByShowtime(int showtimeId) throws Exception {
		Set<Integer> seatIds = new HashSet<>();
		String sql = "SELECT t.seat_id FROM ticket t JOIN booking b ON b.booking_id = t.booking_id WHERE t.showtime_id = ? AND b.booking_status = 'confirmed'";
		Connection con = DBconfig.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, showtimeId);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) seatIds.add(rs.getInt("seat_id"));
		rs.close(); ps.close(); con.close();
		return seatIds;
	}
	/**
	 * Retrieves total bookings made by a user in the current month.
	 *
	 * @param userId
	 * @return total booking count for current month
	 * @throws Exception if database operation fails
	 */
	public int getCurrentMonthBookings(int userId) throws Exception {
	    int total = 0;
	    String sql = "SELECT COUNT(*) FROM booking WHERE MONTH(booking_date) = MONTH(CURDATE()) AND YEAR(booking_date) = YEAR(CURDATE()) AND user_id=?";
	    Connection con = DBconfig.getConnection();
	    PreparedStatement ps = con.prepareStatement(sql);
	    ps.setInt(1, userId);
	    ResultSet rs = ps.executeQuery();
	    if (rs.next()) {
	        total = rs.getInt(1);
	    }
	    rs.close();
	    ps.close();
	    con.close();
	    return total;
	}
	/**
	 * Retrieves total bookings made in the current month.
	 *
	 * @return total booking count for current month
	 * @throws Exception if database operation fails
	 */
	public int getCurrentMonthBookings() throws Exception {
	    int total = 0;
	    String sql = "SELECT COUNT(*) FROM booking WHERE MONTH(booking_date) = MONTH(CURDATE()) AND YEAR(booking_date) = YEAR(CURDATE())";
	    Connection con = DBconfig.getConnection();
	    PreparedStatement ps = con.prepareStatement(sql);
	    ResultSet rs = ps.executeQuery();
	    if (rs.next()) {
	        total = rs.getInt(1);
	    }
	    rs.close();
	    ps.close();
	    con.close();
	    return total;
	}
	/**
	 * Retrieves total revenue for a specific date.
	 *
	 * @param date
	 * @return total revenue amount
	 * @throws Exception if database operation fails
	 */
	public double getRevenue(LocalDate date) throws Exception {
	    double total = 0;
		String sql = " SELECT SUM(total_amount) FROM booking WHERE DATE(booking_date) = ?";
	    Connection con = DBconfig.getConnection();
	    PreparedStatement ps = con.prepareStatement(sql);
	    ps.setDate(1, Date.valueOf(date));
	    ResultSet rs = ps.executeQuery();
	    if (rs.next()) {
	       total = rs.getDouble(1);
	    }
	    rs.close();
	    ps.close();
	    con.close();
	    return total;
	}
	/**
	 * Retrieves total bookings for a specific date.
	 *
	 * @param date
	 * @return total booking count
	 * @throws Exception if database operation fails
	 */
	public int getBookings(LocalDate date) throws Exception {
	    int total = 0;
	    String sql = "SELECT COUNT(*) FROM booking WHERE booking_date = ?";
	    Connection con = DBconfig.getConnection();
	    PreparedStatement ps = con.prepareStatement(sql);
	    ps.setDate(1, Date.valueOf(date));
	    ResultSet rs = ps.executeQuery();
	    if (rs.next()) {
	        total = rs.getInt(1);
	    }
	    rs.close();
	    ps.close();
	    con.close();
	    return total;
	}
	/**
	 * Creates a BookingModel object from the result set.
	 *
	 * @param rs
	 * @return populated BookingModel object
	 * @throws SQLException if result set processing fails
	 */
	public BookingModel createBookingModel(ResultSet rs) throws SQLException {
		BookingModel booking = new BookingModel();
		booking.setBookingId(rs.getInt("booking_id"));
		booking.setUserId(rs.getInt("user_id"));
		booking.setBookingDate(rs.getDate("booking_date").toLocalDate());
		booking.setBookingTime(rs.getTime("booking_time").toLocalTime());
		booking.setBookingStatus(rs.getString("booking_status"));
		booking.setTotalAmount(rs.getDouble("total_amount"));
		booking.setBookingChannel(rs.getString("booking_channel"));
		booking.setLoyaltyPointsEarned(rs.getInt("Loyalty_points_earned"));
		return booking;
	}
	

}
