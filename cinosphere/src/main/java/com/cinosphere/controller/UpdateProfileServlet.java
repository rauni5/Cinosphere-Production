package com.cinosphere.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import com.cinosphere.dao.UsersDAO;
import com.cinosphere.model.MembershipModel;
import com.cinosphere.model.UsersModel;
import com.cinosphere.service.LoginService;
import com.cinosphere.service.MembershipService;
import com.cinosphere.utils.FileuploadUtil;
import com.cinosphere.utils.SessionUtil;


/**
 * Servlet implementation class UpdateProfileServlet
 * 
 * This servlet handles user profile updates including personal information
 * such as name, email, date of birth, and profile image upload. It retrieves
 * the current user from the session, updates the database with the new details,
 * refreshes the session data after a successful update, and manages profile
 * image replacement by storing the new image and removing the previous one.
 * The updated profile information is then reflected in the user session and
 * the user is redirected back to the profile page.
 * 
 *@author Raunit Giri
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/updateprofile" })
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024 * 2,
	    maxFileSize = 1024 * 1024 * 10,
	    maxRequestSize = 1024 * 1024 * 50
	)
public class UpdateProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIR =System.getProperty("user.home")+ File.separator+ "webassets"+ File.separator+"profile";  // Directory where profile images are stored 
	private UsersDAO usersdao = new UsersDAO();
    private MembershipService membershipService = new MembershipService();
    
	/**
	 * Handles GET requests for the update profile page. It retrieves the logged-in
	 * user’s membership details and forwards them to the update profile view for
	 * display and editing.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UsersModel user = (UsersModel) SessionUtil.getAttribute(request, "user");
		try {
			MembershipModel membership = membershipService.getByUserId(user.getUserId());
			request.setAttribute("membership", membership);
		} catch (Exception e) {
			request.setAttribute("error", "Failed to load profile details.");
			e.printStackTrace();
		}
		request.setAttribute("activeTab", "updateprofile");
		request.getRequestDispatcher("/WEB-INF/pages/updateProfile.jsp").forward(request, response);
	}

	/**
	 * 
	 * Handles POST requests for updating user profile information. It processes form
	 * data submitted by the user, updates the database records, refreshes session
	 * information, and manages profile image upload if provided. After processing,
	 * the user is redirected to the profile page or back to the update page in case
	 * of an error.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UsersModel user = (UsersModel) SessionUtil.getAttribute(request, "user");
		String firstName = request.getParameter("userFirstName");
		String lastName = request.getParameter("userLastName");
		String email = request.getParameter("userEmail");
		LocalDate dob = LocalDate.parse(request.getParameter("userDob"));
		try {
			boolean isUpdated = usersdao.UpdateUser(user.getUserId(), firstName, lastName, email,dob);
			if(isUpdated) {
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setEmail(email);
				user.setDateOfBirth(dob);
				LoginService login = new LoginService();
				login.login(user, request);
				Part filePart = request.getPart("profileAvatarInput");
				 if (FileuploadUtil.isImage(filePart)) {
					 
					 String userId = String.valueOf(user.getUserId());
					 File folder = new File(UPLOAD_DIR);
					    File[] oldFiles = folder.listFiles((dir, name) -> name.startsWith(userId + "."));
					    if (oldFiles != null) {
					        for (File old : oldFiles) {
					            old.delete();
					        }
					    }
					 
					 String extension = FileuploadUtil.getFileExtension(filePart.getSubmittedFileName());
                     String fileName = userId + extension;
                     FileuploadUtil.saveFile(filePart, UPLOAD_DIR, fileName);
                     request.setAttribute("msg", "Update Success");
				 }
				 
			}else {
				request.setAttribute("error", "Update Failed");
				response.sendRedirect(request.getContextPath() + "/updateprofile");
				return;
			}
		} catch (Exception e) {
			request.setAttribute("error", "Unexpected error");
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/updateprofile");
			return;
		}
		response.sendRedirect(request.getContextPath() + "/profile");
		return;
	}
	

}
