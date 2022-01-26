package clavardage.view.alert;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import clavardage.controller.gui.MainGUI;
import clavardage.view.Application;
import clavardage.view.Application.Destinataire;
import clavardage.view.listener.MouseCloseConversation;
import clavardage.view.mystyle.MyJButtonText;

public class ActionCloseConv implements ActionListener {
	
	public ActionCloseConv() {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (Application.getMessageWindow().getDiscussionDisplay().getTypeConversation() == Destinataire.User) {
				MainGUI.closeConversation(Application.getMessageWindow().getDiscussionDisplay().getIdConversation());
			} else {
				MouseCloseConversation.closeConversation(Application.getMessageWindow().getDiscussionDisplay().getIdConversation());
			}
			((MyJButtonText) e.getSource()).setForeground(Application.getBLUE());
			Application.getMessageWindow().setAlertOpen(false) ;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
	}


}
