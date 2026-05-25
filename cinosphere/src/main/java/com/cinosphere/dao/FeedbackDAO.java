package com.cinosphere.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.cinosphere.model.FeedbackModel;
import com.cinosphere.utils.DBconfig;
/**
 * DAO class responsible for all feedback-related database operations.
 */
public class FeedbackDAO {
	/**
	 * Inserts a new feedback record into the database.
	 *
	 * @param userId
	 * @param movieId
	 * @param rating
	 * @param description
	 * @param feedbackDate
	 * @param feedbackTime
	 * @param feedbackStatus
	 * @return true if inserted successfully
	 * @throws SQLException if database operation fails
	 */
	public boolean insert(int userId,int movieId,int rating,String description, LocalDate feedbackDate, LocalTime feedbackTime, String feedbackStatus) throws SQLException {
        String sql = "INSERT INTO feedback (user_id, movie_id, rating, "
                   + "description, feedback_date,feedback_time, feedback_status) VALUES (?, ?, ?, ?,?,?,?)";
        Connection con = DBconfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt        (1, userId);
            ps.setInt        (2, movieId);
            ps.setInt     (3, rating);
            ps.setString     (4, description);
            ps.setDate        (5, Date.valueOf(feedbackDate));
            ps.setTime (5,Time.valueOf(feedbackTime));
            ps.setString(7, feedbackStatus);
            return ps.executeUpdate() > 0;
        }
	
	/**
	 * Retrieves all feedback records from the database.
	 *
	 * @return list of feedback records
	 * @throws Exception if database operation fails
	 */
	public List<FeedbackModel> getAllFeedback() throws Exception{
		List<FeedbackModel> feedbacks = new ArrayList<>();
		String sql = "Select * from feedback";
		Connection con = DBconfig.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			feedbacks.add(createFeedbackModel(rs));
		}
		rs.close();
        ps.close();
        con.close();
        return feedbacks;
	}
	
	/**
	 * Retrieves feedback records for a specific user.
	 *
	 * @param userId
	 * @return list of feedback records
	 * @throws Exception if database operation fails
	 */
	public List<FeedbackModel> findByUserId(int userId) throws Exception{
		List<FeedbackModel> feedbacks = new ArrayList<>();
		String sql = "Select * from feedback WHERE user_id=?";
		Connection con = DBconfig.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, userId);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			feedbacks.add(createFeedbackModel(rs));
		}
		rs.close();
        ps.close();
        con.close();
        return feedbacks;
	}
	
	
	
	/**
	 * Creates a FeedbackModel object from the result set.
	 *
	 * @param rs
	 * @return populated FeedbackModel object
	 * @throws Exception if result set processing fails
	 */
	public FeedbackModel createFeedbackModel(ResultSet rs) throws Exception{
		FeedbackModel feedback = new FeedbackModel();
		feedback.setFeedbackId(rs.getInt("feedback_id"));
		feedback.setUserId(rs.getInt("user_id"));
		feedback.setMovieId(rs.getInt("movie_id"));
		feedback.setRating(rs.getInt("rating"));
		feedback.setDescription(rs.getString("description"));
		feedback.setFeedbackDate(rs.getDate("feedback_date").toLocalDate());
		feedback.setFeedbackTime(rs.getTime("feedback_time").toLocalTime());
		feedback.setFeedbackStatus(rs.getString("feedback_status"));
		return feedback;
	}
}
