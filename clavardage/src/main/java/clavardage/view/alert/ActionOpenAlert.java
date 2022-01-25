package clavardage.view.alert;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

import javax.swing.JMenuItem;

import clavardage.view.Application;
import clavardage.view.alert.MyAlertPanel.AlertAction;
import clavardage.view.listener.ActionSetColorTheme;
import clavardage.view.main.DestinataireJPanel;
import clavardage.view.mystyle.MyChangeLogPanel;

public class ActionOpenAlert implements ActionListener {

	private boolean needNameDest;
	private MyAlertPanel alert;
	private AlertAction typeAction ;
	
	public ActionOpenAlert(AlertAction action) {
		needNameDest = true ;
		typeAction = action;
		if (typeAction == AlertAction.CHANGELOG) {
			alert =  new MyChangeLogPanel();
			needNameDest = false;
		} else {
			alert = new MyAlertPanel(typeAction);
			if (typeAction == AlertAction.DISCONNECT) {needNameDest = false;}
		}
	}
	
	public ActionOpenAlert(DestinataireJPanel user, JMenuItem item) {
		needNameDest = true ;
		typeAction = AlertAction.ADDMEMBER;
		alert = new MyAlertPanel(user, item);
	}
	
	public void actionPerformed(ActionEvent e) {
			//display alert
			Application.getMessageWindow().getMessageContainer().setViewportView(alert);
			ActionSetColorTheme.customDiscussionDisplay(Application.getColorThemeApp());
			
			//hide useless information
			Application.getMessageWindow().getEditNameGroup().setVisible(false);
			Application.getMessageWindow().getSettingsGroups().setVisible(false);
			Application.getMessageWindow().getCloseDiscussion().setVisible(false);
			Application.getMessageWindow().getNewMsg().setVisible(false);
			Application.getMessageWindow().getNorthDiscussion().setVisible(needNameDest);
			Application.getMessageWindow().getMessageContainer().validate();

			//you have to click on cancel or confirm to close the alert
			Application.getMessageWindow().setAlertOpen(true) ;
			
			//for Change log
			if (typeAction == AlertAction.CHANGELOG) {((MyChangeLogPanel) alert).getNewLogin().requestFocus(); ((MyChangeLogPanel) alert).getNewLogin().selectAll();}
	}

}
