package clavardage.controller.authentification;

import clavardage.model.exceptions.UserNotConnectedException;
import clavardage.model.exceptions.WrongIdentifiantsException;
import clavardage.model.managers.UserManager;
import clavardage.model.objects.UserPrivate;

import java.sql.SQLException;

public class AuthOperations {

    private static UserPrivate user;
    private static final UserManager userMngr;

    static {
        user = new UserPrivate(null, null, null, null, null);
        userMngr = new UserManager();
    }

    /**
     * Check if there is a currently connected user
     * @return
     */
    public static boolean isUserConnected() {
        return user.getUUID() != null;
    }

    /**
     * Throw an exception if the user is not authenticated
     * @throws UserNotConnectedException
     */
    public static void cancelIfNotConnected() throws UserNotConnectedException {
        if(!isUserConnected()) throw new UserNotConnectedException("Unauthorized operation: no user connected");
    }

    /**
     * Retrieve the currently connected user
     * @return
     * @throws UserNotConnectedException
     */
    public static UserPrivate getConnectedUser() throws UserNotConnectedException {
        if(!isUserConnected()) {
            throw new UserNotConnectedException("No user connected to the current instance of the application");
        }
        return user;
    }

    /**
     * Connect the current user
     * @param mail
     * @param password
     * @throws SQLException
     * @throws WrongIdentifiantsException
     */
    public static void connectUser(String mail, String password) throws Exception {
        if(isUserConnected()) {
            throw new Exception("User already connected. Please use disconnect method first");
        }
        user = userMngr.connect(mail, password);
    }

    /**
     * Disconnect the currently connected user
     */
    public static void disconnectUser() {
        userMngr.disconnect(user);
    }
}
