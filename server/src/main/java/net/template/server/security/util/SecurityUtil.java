package net.template.server.security.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SecurityUtil {

    // Method to encode a plain password using SHA-256
    public static String encodePassword(String plainPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(plainPassword.getBytes());

            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error occurred while hashing password", e);
        }
    }
}
