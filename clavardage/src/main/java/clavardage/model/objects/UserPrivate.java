package clavardage.model.objects;

import java.util.UUID;

public class UserPrivate extends User {

    private String password;
    private String mail;

    /**
     * @param uuid
     * @param login
     * @param password
     * @param mail
     */
    public UserPrivate(UUID uuid, String login, String password, String mail) {
        super(uuid, login);
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
