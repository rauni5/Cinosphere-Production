package com.cinosphere.utils;

import org.mindrot.jbcrypt.BCrypt;
/**
 * Utility Class for passwordHashing and Checking
 * 
 * helper responsible for creating hash password and 
 * 
 * @author Raunit Giri
 */
public class PasswordUtil {
	//Salt Cost
    private static final int COST = 10;
    
    /**
     * Turns String password into hash password
     * @param password
     * @return hashed password
     */
    public static String getHashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(COST));
    }
    /**
     * Compares String with Hash 
     * @param input Password to check
     * @param storedHash Stored hashed password
     * @return boolean
     */
    public static boolean checkPassword(String input, String storedHash) {
        return BCrypt.checkpw(input, storedHash);
    }
}
