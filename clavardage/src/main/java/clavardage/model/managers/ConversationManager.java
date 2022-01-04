package clavardage.model.managers;

import clavardage.model.exceptions.ConversationDoesNotExistException;
import clavardage.model.objects.Conversation;
import clavardage.model.objects.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class ConversationManager extends DatabaseManager {

    /**
     * @param user
     * @param conversation
     */
    public void addUserToConversation(User user, Conversation conversation) {

    }

    /**
     * @param user
     * @param conversation
     */
    public void removeUserFromConversation(User user, Conversation conversation) {

    }

    public Conversation getConversationByUUID(UUID uuid) throws Exception {
        Conversation conv = null;
        PreparedStatement pstmt = getConnection().prepareStatement("SELECT * FROM conversation WHERE conversation.uuid = ?");

        pstmt.setString(1, uuid.toString());

        ResultSet res = pstmt.executeQuery();
        if(res.next()) {
            conv = new Conversation(UUID.fromString(res.getString("uuid")), res.getString("name"), res.getTimestamp("date_created").toLocalDateTime(), (new UserManager()).getUsersByConversationUUID(uuid));
        } else {
            res.close();
            pstmt.close();
            throw new Exception("Conversation does not exist");
        }

        res.close();
        pstmt.close();

        return conv;
    }
    public Conversation getConversationByUserConvUUID(UUID uuid) throws Exception {
        Conversation conv = null;
        PreparedStatement pstmt = getConnection().prepareStatement("SELECT conversation.* FROM conversation, user_in_conversation uic WHERE conversation.uuid = uic.uuid_conversation AND uic.uuid = ?");

        pstmt.setString(1, uuid.toString());

        ResultSet res = pstmt.executeQuery();
        if(res.next()) {
            conv = new Conversation(UUID.fromString(res.getString("uuid")), res.getString("name"), res.getTimestamp("date_created").toLocalDateTime(), (new UserManager()).getUsersByConversationUUID(uuid));
        } else {
            res.close();
            pstmt.close();
            throw new Exception("Conversation does not exist");
        }

        res.close();
        pstmt.close();

        return conv;
    }

    public Conversation getConversationByTwoUsers(User u1, User u2) throws Exception {
        PreparedStatement pstmt = getConnection().prepareStatement("""
SELECT c.* FROM conversation AS c, user_in_conversation AS uic1, user_in_conversation uic2
WHERE uic1.uuid_conversation = c.uuid AND uic2.uuid_conversation = c.uuid
AND uic1.uuid_user = ? AND uic2.uuid_user = ?""");

        pstmt.setString(1, u1.getUUID().toString());
        pstmt.setString(2, u2.getUUID().toString());

        ResultSet res = pstmt.executeQuery();
        Conversation conv;
        UserManager um = new UserManager();
        // could be enhanced by checking this condition directly in the SQL request, but will break some ORM separation
        while(res.next()) {
            conv = new Conversation(UUID.fromString(res.getString("uuid")), res.getString("name"), res.getTimestamp("date_created").toLocalDateTime(), new ArrayList<User>(Arrays.asList(u1, u2)));
            // if too slow, we will break the ORM to check this condition directly in the request.
            if(um.getUsersByConversation(conv).size() == 2) {
                res.close();
                pstmt.close();
                return conv;
            }
        }
        res.close();
        pstmt.close();
        throw new ConversationDoesNotExistException("Conversation does not exist");
    }

    public Conversation createConversation(String name, LocalDateTime dateCreated, ArrayList<User> userList) throws Exception {
        final boolean oldstate = getConnection().getAutoCommit();
        getConnection().setAutoCommit(false);

        try {
            String req = "INSERT INTO conversation(uuid, name, date_created) VALUES(?, ?, ?)";
            PreparedStatement pstmt = getConnection().prepareStatement(req);

            UUID uuid = UUID.randomUUID();
            pstmt.setString(1, uuid.toString());
            pstmt.setString(2, name);
            pstmt.setTimestamp(3, Timestamp.valueOf(dateCreated));

            pstmt.executeUpdate();
            pstmt.close();

            Conversation conversation = new Conversation(uuid, name, dateCreated, userList);

            for (User u : userList) {
                req = "INSERT INTO user_in_conversation(uuid, uuid_user, uuid_conversation) VALUES(?, ?, ?)";
                pstmt = getConnection().prepareStatement(req);

                uuid = UUID.randomUUID();
                pstmt.setString(1, uuid.toString());
                pstmt.setString(2, u.getUUID().toString());
                pstmt.setString(3, conversation.getUUID().toString());

                pstmt.executeUpdate();
                pstmt.close();
            }

            getConnection().commit();
            getConnection().setAutoCommit(oldstate);
            return conversation;
        } catch (Exception e) {
            getConnection().rollback();
            getConnection().setAutoCommit(oldstate);
            throw e;
        }
    }

    public void addExistingConversation(UUID uuid, String name, LocalDateTime dateCreated, ArrayList<User> userList) throws Exception {
        final boolean oldstate = getConnection().getAutoCommit();
        getConnection().setAutoCommit(false);

        try {
            String req = "INSERT INTO conversation(uuid, name, date_created) VALUES(?, ?, ?)";
            PreparedStatement pstmt = getConnection().prepareStatement(req);

            pstmt.setString(1, uuid.toString());
            pstmt.setString(2, name);
            pstmt.setTimestamp(3, Timestamp.valueOf(dateCreated));

            pstmt.executeUpdate();
            pstmt.close();

            Conversation conversation = new Conversation(uuid, name, dateCreated, userList);

            for (User u : userList) {
                req = "INSERT INTO user_in_conversation(uuid, uuid_user, uuid_conversation) VALUES(?, ?, ?)";
                pstmt = getConnection().prepareStatement(req);

                uuid = UUID.randomUUID();
                pstmt.setString(1, uuid.toString());
                pstmt.setString(2, u.getUUID().toString());
                pstmt.setString(3, conversation.getUUID().toString());

                pstmt.executeUpdate();
                pstmt.close();
            }

            getConnection().commit();
            getConnection().setAutoCommit(oldstate);
        } catch (Exception e) {
            getConnection().rollback();
            getConnection().setAutoCommit(oldstate);
            throw e;
        }
    }

    public boolean isConversationExist(UUID uuid) throws SQLException {
        boolean exists = false;

        PreparedStatement pstmt = getConnection().prepareStatement("SELECT * FROM conversation WHERE conversation.uuid = ?");

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
