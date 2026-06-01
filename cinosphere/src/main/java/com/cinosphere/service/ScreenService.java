package com.cinosphere.service;

import com.cinosphere.dto.UpdateBasePriceRequest;
import com.cinosphere.model.ScreenModel;
import com.cinosphere.repository.ScreenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Handles screen queries and base price updates.
 *
 * Changes from original ScreenService:
 *  - updateBasePrice() accepts the typed DTO instead of a raw double.
 *  - Injected ScreenRepository instead of new ScreenDAO().
 */
@Service
public class ScreenService {

    @Autowired
    private ScreenRepository screenRepository;

    public ScreenModel getScreenById(int screenId) {
        return screenRepository.findById(screenId)
                .orElseThrow(() -> new IllegalArgumentException("Screen not found: " + screenId));
    }

    public List<ScreenModel> getAllScreens() {
        return screenRepository.findAll();
    }

    public ScreenModel updateBasePrice(int screenId, UpdateBasePriceRequest request) {
        ScreenModel screen = getScreenById(screenId);
        screen.setBasePrice(request.getBasePrice());
        return screenRepository.save(screen);
    }
}