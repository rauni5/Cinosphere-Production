package com.cinosphere.service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.cinosphere.dao.BookingDAO;
import com.cinosphere.model.BookingModel;
import com.cinosphere.model.MembershipModel;
import com.cinosphere.model.UsersModel;
import com.cinosphere.utils.SessionUtil;

import jakarta.servlet.http.HttpServletRequest;
/**
 * Service Class that is the bridge between Servlet and BookingDAO
 * Contains methods used to call methods of DAO and perform interaction with DB Booking Table
 * 
 * @author Raunit Giri
 */
public class BookingService {
	private BookingDAO bookingDAO = new BookingDAO();
	/**
	 * Finds bookings of a specific user
	 * @param userId
	 * @return Bookings list
	 * @throws Exception
	 */
	public List<BookingModel> getBookingsByUserId(int userId) throws Exception {
		return bookingDAO.findByUserId(userId);

	}
	/**
	 * Finds bookings of a specific user with specific booking status
	 * @param userId
	 * @param status
	 * @return Bookings List
	 * @throws Exception
	 */
	public List<BookingModel> getBookingsByUserIdAndBookingStatus(int userId, String status) throws Exception {
			return bookingDAO.findByUserId(userId, status);

	}
	/**
	 * Finds total count of bookings of each users given
	 * @param users
	 * @return total booking List
	 * @throws Exception
	 */
	public List<Integer> getTotalBookings(List<UsersModel> users) throws Exception {
		
		List<Integer> bookingCount = new ArrayList<>();
		for(UsersModel userId: users) {
			
			int totalBooking = getTotalBookings(userId.getUserId());
			
			bookingCount.add(totalBooking);
		}
		
		return bookingCount;
	}
	/**
	 * Finds total count of booking of specific user
	 * @param userId
	 * @return total booking
	 * @throws Exception
	 */
	public int getTotalBookings(int userId) throws Exception{
		
		return bookingDAO.findTotalBookingByUserId(userId);
	}
	/**
	 * Finds total count of bookings with show date later than today of a user
	 * @param userId
	 * @return upcoming booking count
	 * @throws Exception
	 */
	public int getTotalUpcomingBookings(int userId) throws Exception{
		
		return bookingDAO.findTotalUpcomingByUserId(userId);
	}
	/**
	 * Finds loyalty point earned of latest booking made
	 * @param userId
	 * @return loyalty points earned
	 * @throws Exception
	 */
	public int getLatestLoyaltyPointsEarned(int userId) throws Exception {
		BookingModel booking = bookingDAO.findLatestConfirmedByUserId(userId);
		if(booking==null) {
			return 0;
		}
		return booking.getLoyaltyPointsEarned();
	}
	
	/**
	 * 
	 * Finds booking date of nearest booking show date
	 * @param userId
	 * @return Date
	 * @throws Exception
	 */
	public LocalDate getLatestComingBookingDate(int userId) throws Exception{
				
		BookingModel booking = bookingDAO.findLatestComingByUserId(userId);
		if(booking==null) {
			return null;
		}
		return booking.getBookingDate();
	}
	/**
	 * Finds total count of bookings
	 * @return booking total count
	 * @throws Exception
	 */
	public int getTotalBookings() throws Exception{
		return bookingDAO.getTotalBookings();
	}
	/**
	 * Finds bookings with show date later than today of a user
	 * @param userId
	 * @return bookings List
	 * @throws Exception
	 */
	public List<BookingModel> getUpcomingBookings(int userId) throws Exception{
		
		return bookingDAO.findUpcomingByUserId(userId);
	}
	/**
	 * Finds count of bookings with booking date in current month
	 * @param userId
	 * @return booking count
	 * @throws Exception
	 */
	public int getTotalBookingsThisMonth(int userId) throws Exception {
		return bookingDAO.getCurrentMonthBookings(userId);
		
	}
	/**
	 * Finds total revenue generated today
	 * @return double
	 * @throws Exception
	 */
	public double getTodayRevenue() throws Exception {
	    return bookingDAO.getRevenue(LocalDate.now());
	}
	/**
	 * Finds total bookings made today
	 * @return booking count
	 * @throws Exception
	 */
	public int getTodayBooking() throws Exception {
	    return bookingDAO.getBookings(LocalDate.now());
	}
	/**
	 * Finds total revenue generated yesterday
	 * @return double
	 * @throws Exception
	 */
	public double getYesterdayRevenue() throws Exception {
	    return bookingDAO.getRevenue(LocalDate.now().minusDays(1));
	}
	/**
	 * Finds total bookings made yesterday
	 * @return booking count
	 * @throws Exception
	 */
	public int getYesterdayBooking() throws Exception {
	    return bookingDAO.getBookings(LocalDate.now().minusDays(1));
	}
	/**
	 * Finds seatids of bookings with status confirmed
	 * @param showtimeId
	 * @return Set of seat ids
	 * @throws Exception
	 */
	public Set<Integer> getConfirmedSeatIdsByShowtime(int showtimeId) throws Exception {
		return bookingDAO.getConfirmedSeatIdsByShowtime(showtimeId);
	}
	/**
	 * Creates new booking record and gets the booking id 
	 * @param userId
	 * @param today
	 * @param now
	 * @param string
	 * @param totalAmount
	 * @param string2
	 * @param pointsEarned
	 * @return booking Id
	 * @throws Exception
	 */
	public int insertAndGetId(int userId, LocalDate today, LocalTime now, String string, double totalAmount,
			String string2, int pointsEarned) throws Exception {
		return bookingDAO.insertAndGetId(userId, today, now, string, totalAmount, string2, pointsEarned);
	}
	/**
	 * Finds all bookings
	 * @return booking List
	 * @throws Exception
	 */
	public List<BookingModel> getAllBookings() throws Exception {
		return bookingDAO.getAllBookings();
	}
	/**
	 * Changes status of booking to Archive
	 * @param bookingId
	 * @throws Exception
	 */
	public void updateBookingStatusToArchive(int bookingId) throws Exception {
		bookingDAO.updateBookingStatus(bookingId, "archive");
		
	}

}
