package com.cinosphere.controller;

import com.cinosphere.dto.LoginRequest;
import com.cinosphere.dto.LoginResponse;
import com.cinosphere.dto.RegisterRequest;
import com.cinosphere.service.LoginService;
import com.cinosphere.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RegisterService registerService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = loginService.authenticate(request);
       return ResponseEntity.ok(response);
    }
    
    @PostMapping("/register")
    public void register(@Valid @RequestBody RegisterRequest request) {
        registerService.register(request);
        
    }
}