package com.cinosphere.repository;

import com.cinosphere.model.MovieModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<MovieModel, Integer> {

    // ========== BASIC FINDERS ==========
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

    @Query("""
        SELECT m FROM MovieModel m
        WHERE (:language IS NULL OR m.movieLanguage = :language)
        AND (:genre IS NULL OR m.genre = :genre)
        AND (:status IS NULL OR m.movieStatus = :status)
        AND (:keyword IS NULL OR LOWER(m.movieName) LIKE LOWER(CONCAT('%', :keyword, '%')))
    """)
    List<MovieModel> findByFilters(
            @Param("language") String language,
            @Param("genre") String genre,
            @Param("status") String status,
            @Param("keyword") String keyword
    );
}