package clavardage.view.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import clavardage.view.main.Application;
import clavardage.view.main.MessageWindow.Destinataire;
import clavardage.view.mystyle.MyJButtonText;

public class ActionChangeLog implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Changement du login à coder");
		
		
		
		//TODO
		
		
		
		
		
		Application.getMessageWindow().getMessageContainer().setViewportView(Application.getMessageWindow().getDiscussionDisplay());
		ActionSetColorTheme.customDiscussionDisplay(Application.getColorThemeApp());
		if (Application.getMessageWindow().isConversationOpen()) {
			if (Application.getMessageWindow().getDiscussionDisplay().getTypeConversation() == Destinataire.Group) {
				Application.getMessageWindow().getEditNameGroup().setVisible(true);
				Application.getMessageWindow().getSettingsGroups().setVisible(true);
			}
			Application.getMessageWindow().getNewMsg().setVisible(true);
		}
		Application.getMessageWindow().getMessageContainer().validate();
		((MyJButtonText) e.getSource()).setForeground(Application.getBLUE());
		Application.getMessageWindow().getNorthDiscussion().setVisible(true);
		Application.getMessageWindow().setAlertOpen(false) ;

	}

}
