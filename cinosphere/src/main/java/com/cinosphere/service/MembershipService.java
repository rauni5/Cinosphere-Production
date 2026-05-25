package com.cinosphere.service;

import java.util.ArrayList;
import java.util.List;

import com.cinosphere.dao.MembershipDAO;
import com.cinosphere.model.MembershipModel;
import com.cinosphere.model.UsersModel;
import com.cinosphere.utils.SessionUtil;

import jakarta.servlet.http.HttpServletRequest;
/**
 * Service Class that is the bridge between Servlet and MembershipDAO
 * Contains methods used to call methods of DAO and perform interaction with DB Membership Table
 * 
 * @author Raunit Giri
 */
public class MembershipService {
	private MembershipDAO membershipDAO = new MembershipDAO();
	/**
	 * Finds membership using userID
	 * @param userId
	 * @return membership
	 * @throws Exception
	 */
	public MembershipModel getByUserId(int userId) throws Exception {

			return membershipDAO.findByUserId(userId);


}
	
	/**
	 * Finds memberships of all users given
	 * @param users
	 * @return membership List
	 * @throws Exception
	 */
	public List<MembershipModel> getMemberships(List<UsersModel> users) throws Exception {
		List<MembershipModel> memberships = new ArrayList<>();
		
		for(UsersModel user: users) {
			
			MembershipModel membership = membershipDAO.findByUserId(user.getUserId());
			
			memberships.add(membership);
		}
		
		return memberships;
		
	}
	
/**
 * Update loyalty points of membership using userId
 * @param userId
 * @param newPoints
 * @throws Exception
 */
	public void updateMembershipLoyaltyPoints(int userId, int newPoints) throws Exception {
		membershipDAO.updateMembershipLoyaltyPoints(userId, newPoints);
		
	}
}
