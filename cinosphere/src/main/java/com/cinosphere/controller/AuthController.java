package com.cinosphere.controller;

import com.cinosphere.dto.ApiResponse;
import com.cinosphere.dto.LoginRequest;
import com.cinosphere.dto.LoginResponse;
import com.cinosphere.dto.RegisterRequest;
import com.cinosphere.service.LoginService;
import com.cinosphere.service.RegisterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * POST /api/auth/login    — returns JWT + user info
 * POST /api/auth/register — creates account, waits for admin activation
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private LoginService    loginService;
    @Autowired private RegisterService registerService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = loginService.authenticate(request);
        return ResponseEntity.ok(ApiResponse.ok("Login successful", response));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(
            @Valid @RequestBody RegisterRequest request) {

        registerService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Registration successful. Await admin approval."));
    }
}
