package com.cinosphere.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Utility class responsible for session operations
 * 
 * Helpers to ensure consistent behavior around creation,
 * retrieval, and invalidation of sessions.
 * 
 * @author Raunit Giri
 */
public class SessionUtil {
	/**
     * Adds an attribute and sets a custom session timeout.
     * @param request HttpServletRequest
     * @param name Name of attribute
     * @param value Value of attribute
     * @param seconds Time before session expires due to inactivity.
     */
	public static void setAttribute(HttpServletRequest request, String name, Object value, int seconds) {
        HttpSession session = request.getSession(true);
        session.setAttribute(name, value);
        session.setMaxInactiveInterval(seconds);
    }
	/**
     * Obtains an attribute from current HTTP session
     * @param request HttpServletRequest
     * @param name Name of attribute
     * @return the attribute value if found, or null if session does not exist
 *         or the attribute is not set
     */
	public static Object getAttribute(HttpServletRequest request, String name) {
        HttpSession session = request.getSession(false);
        return (session != null) ? session.getAttribute(name) : null;
    }
	/**
     * Removes an attribute from current HTTP session
     * @param request HttpServletRequest
     * @param name Name of attribute
     */
	public static void removeAttribute(HttpServletRequest request, String name) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(name);
        }
    }
	/**
     * Ends current active session
     * @param request HttpServletRequest
     */
	public static void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

}
