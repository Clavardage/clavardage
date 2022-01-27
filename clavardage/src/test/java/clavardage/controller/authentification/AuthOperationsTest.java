package clavardage.controller.authentification;

import clavardage.model.exceptions.UserAlreadyConnectedException;
import clavardage.model.exceptions.UserNotConnectedException;
import clavardage.model.managers.UserManager;
import clavardage.model.objects.UserPrivate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.net.InetAddress;
import java.util.UUID;

public class AuthOperationsTest {

    private static final UserManager user_mngr = new UserManager();
    private static UserPrivate user;
    private static String password = "test";

    @BeforeAll
    public static void setupAll() throws Exception {
        user = user_mngr.createUser("test", password, UUID.randomUUID().toString() + "@mail.com", InetAddress.getLocalHost());
    }

    @Test
    public void connectUserTest() throws Exception {
        AuthOperations.connectUser(user.getMail(), password);
        try {
            AuthOperations.connectUser(user.getMail(), password);
            fail();
        } catch (UserAlreadyConnectedException ignore) { }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void isUserConnectedTest() throws Exception {
        assertFalse(AuthOperations.isUserConnected());
        AuthOperations.connectUser(user.getMail(), password);
        assertTrue(AuthOperations.isUserConnected());
    }

    @Test
    public void cancelIfNotConnectedTest() throws Exception {
        try {
            AuthOperations.cancelIfNotConnected();
            fail();
        } catch (UserNotConnectedException ignore) { }
        catch (Exception e) {
            fail();
        }
        AuthOperations.connectUser(user.getMail(), password);
        AuthOperations.cancelIfNotConnected();
    }

    @Test
    public void getConnectedUserTest() throws Exception {
        AuthOperations.connectUser(user.getMail(), password);
        assertEquals(user.getUUID(), AuthOperations.getConnectedUser().getUUID());
    }

    @Test
    public void editUsernameTest() throws Exception {
        String newName = "new_test";
        AuthOperations.connectUser(user.getMail(), password);
        AuthOperations.editUsername(newName);
        assertEquals(newName, AuthOperations.getConnectedUser().getLogin());
        assertEquals(newName, user_mngr.getUserByUUID(user.getUUID()).getLogin());
    }

    @Test
    public void disconnectUserTest() throws Exception {
        AuthOperations.connectUser(user.getMail(), password);
        AuthOperations.disconnectUser();
        assertFalse(AuthOperations.isUserConnected());
    }

    @AfterEach
    public void teardownEach() {
        AuthOperations.disconnectUser();
    }

    @AfterAll
    public static void teardownAll() throws Exception {
        user_mngr.deleteUser(user.getUUID());
    }
}
