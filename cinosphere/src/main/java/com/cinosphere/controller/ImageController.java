package com.cinosphere.controller;

import com.cinosphere.config.FileStorageConfig;
import com.cinosphere.dto.ApiResponse;
import com.cinosphere.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Handles profile photo upload and serving.
 *
 * The WebConfig resource handler serves files in ~/webassets/profile/ directly
 * at /uploads/profiles/**, so a plain GET works for existing named files.
 * This controller adds two things the static handler can't do:
 *
 *   POST /api/users/me/photo
 *       Accepts a multipart image, saves it as {username}.{ext} in the
 *       profile directory, replacing any previous image.
 *       Used by UpdateProfile.jsx when the user picks a new avatar.
 *
 *   GET /uploads/profiles/default
 *       Falls back to the default image when a user has no photo yet.
 *       The React components already use /uploads/profiles/{username} as the
 *       src — the static handler serves it if the file exists; this endpoint
 *       is the fallback the browser hits if the static handler returns 404.
 *       (In practice you'll put a default.jpg in ~/webassets/profile/ and the
 *        static handler will serve it directly — this controller is the safety net.)
 */
@RestController
public class ImageController {

    @Autowired
    private FileStorageService fileStorageService;

    // ----------------------------------------------------------------
    // Upload — POST /api/users/me/photo
    // ----------------------------------------------------------------

    /**
     * Saves the uploaded image as {username}.{ext} in ~/webassets/profile/.
     * Replaces any previous photo for that user.
     *
     * Frontend usage (UpdateProfile.jsx):
     *   const fd = new FormData()
     *   fd.append('photo', file)
     *   await api.postForm('/api/users/me/photo', fd)
     */
    @PostMapping(value = "/api/users/me/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<String>> uploadProfilePhoto(
            @RequestParam("photo") MultipartFile photo,
            Authentication auth) throws IOException {

        String username = auth.getName();
        String savedFilename = fileStorageService.saveProfileImage(photo, username);

        // Return the public URL the browser can immediately use
        String publicUrl = "/uploads/profiles/" + savedFilename;
        return ResponseEntity.ok(ApiResponse.ok("Profile photo updated", publicUrl));
    }

    // ----------------------------------------------------------------
    // Serve — GET /uploads/profiles/{filename}
    // Fallback: if static handler misses (no file exists), this streams
    // the default image instead of returning 404 to the browser.
    // ----------------------------------------------------------------

    /**
     * Streams a profile image by filename.
     * If the requested file doesn't exist, streams the default profile image.
     * If neither exists, returns 404.
     *
     * This endpoint is hit when the static resource handler can't find the file
     * (e.g. a user has never uploaded a photo).
     *
     * Note: WebConfig maps /uploads/profiles/** to ~/webassets/profile/ as a
     * static resource — this @GetMapping only fires if the file is NOT found
     * by the static handler (Spring tries static resources before controllers).
     */
    @GetMapping(value = "/api/uploads/profiles/{filename:.+}")
    public ResponseEntity<byte[]> serveProfileImage(
            @PathVariable String filename) throws IOException {

        // Strip extension to get prefix (e.g. "raunit.jpg" → "raunit")
        String prefix = filename.contains(".")
                ? filename.substring(0, filename.lastIndexOf('.'))
                : filename;

        File imageFile = fileStorageService.resolveFile(FileStorageConfig.PROFILE_DIR, prefix);

        if (imageFile == null || !imageFile.exists()) {
            return ResponseEntity.notFound().build();
        }

        String contentType = Files.probeContentType(imageFile.toPath());
        if (contentType == null) contentType = "image/jpeg";

        byte[] bytes = Files.readAllBytes(imageFile.toPath());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(bytes);
    }

    @GetMapping(value = "/api/uploads/movies/{filename:.+}")
    public ResponseEntity<byte[]> serveMovieImage(
            @PathVariable String filename) throws IOException {

        String prefix = filename.contains(".")
                ? filename.substring(0, filename.lastIndexOf('.'))
                : filename;

        File imageFile = fileStorageService.resolveFile(FileStorageConfig.POSTER_DIR, prefix);

        if (imageFile == null || !imageFile.exists()) {
            return ResponseEntity.notFound().build();
        }

        String contentType = Files.probeContentType(imageFile.toPath());
        if (contentType == null) contentType = "image/jpeg";

        byte[] bytes = Files.readAllBytes(imageFile.toPath());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(bytes);
    }
    @GetMapping(value = "/api/uploads/movies/background/{filename:.+}")
        public ResponseEntity<byte[]> serveMovieBackground(
                @PathVariable String filename) throws IOException {

            String prefix = filename.contains(".")
                    ? filename.substring(0, filename.lastIndexOf('.'))
                    : filename;

            File imageFile = fileStorageService.resolveFile(FileStorageConfig.BACKGROUND_DIR, prefix);

            if (imageFile == null || !imageFile.exists()) {
                return ResponseEntity.notFound().build();
            }

            String contentType = Files.probeContentType(imageFile.toPath());
            if (contentType == null) contentType = "image/jpeg";

            byte[] bytes = Files.readAllBytes(imageFile.toPath());
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(bytes);
        }
        @GetMapping(value = "/api/uploads/logo/{filename:.+}")
        public ResponseEntity<byte[]> serveLogo(
                @PathVariable String filename) throws IOException {

            String prefix = filename.contains(".")
                    ? filename.substring(0, filename.lastIndexOf('.'))
                    : filename;

            File imageFile = fileStorageService.resolveFile(FileStorageConfig.LOGO_DIR, prefix);

            if (imageFile == null || !imageFile.exists()) {
                return ResponseEntity.notFound().build();
            }

            String contentType = Files.probeContentType(imageFile.toPath());
            if (contentType == null) contentType = "image/jpeg";

            byte[] bytes = Files.readAllBytes(imageFile.toPath());
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(bytes);
        }
}