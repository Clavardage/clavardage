package clavardage.model.managers;

import clavardage.model.objects.UserPrivate;

import java.util.UUID;

public class UserManager extends DatabaseManager {

    /**
     * @param uuid
     * @param password
     * @return
     */
    public UserPrivate connect(UUID uuid, String password) {
        return new UserPrivate(uuid, "", password, "");
    }

    /**
     * @param user
     */
    public void disconnect(UserPrivate user) {

    }

    /**
     * @param user
     * @param newLogin
     */
    public void updateLogin(UserPrivate user, String newLogin) {

    }

    /**
     * @param login
     * @param password
     * @param mail
     * @return
     */
    public UserPrivate createUser(String login, String password, String mail) {
        return new UserPrivate(UUID.randomUUID(), login, password, mail);
    }
}
