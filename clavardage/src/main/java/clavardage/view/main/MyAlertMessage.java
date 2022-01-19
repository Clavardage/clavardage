package clavardage.view.main;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class MyAlertMessage extends JLabel {
	
	public MyAlertMessage(String text) {
		super(text, SwingConstants.CENTER);
		setFont(new Font("Tahoma", Font.PLAIN, 24));
		setForeground(Application.COLOR_BLUE);
		setAlignmentX(CENTER_ALIGNMENT);
	}

	public MyAlertMessage(String text, int i) {
		super(text, SwingConstants.CENTER);
 		setFont(new Font("Tahoma", Font.PLAIN, i));
		setForeground(Application.COLOR_BLUE);
		setAlignmentX(CENTER_ALIGNMENT);	}
}
