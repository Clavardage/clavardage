package clavardage.view.listener;

import java.awt.Component;
import java.util.UUID;

import clavardage.view.Application;
import clavardage.view.MessageWindow;
import clavardage.view.main.DestinataireJPanel;

/**
 * @author Célestine Paillé
 */
public class MouseCloseConversation {
	
	public static void closeConversation(UUID idConv) throws Exception {
		MessageWindow w = Application.getMessageWindow();
		w.getNewMsg().setVisible(false);
		w.getNorthDiscussion().setVisible(false);

		for (Component panel : w.getListUsers().getComponents()) {
			if (idConv == null || ((DestinataireJPanel) panel).getIdDestinataire().equals(idConv)) {
				((DestinataireJPanel) panel).closeConversationInList();
			}
		}
		
		for (Component panel : w.getListGroups().getComponents()) {
			if (idConv == null || ((DestinataireJPanel) panel).getIdDestinataire().equals(idConv)) {
				((DestinataireJPanel) panel).closeConversationInList();
			}
		}

		w.getMessageContainer().setViewportView(w.getAllDiscussionClose());
		w.setDiscussionDisplay(w.getAllDiscussionClose());
		ActionSetColorTheme.customDiscussionDisplay(Application.getColorThemeApp());
		
		w.setConversationOpen(false);
	}
	
	public static void closeAllConversation() throws Exception {
		closeConversation(null);
	}

}
