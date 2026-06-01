package com.cinosphere.controller;

import com.cinosphere.dto.MovieRequest;
import com.cinosphere.model.MovieModel;
import com.cinosphere.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<List<MovieModel>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllActiveMovies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieModel> getMovie(@PathVariable int id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @PostMapping(consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MovieModel> addMovie(
            @ModelAttribute MovieRequest request,
            @RequestParam(required = false) MultipartFile poster,
            @RequestParam(required = false) MultipartFile backgroundPoster
    ) throws IOException {

        MovieModel movie =
                movieService.addMovie(request, poster, backgroundPoster);

        return ResponseEntity.status(201).body(movie);
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MovieModel> updateMovie(
            @PathVariable int id,
            @ModelAttribute MovieRequest request,
            @RequestParam(required = false) MultipartFile poster,
            @RequestParam(required = false) MultipartFile backgroundPoster
    ) throws IOException {

        MovieModel movie =
                movieService.updateMovie(id, request, poster, backgroundPoster);

        return ResponseEntity.ok(movie);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> archiveMovie(
            @PathVariable int id) {

        movieService.archiveMovie(id);

        return ResponseEntity.ok(
                Map.of("message", "Movie archived")
        );
    }
}