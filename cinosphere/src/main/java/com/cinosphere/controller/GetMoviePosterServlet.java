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
 * Servlet implementation class GetMoviePosterServlet
 * 
 * This servlet is responsible for retrieving and serving movie poster images
 * from the server storage. It fetches the poster based on the provided movie
 * name parameter(movie ID) and streams the image directly to the client. If the specific
 * movie poster is not found, it falls back to a default poster image to ensure
 * a consistent UI experience across the application.
 * 
 * @author Raunit Giri
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/movieposter" })
public class GetMoviePosterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIR =System.getProperty("user.home")+ File.separator+ "webassets"+ File.separator+"poster";   
	private static final String DEFAULT_IMAGE_NAME = "default";
	/**
	 * Handles GET requests for retrieving movie poster images. It searches the
	 * server directory for a poster matching the given movie name. If no match
	 * is found, a default poster image is used. The resolved image is then written
	 * directly to the HTTP response with the correct MIME type.
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
