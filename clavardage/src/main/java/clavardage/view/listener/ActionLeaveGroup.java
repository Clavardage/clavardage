package clavardage.view.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

import clavardage.controller.authentification.AuthOperations;
import clavardage.model.exceptions.UserNotConnectedException;
import clavardage.view.main.Application;
import clavardage.view.main.DestinataireJPanel;
import clavardage.view.mystyle.MyDate;
import clavardage.view.mystyle.MyDayInfo;
import clavardage.view.mystyle.MyInfoPanel;
import clavardage.view.mystyle.MyJButtonText;

public class ActionLeaveGroup implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			UUID idConv = Application.getMessageWindow().getDiscussionDisplay().getIdConversation()	;
			MyDate date = new MyDate();
			boolean newDay = ActionSendMessage.needDayPanel(date, Application.getMessageWindow().getDiscussionDisplay());
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
			AdapterLayout.redimConv();

			Application.getMessageWindow().getAllMessagesGroups().remove(Application.getMessageWindow().getDiscussionDisplay()) ;
			Application.getMessageWindow().getAllGroups().remove(oldGroup);
			Application.getMessageWindow().getListGroups().removeAll();
			for (DestinataireJPanel group : Application.getMessageWindow().getAllGroups()) {
				Application.getMessageWindow().getListGroups().add(group);
			}
			MouseCloseConversation.closeAllConversation();
			Application.getMessageWindow().getListGroups().validate();
			Application.getMessageWindow().getMessageContainer().validate();
			((MyJButtonText) e.getSource()).setForeground(Application.getBLUE());
			Application.getMessageWindow().setAlertOpen(false) ;


		} catch (UserNotConnectedException e1) {
			e1.printStackTrace();
		}
	}
}
