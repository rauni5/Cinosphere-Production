package com.cinosphere.dto;

/**
 * DTO returned after a successful login.
 * Carries the JWT token and basic user info so the client
 * can display the user's name and know their role immediately
 * without an extra /me call.
 */
public class LoginResponse {

    private String token;
    private int userId;
    private String username;
    private String firstName;
    private String lastName;
    private String userRole;

    public LoginResponse(String token, int userId, String username,
                         String firstName, String lastName, String userRole) {
        this.token     = token;
        this.userId    = userId;
        this.username  = username;
        this.firstName = firstName;
        this.lastName  = lastName;
        this.userRole  = userRole;
    }

    public String getToken()     { return token; }
    public int    getUserId()    { return userId; }
    public String getUsername()  { return username; }
    public String getFirstName() { return firstName; }
    public String getLastName()  { return lastName; }
    public String getUserRole()  { return userRole; }
}