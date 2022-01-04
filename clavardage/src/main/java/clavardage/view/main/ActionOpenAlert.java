package clavardage.view.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		Application.getMessageWindow().customDiscussionDisplay(Application.getColorThemeApp());
		Application.getMessageWindow().getEditNameGroup().setVisible(false);
		Application.getMessageWindow().getSettingsGroups().setVisible(false);
		Application.getMessageWindow().getNewMsg().setVisible(false);
	}

}
