package clavardage.model.managers;

import clavardage.controller.authentification.PasswordFactory;
import clavardage.model.exceptions.WrongIdentifiantsException;
import clavardage.model.objects.Conversation;
import clavardage.model.objects.User;
import clavardage.model.objects.UserPrivate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class UserManager extends DatabaseManager {

    /**
     * @param mail
     * @param password
     * @return
     */
    public UserPrivate connect(String mail, String password) throws SQLException, WrongIdentifiantsException, UnknownHostException {
        PreparedStatement pstmt = getConnection().prepareStatement("SELECT user.* FROM user WHERE user.mail = ?");

        pstmt.setString(1, mail);

        ResultSet res = pstmt.executeQuery();
        UserPrivate u;
        if(res.next()) {
            if(!PasswordFactory.verify(password, res.getString("password"))) {
                throw new WrongIdentifiantsException("Wrong password");
            }
            u = new UserPrivate(UUID.fromString(res.getString("uuid")), res.getString("login"), res.getString("password"), res.getString("mail"), InetAddress.getByName(res.getString("last_ip")));
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
        user.setLastIp(null);
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
    public UserPrivate createUser(String login, String rawPassword, String mail, InetAddress lastIp) throws SQLException {
        String req = "INSERT INTO user(uuid, login, password, mail, last_ip) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pstmt = getConnection().prepareStatement(req);

        String hashedPass = PasswordFactory.generateHash(rawPassword);
        UUID uuid = UUID.randomUUID();
        pstmt.setString(1, uuid.toString());
        pstmt.setString(2, login);
        pstmt.setString(3, hashedPass);
        pstmt.setString(4, mail);
        pstmt.setString(5, lastIp.getHostName());

        pstmt.executeUpdate();
        pstmt.close();

        return new UserPrivate(uuid, login, hashedPass, mail, lastIp);
    }
    /**
     * Add an existing user to the database
     * @param uuid
     * @param login
     * @param hashedPassword has to be a Bcrypt hash
     * @param mail
     * @return
     */
    public void addExistingUser(UUID uuid, String login, String hashedPassword, String mail, InetAddress lastIp) throws SQLException {
        String req = "INSERT INTO user(uuid, login, password, mail, last_ip) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pstmt = getConnection().prepareStatement(req);

        pstmt.setString(1, uuid.toString());
        pstmt.setString(2, login);
        pstmt.setString(3, hashedPassword);
        pstmt.setString(4, mail);
        pstmt.setString(5, lastIp.getHostName());

        pstmt.executeUpdate();
        pstmt.close();
    }

    /**
     * @return user list
     * @throws Exception
     */
    public ArrayList<User> getAllUsers() throws Exception {
        ArrayList<User> users = new ArrayList<>();

        PreparedStatement pstmt = getConnection().prepareStatement("SELECT user.uuid, user.login, user.last_ip FROM user");

        ResultSet res = pstmt.executeQuery();
        while(res.next()) {
            users.add(new User(UUID.fromString(res.getString("uuid")), res.getString("login"), InetAddress.getByName(res.getString("last_ip"))));
        }

        res.close();
        pstmt.close();

        return users;
    }

    public ArrayList<User> getUsersByConversation(Conversation conv) throws SQLException, UnknownHostException {
        ArrayList<User> users = new ArrayList<>();

        PreparedStatement pstmt = getConnection().prepareStatement("""
SELECT user.uuid, user.login, user.last_ip
FROM user, user_in_conversation AS uic
WHERE user.uuid = uic.uuid_user AND uic.uuid_conversation = ?""");

        pstmt.setString(1, conv.getUUID().toString());

        ResultSet res = pstmt.executeQuery();
        while(res.next()) {
            users.add(new User(UUID.fromString(res.getString("uuid")), res.getString("login"), InetAddress.getByName(res.getString("last_ip"))));
        }

        res.close();
        pstmt.close();

        return users;
    }

    public ArrayList<User> getUsersByConversationUUID(UUID uuid) throws SQLException, UnknownHostException {
        ArrayList<User> users = new ArrayList<>();

        PreparedStatement pstmt = getConnection().prepareStatement("""
SELECT user.uuid, user.login, user.last_ip
FROM user, user_in_conversation AS uic
WHERE user.uuid = uic.uuid_user AND uic.uuid_conversation = ?""");

        pstmt.setString(1, uuid.toString());

        ResultSet res = pstmt.executeQuery();
        while(res.next()) {
            users.add(new User(UUID.fromString(res.getString("uuid")), res.getString("login"), InetAddress.getByName(res.getString("last_ip"))));
        }

        res.close();
        pstmt.close();

        return users;
    }
    public User getUserByUserConvUUID(UUID uuid) throws Exception {
        User u = null;
        PreparedStatement pstmt = getConnection().prepareStatement("SELECT user.uuid, user.login, user.last_ip FROM user, user_in_conversation uic WHERE uic.uuid_user = user.uuid AND uic.uuid = ?");

        pstmt.setString(1, uuid.toString());

        ResultSet res = pstmt.executeQuery();
        if(res.next()) {
            u = new User(UUID.fromString(res.getString("uuid")), res.getString("login"), InetAddress.getByName(res.getString("last_ip")));
        } else {
            res.close();
            pstmt.close();
            throw new Exception("User does not exist");
        }

        res.close();
        pstmt.close();

        return u;
    }

    public User getUserByUUID(UUID uuid) throws Exception {
        User u = null;
        PreparedStatement pstmt = getConnection().prepareStatement("SELECT user.uuid, user.login, user.last_ip FROM user WHERE user.uuid = ?");

        pstmt.setString(1, uuid.toString());

        ResultSet res = pstmt.executeQuery();
        if(res.next()) {
            u = new User(UUID.fromString(res.getString("uuid")), res.getString("login"), InetAddress.getByName(res.getString("last_ip")));
        } else {
            res.close();
            pstmt.close();
            throw new Exception("User does not exist");
        }

        res.close();
        pstmt.close();

        return u;
    }

    public boolean isUserExist(UUID uuid) throws Exception {
        boolean exists = false;

        PreparedStatement pstmt = getConnection().prepareStatement("SELECT * FROM user WHERE user.uuid = ?");

        pstmt.setString(1, uuid.toString());
        ResultSet res = pstmt.executeQuery();

        if(res.next()) {
            exists = true;
        }

        res.close();
        pstmt.close();

        return exists;
    }
}
