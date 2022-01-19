package clavardage.view.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

import clavardage.controller.authentification.AuthOperations;
import clavardage.model.exceptions.UserNotConnectedException;

public class ActionLeaveGroup implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			UUID idConv = Application.getMessageWindow().getDiscussionDisplay().getIdConversation()	;
			MyDate date = new MyDate();
			boolean newDay = Application.getMessageWindow().needDayPanel(date, Application.getMessageWindow().getDiscussionDisplay());
			if (newDay) {
				if (Application.getMessageWindow().getDiscussionDisplay().isEmptyDiscussion()) {
					Application.getMessageWindow().getDiscussionDisplay().startConversation();
				}
				Application.getMessageWindow().getDiscussionDisplay().add(new MyDayInfo(date));
			}
			Application.getMessageWindow().getDiscussionDisplay().add(new MyInfoPanel());
			Application.getMessageWindow().getDiscussionDisplay().removeMemberConversation(AuthOperations.getConnectedUser().getUUID());
			
			DestinataireJPanel oldGroup = null;
			for (DestinataireJPanel group : Application.getMessageWindow().getAllGroups()) {
				if (group.getIdDestinataire() == idConv) {
					oldGroup = group;
				}
			}
			
			Application.getMessageWindow().getAllMessagesGroups().remove(Application.getMessageWindow().getDiscussionDisplay()) ;
			Application.getMessageWindow().getAllGroups().remove(oldGroup);
			Application.getMessageWindow().getListGroups().removeAll();
			for (DestinataireJPanel group : Application.getMessageWindow().getAllGroups()) {
				Application.getMessageWindow().getListGroups().add(group);
			}
			
			Application.getMessageWindow().closeAllConversation();
			Application.getMessageWindow().getListGroups().validate();
			Application.getMessageWindow().getMessageContainer().validate();
			((MyJButtonText) e.getSource()).setForeground(Application.COLOR_BLUE);

		} catch (UserNotConnectedException e1) {
			e1.printStackTrace();
		}
	}
}
