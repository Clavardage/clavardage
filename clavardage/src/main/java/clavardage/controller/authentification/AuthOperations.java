package clavardage.controller.authentification;

import clavardage.controller.connectivity.ConnectivityDaemon;
import clavardage.model.exceptions.UserAlreadyConnectedException;
import clavardage.model.exceptions.UserNotConnectedException;
import clavardage.model.exceptions.WrongIdentifiantsException;
import clavardage.model.managers.UserManager;
import clavardage.model.objects.UserPrivate;

import java.sql.SQLException;

/**
 * Authentification Operations
 * @author Romain MONIER
 */
public class AuthOperations {

    private static UserPrivate user;
    private static final UserManager userMngr;

    static {
        user = new UserPrivate(null, null, null, null, null);
        userMngr = new UserManager();
    }

    /**
     * Check if there is a currently connected user
     * @author Romain MONIER
     * @return
     */
    public static boolean isUserConnected() {
        return user.getUUID() != null;
    }

    /**
     * Throw an exception if the user is not authenticated
     * @author Romain MONIER
     * @throws UserNotConnectedException
     */
    public static void cancelIfNotConnected() throws UserNotConnectedException {
        if(!isUserConnected()) throw new UserNotConnectedException("Unauthorized operation: no user connected");
    }

    /**
     * Retrieve the currently connected user
     * @author Romain MONIER
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
     * @author Romain MONIER
     * @param mail
     * @param password
     * @throws Exception
     */
    public static void connectUser(String mail, String password) throws Exception {
        if(isUserConnected()) {
            throw new UserAlreadyConnectedException("User already connected. Please use disconnect method first");
        }
        user = userMngr.connect(mail, password);
    }

    /**
     * Change the current connected user login
     * @author Romain MONIER
     * @param newUsername
     * @throws Exception
     */
    public static void editUsername(String newUsername) throws Exception {
        cancelIfNotConnected();
        userMngr.updateLogin(user.getUUID(), newUsername);
        user.setLogin(newUsername);
    }

    /**
     * Disconnect the currently connected user
     * @author Romain MONIER
     */
    public static void disconnectUser() {
        try {
            ConnectivityDaemon.getConversationService().closeAllConversations();
        } catch(Exception ignore) { }
        userMngr.disconnect(user);
    }
}
