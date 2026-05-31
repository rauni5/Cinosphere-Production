package com.cinosphere.repository;

import com.cinosphere.model.TheatreModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TheatreRepository extends JpaRepository<TheatreModel, Integer> {

    // SELECT * FROM theatre WHERE theatre_id = ?
    Optional<TheatreModel> findByTheatreId(int theatreId);
}