package com.cinosphere.repository;

import com.cinosphere.model.ShowtimeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ShowtimeRepository extends JpaRepository<ShowtimeModel, Integer> {

    // SELECT * FROM showtime WHERE show_status = 'ACTIVE'
    List<ShowtimeModel> findByShowStatus(String showStatus);

    // SELECT * FROM showtime WHERE movie_id = ?
    List<ShowtimeModel> findByMovieId(int movieId);

    // SELECT * FROM showtime WHERE screen_id = ?
    List<ShowtimeModel> findByScreenId(int screenId);

    // SELECT * FROM showtime WHERE show_date = ?
    List<ShowtimeModel> findByShowDate(LocalDate showDate);

    // SELECT * FROM showtime WHERE showtime_id = ?
    Optional<ShowtimeModel> findByShowtimeId(int showtimeId);

    // ORDERED active shows (replacement for findAllActive)
    List<ShowtimeModel> findByShowStatusOrderByShowDateAscMovieIdAscStartTimeAsc(String showStatus);

    // DELETE FROM showtime WHERE movie_id = ?
    void deleteByMovieId(int movieId);
}