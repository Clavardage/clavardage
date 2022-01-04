package clavardage.view.main;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MyDiscussionContainer extends JPanel {
	
	public MyDiscussionContainer(MyDiscussionPanel d) {
		super();
		setOpaque(false);
		setBorder(new EmptyBorder(15, 15, 15, 15));
		setLayout(new GridLayout(1, 0, 0, 0));
		add(d);
	}
}
