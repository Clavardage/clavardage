package clavardage.view.mystyle;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

import clavardage.view.main.Application;

@SuppressWarnings("serial")
public class MyAlertMessage extends JLabel {
	
	private Dimension maxSize ;
	
	public MyAlertMessage(String text, int i) {
		super(text);
		setAlignmentX(CENTER_ALIGNMENT);
 		setFont(new Font("Tahoma", Font.PLAIN, i));
		setForeground(Application.getBLUE());
		maxSize = getPreferredSize();

		setOpaque(true);
		setBackground(Application.getRED());
	}
	
	public MyAlertMessage(String text) {
		this(text, 24);
	}

	public Dimension getMaxSize() {
		return maxSize;
	}
}
