package clavardage.view.main;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class MyJButtonText extends JButton {
	
	public MyJButtonText(String text) {
		super(text);
		setForeground(Application.COLOR_BLUE);
		setFont(new Font("Dialog", Font.PLAIN, 20));
		setBorderPainted(false);
		setFocusPainted(false);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setContentAreaFilled(false);
		setMargin(new Insets(0, 0, 0, 0));
	}

}
