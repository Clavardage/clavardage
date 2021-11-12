package clavardage.controller;

import clavardage.controller.gui.MainGUI;
import clavardage.model.managers.ConversationManager;

import java.io.InputStream;
import java.util.Objects;

/** Main Class
 * @author Romain MONIER
 */
public class Clavardage
{
    public static InputStream getResourceStream(String path) {
        return Objects.requireNonNull(Clavardage.class.getResourceAsStream(path));
    }

    /** Starting point
     * @author Romain MONIER
     * @param args
     */
    public static void main(String[] args)
    {
        try {
            MainGUI.createGUI();
        } catch (Exception e) {
            System.err.println("Error during the creation of the Main Window. [" + e + "]");
            e.printStackTrace();
        }

        new ConversationManager(); // test for db init
    }
}
