package clavardage.view.alert;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import clavardage.model.exceptions.UserNotConnectedException;
import clavardage.view.Application;
import clavardage.view.listener.ActionSendMessage;
import clavardage.view.main.DestinataireJPanel;
import clavardage.view.mystyle.MyDate;
import clavardage.view.mystyle.MyDayInfo;
import clavardage.view.mystyle.MyInfoPanel;

public class ActionAddMember implements ActionListener {
	
	private JMenuItem item;
	
	public ActionAddMember(JMenuItem item) {
		this.item = item;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DestinataireJPanel newMember = null;
		for (DestinataireJPanel newUser : Application.getMessageWindow().getAllUsers()) {
			if (this.item.getText().equals(newUser.getNameDestinataire())) {
				newMember = newUser;
			}
		}
		
		Application.getMessageWindow().getDiscussionDisplay().addMemberConversation(newMember.getIdDestinataire());
		
		MyDate date = new MyDate();
		boolean newDay = ActionSendMessage.needDayPanel(date, Application.getMessageWindow().getDiscussionDisplay());
		if (newDay) {
    		if (Application.getMessageWindow().getDiscussionDisplay().isEmptyDiscussion()) {
    			Application.getMessageWindow().getDiscussionDisplay().startConversation();
    		}
    		Application.getMessageWindow().getDiscussionDisplay().add(new MyDayInfo(date));
		}
		try {
			Application.getMessageWindow().getDiscussionDisplay().add(new MyInfoPanel(newMember));
		} catch (UserNotConnectedException e1) {
			e1.printStackTrace();
		}
		
		ActionCloseAlert.closeAlert(e);
	}

}
