package clavardage.view.main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.UUID;

import javax.swing.JTextArea;

import clavardage.view.main.MessageWindow.Destinataire;

public class MouseOpenConversation implements MouseListener {
	
	private DestinataireJPanel dest;

	
	public MouseOpenConversation(DestinataireJPanel dest) {
		super();
		this.dest = dest;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		MessageWindow window = Application.getMessageWindow();
		window.openConversation(dest.getNameDestinataire(), dest.getIdDestinataire(), dest.getTypeDestinataire());
		try {
			dest.openMyConversation();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		dest.getPanelName().setForeground(Application.COLOR_RED);

	}

	@Override
	public void mouseExited(MouseEvent e) {
		dest.getPanelName().setForeground(dest.getPanel().getForeground());		    
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
