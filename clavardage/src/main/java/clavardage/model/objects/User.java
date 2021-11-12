package clavardage.model.objects;

import java.util.UUID;

public class User {

    private UUID uuid;
    private String login;

    /**
     * @param uuid
     * @param login
     */
    public User(UUID uuid, String login) {
        this.uuid = uuid;
        this.login = login;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
