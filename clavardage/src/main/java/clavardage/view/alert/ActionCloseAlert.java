package clavardage.view.alert;

import java.awt.event.ActionEvent;

import clavardage.view.Application;
import clavardage.view.Application.Destinataire;
import clavardage.view.listener.ActionSetColorTheme;
import clavardage.view.listener.AdapterLayout;
import clavardage.view.listener.MouseCloseConversation;
import clavardage.view.listener.MouseOpenConversation;
import clavardage.view.main.DestinataireJPanel;
import clavardage.view.mystyle.MyJButtonText;

public class ActionCloseAlert {
	

	
	public static void closeAlert(ActionEvent e) {
		Application.getMessageWindow().getMessageContainer().setViewportView(Application.getMessageWindow().getDiscussionDisplay());
		ActionSetColorTheme.customDiscussionDisplay(Application.getColorThemeApp());
		if (Application.getMessageWindow().isConversationOpen()) {
			if (Application.getMessageWindow().getDiscussionDisplay().getTypeConversation() == Destinataire.Group) {
				Application.getMessageWindow().getEditNameGroup().setVisible(true);
				Application.getMessageWindow().getSettingsGroups().setVisible(true);
				MouseOpenConversation.createAddMemberInGroup();
				MouseOpenConversation.createSeeMembersGroup();
			}
			Application.getMessageWindow().getCloseDiscussion().setVisible(true);
			Application.getMessageWindow().getNewMsg().setVisible(true);
		}
		Application.getMessageWindow().getMessageContainer().validate();
		((MyJButtonText) e.getSource()).setForeground(Application.getBLUE());
		Application.getMessageWindow().getNorthDiscussion().setVisible(true);
		Application.getMessageWindow().setAlertOpen(false) ;

		AdapterLayout.redimConv();		
	}
	
	public static void closeAlert(ActionEvent e, DestinataireJPanel oldGroup) throws Exception {
		
		Application.getMessageWindow().getAllGroups().remove(oldGroup);
		Application.getMessageWindow().getListGroups().removeAll();
		for (DestinataireJPanel group : Application.getMessageWindow().getAllGroups()) {
			Application.getMessageWindow().getListGroups().add(group);
		}
		MouseCloseConversation.closeConversation(oldGroup.getIdDestinataire());
		Application.getMessageWindow().getListGroups().validate();
		Application.getMessageWindow().getMessageContainer().validate();
		((MyJButtonText) e.getSource()).setForeground(Application.getBLUE());
		Application.getMessageWindow().setAlertOpen(false) ;
		
	}

	



}
