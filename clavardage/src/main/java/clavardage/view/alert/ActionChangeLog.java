package clavardage.view.alert;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import clavardage.controller.gui.MainGUI;
import clavardage.view.Application;

public class ActionChangeLog implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			MainGUI.editUsername(((MyChangeLogPanel)(Application.getMessageWindow().getMessageContainer().getViewport().getComponent(0))).getNewLogin().getText());
			ActionCloseAlert.closeAlert(e);
		} catch (Exception ex) {
			((MyChangeLogPanel)(Application.getMessageWindow().getMessageContainer().getViewport().getComponent(0))).getError().setText(ex.getMessage() + " Only 3 to 20 alphanumeric characters and underscores authorized");
			((MyChangeLogPanel)(Application.getMessageWindow().getMessageContainer().getViewport().getComponent(0))).getError().setVisible(true);
			ex.printStackTrace();
		}
		
	}

}
