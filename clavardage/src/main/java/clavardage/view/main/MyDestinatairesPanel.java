package clavardage.view.main;

import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MyDestinatairesPanel extends JPanel {
	
	public MyDestinatairesPanel(JPanel users, JPanel groups) throws IOException {
		
		super();
		setOpaque(false);
		setLayout(new GridLayout(2, 1, 0, 0));

		add(users);
		add(groups);
	}
}
