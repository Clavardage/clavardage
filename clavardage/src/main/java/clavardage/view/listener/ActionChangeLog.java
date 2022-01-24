package clavardage.view.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import clavardage.controller.gui.MainGUI;
import clavardage.view.main.Application;
import clavardage.view.main.MessageWindow.Destinataire;
import clavardage.view.mystyle.MyChangeLogPanel;
import clavardage.view.mystyle.MyJButtonText;

public class ActionChangeLog implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			MainGUI.editUsername(((MyChangeLogPanel)(Application.getMessageWindow().getMessageContainer().getViewport().getComponent(0))).getNewLogin().getText());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Application.getMessageWindow().getMessageContainer().setViewportView(Application.getMessageWindow().getDiscussionDisplay());
		ActionSetColorTheme.customDiscussionDisplay(Application.getColorThemeApp());
		if (Application.getMessageWindow().isConversationOpen()) {
			if (Application.getMessageWindow().getDiscussionDisplay().getTypeConversation() == Destinataire.Group) {
				Application.getMessageWindow().getEditNameGroup().setVisible(true);
				Application.getMessageWindow().getSettingsGroups().setVisible(true);
			}
			Application.getMessageWindow().getNewMsg().setVisible(true);
		}
		Application.getMessageWindow().getMessageContainer().validate();
		((MyJButtonText) e.getSource()).setForeground(Application.getBLUE());
		Application.getMessageWindow().getNorthDiscussion().setVisible(true);
		Application.getMessageWindow().setAlertOpen(false) ;

	}

}
