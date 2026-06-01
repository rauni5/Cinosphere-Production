package com.cinosphere.service;

import com.cinosphere.dto.UpdatePasswordRequest;
import com.cinosphere.dto.UpdateProfileRequest;
import com.cinosphere.model.UsersModel;
import com.cinosphere.repository.UserRepository;
import com.cinosphere.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Handles user profile operations.
 *
 * Merges old UserService + UpdatePasswordService into a single class —
 * both operate on the same entity, so there is no reason to split them.
 *
 * Changes from originals:
 *  - No HttpServletRequest parameters; username comes from the JWT principal
 *    (passed in by the controller from Authentication.getName()).
 *  - No SessionUtil.setAttribute calls — the JWT already carries identity;
 *    the client re-fetches /api/users/me if it needs updated data.
 *  - Throws IllegalArgumentException for business-rule violations
 *    (consistent with LoginService / RegisterService pattern).
 *  - Injected UserRepository instead of new UsersDAO().
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // ---- lookup ----

    public List<UsersModel> getAllUsers() {
        return userRepository.findAll();
    }

    public UsersModel getUserById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public UsersModel getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public List<UsersModel> getUsersByStatus(boolean isActive) {
        return userRepository.findByIsActive(isActive);
    }

    public List<UsersModel> searchByUsername(String keyword) {
        return userRepository.findByUsernameContaining(keyword);
    }

    public int getTodayNewUsers() {
        return (int) userRepository.countByRegistrationDate(LocalDate.now());
    }

    public int getYesterdayNewUsers() {
        return (int) userRepository.countByRegistrationDate(LocalDate.now().minusDays(1));
    }

    // ---- profile update ----

    /**
     * Updates first name, last name, email, and date of birth.
     * Does not allow changing username or role.
     */
    public UsersModel updateProfile(String username, UpdateProfileRequest request) {
        UsersModel user = getUserByUsername(username);

        // if changing email, make sure no one else has it
        if (!user.getEmail().equalsIgnoreCase(request.getEmail()) &&
                userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use by another account");
        }

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setDateOfBirth(request.getDateOfBirth());

        return userRepository.save(user);
    }

    // ---- password change ----

    /**
     * Verifies the current password then replaces it with the new one.
     * Moved from UpdatePasswordService; no HttpServletRequest needed.
     */
    public void updatePassword(String username, UpdatePasswordRequest request) {
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("New passwords do not match");
        }

        UsersModel user = getUserByUsername(username);

        if (!PasswordUtil.checkPassword(request.getCurrentPassword(), user.getHashPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        user.setHashPassword(PasswordUtil.getHashPassword(request.getNewPassword()));
        userRepository.save(user);
    }

    // ---- admin actions ----

    public void deactivateUser(int userId) {
        UsersModel user = getUserById(userId);
        user.setisActive(false);
        userRepository.save(user);
    }

    public void activateUser(int userId) {
        UsersModel user = getUserById(userId);
        user.setisActive(true);
        userRepository.save(user);
    }
}