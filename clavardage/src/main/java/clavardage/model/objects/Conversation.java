package clavardage.model.objects;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Conversation implements Serializable {

    private int id;
    private String name;
    private LocalDateTime dateCreated;
    private ArrayList<User> listUsers;

    /**
     * @param id
     * @param name
     * @param dateCreated
     * @param listUsers
     */
    public Conversation(int id, String name, LocalDateTime dateCreated, ArrayList<User> listUsers) {
        this.id = id;
        this.name = name;
        this.dateCreated = dateCreated;
        this.listUsers = listUsers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
