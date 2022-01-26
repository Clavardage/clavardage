package clavardage.controller;

import clavardage.controller.connectivity.ConnectivityDaemon;
import clavardage.controller.gui.MainGUI;

import java.io.InputStream;
import java.util.Objects;

/**
 * Main Class
 * @author Romain MONIER
 */
public class Clavardage
{
    /**
     * Retrieve an InputStream corresponding to the resource file.
     * This is mandatory for the JAR build to work
     * @author Romain MONIER
     * @param path
     * @return
     */
    public static InputStream getResourceStream(String path) {
        return Objects.requireNonNull(Clavardage.class.getResourceAsStream(path));
    }

    /**
     * Starting point
     * @author Romain MONIER
     * @param args
     */
    public static void main(String[] args)
    {
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
}


