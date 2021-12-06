package clavardage.view.main;

import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

public class MyRoundJPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Shape shape;
    private int radius;

    
    public MyRoundJPanel(int r) {
    	super();
        setOpaque(false);
        radius = r;

    }

	protected void paintComponent(Graphics g) {
         g.setColor(getBackground());
         g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, this.radius, this.radius);
         super.paintComponent(g);
    }
    protected void paintBorder(Graphics g) {
         g.setColor(getBackground());
         g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, this.radius, this.radius);
    }
    public boolean contains(int x, int y) {
         if (shape == null || !shape.getBounds().equals(getBounds())) {
             shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, this.radius, this.radius);
         }
         return shape.contains(x, y);
    }
}
