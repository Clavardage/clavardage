package clavardage.view.main;

import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class MyJButton extends JButton {

	public MyJButton(ImageIcon icon, ImageIcon iconHover) {
		super();
		setOpaque(false);
		setBorderPainted(false);
		setFocusPainted(false);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setIgnoreRepaint(true);
		setContentAreaFilled(false);
		setIcon(icon);
		setRolloverIcon(iconHover);
		setPreferredSize(new Dimension(this.getIcon().getIconWidth(),this.getIcon().getIconHeight()));
	}
}
