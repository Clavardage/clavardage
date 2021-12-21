package clavardage.view.main;

import java.awt.Font;

import javax.swing.JLabel;

public class MyAlertMessage extends JLabel {
	
	public MyAlertMessage(String text, int contrainte) {
		super(text,contrainte);
		setFont(new Font("Tahoma", Font.PLAIN, 24));
		setForeground(Application.COLOR_BLUE);
	}

}
