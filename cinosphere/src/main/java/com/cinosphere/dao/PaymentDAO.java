package com.cinosphere.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import com.cinosphere.model.PaymentModel;
import com.cinosphere.utils.DBconfig;
/**
 * DAO class responsible for all payment-related database operations.
 */
public class PaymentDAO {
	/**
	 * Inserts a new payment record into the database.
	 *
	 * @param bookingId
	 * @param paymentMethod
	 * @param paymentAmount
	 * @param paymentDate
	 * @param paymentTime
	 * @param paymentStatus
	 * @return true if inserted successfully
	 * @throws Exception if database operation fails
	 */
	public boolean insert(int bookingId, String paymentMethod, double paymentAmount, LocalDate paymentDate, LocalTime paymentTime, String paymentStatus) throws Exception {
		String sql = "INSERT INTO payment (booking_id, payment_method, payment_amount, payment_date, payment_time, payment_status) "
				   + "VALUES (?, ?, ?, ?, ?, ?)";
		Connection con = DBconfig.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt       (1, bookingId);
		ps.setString    (2, paymentMethod);
		ps.setBigDecimal(3, BigDecimal.valueOf(paymentAmount));
		ps.setDate      (4, Date.valueOf(paymentDate));
		ps.setTime      (5, Time.valueOf(paymentTime));
		ps.setString    (6, paymentStatus);
		boolean result = ps.executeUpdate() > 0;
		ps.close();
		con.close();
		return result;
	}
	/**
	 * Retrieves payment details using payment ID.
	 *
	 * @param paymentId
	 * @return payment record
	 * @throws Exception if database operation fails
	 */
	public PaymentModel findByPaymentId(int paymentId) throws Exception {
		PaymentModel payment = null;
		String sql = "SELECT * FROM payment WHERE payment_id = ?";
		Connection con = DBconfig.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, paymentId);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			payment = createPaymentModel(rs);
		}
		rs.close();
		ps.close();
		con.close();
		return payment;
	}
	/**
	 * Retrieves payment details using booking ID.
	 *
	 * @param bookingId
	 * @return payment record
	 * @throws Exception if database operation fails
	 */
	public PaymentModel findByBookingId(int bookingId) throws Exception {
		PaymentModel payment = null;
		String sql = "SELECT * FROM payment WHERE booking_id = ?";
		Connection con = DBconfig.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, bookingId);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			payment = createPaymentModel(rs);
		}
		rs.close();
		ps.close();
		con.close();
		return payment;
	}
	/**
	 * Retrieves all payment records from the database.
	 *
	 * @return list of payment records
	 * @throws Exception if database operation fails
	 */
	public List<PaymentModel> getAllPayments() throws Exception {
		List<PaymentModel> payments = new ArrayList<>();
		String sql = "SELECT * FROM payment";
		Connection con = DBconfig.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			payments.add(createPaymentModel(rs));
		}
		rs.close();
		ps.close();
		con.close();
		return payments;
	}
	/**
	 * Updates payment status.
	 *
	 * @param paymentId
	 * @param paymentStatus
	 * @return true if updated successfully
	 * @throws Exception if database operation fails
	 */
	public boolean updatePaymentStatus(int paymentId, String paymentStatus) throws Exception {
		String sql = "UPDATE payment SET payment_status = ? WHERE payment_id = ?";
		Connection con = DBconfig.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, paymentStatus);
		ps.setInt   (2, paymentId);
		boolean result = ps.executeUpdate() > 0;
		ps.close();
		con.close();
		return result;
	}
	/**
	 * Creates a PaymentModel object from the result set.
	 *
	 * @param rs
	 * @return populated PaymentModel object
	 * @throws SQLException if result set processing fails
	 */
	public PaymentModel createPaymentModel(ResultSet rs) throws SQLException {
		PaymentModel payment = new PaymentModel();
		payment.setPaymentId(rs.getInt("payment_id"));
		payment.setBookingId(rs.getInt("booking_id"));
		payment.setPaymentMethod(rs.getString("payment_method"));
		payment.setPaymentAmount(rs.getDouble("payment_amount"));
		payment.setPaymentDate(rs.getDate("payment_date").toLocalDate());
		payment.setPaymentTime(rs.getTime("payment_time").toLocalTime());
		payment.setPaymentStatus(rs.getString("payment_status"));
		return payment;
	}
}
