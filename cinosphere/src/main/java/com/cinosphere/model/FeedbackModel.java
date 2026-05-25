package com.cinosphere.model;

import java.time.LocalDate;
import java.time.LocalTime;
/**
 * Model class representing database table Feedback and its attributes
 */
public class FeedbackModel {
	private int feedbackId;
    private int userId;
    private int movieId;
    private int rating;
    private String description;
    private LocalDate feedbackDate;
    private LocalTime feedbackTime;
    private String feedbackStatus;
    /**
     * Returns the unique feedback ID.
     * 
     * @return int
     */
    public int getFeedbackId() { 
    	return feedbackId; 
    }

    /**
     * Sets the unique feedback ID.
     * 
     * @param feedbackId
     */
    public void setFeedbackId(int feedbackId) {
    	this.feedbackId = feedbackId; 
    }

    /**
     * Returns the user ID associated with the feedback.
     * 
     * @return int
     */
    public int getUserId() { 
    	return userId; 
    }

    /**
     * Sets the user ID associated with the feedback.
     * 
     * @param userId
     */
    public void setUserId(int userId) { 
    	this.userId = userId; 
    }

    /**
     * Returns the movie ID associated with the feedback.
     * 
     * @return int
     */
    public int getMovieId() { 
    	return movieId; 
    }

    /**
     * Sets the movie ID associated with the feedback.
     * 
     * @param movieId
     */
    public void setMovieId(int movieId) { 
    	this.movieId = movieId; 
    }

    /**
     * Returns the rating given in the feedback.
     * 
     * @return int
     */
    public int getRating() { 
    	return rating; 
    }

    /**
     * Sets the rating for the feedback.
     * 
     * @param rating
     */
    public void setRating(int rating) { 
    	this.rating = rating; 
    }

    /**
     * Returns the feedback description.
     * 
     * @return String
     */
    public String getDescription() { 
    	return description; 
    }

    /**
     * Sets the feedback description.
     * 
     * @param description
     */
    public void setDescription(String description) { 
    	this.description = description;
    }

    /**
     * Returns the feedback date.
     * 
     * @return LocalDate
     */
    public LocalDate getFeedbackDate() { 
    	return feedbackDate;
    }

    /**
     * Sets the feedback date.
     * 
     * @param feedbackDate
     */
    public void setFeedbackDate(LocalDate feedbackDate) { 
    	this.feedbackDate = feedbackDate; 
    }

    /**
     * Returns the feedback time.
     * 
     * @return LocalTime
     */
    public LocalTime getFeedbackTime() { 
    	return feedbackTime; 
    }

    /**
     * Sets the feedback time.
     * 
     * @param feedbackTime
     */
    public void setFeedbackTime(LocalTime feedbackTime) { 
    	this.feedbackTime = feedbackTime; 
    }

    /**
     * Returns the feedback status.
     * 
     * @return String
     */
    public String getFeedbackStatus() { 
    	return feedbackStatus; 
    }

    /**
     * Sets the feedback status.
     * 
     * @param feedbackStatus
     */
    public void setFeedbackStatus(String feedbackStatus) { 
    	this.feedbackStatus = feedbackStatus; 
    }
}
