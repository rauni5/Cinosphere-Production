package com.cinosphere.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Servlet implementation class GetIconServlet
 * 
 * This servlet is responsible for retrieving and serving icon images stored
 * on the server. It fetches the requested icon based on the provided name
 * parameter and streams the image directly to the client. This enables dynamic
 * icon rendering throughout the application UI components.
 * 
 * @author Raunit Giri
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/icon" })
public class GetIconServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIR =System.getProperty("user.home")+ File.separator+ "webassets"+ File.separator+"icon";   

	/**
	 * Handles GET requests for retrieving icon images. It validates the request
	 * parameter, searches the server directory for a matching icon file, and
	 * streams the image to the HTTP response with the correct MIME type. If the
	 * icon is not found, an appropriate error response is returned.
	 *
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
        if (name == null || name.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing name parameter");
            return;
        }

        File folder = new File(UPLOAD_DIR);
        File imageFile = null;

        if (folder.exists() && folder.isDirectory()) {
            File[] matches = folder.listFiles((dir, fileName) -> fileName.startsWith(name + "."));
            if (matches != null && matches.length > 0) {
                imageFile = matches[0];
            }
        }
        // Serve whatever file we resolved
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
