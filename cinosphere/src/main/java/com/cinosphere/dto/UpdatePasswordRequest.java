package com.cinosphere.dto;

import jakarta.validation.constraints.*;

/**
 * DTO for password change requests.
 * Replaces raw parameter extraction in UpdatePasswordServlet.
 * The three-field pattern (current / new / confirm) enforces
 * that the client sends all three before the service does any DB work.
 */
public class UpdatePasswordRequest {

    @NotBlank(message = "Current password is required")
    private String currentPassword;

    @NotBlank(message = "New password is required")
    @Size(min = 8, message = "New password must be at least 8 characters")
    private String newPassword;

    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;

    public String getCurrentPassword()        { return currentPassword; }
    public void setCurrentPassword(String v)  { this.currentPassword = v; }

    public String getNewPassword()        { return newPassword; }
    public void setNewPassword(String v)  { this.newPassword = v; }

    public String getConfirmPassword()        { return confirmPassword; }
    public void setConfirmPassword(String v)  { this.confirmPassword = v; }
}