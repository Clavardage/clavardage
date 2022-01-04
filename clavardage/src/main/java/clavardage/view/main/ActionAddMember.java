package clavardage.view.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JMenuItem;

import clavardage.model.exceptions.UserNotConnectedException;

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
		boolean newDay = Application.getMessageWindow().needDayPanel(date);
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
		
		Application.getMessageWindow().getMessageContainer().setViewportView(Application.getMessageWindow().getDiscussionDisplay());
		Application.getMessageWindow().customDiscussionDisplay(Application.getColorThemeApp());
		Application.getMessageWindow().getEditNameGroup().setVisible(true);
		Application.getMessageWindow().getSettingsGroups().setVisible(true);
		Application.getMessageWindow().getNewMsg().setVisible(true);
		
		Application.getMessageWindow().getMessageContainer().validate();
		Application.getMessageWindow().createAddMemberInGroup();
		Application.getMessageWindow().createSeeMembersGroup();
		((MyJButtonText) e.getSource()).setForeground(Application.COLOR_BLUE);


	}

}
