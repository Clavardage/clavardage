package clavardage.model.exceptions;

/**
 * User already connected to the current instance of the application
 */
public class UserAlreadyConnectedException extends Exception {
    /**
     * @param e description
     */
    public UserAlreadyConnectedException(String e) {
        super(e);
    }
}
