package com.cinosphere.service;

import java.time.LocalDate;
import java.util.List;

import com.cinosphere.dao.UsersDAO;
import com.cinosphere.model.UsersModel;
/**
 * Service Class that is the bridge between Servlet and UsersDAO
 * Contains methods used to call methods of DAO and perform interaction with DB Users Table
 * 
 * @author Raunit Giri
 */

public class UserService {
	private UsersDAO usersDAO = new UsersDAO();
	/**
	 * Finds all users
	 * @return user list
	 * @throws Exception
	 */
	public List<UsersModel> getAllUsers() throws Exception {
        return usersDAO.getAllUser();
    }
	/**
	 * Deactivates a given user
	 * @param userId
	 * @return boolean
	 * @throws Exception
	 */
	public boolean deactivateUser(int userId) throws Exception {
        return usersDAO.deleteUser(userId);
    }
	/**
	 * Activate and verify a given user
	 * @param userId
	 * @return boolean
	 * @throws Exception
	 */
	public boolean activateUser(int userId) throws Exception {
        return usersDAO.ActivateUser(userId);
    }
	/**
	 * Find user by Status
	 * @param isActive
	 * @return user LSit
	 * @throws Exception
	 */
	public List<UsersModel> getUsersByStatus(boolean isActive) throws Exception {
		
		return usersDAO.getUserByStatus(isActive);
	}
	/**
	 * Finds user with similar username
	 * @param searchUser
	 * @return user list
	 * @throws Exception
	 */
	public List<UsersModel> findByUsernames(String searchUser) throws Exception {
		return usersDAO.findByUsernames(searchUser);
	}
	/**
	 * Finds count of users registered today
	 * @return count of new users
	 * @throws Exception
	 */
	public int getTodayNewUsers() throws Exception{
	    return usersDAO.getNewUsers(LocalDate.now());
	}
	/**
	 * Finds count of users registered yesterday
	 * @return count of users
	 * @throws Exception
	 */
	public int getYesterdayNewUsers() throws Exception{
	    return usersDAO.getNewUsers(LocalDate.now().minusDays(1));
	}
	/**
	 * Finds user with equal user Id
	 * @param userId
	 * @return User
	 * @throws Exception
	 */
	public UsersModel getUserById(int userId) throws Exception {
		return usersDAO.findByUserId(userId);
	}
}
