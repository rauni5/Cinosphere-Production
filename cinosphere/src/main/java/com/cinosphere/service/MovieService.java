package com.cinosphere.service;


import java.time.LocalDate;
import java.util.List;

import com.cinosphere.dao.MovieDAO;
import com.cinosphere.model.MovieModel;
/**
 * Service Class that is the bridge between Servlet and MovieDAO
 * Contains methods used to call methods of DAO and perform interaction with DB Movie Table
 * 
 * @author Raunit Giri
 */
public class MovieService {
	private MovieDAO movieDAO = new MovieDAO();
	public List<MovieModel> getFilteredMovies(String language, String genre, String status, String keyword) throws Exception {

			List<MovieModel> filtered = movieDAO.findByFilters(language, genre, status, keyword);

			return filtered;
}
/**
 * FInds movie using movie Id
 * @param movieId
 * @return movie
 * @throws Exception
 */
public MovieModel getMovieById(int movieId) throws Exception {
return movieDAO.findById(movieId);
}
/**
 * Finds all now showing movies
 * @return movie List
 * @throws Exception
 */
public List<MovieModel> getAllActiveMovies() throws Exception {
return movieDAO.getAllActiveMovie();
}
/**
 * Finds latest 4 now showing movies
 * @return movie List
 * @throws Exception
 */
public List<MovieModel> get4ActiveMovies() throws Exception {
	return movieDAO.get4ActiveMovie();
}
/**
 * Finds all movies
 * @return movie List
 * @throws Exception
 */
public List<MovieModel> getAllMovies() throws Exception {
	return movieDAO.getAllMovie();
}
/**
 * Finds movie using Status
 * @param status
 * @return movie List
 * @throws Exception
 */
public List<MovieModel> getMoviesByStatus(String status) throws Exception {
 
	return movieDAO.findByMovieStatus(status);
}
/**
 * Finds similar movie using movie name
 * @param searchMovie
 * @return movie list
 * @throws Exception
 */
public List<MovieModel> findByMovieName(String searchMovie) throws Exception{
	
	return movieDAO.findByMovieName(searchMovie);
}
/**
 * updates movie status of given movie Id
 * @param movieId
 * @param movieStatus
 * @return
 * @throws Exception
 */
public boolean updateMovieStatus(int movieId,String movieStatus) throws Exception{
	return movieDAO.updateStatus(movieId, movieStatus);

}
/**
 * Creates new movie and return movie Id of it
 * @param movieName
 * @param duration
 * @param director
 * @param genre
 * @param movieLanguage
 * @param description
 * @param releaseDate
 * @param movieStatus
 * @param ageRating
 * @return movie Id
 * @throws Exception
 */
public int insertAndGetId(String movieName, int duration, String director, String genre, String movieLanguage, String description, LocalDate releaseDate, String movieStatus, String ageRating) throws Exception {
	return movieDAO.insertAndGetId(movieName, duration, director, genre, movieLanguage, description, releaseDate, movieStatus, ageRating);
	
}
/**
 * Update movie details of given movieId
 * @param movieId
 * @param movieName
 * @param duration
 * @param director
 * @param genre
 * @param movieLang
 * @param description
 * @param releaseDate
 * @param movieStatus
 * @param ageRating
 * @return
 * @throws Exception
 */
public boolean updateMovie(int movieId, String movieName, int duration, String director, String genre, String movieLang,String description, LocalDate releaseDate, String movieStatus, String ageRating) throws Exception {
	return movieDAO.updateMovie(movieId, movieName, duration, director, genre, movieLang, description, releaseDate, movieStatus, ageRating);
	
}
}
