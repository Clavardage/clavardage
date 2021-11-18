package clavardage.model.exceptions;

/**
 * No user connected to the current instance of the application
 */
public class UserNotConnectedException extends Exception {
    /**
     * @param e description
     */
    public UserNotConnectedException(String e) {
        super(e);
    }
}
