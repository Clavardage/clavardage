package clavardage.view.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import clavardage.view.Application;
import clavardage.view.mystyle.MyJButtonText;

public class MouseFocusColor implements MouseListener {

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		((MyJButtonText) e.getSource()).setForeground(Application.getBLUE());
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		if ( ((MyJButtonText) e.getSource()).getText().equals("Confirm") ) {
			((MyJButtonText) e.getSource()).setForeground(Application.getGREEN());

		} else if ( ((MyJButtonText) e.getSource()).getText().equals("Cancel") ) {
			((MyJButtonText) e.getSource()).setForeground(Application.getRED());

		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

}
