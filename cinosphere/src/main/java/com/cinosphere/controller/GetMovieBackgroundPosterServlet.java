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
 * Servlet implementation class GetMovieBackgroundPosterServlet
 * 
 * This servlet is responsible for retrieving and serving movie background
 * images used in the application UI. It fetches the background image based
 * on the provided movie name parameter and streams it directly to the client.
 * If the specific background image is not found, a default image is used to
 * ensure the UI remains visually consistent across all movie pages.
 * 
 * @author Raunit Giri
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/background" })
public class GetMovieBackgroundPosterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIR =System.getProperty("user.home")+ File.separator+ "webassets"+ File.separator+"background";   
	private static final String DEFAULT_IMAGE_NAME = "default";
	/**
	 * Handles GET requests for retrieving movie background images. It searches the
	 * server directory for a background image matching the given movie name. If
	 * not found, a default image is used. The resolved image is then streamed
	 * directly to the HTTP response with the appropriate MIME type.
	 *
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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

        // Fall back to default image if not found
        if (imageFile == null || !imageFile.exists()) {
        	File[] matches = folder.listFiles((dir, fileName) -> fileName.startsWith(DEFAULT_IMAGE_NAME + "."));
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