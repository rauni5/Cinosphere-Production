package com.cinosphere.controller;

import com.cinosphere.dto.ApiResponse;
import com.cinosphere.dto.UpdatePasswordRequest;
import com.cinosphere.dto.UpdateProfileRequest;
import com.cinosphere.model.UsersModel;
import com.cinosphere.service.BookingService;
import com.cinosphere.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * GET    /api/users/me           — current user's profile  (UpdateProfile.jsx, UserPanel.jsx)
 * PUT    /api/users/me           — update profile fields   (UpdateProfile.jsx)
 * PUT    /api/users/me/password  — change password         (UpdateProfile.jsx)
 * DELETE /api/users/me           — deactivate account      (UpdateProfile.jsx)
 * GET    /api/users/me/stats     — dashboard stat numbers  (UserPanel.jsx)
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired private UserService    userService;
    @Autowired private BookingService bookingService;

    // ----------------------------------------------------------------
    // Profile
    // ----------------------------------------------------------------

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UsersModel>> getProfile(Authentication auth) {
        UsersModel user = userService.getUserById(Integer.parseInt(auth.getName()));
        return ResponseEntity.ok(ApiResponse.ok("OK", user));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UsersModel>> updateProfile(
            @Valid @RequestBody UpdateProfileRequest request,
            Authentication auth) {

        UsersModel updated = userService.updateProfile(Integer.parseInt(auth.getName()), request);
        return ResponseEntity.ok(ApiResponse.ok("Profile updated", updated));
    }

    @PutMapping("/me/password")
    public ResponseEntity<ApiResponse<Void>> updatePassword(
            @Valid @RequestBody UpdatePasswordRequest request,
            Authentication auth) {

        userService.updatePassword(Integer.parseInt(auth.getName()), request);
        return ResponseEntity.ok(ApiResponse.ok("Password updated"));
    }

    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse<Void>> deactivateAccount(Authentication auth) {
        UsersModel user = userService.getUserById(Integer.parseInt(auth.getName()));
        userService.deactivateUser(user.getUserId());
        return ResponseEntity.ok(ApiResponse.ok("Account deactivated"));
    }

    // ----------------------------------------------------------------
    // Dashboard stats — used by UserPanel.jsx
    // Returns a flat map so the frontend doesn't need a new DTO class
    // ----------------------------------------------------------------

    @GetMapping("/me/stats")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getMyStats(Authentication auth) {
        UsersModel user = userService.getUserById(Integer.parseInt(auth.getName()));
        int userId      = user.getUserId();

        Map<String, Object> stats = Map.of(
            "totalBookings",     bookingService.getTotalBookingsByUser(userId),
            "upcomingBookings",  bookingService.getTotalUpcomingBookings(userId),
            "bookingsThisMonth", bookingService.getTotalBookingsThisMonth(userId),
            "lastPointsEarned",  bookingService.getLatestLoyaltyPointsEarned(userId),
            "nextShowDate",      bookingService.getLatestComingBookingDate(userId) != null
                                     ? bookingService.getLatestComingBookingDate(userId).toString()
                                     : "None"
        );

        return ResponseEntity.ok(ApiResponse.ok("OK", stats));
    }
}
