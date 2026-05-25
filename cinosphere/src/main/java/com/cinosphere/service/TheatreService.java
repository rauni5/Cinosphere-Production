package com.cinosphere.service;

import com.cinosphere.dao.TheatreDAO;
import com.cinosphere.model.TheatreModel;
/**
 * Service Class that is the bridge between Servlet and TheatreDAO
 * Contains methods used to call methods of DAO and perform interaction with DB Theatre Table
 * 
 * @author Raunit Giri
 */
public class TheatreService {
	private TheatreDAO theatreDAO = new TheatreDAO();
	/**
	 * Finds theatre by Id
	 * @param theatreId
	 * @return Theatre
	 * @throws Exception
	 */
	public TheatreModel getTheatreById(int theatreId) throws Exception {
		return theatreDAO.findById(theatreId);
	}
}
