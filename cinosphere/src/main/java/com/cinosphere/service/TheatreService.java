package com.cinosphere.service;

import com.cinosphere.model.TheatreModel;
import com.cinosphere.repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Handles theatre queries.
 *
 * Changes from original TheatreService:
 *  - Added getAll() — needed by AdminController for dropdowns.
 *  - Throws IllegalArgumentException instead of returning null.
 *  - Injected TheatreRepository instead of new TheatreDAO().
 */
@Service
public class TheatreService {

    @Autowired
    private TheatreRepository theatreRepository;

    public TheatreModel getTheatreById(int theatreId) {
        return theatreRepository.findById(theatreId)
                .orElseThrow(() -> new IllegalArgumentException("Theatre not found: " + theatreId));
    }

    public List<TheatreModel> getAllTheatres() {
        return theatreRepository.findAll();
    }
}