package clavardage.view.listener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import clavardage.view.main.Application;
import clavardage.view.main.Application.ColorThemeApp;
import clavardage.view.main.DestinataireJPanel;
import clavardage.view.main.LoginWindow;
import clavardage.view.main.MessageBuble;
import clavardage.view.main.MessageWindow;
import clavardage.view.main.SectionTextJPanel;
import clavardage.view.main.SignInWindow;
import clavardage.view.mystyle.MyRoundJTextField;

public class ActionSetColorTheme implements ActionListener {
	
	private ColorThemeApp color;

	public ActionSetColorTheme(ColorThemeApp c) {
		color = c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Application.setColorTheme(color);		
	}
	
	public static void customThemeLogin(ColorThemeApp c) {
		((Application) Application.getApp()).changeColorThemeApp(c);
		LoginWindow w = Application.getLoginWindow();
		w.setBackground(Application.getCOLOR_BACKGROUND());
		w.getLogPanel().setBackground(Application.getCOLOR_BACKGROUND2());	

		/* ** Sections ** */
		w.getSections().setBackground(Application.getCOLOR_BACKGROUND2());
		for (Component panel : w.getSections().getComponents()) {
			if ( panel.getClass().getName().equals("clavardage.view.main.SectionTextJPanel")  ) {
				((SectionTextJPanel) panel).setColorTextSession(Application.getCOLOR_EDIT_MESSAGE(), Application.getCOLOR_TEXT_EDIT());
			}
		}
		w.getSectionContainer().getVerticalScrollBar().setBackground(Application.getCOLOR_BACKGROUND2());
		
		w.getLogButton().setForeground(Application.getCOLOR_BACKGROUND2());
	}
	
	public static void customThemeSignIn(ColorThemeApp c) {
		((Application) Application.getApp()).changeColorThemeApp(c);
		SignInWindow w = Application.getSignInWindow();
		w.setBackground(Application.getCOLOR_BACKGROUND());
		w.getLogPanel().setBackground(Application.getCOLOR_BACKGROUND2());	

		/* ** Sections ** */
		w.getSections().setBackground(Application.getCOLOR_BACKGROUND2());
		for (Component panel : w.getSections().getComponents()) {
			if ( panel.getClass().getName().equals("clavardage.view.main.SectionTextJPanel")  ) {
				((SectionTextJPanel) panel).setColorTextSession(Application.getCOLOR_EDIT_MESSAGE(), Application.getCOLOR_TEXT_EDIT());
			}
		}
		w.getSectionContainer().getVerticalScrollBar().setBackground(Application.getCOLOR_BACKGROUND2());
		
		w.getLogButton().setForeground(Application.getCOLOR_BACKGROUND2());
	}
	
	public static void customThemeMessage(ColorThemeApp c) {
		((Application) Application.getApp()).changeColorThemeApp(c);
		MessageWindow w = Application.getMessageWindow();

		
		w.setBackground(Application.getCOLOR_BACKGROUND());

		/* ** Menu Bar ** */
		w.getMenuBar().setBackground(Application.getCOLOR_BACKGROUND2());

		/* ** Body's App ** */
		w.getBodyApp().setBackground(Application.getCOLOR_BACKGROUND());
		
		// -- Destinataires -- //
		w.getListUsers().setBackground(Application.getCOLOR_BACKGROUND());
		for (Component panel : w.getListUsers().getComponents()) {
			((DestinataireJPanel) panel).setForegroundNamePanel();
		}
		w.getUsersContainer().getVerticalScrollBar().setBackground(Application.getCOLOR_BACKGROUND());
		
		w.getListGroups().setBackground(Application.getCOLOR_BACKGROUND());
		for (Component panel : w.getListGroups().getComponents()) {
			((DestinataireJPanel) panel).setForegroundNamePanel();
		}
		w.getGroupsContainer().getVerticalScrollBar().setBackground(Application.getCOLOR_BACKGROUND()); 
		
		// -- Discussion -- //
		w.getDiscussion().setBackground(Application.getCOLOR_BACKGROUND2());
		customDiscussionDisplay(c);
		
		w.getMessageContainer().getVerticalScrollBar().setBackground(Application.getCOLOR_BACKGROUND2());

		w.getEditMsg().setBackground(Application.getCOLOR_EDIT_MESSAGE());
		if (((MyRoundJTextField) w.getEditMsg()).isEmptyText()) {
			w.getEditMsg().setForeground(Application.getCOLOR_TEXT_EDIT());
		} else {
			w.getEditMsg().setForeground(Application.getCOLOR_TEXT());
		}
	}
	
	public static void customDiscussionDisplay(ColorThemeApp c) {
		MessageWindow w = Application.getMessageWindow();
		for (Component panel : w.getDiscussionDisplay().getComponents()) {
			if (panel.getClass().getName() == "clavardage.view.main.MessageBuble") {
				((MessageBuble) panel).setColorPanel();
			}
		}
		w.getMessageContainer().getViewport().getView().setBackground(Application.getCOLOR_BACKGROUND2());
	}
}
