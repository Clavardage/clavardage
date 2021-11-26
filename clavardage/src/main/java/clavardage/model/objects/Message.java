package clavardage.model.objects;

import java.io.Serializable;

public class Message implements Serializable {

    private int id;
    private String text;
    private User user;
    private Conversation conversation;

    /**
     * @param id
     * @param text
     * @param user
     * @param conversation
     */
    public Message(int id, String text, User user, Conversation conversation) {
        this.id = id;
        this.text = text;
        this.user = user;
        this.conversation = conversation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
