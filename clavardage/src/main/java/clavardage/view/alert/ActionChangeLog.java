package clavardage.view.alert;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import clavardage.controller.gui.MainGUI;
import clavardage.view.Application;
import clavardage.view.mystyle.MyChangeLogPanel;

public class ActionChangeLog implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			MainGUI.editUsername(((MyChangeLogPanel)(Application.getMessageWindow().getMessageContainer().getViewport().getComponent(0))).getNewLogin().getText());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		ActionCloseAlert.closeAlert(e);
	}

}
