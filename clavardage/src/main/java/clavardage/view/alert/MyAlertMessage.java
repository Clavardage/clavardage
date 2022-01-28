package clavardage.view.alert;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

import clavardage.view.Application;
/**
 * @author Célestine Paillé
 */
@SuppressWarnings("serial")
public class MyAlertMessage extends JLabel {
	
	private Dimension maxSize ;
	private int sizeFontChosen, sizeFont;
	private String info;
	
	public MyAlertMessage(String text, int i) {
		super(text);
		setAlignmentX(CENTER_ALIGNMENT);
 		setFont(new Font("Tahoma", Font.PLAIN, i));
		setForeground(Application.getBLUE());
		maxSize = getPreferredSize();
		sizeFont = i ;
		sizeFontChosen = i ;
		info = text;
	}
	
	public MyAlertMessage(String text) {
		this(text, 26);
	}

	public Dimension getMaxSize() {
		return maxSize;
	}
	
	public int getSizeFont() {
		return sizeFont;
	}
	
	public int getSizeFontChosen() {
		return sizeFontChosen;
	}
	
	public void setSizeFont(int i) {
		sizeFont = i;
 		setFont(new Font("Tahoma", Font.PLAIN, sizeFont));
 		JLabel newInfo = new JLabel(info);
 		newInfo.setFont(new Font("Tahoma", Font.PLAIN, sizeFont));
		maxSize = newInfo.getPreferredSize();
	}

	public String getInfo() {
		return info;
	}
}
