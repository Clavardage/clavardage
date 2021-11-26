package clavardage.model.objects;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.UUID;

public class User implements Serializable {

    private UUID uuid;
    private String login;
    private InetAddress lastIp;

    /**
     * @param uuid
     * @param login
     * @param lastIp
     */
    public User(UUID uuid, String login, InetAddress lastIp) {
        this.uuid = uuid;
        this.login = login;
        this.lastIp = lastIp;
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

    public InetAddress getLastIp() {
        return lastIp;
    }

    public void setLastIp(InetAddress lastIp) {
        this.lastIp = lastIp;
    }
}
