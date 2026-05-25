package com.cinosphere.model;

import java.time.LocalDate;
/*8
 * Model class representing database table Movie and its attributes
 */
public class MovieModel {
	private int movieId;
    private String movieName;
    private int duration;
    private String director;
    private String genre;
    private String movieLanguage;
    private String description;
    private LocalDate releaseDate;
    private String movieStatus;
    private String ageRating;
    /**
     * Returns the unique movie ID.
     * 
     * @return int
     */
    public int getMovieId() { 
    	return movieId; 
    }

    /**
     * Sets the unique movie ID.
     * 
     * @param movieId
     */
    public void setMovieId(int movieId) { 
    	this.movieId = movieId; 
    }

    /**
     * Returns the movie name.
     * 
     * @return String
     */
    public String getMovieName() { 
    	return movieName; 
    }

    /**
     * Sets the movie name.
     * 
     * @param movieName
     */
    public void setMovieName(String movieName) { 
    	this.movieName = movieName; 
    }

    /**
     * Returns the duration of the movie.
     * 
     * @return int
     */
    public int getDuration() { 
    	return duration; 
    }

    /**
     * Sets the duration of the movie.
     * 
     * @param duration
     */
    public void setDuration(int duration) { 
    	this.duration = duration; 
    }

    /**
     * Returns the director of the movie.
     * 
     * @return String
     */
    public String getDirector() { 
    	return director; 
    }

    /**
     * Sets the director of the movie.
     * 
     * @param director
     */
    public void setDirector(String director) { 
    	this.director = director;
    }

    /**
     * Returns the genre of the movie.
     * 
     * @return String
     */
    public String getGenre() { 
    	return genre; 
    }

    /**
     * Sets the genre of the movie.
     * 
     * @param genre
     */
    public void setGenre(String genre) { 
    	this.genre = genre; 
    }

    /**
     * Returns the language of the movie.
     * 
     * @return String
     */
    public String getMovieLanguage() { 
    	return movieLanguage; 
    }

    /**
     * Sets the language of the movie.
     * 
     * @param movieLanguage
     */
    public void setMovieLanguage(String movieLanguage) { 
    	this.movieLanguage = movieLanguage; 
    }

    /**
     * Returns the description of the movie.
     * 
     * @return String
     */
    public String getDescription() { 
    	return description; 
    }

    /**
     * Sets the description of the movie.
     * 
     * @param description
     */
    public void setDescription(String description) { 
    	this.description = description; 
    }

    /**
     * Returns the release date of the movie.
     * 
     * @return LocalDate
     */
    public LocalDate getReleaseDate() { 
    	return releaseDate; 
    }

    /**
     * Sets the release date of the movie.
     * 
     * @param releaseDate
     */
    public void setReleaseDate(LocalDate releaseDate) { 
    	this.releaseDate = releaseDate;
    }

    /**
     * Returns the movie status.
     * 
     * @return String
     */
    public String getMovieStatus() { 
    	return movieStatus;
    }

    /**
     * Sets the movie status.
     * 
     * @param movieStatus
     */
    public void setMovieStatus(String movieStatus) { 
    	this.movieStatus = movieStatus; 
    }

    /**
     * Returns the age rating of the movie.
     * 
     * @return String
     */
    public String getAgeRating() {
    	return ageRating; 
    }

    /**
     * Sets the age rating of the movie.
     * 
     * @param ageRating
     */
    public void setAgeRating(String ageRating) { 
    	this.ageRating = ageRating; 
    }
}
