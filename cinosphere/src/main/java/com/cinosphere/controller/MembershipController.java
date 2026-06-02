package com.cinosphere.controller;

import com.cinosphere.dto.ApiResponse;
import com.cinosphere.model.MembershipModel;
import com.cinosphere.model.UsersModel;
import com.cinosphere.service.MembershipService;
import com.cinosphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * GET /api/memberships/me — current user's membership + loyalty points
 *
 * Used by: UserPanel.jsx, UpdateProfile.jsx, Booking.jsx
 */
@RestController
@RequestMapping("/api/memberships")
public class MembershipController {

    @Autowired private MembershipService membershipService;
    @Autowired private UserService       userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<MembershipModel>> getMyMembership(Authentication auth) {
        UsersModel user       = userService.getUserById(Integer.parseInt(auth.getName()));
        MembershipModel mem   = membershipService.getByUserId(user.getUserId());
        return ResponseEntity.ok(ApiResponse.ok("OK", mem));
    }
}
