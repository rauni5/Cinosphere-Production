package com.cinosphere.controller;

import com.cinosphere.dto.UpdatePasswordRequest;
import com.cinosphere.dto.UpdateProfileRequest;
import com.cinosphere.model.UsersModel;
import com.cinosphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UsersModel> getProfile(Authentication auth) {
        return ResponseEntity.ok(
                userService.getUserByUsername(auth.getName())
        );
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateProfile(
            @RequestBody UpdateProfileRequest request,
            Authentication auth
    ) {
        UsersModel updatedUser =
                userService.updateProfile(auth.getName(), request);

        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/me/password")
    public ResponseEntity<?> updatePassword(
            @RequestBody UpdatePasswordRequest request,
            Authentication auth
    ) {
        userService.updatePassword(auth.getName(), request);

        return ResponseEntity.ok(
                Map.of("message", "Password changed successfully")
        );
    }

    @DeleteMapping("/me")
    public ResponseEntity<?> deleteAccount(Authentication auth) {
        UsersModel user =
                userService.getUserByUsername(auth.getName());

        userService.deactivateUser(user.getUserId());

        return ResponseEntity.ok(
                Map.of("message", "Account deactivated")
        );
    }
}