package clavardage.view.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicScrollBarUI;

@SuppressWarnings("serial")
public class MyJScrollBarUI extends BasicScrollBarUI {

	private final Dimension d = new Dimension();

	public MyJScrollBarUI() {
		super();
		UIManager.getLookAndFeelDefaults().put("ScrollBar.minimumThumbSize", new Dimension(30,18));
	}
	
    @Override
    protected JButton createDecreaseButton(int orientation) {
      return new JButton() {
		@Override
        public Dimension getPreferredSize() { return d; }
      };
    }
    
    @Override
    protected JButton createIncreaseButton(int orientation) {
      return new JButton() {
        @Override
        public Dimension getPreferredSize() { return d; }
      };
    }
    
    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        Color color = Application.COLOR_SCROLL_BAR;
        g2.setPaint(color);
        g2.fillRoundRect(r.x,r.y,r.width,r.height,25,25);
        g2.setPaint(Application.COLOR_BACKGROUND);
        g2.drawRoundRect(r.x,r.y,r.width,r.height,25,25);
        g2.setBackground(Application.COLOR_BACKGROUND);
        g2.dispose();
    }
    
    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
      Graphics2D g2 = (Graphics2D)g.create();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_ON);
      Color color = null;
      JScrollBar sb = (JScrollBar)c;
      if(!sb.isEnabled() || r.width>r.height) {
        return;
      }else if(isDragging) {
        color = Application.COLOR_CURSOR_SCROLL_HOVER;
      }else if(isThumbRollover()) {
        color = Application.COLOR_CURSOR_SCROLL_HOVER;
      }else {
        color = Application.COLOR_CURSOR_SCROLL;
      }
      g2.setPaint(color);
      g2.fillRoundRect(r.x+4,r.y,r.width-8,r.height,20,20);
      g2.setPaint(Application.COLOR_CURSOR_SCROLL);
      g2.drawRoundRect(r.x+4,r.y,r.width-8,r.height,20,20);
      g2.dispose();
    }
    
    @Override
    protected void setThumbBounds(int x, int y, int width, int height) {
      super.setThumbBounds(x, y, width, height);
      scrollbar.repaint();
    }
       
    
}
