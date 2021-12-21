package clavardage.view.main;

import java.awt.Font;

import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class MyTitle extends JTextArea {
	
	public MyTitle(String text) {
		super(text);
		setOpaque(false);
		setForeground(Application.COLOR_BLUE);
		setFont(new Font("Dialog", Font.PLAIN, 16));
		setBorder(new EmptyBorder(10, 15, 10, 15));
		setEditable(false);
		setHighlighter(null);
	}

}
