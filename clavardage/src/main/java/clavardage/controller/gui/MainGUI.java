package clavardage.controller.gui;

import clavardage.controller.Clavardage;
import clavardage.model.managers.UserManager;
import clavardage.view.main.Application;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 */
public class MainGUI {
    /**
     * Create and display Application View
     * @return the application frame
     */
    public static JFrame createGUI() throws IOException {
        JFrame app = new Application("Clavardage", new ImageIcon(ImageIO.read(Clavardage.getResourceStream("/img/icons/icon.png"))));
        app.setVisible(true);
        return app;
    }

    public static ArrayList<String> getUsernames() {
        UserManager uman = new UserManager();
        ArrayList<String> names = new ArrayList<String>();
        try {
            uman.getAllUsers().forEach((u) -> names.add(u.getLogin()));
        } catch (Exception e) {
            System.err.println("Error: " + e);
            e.printStackTrace();
        }

        return names;
    }
}
