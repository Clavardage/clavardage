package clavardage.view.main;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

public class MyRoundJTextField extends JTextField {

	private static final long serialVersionUID = 1L;
	
	private Shape shape;
	private boolean empty ;
    private int radius;
    
    public MyRoundJTextField(int r) {
        super();
        setOpaque(false);
        empty = true;
        radius = r;
    }

	protected void paintComponent(Graphics g) {
         g.setColor(getBackground());
         g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, this.radius, this.radius);
         super.paintComponent(g);
    }
    protected void paintBorder(Graphics g) {
         g.setColor(getBackground());
         g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, this.radius,this.radius);
    }
    public boolean contains(int x, int y) {
         if (shape == null || !shape.getBounds().equals(getBounds())) {
             shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, this.radius,this.radius);
         }
         return shape.contains(x, y);
    }
    
    public boolean isEmpty() {
    	return this.empty ;
    }
    
    public void setEmpty(boolean v) {
    	this.empty = v;
    }
}