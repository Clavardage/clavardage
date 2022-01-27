package clavardage.view.listener;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import clavardage.view.Application;
import clavardage.view.Application.ColorThemeApp;
import clavardage.view.LoginWindow;
import clavardage.view.MessageWindow;
import clavardage.view.SignInWindow;
import clavardage.view.main.DestinataireJPanel;
import clavardage.view.main.MessageBuble;
import clavardage.view.main.SectionTextJPanel;
import clavardage.view.mystyle.MyRoundJTextField;

/**
 * Gathers the methods modifying the color of the window based on the theme of the app.
 * @author Célestine Paillé
 */
public class ActionSetColorTheme implements ActionListener {
	
	private ColorThemeApp color;

	public ActionSetColorTheme(ColorThemeApp c) {
		color = c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		setColorTheme(color);		
	}
	
	public static void customThemeLogin(ColorThemeApp c) {
		changeColorThemeApp(c);
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
		changeColorThemeApp(c);
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
		changeColorThemeApp(c);
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
	
	
	/**
	 * Change the theme of all windows
	 * */
	public void setColorTheme(ColorThemeApp color) {
		customThemeMessage(color);
		customThemeLogin(color);
		customThemeSignIn(color);

	}
	
	/**
	 * Change all static colors according to the chosen theme
	 * @param color is the chosen theme (DARK or LIGHT)
	 * */
	public static void changeColorThemeApp(ColorThemeApp color) {
		Application.setColorThemeApp(color);
		if (color == ColorThemeApp.LIGHT) {
			Application.setCOLOR_BACKGROUND(new Color(247,249,251)) ;
			Application.setCOLOR_BACKGROUND2(new Color(255,255,255)) ;
			
			Application.setCOLOR_EDIT_MESSAGE(new Color(237,237,237)) ;
			
			Application.setCOLOR_MINE_MESSAGE(new Color(212,212,212)) ;
			Application.setCOLOR_TEXT_MINE_MESSAGE(new Color(0,0,0));

			Application.setCOLOR_SCROLL_BAR(new Color(241,242,243)) ;
			Application.setCOLOR_CURSOR_SCROLL(new Color(219,219,219)) ;
			Application.setCOLOR_CURSOR_SCROLL_HOVER(new Color(201,201,201)) ;
						
			Application.setCOLOR_TEXT(new Color(0,0,0));
		} else if (color == ColorThemeApp.DARK) {
			Application.setCOLOR_BACKGROUND(new Color(20,16,12)) ;
			Application.setCOLOR_BACKGROUND2(new Color(0,0,0)) ;
			
			Application.setCOLOR_EDIT_MESSAGE(new Color(18,18,18)) ;
			
			Application.setCOLOR_MINE_MESSAGE(new Color(43,43,43)) ;
			Application.setCOLOR_TEXT_MINE_MESSAGE(new Color(255,255,255)) ;

			Application.setCOLOR_SCROLL_BAR(new Color(14,13,12)) ;
			Application.setCOLOR_CURSOR_SCROLL(new Color(36,36,36)) ;
			Application.setCOLOR_CURSOR_SCROLL_HOVER(new Color(54,54,54)) ;
			
			Application.setCOLOR_TEXT(new Color (217,217,217));
		}
	}
}
