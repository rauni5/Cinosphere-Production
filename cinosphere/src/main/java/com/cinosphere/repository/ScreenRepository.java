package com.cinosphere.repository;

import com.cinosphere.model.ScreenModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScreenRepository extends JpaRepository<ScreenModel, Integer> {

    // SELECT * FROM screen WHERE theatre_id = ?
    List<ScreenModel> findByTheatreId(int theatreId);

    // SELECT * FROM screen WHERE screen_id = ?
    Optional<ScreenModel> findByScreenId(int screenId);
}