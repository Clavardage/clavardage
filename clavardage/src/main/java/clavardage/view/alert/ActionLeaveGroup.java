package clavardage.view.alert;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

import clavardage.controller.authentification.AuthOperations;
import clavardage.view.Application;
import clavardage.view.listener.ActionSendMessage;
import clavardage.view.main.DestinataireJPanel;
import clavardage.view.mystyle.MyDate;
import clavardage.view.mystyle.MyDayInfo;
import clavardage.view.mystyle.MyInfoPanel;
/**
 * @author Célestine Paillé
 */
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

			ActionCloseAlert.closeAlert(e, oldGroup);

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
