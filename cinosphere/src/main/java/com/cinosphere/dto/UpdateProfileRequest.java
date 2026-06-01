package com.cinosphere.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

/**
 * DTO for profile update requests.
 * Maps to the fields UpdateProfileServlet used to read from the form.
 * Username and role are intentionally excluded — those are not user-editable.
 */
public class UpdateProfileRequest {

    @NotBlank(message = "First name is required")
    @Pattern(regexp = "[a-zA-Z]+", message = "First name must contain letters only")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Pattern(regexp = "[a-zA-Z]+", message = "Last name must contain letters only")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email address")
    private String email;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    public String getFirstName()       { return firstName; }
    public void setFirstName(String v) { this.firstName = v; }

    public String getLastName()        { return lastName; }
    public void setLastName(String v)  { this.lastName = v; }

    public String getEmail()           { return email; }
    public void setEmail(String v)     { this.email = v; }

    public LocalDate getDateOfBirth()        { return dateOfBirth; }
    public void setDateOfBirth(LocalDate v)  { this.dateOfBirth = v; }
}