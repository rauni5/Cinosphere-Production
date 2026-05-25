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
 * Servlet implementation class GetLogoServlet
 * 
 * This servlet is responsible for retrieving and serving logo images stored
 * on the server. It fetches the appropriate logo based on the provided name
 * parameter and streams it directly to the client for rendering in the UI.
 * This allows dynamic logo loading across different parts of the application.
 * 
 * @author Raunit Giri
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/logo" })
public class GetLogoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIR =System.getProperty("user.home")+ File.separator+ "webassets"+ File.separator+"logo";   

	/**
	 * Handles GET requests for retrieving logo images. It searches the server
	 * directory for a logo matching the provided name parameter and writes the
	 * image directly to the HTTP response with the correct content type. If no
	 * matching image is found, a 404 error is returned.
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
        // Serve whatever file we resolved
        if (imageFile != null && imageFile.exists()) {
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
