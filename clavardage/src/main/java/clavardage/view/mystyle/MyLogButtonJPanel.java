package clavardage.view.mystyle;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MyLogButtonJPanel extends JPanel {
	private Image img;

	public MyLogButtonJPanel(Image img) {
		super();
		this.img = img;
		setOpaque(false);
		setPreferredSize(new Dimension(200, 80));
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setLayout(new GridBagLayout());
	}
	public void paintComponent(Graphics graphics) 
	{
		graphics.drawImage(img.getScaledInstance(-1, this.getSize().height, Image. SCALE_SMOOTH), 0, 0 , this);
		super.paintComponent(graphics);
	}

	public void setImg(Image newImg) {
		img = newImg;
	}

}
