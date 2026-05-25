package com.cinosphere.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.cinosphere.dao.ShowtimeDAO;
import com.cinosphere.model.ShowtimeModel;
/**
 * Service Class that is the bridge between Servlet and ShotimeDAO
 * Contains methods used to call methods of DAO and perform interaction with DB Showtime Table
 * 
 * @author Raunit Giri
 */
public class ShowtimeService {
	private ShowtimeDAO showtimeDAO = new ShowtimeDAO();
	/**
	 * Finds showtime using Id
	 * @param showtimeId
	 * @return Showtime
	 * @throws Exception
	 */
	public ShowtimeModel getShowtimeById(int showtimeId) throws Exception{
			return showtimeDAO.findByShowtimeId(showtimeId);

	}
	/**
	 * Finds showtimes using ScreenId
	 * @param screenId
	 * @return Showtime List
	 * @throws Exception
	 */
	public List<ShowtimeModel> getShowtimesByScreenId(int screenId) throws Exception {
		return showtimeDAO.findByScreenId(screenId);
	}
	/**
	 * Finds SHowtimes using movieId
	 * @param movieId
	 * @return SHowtime List
	 * @throws Exception
	 */
	public List<ShowtimeModel> getShowtimesByMovieId(int movieId) throws Exception {
		return showtimeDAO.findByMovieId(movieId);
	}
	/**
	 *  Creates new showtime using details provided
	 * @param screenId
	 * @param movieId
	 * @param showDate
	 * @param startTime
	 * @param endTime
	 * @param showStatus
	 * @param showType
	 * @return boolean
	 * @throws Exception
	 */
	public boolean insertShowtime(int screenId, int movieId, LocalDate showDate, LocalTime startTime, LocalTime endTime, String showStatus, String showType) throws Exception {
		return showtimeDAO.insert(screenId, movieId, showDate, startTime, endTime, showStatus, showType);
	}
	/**
	 * Removes showtime record using movieId
	 * @param movieId
	 * @return boolean
	 * @throws Exception
	 */
	public boolean deleteShowtimesByMovieId(int movieId) throws Exception {
		return showtimeDAO.deleteByMovieId(movieId);
		
	}
}
