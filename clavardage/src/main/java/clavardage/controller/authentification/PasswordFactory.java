package clavardage.controller.authentification;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * We use a Bcrypt algorithm as it is safer than the built-in PBKDF2 algorithm
 * @author Romain MONIER
 */
public class PasswordFactory {

    /**
     * Generate hash
     * @author Romain MONIER
     * @param rawPassword
     * @return hash
     */
    public static String generateHash(String rawPassword) {
        return BCrypt.withDefaults().hashToString(12, rawPassword.toCharArray());
    }

    /**
     * Verify if the passwords match
     * @author Romain MONIER
     * @param rawPassword
     * @param hashedPassword
     * @return true if matches
     */
    public static boolean verify(String rawPassword, String hashedPassword) {
        BCrypt.Result res = BCrypt.verifyer().verify(rawPassword.toCharArray(), hashedPassword);
        return res.verified;
    }
}
