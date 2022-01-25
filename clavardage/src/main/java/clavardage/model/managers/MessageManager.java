package clavardage.model.managers;

import clavardage.model.objects.Conversation;
import clavardage.model.objects.Message;
import clavardage.model.objects.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class MessageManager extends DatabaseManager {
    public Message getMessageByUUID(UUID uuid) throws Exception {

        Message msg = null;
        PreparedStatement pstmt = getConnection().prepareStatement("SELECT * FROM message WHERE message.uuid = ?");

        pstmt.setString(1, uuid.toString());

        ResultSet res = pstmt.executeQuery();
        if(res.next()) {
            msg = new Message(UUID.fromString(res.getString("uuid")), res.getString("text"), (new UserManager()).getUserByUserConvUUID(UUID.fromString(res.getString("user_conv"))), (new ConversationManager()).getConversationByUserConvUUID(UUID.fromString(res.getString("user_conv"))), res.getTimestamp("date_created").toLocalDateTime());
        } else {
            res.close();
            pstmt.close();
            throw new Exception("Message does not exist");
        }

        res.close();
        pstmt.close();

        return msg;
    }

    public ArrayList<Message> getLastMessagesFromConversation(Conversation c, int numberOfMessages, int page) throws Exception {
        ArrayList<Message> msg = new ArrayList<Message>();

        String limit_clause = (numberOfMessages > -1) ? ("LIMIT " + numberOfMessages + ((page > -1) ? " OFFSET " + numberOfMessages*page : "")) : "";

        PreparedStatement pstmt = getConnection().prepareStatement("""
SELECT message.* FROM message, user_in_conversation uic
WHERE uic.uuid_conversation = ? AND message.user_conv = uic.uuid
ORDER BY message.date_created DESC
""" + limit_clause);

        pstmt.setString(1, c.getUUID().toString());

        ResultSet res = pstmt.executeQuery();
        while(res.next()) {
            msg.add(new Message(UUID.fromString(res.getString("uuid")), res.getString("text"), (new UserManager()).getUserByUserConvUUID(UUID.fromString(res.getString("user_conv"))), (new ConversationManager()).getConversationByUserConvUUID(UUID.fromString(res.getString("user_conv"))), res.getTimestamp("date_created").toLocalDateTime()));
        }

        res.close();
        pstmt.close();

        return msg;
    }

    public ArrayList<Message> getLastMessagesFromConversation(Conversation c, int numberOfMessages) throws Exception {
        return getLastMessagesFromConversation(c, numberOfMessages,-1);
    }

    public ArrayList<Message> getAllMessagesFromConversation(Conversation c) throws Exception {
        return getLastMessagesFromConversation(c, -1);
    }

    /**
     * Save a message by choosing a unique UUID and adding it to the database
     * @param text
     * @param u
     * @param c
     * @return
     * @throws Exception
     */
    public Message saveNewMessage(String text, User u, Conversation c) throws Exception {
        String reqUIC = "SELECT uuid FROM user_in_conversation WHERE uuid_conversation = ? AND uuid_user = ?";
        PreparedStatement pstmtUIC = getConnection().prepareStatement(reqUIC);
        pstmtUIC.setString(1, c.getUUID().toString());
        pstmtUIC.setString(2, u.getUUID().toString());
        ResultSet resUIC = pstmtUIC.executeQuery();

        if(resUIC.next()) {
            String req = "INSERT INTO message(uuid, text, user_conv, date_created) VALUES(?, ?, ?, ?)";
            PreparedStatement pstmt = getConnection().prepareStatement(req);

            UUID uuid = UUID.randomUUID();
            LocalDateTime date = LocalDateTime.now();
            pstmt.setString(1, uuid.toString());
            pstmt.setString(2, text);
            pstmt.setString(3, resUIC.getString("uuid"));
            pstmt.setTimestamp(4, Timestamp.valueOf(date));

            pstmt.executeUpdate();
            pstmt.close();

            resUIC.close();
            pstmtUIC.close();

            return new Message(uuid, text, u, c, date);
        } else {
            resUIC.close();
            pstmtUIC.close();
            throw new Exception("Conversation for the message does not exist");
        }
    }

    /**
     * Add existing message to the database
     * @param uuid
     * @param text
     * @param u
     * @param c
     * @param date
     * @throws Exception
     */
    public void saveExistingMessage(UUID uuid, String text, User u, Conversation c, LocalDateTime date) throws Exception {
        String reqUIC = "SELECT uuid FROM user_in_conversation WHERE uuid_conversation = ? AND uuid_user = ?";
        PreparedStatement pstmtUIC = getConnection().prepareStatement(reqUIC);
        pstmtUIC.setString(1, c.getUUID().toString());
        pstmtUIC.setString(2, u.getUUID().toString());
        ResultSet resUIC = pstmtUIC.executeQuery();

        if(resUIC.next()) {
            String req = "INSERT INTO message(uuid, text, user_conv, date_created) VALUES(?, ?, ?, ?)";
            PreparedStatement pstmt = getConnection().prepareStatement(req);

            pstmt.setString(1, uuid.toString());
            pstmt.setString(2, text);
            pstmt.setString(3, resUIC.getString("uuid"));
            pstmt.setTimestamp(4, Timestamp.valueOf(date));

            try {
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            pstmt.close();

            resUIC.close();
            pstmtUIC.close();
        } else {
            resUIC.close();
            pstmtUIC.close();
            throw new Exception("Conversation for the message does not exist");
        }
    }

    public boolean isMessageExist(UUID uuid) throws SQLException {
        boolean exists = false;

        PreparedStatement pstmt = getConnection().prepareStatement("SELECT * FROM message WHERE message.uuid = ?");

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
