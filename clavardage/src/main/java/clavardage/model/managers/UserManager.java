package clavardage.model.managers;

import clavardage.controller.authentification.PasswordFactory;
import clavardage.model.exceptions.WrongIdentifiantsException;
import clavardage.model.objects.User;
import clavardage.model.objects.UserPrivate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class UserManager extends DatabaseManager {

    /**
     * @param uuid
     * @param password
     * @return
     */
    public UserPrivate connect(UUID uuid, String password) throws SQLException, WrongIdentifiantsException {
        PreparedStatement pstmt = getConnection().prepareStatement("SELECT user.* FROM user WHERE user.uuid = ?");

        pstmt.setString(1, uuid.toString());

        ResultSet res = pstmt.executeQuery();
        UserPrivate u;
        if(res.next()) {
            if(!PasswordFactory.verify(password, res.getString("password"))) {
                throw new WrongIdentifiantsException("Wrong password");
            }
            u = new UserPrivate(UUID.fromString(res.getString("uuid")), res.getString("login"), res.getString("password"), res.getString("mail"));
        } else {
            res.close();
            pstmt.close();
            throw new WrongIdentifiantsException("User does not exist");
        }

        res.close();
        pstmt.close();

        return u;
    }

    /**
     * @param user
     */
    public void disconnect(UserPrivate user) {
        user.setUUID(null);
        user.setLogin(null);
        user.setPassword(null);
        user.setMail(null);
    }

    /**
     * @param user
     * @param newLogin
     */
    public void updateLogin(UserPrivate user, String newLogin) throws Exception {
        String req = "UPDATE user SET user.login = ? WHERE user.uuid = ?";
        PreparedStatement pstmt = getConnection().prepareStatement(req);

        pstmt.setString(1, newLogin);
        pstmt.setString(2, user.getUUID().toString());

        if(pstmt.executeUpdate() == 0) { // if no user edited
            pstmt.close();
            throw new Exception("User not found");
        }

        pstmt.close();
    }

    /**
     * Create a new user by choosing a unique UUID and adding it to the database
     * @param login
     * @param rawPassword
     * @param mail
     * @return
     */
    public UserPrivate createUser(String login, String rawPassword, String mail) throws SQLException {
        String req = "INSERT INTO user(uuid, login, password, mail) VALUES(?, ?, ?, ?)";
        PreparedStatement pstmt = getConnection().prepareStatement(req);

        String hashedPass = PasswordFactory.generateHash(rawPassword);
        pstmt.setString(1, UUID.randomUUID().toString());
        pstmt.setString(2, login);
        pstmt.setString(3, hashedPass);
        pstmt.setString(4, mail);

        pstmt.executeUpdate();
        pstmt.close();

        return new UserPrivate(UUID.randomUUID(), login, hashedPass, mail);
    }
    /**
     * Add an existing user to the database
     * @param uuid
     * @param login
     * @param hashedPassword has to be a Bcrypt hash
     * @param mail
     * @return
     */
    public void addExistingUser(UUID uuid, String login, String hashedPassword, String mail) throws SQLException {
        String req = "INSERT INTO user(uuid, login, password, mail) VALUES(?, ?, ?, ?)";
        PreparedStatement pstmt = getConnection().prepareStatement(req);

        pstmt.setString(1, uuid.toString());
        pstmt.setString(2, login);
        pstmt.setString(3, hashedPassword);
        pstmt.setString(4, mail);

        pstmt.executeUpdate();
        pstmt.close();
    }

    /**
     * @return user list
     * @throws Exception
     */
    public ArrayList<User> getAllUsers() throws Exception {
        ArrayList<User> users = new ArrayList<>();

        PreparedStatement pstmt = getConnection().prepareStatement("SELECT user.uuid, user.login FROM user");

        ResultSet res = pstmt.executeQuery();
        while(res.next()) {
            users.add(new User(UUID.fromString(res.getString("uuid")), res.getString("login")));
        }

        res.close();
        pstmt.close();

        return users;
    }
}
