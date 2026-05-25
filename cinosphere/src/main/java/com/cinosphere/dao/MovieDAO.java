package com.cinosphere.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.cinosphere.model.MovieModel;
import com.cinosphere.utils.DBconfig;
/**
 * DAO class responsible for all movie-related database operations.
 */
public class MovieDAO {
	/**
	 * Inserts a new movie record into the database.
	 *
	 * @param movieName
	 * @param duration
	 * @param director
	 * @param genre
	 * @param movieLanguage
	 * @param description
	 * @param releaseDate
	 * @param movieStatus
	 * @param ageRating
	 * @return true if inserted successfully
	 * @throws Exception if database operation fails
	 */
	public boolean insert(String movieName, int duration, String director, String genre, String movieLanguage, String description,LocalDate releaseDate ,String movieStatus, String ageRating) throws Exception {
		String sql = "INSERT INTO movie (movie_name,duration,director,genre,movie_language,description,release_date,movie_status,age_rating)"
				+"VALUES (?,?,?,?,?,?,?,?,?)";
		Connection con = DBconfig.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setString(1, movieName);
		ps.setInt(2, duration);
		ps.setString(3, director);
		ps.setString(4, genre);
		ps.setString(5, movieLanguage);
		ps.setString(6, description);
		ps.setDate(7,Date.valueOf(releaseDate));
		ps.setString(8, movieStatus);
		ps.setString(9, ageRating);
		return ps.executeUpdate() >0;
	}
	/**
	 * Inserts a new movie and returns the generated movie ID.
	 *
	 * @param movieName
	 * @param duration
	 * @param director
	 * @param genre
	 * @param movieLanguage
	 * @param description
	 * @param releaseDate
	 * @param movieStatus
	 * @param ageRating
	 * @return generated movie ID, or -1 if insertion fails
	 * @throws Exception if database operation fails
	 */
	public int insertAndGetId(String movieName, int duration, String director, String genre, String movieLanguage, String description,LocalDate releaseDate ,String movieStatus, String ageRating) throws Exception {
		String sql = "INSERT INTO movie (movie_name,duration,director,genre,movie_language,description,release_date,movie_status,age_rating)"
				+"VALUES (?,?,?,?,?,?,?,?,?)";
		Connection con = DBconfig.getConnection();
		PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		
		ps.setString(1, movieName);
		ps.setInt(2, duration);
		ps.setString(3, director);
		ps.setString(4, genre);
		ps.setString(5, movieLanguage);
		ps.setString(6, description);
		ps.setDate(7,Date.valueOf(releaseDate));
		ps.setString(8, movieStatus);
		ps.setString(9, ageRating);
		int rows = ps.executeUpdate();
		if (rows == 0) { ps.close(); con.close(); return -1; }
		ResultSet keys = ps.getGeneratedKeys();
		int newId = keys.next() ? keys.getInt(1) : -1;
		keys.close(); ps.close(); con.close();
		return newId;
	}
	/**
	 * Updates movie details in the database.
	 *
	 * @param movieId
	 * @param movieName
	 * @param duration
	 * @param director
	 * @param genre
	 * @param movieLanguage
	 * @param description
	 * @param releaseDate
	 * @param movieStatus
	 * @param ageRating
	 * @return true if updated successfully
	 * @throws Exception if database operation fails
	 */
	public boolean updateMovie(int movieId, String movieName, int duration, String director, String genre, String movieLanguage, String description, LocalDate releaseDate, String movieStatus, String ageRating) throws Exception {	
		String sql = "UPDATE movie SET movie_name = ?, duration = ?, director = ?, genre = ?, movie_language = ?,description = ?, release_date = ?, movie_status = ?, age_rating = ? WHERE movie_id = ?";
		try (Connection con = DBconfig.getConnection();
		PreparedStatement ps = con.prepareStatement(sql)) {
		ps.setString(1, movieName);
		ps.setInt(2, duration);
		ps.setString(3, director);
		ps.setString(4, genre);
		ps.setString(5, movieLanguage);
		ps.setString(6, description);
		ps.setDate(7, Date.valueOf(releaseDate));
		ps.setString(8, movieStatus);
		ps.setString(9, ageRating);
		ps.setInt(10, movieId);		
		return ps.executeUpdate() > 0;
		}
	}
	/**
	 * Updates movie status.
	 *
	 * @param movieId
	 * @param movieStatus
	 * @return true if updated successfully
	 * @throws Exception if database operation fails
	 */
	public boolean updateStatus(int movieId,String movieStatus) throws Exception{
		Connection con = DBconfig.getConnection();
		String sql = "UPDATE movie SET movie_status=? WHERE movie_id = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, movieStatus);
		ps.setInt(2, movieId);
		return ps.executeUpdate() > 0;
	}
	
	
	
