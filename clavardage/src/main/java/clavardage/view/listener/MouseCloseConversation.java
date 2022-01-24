package clavardage.view.listener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import clavardage.view.main.Application;
import clavardage.view.main.DestinataireJPanel;
import clavardage.view.main.MessageWindow;

public class MouseCloseConversation implements ActionListener {

	public MouseCloseConversation() {
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {	
	}
	
	public static void closeConversation() throws Exception {
		MessageWindow w = Application.getMessageWindow();
		/* hide newMsg and nameDestinataire if it is necessary */
		if (w.isConversationOpen()) {
			w.setConversationOpen(false);
			w.getNameDestinataire().setText("");
			w.getNewMsg().setVisible(false);
			
			for (Component panel : w.getListUsers().getComponents()) {
				((DestinataireJPanel) panel).closeConversationInList();
			}
			for (Component panel : w.getListGroups().getComponents()) {
				((DestinataireJPanel) panel).closeConversationInList();
			}
		}
		
		w.getMessageContainer().setViewportView(w.getAllDiscussionClose());
		w.setDiscussionDisplay(w.getAllDiscussionClose());
		w.getMessageContainer().validate();
	}
	
	public static void closeAllConversation() {
		MessageWindow w = Application.getMessageWindow();
		w.getNameDestinataire().setText("");
		w.getEditNameGroup().setVisible(false);
		w.getSettingsGroups().setVisible(false);
		w.getNewMsg().setVisible(false);
		w.getMessageContainer().setViewportView(w.getAllDiscussionClose());
		w.setDiscussionDisplay(w.getAllDiscussionClose());
		ActionSetColorTheme.customDiscussionDisplay(Application.getColorThemeApp());
		w.setConversationOpen(false) ;
	}

}
