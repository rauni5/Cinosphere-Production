package com.cinosphere.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

/**
 * DTO for new-user registration.
 * Bean-Validation annotations enforce the same rules that were
 * previously scattered across RegisterService.Authentication().
 * The service layer now only handles business logic (duplicate checks,
 * password hashing, DB inserts) — not format validation.
 */
public class RegisterRequest {

    @NotBlank(message = "First name is required")
    @Pattern(regexp = "[a-zA-Z]+", message = "First name must contain letters only")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Pattern(regexp = "[a-zA-Z]+", message = "Last name must contain letters only")
    private String lastName;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email address")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "(?i)male|female|other", message = "Gender must be male, female, or other")
    private String gender;

    public String getFirstName()       { return firstName; }
    public void setFirstName(String v) { this.firstName = v; }

    public String getLastName()        { return lastName; }
    public void setLastName(String v)  { this.lastName = v; }

    public String getUsername()        { return username; }
    public void setUsername(String v)  { this.username = v; }

    public String getEmail()           { return email; }
    public void setEmail(String v)     { this.email = v; }

    public String getPassword()        { return password; }
    public void setPassword(String v)  { this.password = v; }

    public String getConfirmPassword()        { return confirmPassword; }
    public void setConfirmPassword(String v)  { this.confirmPassword = v; }

    public LocalDate getDateOfBirth()         { return dateOfBirth; }
    public void setDateOfBirth(LocalDate v)   { this.dateOfBirth = v; }

    public String getGender()          { return gender; }
    public void setGender(String v)    { this.gender = v; }
}