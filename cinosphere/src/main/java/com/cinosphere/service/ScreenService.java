package com.cinosphere.service;

import java.util.List;

import com.cinosphere.dao.ScreenDAO;
import com.cinosphere.model.ScreenModel;
/**
 * Service Class that is the bridge between Servlet and ScreengDAO
 * Contains methods used to call methods of DAO and perform interaction with DB Screen Table
 * 
 * @author Raunit Giri
 */
public class ScreenService {
	private ScreenDAO screenDAO = new ScreenDAO();
		/**
		 * Finds screen suing Id
		 * @param screenId
		 * @return screen
		 * @throws Exception
		 */
		public ScreenModel getScreenById(int screenId) throws Exception {
			return screenDAO.findByScreenId(screenId);
		}
		/**
		 * Updates base price of screen
		 * @param screenId
		 * @param basePrice
		 * @return boolean
		 * @throws Exception
		 */
		public boolean updateBasePrice(int screenId, double basePrice) throws Exception {
			return screenDAO.updateScreenBasePrice(screenId, basePrice);
		}

		/**
		 * Finds all screen records
		 * @return screen
		 * @throws Exception
		 */
		public List<ScreenModel> getAllScreens() throws Exception{
			return screenDAO.getAllScreen();
		}
}
