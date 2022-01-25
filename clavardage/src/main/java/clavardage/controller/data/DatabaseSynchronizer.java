package clavardage.controller.data;

import clavardage.model.managers.ConversationManager;
import clavardage.model.managers.MessageManager;
import clavardage.model.managers.UserManager;
import clavardage.model.objects.*;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Please feed the synchronizer with coherence! For instance, don't feed a conversation with a user which
 * has not been added before, otherwise the adding will fail when the synchronize method is called.
 */
public class DatabaseSynchronizer {

    private static final ArrayList<Conversation> convList;
    private static final ArrayList<UserPrivate> userPrivateList;
    private static final ArrayList<User> userList;
    private static final ArrayList<Message> msgList;

    static {
        convList = new ArrayList<Conversation>();
        userPrivateList = new ArrayList<UserPrivate>();
        userList = new ArrayList<User>();
        msgList = new ArrayList<Message>();
    }

    public static void feedWithConversation(ArrayList<Conversation> list) {
        convList.addAll(list);
    }

    public static void feedWithUserPrivate(ArrayList<UserPrivate> list) {
        userPrivateList.addAll(list);
    }

    public static void feedWithUser(ArrayList<User> list) {
        userList.addAll(list);
    }

    public static void feedWithMessage(ArrayList<Message> list) {
        msgList.addAll(list);
    }

    public static void synchronize() {
        for(User u : (ArrayList<User>)userList.clone()) {
            try {
                UserManager man = new UserManager();
                if(!man.isUserExist(u.getUUID())) {
                    man.addExistingUser(u.getUUID(), u.getLogin(), "", u.getUUID().toString() + "@clav.com", u.getLastIp());
                } else {
                    man.updateLogin(u.getUUID(), u.getLogin());
                    man.updateLastIp(u.getUUID(), u.getLastIp());
                }
                userList.remove(u);
            } catch(Exception ignore) { }
        }
        for(UserPrivate u : (ArrayList<UserPrivate>)userPrivateList.clone()) {
            try {
                UserManager man = new UserManager();
                if(!man.isUserExist(u.getUUID())) {
                    man.addExistingUser(u.getUUID(), u.getLogin(), u.getPassword(), u.getMail(), u.getLastIp());
                } else {
                    man.updateLogin(u.getUUID(), u.getLogin());
                    man.updateHashedPassword(u.getUUID(), u.getPassword());
                    man.updateMail(u.getUUID(), u.getMail());
                    man.updateLastIp(u.getUUID(), u.getLastIp());
                }
                userPrivateList.remove(u);
            } catch(Exception ignore) { }
        }
        for(Conversation c : (ArrayList<Conversation>)convList.clone()) {
            try {
                ConversationManager man = new ConversationManager();
                if(!man.isConversationExist(c.getUUID())) {
                    man.addExistingConversation(c.getUUID(), c.getName(), c.getDateCreated(), c.getListUsers());
                } else {
                    man.updateName(c.getUUID(), c.getName());
                    //TODO: edit list user too (hence the user_in_conv still_in bool)
                }
                convList.remove(c);
            } catch(Exception ignore) { }
        }
        for(Message m : (ArrayList<Message>)msgList.clone()) {
            try {
                MessageManager man = new MessageManager();
                if(!man.isMessageExist(m.getUUID())) {
                    man.saveExistingMessage(m.getUUID(), m.getText(), m.getUser(), m.getConversation(), m.getDateCreated());
                }
                msgList.remove(m);
            } catch(Exception ignore) { }
        }
    }

    public static DatabaseMap<Class<?>, ArrayList<?>> getData() throws Exception {
        DatabaseMap<Class<?>, ArrayList<?>> data = new DatabaseMap<>();

        data.put(UserPrivate.class, (new UserManager()).getAllPrivateUsers());
        data.put(Conversation.class, (new ConversationManager()).getAllConversations());
        data.put(Message.class, (new MessageManager()).getAllMessages());

        return data;
    }
}
