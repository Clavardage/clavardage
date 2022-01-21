package clavardage.view.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import clavardage.controller.authentification.AuthOperations;
import clavardage.view.main.Application;

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
		}
	}

}
