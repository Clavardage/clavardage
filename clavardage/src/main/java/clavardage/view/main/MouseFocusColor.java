package clavardage.view.main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseFocusColor implements MouseListener {

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		((MyJButtonText) e.getSource()).setForeground(Application.COLOR_BLUE);
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		if ( ((MyJButtonText) e.getSource()).getText().equals("Confirm") ) {
			((MyJButtonText) e.getSource()).setForeground(Application.COLOR_GREEN);

		} else if ( ((MyJButtonText) e.getSource()).getText().equals("Cancel") ) {
			((MyJButtonText) e.getSource()).setForeground(Application.COLOR_RED);

		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

}
