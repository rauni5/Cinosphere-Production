package com.cinosphere.repository;

import com.cinosphere.model.MovieModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<MovieModel, Integer> {

    // ========== BASIC FINDERS ==========

    Optional<MovieModel> findByMovieId(int movieId);

    List<MovieModel> findByGenre(String genre);

    List<MovieModel> findByMovieLanguage(String movieLanguage);

    List<MovieModel> findByMovieStatus(String movieStatus);

    List<MovieModel> findByAgeRating(String ageRating);

    List<MovieModel> findByMovieNameContainingIgnoreCase(String movieName);

    // ========== SORTED QUERIES ==========

    List<MovieModel> findAllByOrderByReleaseDateAsc();

    List<MovieModel> findByMovieStatusOrderByReleaseDateAsc(String movieStatus);

    List<MovieModel> findByAgeRatingOrderByReleaseDateAsc(String ageRating);

    List<MovieModel> findByMovieLanguageOrderByReleaseDateAsc(String movieLanguage);

    // ========== LIMIT (replace get4ActiveMovie) ==========

    List<MovieModel> findTop4ByMovieStatusOrderByReleaseDateAsc(String movieStatus);

    // ========== COUNT / FILTER-LIKE BEHAVIOR ==========

    List<MovieModel> findByMovieStatusNot(String movieStatus);

    // ========== INSERT + UPDATE ==========

    // handled automatically via save()
}