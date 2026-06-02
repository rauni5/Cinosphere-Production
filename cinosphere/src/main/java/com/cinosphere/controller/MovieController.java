package com.cinosphere.controller;

import com.cinosphere.dto.ApiResponse;
import com.cinosphere.dto.MovieRequest;
import com.cinosphere.model.MovieModel;
import com.cinosphere.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * GET    /api/movies                         — list all (optional filters: status, keyword, language, genre)
 * GET    /api/movies/{id}                    — single movie detail
 * POST   /api/movies          [ADMIN]        — create movie + upload posters
 * PUT    /api/movies/{id}     [ADMIN]        — update movie + optional new posters
 * DELETE /api/movies/{id}     [ADMIN]        — archive movie (soft delete)
 */
@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired private MovieService movieService;

    // ------------------------------------------------------------------
    // Public endpoints
    // ------------------------------------------------------------------

    /**
     * Flexible list endpoint — all query params are optional.
     *
     * Examples:
     *   GET /api/movies                          → all movies
     *   GET /api/movies?status=NOW_SHOWING       → now showing
     *   GET /api/movies?keyword=raja&genre=Action → filtered
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<MovieModel>>> getMovies(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) String genre) {

        List<MovieModel> movies;

        boolean hasFilter = keyword != null || language != null || genre != null || status != null;

        if (hasFilter) {
            movies = movieService.getFilteredMovies(language, genre, status, keyword);
        } else {
            movies = movieService.getAllMovies();
        }

        return ResponseEntity.ok(ApiResponse.ok("OK", movies));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MovieModel>> getMovie(@PathVariable int id) {
        MovieModel movie = movieService.getMovieById(id);
        return ResponseEntity.ok(ApiResponse.ok("OK", movie));
    }

    // ------------------------------------------------------------------
    // Admin endpoints
    // ------------------------------------------------------------------

    /**
     * Multipart form — movie fields as JSON part + two image files.
     *
     * Content-Type: multipart/form-data
     * Parts:
     *   movieName, duration, director, genre, movieLanguage,
     *   description, releaseDate, movieStatus, ageRating   (text fields)
     *   poster            (file, optional)
     *   backgroundPoster  (file, optional)
     */
    @PostMapping(consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<MovieModel>> addMovie(
            @RequestParam String movieName,
            @RequestParam int duration,
            @RequestParam String director,
            @RequestParam String genre,
            @RequestParam String movieLanguage,
            @RequestParam String description,
            @RequestParam String releaseDate,
            @RequestParam String movieStatus,
            @RequestParam String ageRating,
            @RequestParam(required = false) MultipartFile poster,
            @RequestParam(required = false) MultipartFile backgroundPoster)
            throws IOException {

        MovieRequest req = buildRequest(movieName, duration, director, genre,
                movieLanguage, description, releaseDate, movieStatus, ageRating);

        MovieModel saved = movieService.addMovie(req, poster, backgroundPoster);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Movie added", saved));
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<MovieModel>> updateMovie(
            @PathVariable int id,
            @RequestParam String movieName,
            @RequestParam int duration,
            @RequestParam String director,
            @RequestParam String genre,
            @RequestParam String movieLanguage,
            @RequestParam String description,
            @RequestParam String releaseDate,
            @RequestParam String movieStatus,
            @RequestParam String ageRating,
            @RequestParam(required = false) MultipartFile poster,
            @RequestParam(required = false) MultipartFile backgroundPoster)
            throws IOException {

        MovieRequest req = buildRequest(movieName, duration, director, genre,
                movieLanguage, description, releaseDate, movieStatus, ageRating);

        MovieModel updated = movieService.updateMovie(id, req, poster, backgroundPoster);
        return ResponseEntity.ok(ApiResponse.ok("Movie updated", updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> archiveMovie(@PathVariable int id) {
        movieService.archiveMovie(id);
        return ResponseEntity.ok(ApiResponse.ok("Movie archived"));
    }

    // ------------------------------------------------------------------
    // Helper
    // ------------------------------------------------------------------

    private MovieRequest buildRequest(String movieName, int duration, String director,
                                       String genre, String movieLanguage, String description,
                                       String releaseDate, String movieStatus, String ageRating) {
        MovieRequest req = new MovieRequest();
        req.setMovieName(movieName);
        req.setDuration(duration);
        req.setDirector(director);
        req.setGenre(genre);
        req.setMovieLanguage(movieLanguage);
        req.setDescription(description);
        req.setReleaseDate(java.time.LocalDate.parse(releaseDate));
        req.setMovieStatus(movieStatus);
        req.setAgeRating(ageRating);
        return req;
    }
}
