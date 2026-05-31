package com.cinosphere.repository;

import com.cinosphere.model.SeatModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<SeatModel, Integer> {

    // SELECT * FROM seat WHERE screen_id = ?
    List<SeatModel> findByScreenId(int screenId);

    // SELECT * FROM seat WHERE seat_id = ?
    Optional<SeatModel> findBySeatId(int seatId);
}