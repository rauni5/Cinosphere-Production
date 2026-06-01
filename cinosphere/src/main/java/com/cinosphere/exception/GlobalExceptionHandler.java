package com.cinosphere.exception;

import com.cinosphere.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * Central error handler — every controller in the project benefits automatically.
 *
 * Why this matters:
 *  All refactored services throw IllegalArgumentException for business-rule
 *  violations (duplicate username, wrong password, seat taken, etc.).
 *  Without this handler every one of those would return a 500 Internal Server Error.
 *  With it, the client gets a clean 400/401 JSON response every time.
 *
 * Three cases handled:
 *  1. @Valid bean-validation failures  → 400 with all field error messages joined
 *  2. IllegalArgumentException         → 400 with the service's message
 *  3. Any other RuntimeException       → 500 with a safe generic message
 *     (stack trace still logged server-side for debugging)
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles @Valid / @Validated failures on @RequestBody DTOs.
     * Collects all field errors into one readable message.
     * Example: "firstName: must not be blank; email: must be a well-formed email address"
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(message));
    }

    /**
     * Handles business-rule violations thrown by the service layer.
     * Examples: "Username already exists", "Current password is incorrect",
     *           "Seat 12 is already taken", "Account pending admin approval".
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(ex.getMessage()));
    }

    /**
     * Catch-all for unexpected errors.
     * Returns 500 with a generic message — never exposes internal details.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneral(Exception ex) {
        ex.printStackTrace(); // logged server-side
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("An unexpected error occurred. Please try again."));
    }
}