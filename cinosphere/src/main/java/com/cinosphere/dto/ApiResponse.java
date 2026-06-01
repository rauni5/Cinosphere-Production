package com.cinosphere.dto;

/**
 * Generic API response envelope.
 * Every endpoint returns this so the client always knows:
 *   success — whether the call worked
 *   message — human-readable status text
 *   data    — the actual payload (null on errors)
 *
 * Usage examples:
 *   return ResponseEntity.ok(ApiResponse.ok("Movie added", movie));
 *   return ResponseEntity.badRequest().body(ApiResponse.error("Username already exists"));
 */
public class ApiResponse<T> {

    private boolean success;
    private String  message;
    private T       data;

    private ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data    = data;
    }

    public static <T> ApiResponse<T> ok(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    public static <T> ApiResponse<T> ok(String message) {
        return new ApiResponse<>(true, message, null);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null);
    }

    public boolean isSuccess() { return success; }
    public String  getMessage(){ return message; }
    public T       getData()   { return data; }
}