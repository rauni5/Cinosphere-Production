package com.cinosphere.service;

import com.cinosphere.model.SeatModel;
import com.cinosphere.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Handles seat queries.
 *
 * Changes from original SeatService:
 *  - Throws IllegalArgumentException (not null) when a seat is not found.
 *  - Injected SeatRepository instead of new SeatDAO().
 */
@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    public List<SeatModel> getSeatsByScreenId(int screenId) {
        return seatRepository.findByScreenId(screenId);
    }

    public SeatModel getSeatById(int seatId) {
        return seatRepository.findById(seatId)
                .orElseThrow(() -> new IllegalArgumentException("Seat not found: " + seatId));
    }
}