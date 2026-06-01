package com.cinosphere.service;

import com.cinosphere.dto.MovieRequest;
import com.cinosphere.model.MovieModel;
import com.cinosphere.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

/**
 * Handles movie CRUD and file upload operations.
 *
 * Changes from original MovieService:
 *  - Accepts MovieRequest DTO instead of a long parameter list.
 *  - File handling uses Spring's MultipartFile instead of FileuploadUtil.
 *  - Injected MovieRepository instead of new MovieDAO().
 *  - insertAndGetId() replaced by save() — JPA returns the managed entity with its ID.
 *  - Throws IllegalArgumentException for not-found cases (caught by the global handler).
 */
@Service
public class MovieService {

    private static final String UPLOAD_DIR = "uploads/movies/";

    @Autowired
    private MovieRepository movieRepository;

    // ---- queries ----

    public List<MovieModel> getAllMovies() {
        return movieRepository.findAll();
    }

    public List<MovieModel> getAllActiveMovies() {
        return movieRepository.findByMovieStatus("NOW_SHOWING");
    }
    public List<MovieModel> get4ActiveMovies() {
        return movieRepository.findTop4ByMovieStatusOrderByReleaseDateAsc("NOW_SHOWING");
    }


    public List<MovieModel> getMoviesByStatus(String status) {
        return movieRepository.findByMovieStatus(status);
    }

    public List<MovieModel> getFilteredMovies(String language, String genre,
                                               String status, String keyword) {
        return movieRepository.findByFilters(language, genre, status, keyword);
    }

    public List<MovieModel> searchByName(String keyword) {
        return movieRepository.findByMovieNameContainingIgnoreCase(keyword);
    }

    public MovieModel getMovieById(int movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Movie not found: " + movieId));
    }

    // ---- create ----

    /**
     * Creates a new movie record and saves poster files to disk.
     * Returns the saved entity (with its generated movieId).
     */
    public MovieModel addMovie(MovieRequest request,
                               MultipartFile poster,
                               MultipartFile backgroundPoster) throws IOException {
        MovieModel movie = mapToModel(new MovieModel(), request);

        if (poster != null && !poster.isEmpty()) {
            saveFile(poster, "poster_");
        }
        if (backgroundPoster != null && !backgroundPoster.isEmpty()) {
           saveFile(backgroundPoster, "bg_");
        }

        return movieRepository.save(movie);
    }

    // ---- update ----

    /**
     * Updates movie details. Poster files are optional — if not provided,
     * the existing paths are left unchanged.
     */
    public MovieModel updateMovie(int movieId,
                                  MovieRequest request,
                                  MultipartFile poster,
                                  MultipartFile backgroundPoster) throws IOException {
        MovieModel movie = getMovieById(movieId);
        mapToModel(movie, request);

        if (poster != null && !poster.isEmpty()) {
            saveFile(poster, "poster_");
        }
        if (backgroundPoster != null && !backgroundPoster.isEmpty()) {
            saveFile(backgroundPoster, "bg_");
        }

        return movieRepository.save(movie);
    }

    public void archiveMovie(int movieId) {
        MovieModel movie = getMovieById(movieId);
        movie.setMovieStatus("ARCHIVE");
        movieRepository.save(movie);
    }

    public void updateMovieStatus(int movieId, String status) {
        MovieModel movie = getMovieById(movieId);
        movie.setMovieStatus(status);
        movieRepository.save(movie);
    }

    // ---- helpers ----

    private MovieModel mapToModel(MovieModel movie, MovieRequest request) {
        movie.setMovieName(request.getMovieName());
        movie.setDuration(request.getDuration());
        movie.setDirector(request.getDirector());
        movie.setGenre(request.getGenre());
        movie.setMovieLanguage(request.getMovieLanguage());
        movie.setDescription(request.getDescription());
        movie.setReleaseDate(request.getReleaseDate());
        movie.setMovieStatus(request.getMovieStatus());
        movie.setAgeRating(request.getAgeRating());
        return movie;
    }

    /**
     * Saves a multipart file to the uploads directory.
     * @return the relative file path to store in the DB (e.g. "uploads/movies/poster_123456.jpg")
     */
    private String saveFile(MultipartFile file, String prefix) throws IOException {
        Path dir = Paths.get(UPLOAD_DIR);
        Files.createDirectories(dir);
        String filename = prefix + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Files.copy(file.getInputStream(), dir.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
        return UPLOAD_DIR + filename;
    }
}