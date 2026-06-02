package com.cinosphere.service;

import com.cinosphere.config.FileStorageConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

/**
 * Centralised file I/O service — replaces FileuploadUtil.
 *
 * All upload directories are under ~/webassets/ (same root as the old servlets)
 * so existing files on disk continue to work without moving anything.
 *
 * Methods:
 *   saveMoviePoster(file, movieId)      → saves to ~/webassets/poster/
 *   saveMovieBackground(file, movieId)  → saves to ~/webassets/background/
 *   saveProfileImage(file, username)    → saves to ~/webassets/profile/
 *   resolveFile(dir, name)             → finds file by prefix, returns File or null
 */
@Service
public class FileStorageService {

    // ------------------------------------------------------------------
    // Movie poster
    // ------------------------------------------------------------------

    /**
     * Saves a movie poster and returns the filename saved (not the full path).
     * The filename format is "{movieId}.{ext}" so it matches what
     * GetMoviePosterServlet looked for: files starting with the movie ID.
     */
    public String saveMoviePoster(MultipartFile file, int movieId) throws IOException {
        return save(file, FileStorageConfig.POSTER_DIR, String.valueOf(movieId));
    }

    /** Returns the poster File for a given movieId, or the default if absent. */
    public File resolveMoviePoster(int movieId) {
        return resolveWithDefault(FileStorageConfig.POSTER_DIR, String.valueOf(movieId));
    }

    // ------------------------------------------------------------------
    // Movie background
    // ------------------------------------------------------------------

    public String saveMovieBackground(MultipartFile file, int movieId) throws IOException {
        return save(file, FileStorageConfig.BACKGROUND_DIR, String.valueOf(movieId));
    }

    public File resolveMovieBackground(int movieId) {
        return resolveWithDefault(FileStorageConfig.BACKGROUND_DIR, String.valueOf(movieId));
    }

    // ------------------------------------------------------------------
    // Profile image
    // ------------------------------------------------------------------

    /**
     * Saves a profile image as "{username}.{ext}".
     * Overwrites any previous image for that username (same as the old servlet).
     */
    public String saveProfileImage(MultipartFile file, String username) throws IOException {
        // Delete old image(s) for this user before saving the new one
        deleteByPrefix(FileStorageConfig.PROFILE_DIR, username);
        return save(file, FileStorageConfig.PROFILE_DIR, username);
    }

    /** Returns the profile image File for a username, or the default if absent. */
    public File resolveProfileImage(String username) {
        return resolveWithDefault(FileStorageConfig.PROFILE_DIR, username);
    }

    // ------------------------------------------------------------------
    // Private helpers
    // ------------------------------------------------------------------

    /**
     * Saves a MultipartFile to targetDir with name "{prefix}.{ext}".
     * Creates the directory if it does not exist.
     * Returns the saved filename.
     */
    private String save(MultipartFile file, String targetDir, String prefix) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File must not be empty");
        }

        Path dir = Paths.get(targetDir);
        Files.createDirectories(dir);

        String originalName = file.getOriginalFilename() != null
                ? file.getOriginalFilename() : "file.jpg";
        String ext      = originalName.contains(".")
                ? originalName.substring(originalName.lastIndexOf('.'))
                : ".jpg";
        String filename = prefix + ext;

        Path target = dir.resolve(filename);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        return filename;
    }

    /**
     * Finds the first file in dir whose name starts with prefix.
     * Falls back to a file starting with "default" if no match.
     * Returns null if neither exists (caller handles 404).
     */
    private File resolveWithDefault(String dir, String prefix) {
        File folder = new File(dir);
        if (!folder.exists() || !folder.isDirectory()) return null;

        File match = findByPrefix(folder, prefix);
        if (match != null) return match;

        return findByPrefix(folder, FileStorageConfig.DEFAULT_IMAGE);
    }

    /** Scans a directory for the first file starting with the given prefix. */
    public File resolveFile(String dir, String prefix) {
        return resolveWithDefault(dir, prefix);
    }

    private File findByPrefix(File folder, String prefix) {
        File[] matches = folder.listFiles((d, name) -> name.startsWith(prefix + "."));
        return (matches != null && matches.length > 0) ? matches[0] : null;
    }

    /** Deletes all files in dir that start with prefix (used before re-upload). */
    private void deleteByPrefix(String dir, String prefix) {
        File folder = new File(dir);
        if (!folder.exists()) return;
        File[] old = folder.listFiles((d, name) -> name.startsWith(prefix + "."));
        if (old != null) for (File f : old) f.delete();
    }
}