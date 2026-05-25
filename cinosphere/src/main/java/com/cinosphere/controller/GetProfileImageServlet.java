package com.cinosphere.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
/**
 * Servlet implementation class GetProfileImageServlet
 * 
 * This servlet is responsible for retrieving and serving user profile images.
 * It fetches the image file from the server storage based on the provided
 * username parameter. If a user-specific image is not found, it falls back to
 * a default profile image. The image is then streamed directly to the client
 * with the appropriate MIME type for rendering in the browser.
 * 
 * @author Raunit Giri
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/profileimage" })
public class GetProfileImageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String UPLOAD_DIR =System.getProperty("user.home")+ File.separator+ "webassets"+ File.separator+"profile";   
    private static final String DEFAULT_IMAGE_NAME = "default";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        File folder = new File(UPLOAD_DIR);
        File imageFile = null;

        if (folder.exists() && folder.isDirectory()) {
            File[] matches = folder.listFiles((dir, fileName) -> fileName.startsWith(name + "."));
            if (matches != null && matches.length > 0) {
                imageFile = matches[0];
            }
        }
        if (imageFile == null || !imageFile.exists()) {
        	File[] matches = folder.listFiles((dir, fileName) -> fileName.startsWith(DEFAULT_IMAGE_NAME + "."));
            if (matches != null && matches.length > 0) {
                imageFile = matches[0];
            }
        }
        if (imageFile.exists()) {
            String contentType = getServletContext().getMimeType(imageFile.getName());
            if (contentType == null) contentType = "image/png";

            response.setContentType(contentType);
            response.setContentLength((int) imageFile.length());
            Files.copy(imageFile.toPath(), response.getOutputStream());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found and no default available");
        }
    }
}