package clavardage.view.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import clavardage.controller.authentification.AuthOperations;
import clavardage.view.main.Application;
import clavardage.view.mystyle.MyJButtonText;

public class ActionDisconnect implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		AuthOperations.disconnectUser();
		if(!AuthOperations.isUserConnected()) {
			try {
				MouseCloseConversation.closeConversation();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			Application.displayContent(Application.getApp(), Application.getLoginWindow());
			
			MouseCloseConversation.closeAllConversation();
			Application.getMessageWindow().getMessageContainer().validate();
			((MyJButtonText) e.getSource()).setForeground(Application.getBLUE());
			Application.getMessageWindow().getNorthDiscussion().setVisible(true);
			Application.getMessageWindow().setAlertOpen(false) ;

		}
	}

}
