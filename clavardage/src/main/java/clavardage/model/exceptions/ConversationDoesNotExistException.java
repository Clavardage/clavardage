package clavardage.model.exceptions;

/**
 * No user connected to the current instance of the application
 */
public class ConversationDoesNotExistException extends Exception {
    /**
     * @param e description
     */
    public ConversationDoesNotExistException(String e) {
        super(e);
    }
}
