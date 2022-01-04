package clavardage.model.objects;

import java.io.Serializable;
import java.util.UUID;

public class Message implements Serializable {

    private UUID uuid;
    private String text;
    private User user;
    private Conversation conversation;

    /**
     * @param uuid
     * @param text
     * @param user
     * @param conversation
     */
    public Message(UUID uuid, String text, User user, Conversation conversation) {
        this.uuid = uuid;
        this.text = text;
        this.user = user;
        this.conversation = conversation;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
}