	/**
	 * Retrieves movie details using movie ID.
	 *
	 * @param movieId
	 * @return movie record
	 * @throws Exception if database operation fails
	 */
	
	public MovieModel findById(int movieId) throws Exception{
		MovieModel movie = null;
		Connection con = DBconfig.getConnection();
		String sql = "SELECT * FROM movie WHERE movie_id = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, movieId);
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			movie = createMovieModel(rs);
		}
		
		rs.close();
	    ps.close();
	    con.close();
	    return movie;
	}
	/**
	 * Retrieves movies by genre.
	 *
	 * @param genre
	 * @return list of movies
	 * @throws Exception if database operation fails
	 */
	public List<MovieModel> findByGenre(String genre)throws Exception {
		List<MovieModel> movies = new ArrayList<>();
		Connection con = DBconfig.getConnection();

		String sql = "SELECT * FROM movie WHERE genre = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, genre);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			movies.add(createMovieModel(rs));
		}
		
		rs.close();
	    ps.close();
	    con.close();
	    return movies;
	}
	/**
	 * Retrieves movies by movie name.
	 *
	 * @param movieName
	 * @return list of matching movies
	 * @throws Exception if database operation fails
	 */
	public List<MovieModel> findByMovieName(String movieName)throws Exception {
		
		List<MovieModel> movies = new ArrayList<>();
		Connection con = DBconfig.getConnection();

		String sql = "SELECT * FROM movie WHERE movie_name LIKE ? ORDER BY release_date ASC";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, "%"+movieName+"%");
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			movies.add(createMovieModel(rs));
		}
		
		rs.close();
	    ps.close();
	    con.close();
	    return movies;
	}
	/**
	 * Retrieves movies by age rating.
	 *
	 * @param ageRating
	 * @return list of movies
	 * @throws Exception if database operation fails
	 */
	public List<MovieModel> findByAgeRating(String ageRating)throws Exception {
		
		List<MovieModel> movies = new ArrayList<>();
		Connection con = DBconfig.getConnection();

		String sql = "SELECT * FROM movie WHERE age_rating = ? ORDER BY release_date ASC";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, ageRating);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			movies.add(createMovieModel(rs));
		}
		
		rs.close();
	    ps.close();
	    con.close();
	    return movies;
	}
	/**
	 * Retrieves movies by age rating.
	 *
	 * @param ageRating
	 * @return list of movies
	 * @throws Exception if database operation fails
	 */
	public List<MovieModel> findByMovieLanguage(String movieLanguage)throws Exception {
			
			List<MovieModel> movies = new ArrayList<>();
			Connection con = DBconfig.getConnection();
	
			String sql = "SELECT * FROM movie WHERE movie_language = ? ORDER BY release_date ASC";
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, movieLanguage);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				movies.add(createMovieModel(rs));
			}
			
			rs.close();
		    ps.close();
		    con.close();
		    return movies;
		}
	/**
	 * Retrieves movies by movie status.
	 *
	 * @param status
	 * @return list of movies
	 * @throws Exception if database operation fails
	 */
	public List<MovieModel> findByMovieStatus(String status)throws Exception{
		List<MovieModel> movies = new ArrayList<>();
		Connection con = DBconfig.getConnection();

		String sql = "SELECT * FROM movie WHERE movie_status = ? ORDER BY release_date ASC";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, status);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			movies.add(createMovieModel(rs));
		}
		
		rs.close();
	    ps.close();
	    con.close();
	    return movies;
	}
	/**
	 * Retrieves all active movies.
	 *
	 * @return list of active movies
	 * @throws Exception if database operation fails
	 */
	public List<MovieModel> getAllActiveMovie() throws Exception {
		List<MovieModel> movies = new ArrayList<>();
		String sql = "SELECT * FROM movie WHERE movie_status = ? ORDER BY release_date ASC";
		Connection con = DBconfig.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, "NOW_SHOWING");
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			movies.add(createMovieModel(rs));
		}
		rs.close();
	    ps.close();
	    con.close();
	    return movies;
	}
	/**
	 * Retrieves all movies from the database.
	 *
	 * @return list of movies
	 * @throws Exception if database operation fails
	 */
	public List<MovieModel> getAllMovie() throws Exception {
		List<MovieModel> movies = new ArrayList<>();
		String sql = "SELECT * FROM movie ORDER BY release_date ASC";
		Connection con = DBconfig.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			movies.add(createMovieModel(rs));
		}
		rs.close();
	    ps.close();
	    con.close();
	    return movies;
	}
	/**
	 * Retrieves four active movies.
	 *
	 * @return list of four active movies
	 * @throws Exception if database operation fails
	 */
	public List<MovieModel> get4ActiveMovie() throws Exception{
		List<MovieModel> movies = new ArrayList<>();
		String sql = "SELECT * FROM movie WHERE movie_status = ? ORDER BY release_date ASC LIMIT 4;";
		Connection con = DBconfig.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, "NOW_SHOWING");
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			movies.add(createMovieModel(rs));
		}
		rs.close();
	    ps.close();
	    con.close();
	    return movies;
	}
	/**
	 * Retrieves movies using multiple filter conditions.
	 *
	 * @param language
	 * @param genre
	 * @param status
	 * @param keyword
	 * @return list of filtered movies
	 * @throws Exception if database operation fails
	 */
	public List<MovieModel> findByFilters(String language, String genre,  String status, String keyword) throws Exception {
		List<MovieModel> movies = new ArrayList<>();
		StringBuilder sql = new StringBuilder("SELECT * FROM movie WHERE movie_status != 'ARCHIVE'");
		List<String> params = new ArrayList<>();
		
		if (valueCheck(language)) { sql.append("AND movie_language = ? "); params.add(language); }
		if (valueCheck(genre))    { sql.append("AND genre = ? ");          params.add(genre);    }
		if (valueCheck(status) && !status.equals("all"))   { sql.append("AND movie_status = ? ");   params.add(status);   }
		if (valueCheck(keyword))  { sql.append("AND movie_name LIKE ? ");  params.add("%" + keyword + "%"); }
		
		sql.append("ORDER BY release_date ASC");
		
		Connection con = DBconfig.getConnection();
		PreparedStatement ps = con.prepareStatement(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			ps.setString(i+1, params.get(i));
		}
	
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) movies.add(createMovieModel(rs));
		
		rs.close(); ps.close(); con.close();
		return movies;
	}
	/**
	 * Creates a MovieModel object from the result set.
	 *
	 * @param rs
	 * @return populated MovieModel object
	 * @throws SQLException if result set processing fails
	 */
	public MovieModel createMovieModel(ResultSet rs) throws SQLException  {
		MovieModel movie = new MovieModel();
		movie.setMovieId(rs.getInt("movie_id"));
		movie.setMovieName(rs.getString("movie_name"));
		movie.setDuration(rs.getInt("duration"));
		movie.setDirector(rs.getString("director"));
		movie.setGenre(rs.getString("genre"));
		movie.setMovieLanguage(rs.getString("movie_language"));
		movie.setDescription(rs.getString("description"));
		movie.setReleaseDate(rs.getDate("release_date").toLocalDate());
		movie.setMovieStatus(rs.getString("movie_status"));
		movie.setAgeRating(rs.getString("age_rating"));
		return movie;
	}
	/**
	 * Checks whether a string value is valid.
	 *
	 * @param value
	 * @return true if value is not null or empty
	 */
	private boolean valueCheck(String value) {
		return value!=null && !value.trim().isEmpty();
	}
	
}
