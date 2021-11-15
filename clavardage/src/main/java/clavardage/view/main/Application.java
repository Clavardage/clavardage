package clavardage.view.main;

import clavardage.controller.gui.MainGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Application extends JFrame implements ActionListener, MouseListener {
    public Application(String title, ImageIcon icon) {
        this.setTitle(title);
        this.setIconImage(icon.getImage());
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.addMouseListener(this); // test
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("test");
        MainGUI.getUsernames().forEach(System.out::println);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
