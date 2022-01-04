package clavardage.controller;

import clavardage.controller.connectivity.ConnectivityDaemon;
import clavardage.controller.gui.MainGUI;
import clavardage.model.managers.UserManager;

import java.io.InputStream;
import java.net.InetAddress;
import java.util.Objects;

/** Main Class
 * @author Romain MONIER
 */
public class Clavardage
{
    public static InputStream getResourceStream(String path) {
        return Objects.requireNonNull(Clavardage.class.getResourceAsStream(path));
    }

    public final static boolean machine1 = false; // FOR TESTS, will be deleted

    /** Starting point
     * @author Romain MONIER
     * @param args
     */
    public static void main(String[] args)
    {
        //createTestUsers(); // uncomment to create 50 more users at the next run (test, will be deleted)

        /* MAIN WINDOW */

        try {
            MainGUI.createGUI();
        } catch (Exception e) {
            System.err.println("Error during the creation of the Main Window. [" + e + "]");
            e.printStackTrace();
        }

        /* CONNECTIVITY DAEMON */

        try {
            ConnectivityDaemon.start();
        } catch (Exception e) {
            System.err.println("Error during the creation of ConnectivityDaemon Thread. [" + e + "]");
            e.printStackTrace();
        }
    }

    /**
     * will be deleted, call it to create 50 users
     */
    private static void createTestUsers() {
        UserManager um = new UserManager();
        System.out.print("/!\\ TEST /!\\ : Processing the creation of 50 users.");
        for(int i = 0 ; i < 50 ; i++) {
            try {
                um.createUser("user_" + i, "pass_" + i, "mail_" + i + "@clav.com", InetAddress.getByName("127.0.0." + (i+1)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.print(".");
        }
        System.out.println();
    }
}
