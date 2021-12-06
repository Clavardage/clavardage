package clavardage.model.objects;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class Conversation implements Serializable {

    private UUID uuid;
    private String name;
    private LocalDateTime dateCreated;
    private ArrayList<User> listUsers;

    /**
     * @param uuid
     * @param name
     * @param dateCreated
     * @param listUsers
     */
    public Conversation(UUID uuid, String name, LocalDateTime dateCreated, ArrayList<User> listUsers) {
        this.uuid = uuid;
        this.name = name;
        this.dateCreated = dateCreated;
        this.listUsers = listUsers;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime date_created) {
        this.dateCreated = date_created;
    }

    public ArrayList<User> getListUsers() {
        return listUsers;
    }

    public void setListUsers(ArrayList<User> list_users) {
        this.listUsers = list_users;
    }

    public boolean isWithOneUserOnly() {
        return getListUsers().size() == 2;
    }
}
