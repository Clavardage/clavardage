package clavardage.controller.gui;

import clavardage.controller.Clavardage;
import clavardage.view.main.Application;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

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
}
