package com.cinosphere.model;

import java.time.LocalDate;
import java.time.LocalTime;
/**
 * Model class representing database table ShowtimeModel and its attributes
 */
public class ShowtimeModel {
	private int showtimeId;
    private int screenId;
    private int movieId;
    private LocalDate showDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String showStatus;
    private String showType;
    /**
     * Returns the unique showtime ID.
     * 
     * @return int
     */
    public int getShowtimeId() { 
    	return showtimeId; 
    }

    /**
     * Sets the unique showtime ID.
     * 
     * @param showtimeId
     */
    public void setShowtimeId(int showtimeId) { 
    	this.showtimeId = showtimeId; 
    }

    /**
     * Returns the screen ID associated with the showtime.
     * 
     * @return int
     */
    public int getScreenId() { 
    	return screenId; 
    }

    /**
     * Sets the screen ID associated with the showtime.
     * 
     * @param screenId
     */
    public void setScreenId(int screenId) { 
    	this.screenId = screenId; 
    }

    /**
     * Returns the movie ID associated with the showtime.
     * 
     * @return int
     */
    public int getMovieId() {
    	return movieId; 
    }

    /**
     * Sets the movie ID associated with the showtime.
     * 
     * @param movieId
     */
    public void setMovieId(int movieId) { 
    	this.movieId = movieId; 
    }

    /**
     * Returns the show date.
     * 
     * @return LocalDate
     */
    public LocalDate getShowDate() { 
    	return showDate; 
    }

    /**
     * Sets the show date.
     * 
     * @param showDate
     */
    public void setShowDate(LocalDate showDate) { 
    	this.showDate = showDate; 
    }

    /**
     * Returns the start time of the show.
     * 
     * @return LocalTime
     */
    public LocalTime getStartTime() {
    	return startTime; 
    }

    /**
     * Sets the start time of the show.
     * 
     * @param startTime
     */
    public void setStartTime(LocalTime startTime) { 
    	this.startTime = startTime; 
    }

    /**
     * Returns the end time of the show.
     * 
     * @return LocalTime
     */
    public LocalTime getEndTime() { 
    	return endTime; 
    }

    /**
     * Sets the end time of the show.
     * 
     * @param endTime
     */
    public void setEndTime(LocalTime endTime) { 
    	this.endTime = endTime; 
    }

    /**
     * Returns the show status.
     * 
     * @return String
     */
    public String getShowStatus() { 
    	return showStatus; 
    }

    /**
     * Sets the show status.
     * 
     * @param showStatus
     */
    public void setShowStatus(String showStatus) { 
    	this.showStatus = showStatus; 
    }

    /**
     * Returns the show type.
     * 
     * @return String
     */
    public String getShowType() {
    	return showType; 
    }

    /**
     * Sets the show type.
     * 
     * @param showType
     */
    public void setShowType(String showType) { 
    	this.showType = showType; 
    }
}
