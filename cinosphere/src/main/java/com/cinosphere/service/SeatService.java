package com.cinosphere.service;

import java.util.List;

import com.cinosphere.dao.SeatDAO;
import com.cinosphere.model.SeatModel;
/**
 * Service Class that is the bridge between Servlet and SeatDAO
 * Contains methods used to call methods of DAO and perform interaction with DB Seat Table
 * 
 * @author Raunit Giri
 */
public class SeatService {
	private SeatDAO seatDAO = new SeatDAO();
	/**
	 * Finds Seat using screen Id
	 * @param screenId
	 * @return Seat List
	 * @throws Exception
	 */
	public List<SeatModel> getSeatsByScreenId(int screenId) throws Exception {
		return seatDAO.findByScreenId(screenId);
	}
	/**
	 * Finds seat using Id
	 * @param seatId
	 * @return Seat
	 * @throws Exception
	 */
	public SeatModel getSeatById(int seatId) throws Exception {
		return seatDAO.findBySeatId(seatId);
	}
}
