package clavardage.model.objects;

import java.net.InetAddress;
import java.util.UUID;

public class UserPrivate extends User {

    private String password;
    private String mail;

    /**
     * @param uuid
     * @param login
     * @param password
     * @param mail
     * @param lastIp
     */
    public UserPrivate(UUID uuid, String login, String password, String mail, InetAddress lastIp) {
        super(uuid, login, lastIp);
        this.password = password;
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
