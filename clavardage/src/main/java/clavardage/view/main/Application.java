package clavardage.view.main;

import javax.swing.*;
import java.awt.*;

public class Application extends JFrame {
    public Application(String title, ImageIcon icon) {
        this.setTitle(title);
        this.setIconImage(icon.getImage());
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
