package com.cinosphere.service;

import com.cinosphere.dto.RegisterRequest;
import com.cinosphere.model.MembershipModel;
import com.cinosphere.model.UsersModel;
import com.cinosphere.repository.MembershipRepository;
import com.cinosphere.repository.UserRepository;
import com.cinosphere.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Handles user registration.
 *
 * Changes from original RegisterService:
 *  - Format validation (name patterns, email, gender) moved to @Valid annotations
 *    on RegisterRequest — no manual if/else chains needed here.
 *  - Only business-rule checks remain: duplicate username, duplicate email,
 *    and password confirmation match.
 *  - @Transactional ensures the user insert and membership insert
 *    both succeed or both roll back.
 *  - Injected repositories instead of new UsersDAO() / new MembershipDAO().
 */
@Service
public class RegisterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MembershipRepository membershipRepository;

    /**
     * Validates business rules and registers the user + starter membership.
     * Throws IllegalArgumentException for any rule violation so the controller
     * can return 400 with the exact message.
     */
    @Transactional
    public void register(RegisterRequest request) {
        // passwords must match
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        // duplicate checks
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        // build and save user
        UsersModel user = new UsersModel();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setGender(request.getGender());
        user.setHashPassword(PasswordUtil.getHashPassword(request.getPassword()));
        user.setRegistrationDate(LocalDate.now());
        user.setisActive(false);   // requires admin activation
        user.setUserRole("CUSTOMER");

        UsersModel saved = userRepository.save(user);

        // create starter membership
        MembershipModel membership = new MembershipModel();
        membership.setUserId(saved.getUserId());
        membership.setMembershipType("STARTER");
        membership.setMembershipStatus("Active");
        membership.setTotalLoyaltyPoints(0);
        membership.setDiscountPercentage(BigDecimal.valueOf(0));
        membershipRepository.save(membership);
    }
}