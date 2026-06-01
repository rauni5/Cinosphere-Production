package com.cinosphere.service;

import com.cinosphere.dto.ShowtimeRequest;
import com.cinosphere.model.ShowtimeModel;
import com.cinosphere.repository.ShowtimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Handles showtime CRUD.
 *
 * Changes from original ShowtimeService:
 *  - Accepts ShowtimeRequest DTO instead of individual parameters.
 *  - insertShowtime() returns the saved entity (with its generated ID)
 *    instead of a boolean.
 *  - Injected ShowtimeRepository instead of new ShowtimeDAO().
 */
@Service
public class ShowtimeService {

    @Autowired
    private ShowtimeRepository showtimeRepository;

    public ShowtimeModel getShowtimeById(int showtimeId) {
        return showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new IllegalArgumentException("Showtime not found: " + showtimeId));
    }

    public List<ShowtimeModel> getShowtimesByScreenId(int screenId) {
        return showtimeRepository.findByScreenId(screenId);
    }

    public List<ShowtimeModel> getShowtimesByMovieId(int movieId) {
        return showtimeRepository.findByMovieId(movieId);
    }

    public ShowtimeModel createShowtime(ShowtimeRequest request) {
        ShowtimeModel showtime = new ShowtimeModel();
        showtime.setScreenId(request.getScreenId());
        showtime.setMovieId(request.getMovieId());
        showtime.setShowDate(request.getShowDate());
        showtime.setStartTime(request.getStartTime());
        showtime.setEndTime(request.getEndTime());
        showtime.setShowStatus(request.getShowStatus());
        showtime.setShowType(request.getShowType());
        return showtimeRepository.save(showtime);
    }

    public void deleteShowtimesByMovieId(int movieId) {
        showtimeRepository.deleteByMovieId(movieId);
    }
}