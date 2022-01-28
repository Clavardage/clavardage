package clavardage.view.alert;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import clavardage.controller.authentification.AuthOperations;
import clavardage.view.Application;
/**
 * @author Célestine Paillé
 */
public class ActionDisconnect implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		AuthOperations.disconnectUser();
		if(!AuthOperations.isUserConnected()) {
			Application.displayContent(Application.getApp(), Application.getLoginWindow());
		}
	}

}
