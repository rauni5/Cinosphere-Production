package com.cinosphere.service;

import com.cinosphere.model.MembershipModel;
import com.cinosphere.model.UsersModel;
import com.cinosphere.repository.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles membership queries and loyalty point updates.
 *
 * Changes from original MembershipService:
 *  - No HttpServletRequest parameter — no HTTP concerns here.
 *  - getMemberships(List<UsersModel>) simplified with a stream.
 *  - Injected MembershipRepository instead of new MembershipDAO().
 */
@Service
public class MembershipService {

    @Autowired
    private MembershipRepository membershipRepository;

    public MembershipModel getByUserId(int userId) {
        return membershipRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Membership not found for user: " + userId));
    }

    /**
     * Fetches memberships for a list of users in one go.
     * Missing memberships are silently skipped (orElse(null) + filter).
     */
    public List<MembershipModel> getMemberships(List<UsersModel> users) {
        return users.stream()
                .map(u -> membershipRepository.findByUserId(u.getUserId()).orElse(null))
                .filter(m -> m != null)
                .collect(Collectors.toList());
    }

    public void updateLoyaltyPoints(int userId, int newPoints) {
        MembershipModel membership = getByUserId(userId);
        membership.setTotalLoyaltyPoints(newPoints);
        membershipRepository.save(membership);
    }
}