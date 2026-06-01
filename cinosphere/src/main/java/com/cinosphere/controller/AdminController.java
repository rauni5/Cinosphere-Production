package com.cinosphere.controller;

import com.cinosphere.model.UsersModel;
import com.cinosphere.model.BookingModel;
import com.cinosphere.service.UserService;
import com.cinosphere.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    private BookingService bookingService;

    @GetMapping("/users")
    public ResponseEntity<List<UsersModel>> getUsers() {
        return ResponseEntity.ok(
                userService.getAllUsers()
        );
    }
    @GetMapping("/bookings")
    public ResponseEntity<List<BookingModel>> getBookings() {
        return ResponseEntity.ok(
                bookingService.getAllBookings()
        );
    }
    
}