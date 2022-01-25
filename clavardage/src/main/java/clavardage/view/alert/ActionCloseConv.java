package clavardage.view.alert;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

import clavardage.view.Application;
import clavardage.view.listener.MouseCloseConversation;
import clavardage.view.mystyle.MyJButtonText;

public class ActionCloseConv implements ActionListener {
	
	public ActionCloseConv() {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			MouseCloseConversation.closeConversation(Application.getMessageWindow().getDiscussionDisplay().getIdConversation());
			((MyJButtonText) e.getSource()).setForeground(Application.getBLUE());
			Application.getMessageWindow().setAlertOpen(false) ;
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		
		
	}


}
