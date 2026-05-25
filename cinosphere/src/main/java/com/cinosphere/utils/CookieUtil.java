package com.cinosphere.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/**
 * Utility class for Cookie Operations
 * 
 * Contains helpers to create, get and delete cookie from http response, request
 * 
 * @author Raunit Giri
 */
public class CookieUtil {

	/**
     * Creates and adds a cookie to the response.
     * @param response HttpServletResponse
     * @param name Name of the cookie
     * @param value Value of the cookie
     * @param min Max age in minutes
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int min) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(min*60);
        cookie.setHttpOnly(true); // Prevents XSS access to the cookie
        response.addCookie(cookie);
    }
    
    /**
     * Retrieve a cookie value by name
     * @param response HttpServletResponse
     * @param name Name of the cookie
     * @return Value if cookie found, if not returns null
     */
    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * Deletes a cookie by setting its max age to 0.
     * @param response HttpServletResponse
     * @param name Name of the cookie
     */
    public static void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, "");
        cookie.setPath("/");
        cookie.setMaxAge(0); // Deletes the cookie
        response.addCookie(cookie);
    }

}
