package com.cinosphere.service;

import java.time.LocalDate;
import java.time.LocalTime;

import com.cinosphere.dao.PaymentDAO;
import com.cinosphere.model.PaymentModel;
/**
 * Service Class that is the bridge between Servlet and PaymentDAO
 * Contains methods used to call methods of DAO and perform interaction with DB Payment Table
 * 
 * @author Raunit Giri
 */
public class PaymentService {
	private PaymentDAO paymentDAO = new PaymentDAO();
	/**
	 * Create payment record
	 * @param bookingId
	 * @param paymentMethod
	 * @param paymentAmount
	 * @return boolean
	 * @throws Exception
	 */
	public boolean createPayment(int bookingId, String paymentMethod, double paymentAmount) throws Exception {
		return paymentDAO.insert(bookingId,paymentMethod,paymentAmount,LocalDate.now(),LocalTime.now(),"COMPLETED");
	}
}
