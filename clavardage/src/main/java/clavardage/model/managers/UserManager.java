package clavardage.model.managers;

import clavardage.model.objects.User;
import clavardage.model.objects.UserPrivate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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

    /**
     * @return user list
     * @throws Exception
     */
    public ArrayList<User> getAllUsers() throws Exception {
        ArrayList<User> users = new ArrayList<>();

        PreparedStatement pstmt = getConnection().prepareStatement("SELECT user.uuid, user.login FROM user");

        ResultSet res = pstmt.executeQuery();
        while(res.next())
        {
            users.add(new User(UUID.fromString(res.getString("uuid")), res.getString("login")));
        }

        res.close();
        pstmt.close();

        return users;
    }
}
