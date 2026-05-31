package com.cinosphere.repository;

import com.cinosphere.model.FeedbackModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<FeedbackModel, Integer> {

    // SELECT * FROM feedback WHERE user_id = ?
    List<FeedbackModel> findByUserId(int userId);

    // SELECT * FROM feedback WHERE movie_id = ?
    List<FeedbackModel> findByMovieId(int movieId);

    // SELECT * FROM feedback
    List<FeedbackModel> findAll();
}