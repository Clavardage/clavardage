package clavardage.view.main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import clavardage.controller.gui.MainGUI;
import clavardage.view.main.MessageWindow.Destinataire;

public class MouseOpenConversation implements MouseListener {
	
	private DestinataireJPanel dest;

	
	public MouseOpenConversation(DestinataireJPanel dest) {
		super();
		this.dest = dest;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		try {		
			if (dest.getType() == Destinataire.User) {
				// TODO: display a loading in thread
				// TODO seulement si pastille pas bleue
				MainGUI.openConversation(dest.getIdDestinataire()); // open network communication
				// TODO: remove loading in thread
			}
			MessageWindow window = Application.getMessageWindow();
			window.openConversation(dest.getNameDestinataire(), dest.getIdDestinataire(), dest.getTypeDestinataire());
			dest.openConversationInList();
		} catch (Exception ex) {
			ex.printStackTrace();
			// TODO: remove loading in thread
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
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
