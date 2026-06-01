package com.cinosphere.service;

import com.cinosphere.dto.LoginRequest;
import com.cinosphere.dto.LoginResponse;
import com.cinosphere.model.UsersModel;
import com.cinosphere.repository.UserRepository;
import com.cinosphere.util.JwtUtil;
import com.cinosphere.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Handles user authentication.
 *
 * Changes from original LoginService:
 *  - No HttpServletRequest / HttpServletResponse parameters anywhere.
 *  - No SessionUtil or CookieUtil — auth state lives in the JWT returned to the client.
 *  - Returns a typed LoginResponse DTO instead of a raw "Success" / error String.
 *  - Injected UserRepository instead of new UsersDAO().
 */
@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Authenticates credentials and returns a LoginResponse containing a JWT
     * on success, or throws an IllegalArgumentException with the error message
     * on failure (the controller catches this and returns 401).
     */
    public LoginResponse authenticate(LoginRequest request) {
        UsersModel user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User doesn't exist"));

        if (!user.getisActive()) {
            throw new IllegalArgumentException("Account pending admin approval");
        }

        if (!PasswordUtil.checkPassword(request.getPassword(), user.getHashPassword())) {
            throw new IllegalArgumentException("Password is incorrect");
        }

        String token = jwtUtil.generateToken(user.getUserId(),user.getUsername(), user.getUserRole());

        return new LoginResponse(
                token,
                user.getUserId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getUserRole()
        );
    }
}