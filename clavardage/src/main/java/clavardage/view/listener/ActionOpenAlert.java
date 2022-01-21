package clavardage.view.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import clavardage.view.main.Application;
import clavardage.view.main.DestinataireJPanel;
import clavardage.view.mystyle.MyAlertPanel;

public class ActionOpenAlert implements ActionListener {

	private MyAlertPanel alert;
	
	public ActionOpenAlert(ActionListener action) {
		alert = new MyAlertPanel(action);
	}
	
	public ActionOpenAlert(DestinataireJPanel user, ActionListener action) {
		alert = new MyAlertPanel(user, action);
	}

	public void actionPerformed(ActionEvent e) {
		Application.getMessageWindow().getMessageContainer().setViewportView(alert);
		ActionSetColorTheme.customDiscussionDisplay(Application.getColorThemeApp());
		Application.getMessageWindow().getEditNameGroup().setVisible(false);
		Application.getMessageWindow().getSettingsGroups().setVisible(false);
		Application.getMessageWindow().getNewMsg().setVisible(false);
	}

}
