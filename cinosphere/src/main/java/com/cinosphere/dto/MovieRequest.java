package com.cinosphere.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

/**
 * DTO for creating or updating a movie (admin only).
 * Used for both AddMovieServlet and UpdateMovieServlet — the
 * controller distinguishes POST (create) from PUT (update).
 *
 * Poster and background poster files are sent as separate
 * MultipartFile params alongside this JSON body (or as a
 * multipart/form-data request); see MovieController for details.
 */
public class MovieRequest {

    @NotBlank(message = "Movie name is required")
    private String movieName;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    private int duration;

    @NotBlank(message = "Director is required")
    private String director;

    @NotBlank(message = "Genre is required")
    private String genre;

    @NotBlank(message = "Language is required")
    private String movieLanguage;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Release date is required")
    private LocalDate releaseDate;

    @NotBlank(message = "Movie status is required")
    @Pattern(regexp = "NOW_SHOWING|COMING_SOON|ARCHIVE",
             message = "Status must be NOW_SHOWING, COMING_SOON, or ARCHIVE")
    private String movieStatus;

    @NotBlank(message = "Age rating is required")
    private String ageRating;

    public String getMovieName()         { return movieName; }
    public void setMovieName(String v)   { this.movieName = v; }

    public int getDuration()             { return duration; }
    public void setDuration(int v)       { this.duration = v; }

    public String getDirector()          { return director; }
    public void setDirector(String v)    { this.director = v; }

    public String getGenre()             { return genre; }
    public void setGenre(String v)       { this.genre = v; }

    public String getMovieLanguage()          { return movieLanguage; }
    public void setMovieLanguage(String v)    { this.movieLanguage = v; }

    public String getDescription()       { return description; }
    public void setDescription(String v) { this.description = v; }

    public LocalDate getReleaseDate()         { return releaseDate; }
    public void setReleaseDate(LocalDate v)   { this.releaseDate = v; }

    public String getMovieStatus()       { return movieStatus; }
    public void setMovieStatus(String v) { this.movieStatus = v; }

    public String getAgeRating()         { return ageRating; }
    public void setAgeRating(String v)   { this.ageRating = v; }
}