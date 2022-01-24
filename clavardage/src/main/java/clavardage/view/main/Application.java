package clavardage.view.main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import clavardage.view.listener.ActionSetColorTheme;
import clavardage.view.listener.AdapterLayout;

@SuppressWarnings("serial")
public class Application extends JFrame {

	/* **  ** */
	private static MessageWindow message;
	private static LoginWindow login;
	private static SignInWindow signIn;
	private static JFrame app;
	private static JPanel displayContent = new JPanel();
	
	/* ** Colors ** */
	public enum ColorThemeApp {LIGHT, DARK;}
	protected static Color COLOR_BACKGROUND ;
	protected static Color COLOR_BACKGROUND2 ;
	protected static Color COLOR_EDIT_MESSAGE ;
	protected static Color COLOR_MINE_MESSAGE ;
	protected static Color COLOR_TEXT_MINE_MESSAGE ;
	protected static Color COLOR_SCROLL_BAR ;
	protected static Color COLOR_CURSOR_SCROLL ;
	protected static Color COLOR_CURSOR_SCROLL_HOVER ;
	protected static Color COLOR_SHADOW ;
	protected static Color COLOR_TEXT ;
	protected static Color COLOR_TEXT_EDIT = new Color(127,127,127);
	protected static Color COLOR_TEXT_THEIR_MESSAGE = new Color(255,255,255);
	protected static Color COLOR_BLUE = new Color(72,125,244) ;
	protected static Color COLOR_RED = new Color(238,34,34) ;
	protected static Color COLOR_GREEN = new Color(11,177,58) ;
	protected static Color COLOR_PURPLE = new Color(157,22,180) ;
	protected static Color COLOR_YELLOW = new Color(247,234,69) ;

	private static ColorThemeApp colorThemeApp ;	
	
	public enum LanguageApp {FRENCH, ENGLISH, SPANISH, GERMAN, CHINESE, JAPANESE;}
	private static LanguageApp languageApp ;	


	public Application(String title, ImageIcon icon) {
		
		Application.app = this;
		this.setTitle(title); 
		this.setIconImage(icon.getImage());
//		this.setSize(1200,800);
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH); //full size
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null); //center
		this.setMinimumSize(new Dimension(1200,800));
		colorThemeApp = ColorThemeApp.LIGHT; //default theme
		setLanguageApp(LanguageApp.ENGLISH); //default language
		
		try {
			createLoginWindow();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		displayContent(app, login);
		
		AdapterLayout listener = new AdapterLayout();
		this.addComponentListener(listener);
	}

	
	/**
	 * Create the login Window.
	 * @throws IOException
	 * */
	public static void createLoginWindow() throws IOException {
		login = new LoginWindow();
	}
	
	/**
	 * Create the message Window.
	 * @throws Exception 
	 * */
	public static void createMessageWindow() throws Exception {
		message = new MessageWindow();
	}
	
	public static void createSignInWindow() throws IOException {
		signIn = new SignInWindow();		
	}

	
	/**
	 * Change the theme of all windows
	 * */
	public static void setColorTheme(ColorThemeApp color) {
		ActionSetColorTheme.customThemeMessage(color);
		ActionSetColorTheme.customThemeLogin(color);
	}
	
	/**
	 * Change the display window
	 * */
	public static void displayContent(JFrame app, JPanel content) {
		displayContent = content;
		app.setContentPane(content);
		
		/* Apply the chosen theme */
		if(content.getClass().getName().equals("clavardage.view.main.LoginWindow")) {
			ActionSetColorTheme.customThemeLogin(Application.getColorThemeApp());
		} else if (content.getClass().getName().equals("clavardage.view.main.MessageWindow")) {
			ActionSetColorTheme.customThemeMessage(Application.getColorThemeApp());
		} else if (content.getClass().getName().equals("clavardage.view.main.SignInWindow")) {
			ActionSetColorTheme.customThemeSignIn(Application.getColorThemeApp());
		}
		
		app.revalidate();
		app.repaint();
	}
	
	public static Component getContentDisplay() {return displayContent;}
	public static JFrame getApp() {return app;}
	public static MessageWindow getMessageWindow() {return message;}
	public static LoginWindow getLoginWindow() {return login;}
	public static SignInWindow getSignInWindow() {return signIn;}
	public static ColorThemeApp getColorThemeApp() {return colorThemeApp;}
	public static Dimension getGlobalSize() {return app.getSize();}
	
	/**
	 * Change all colors according to the chosen theme
	 * */
	public void changeColorThemeApp(ColorThemeApp color) {
		colorThemeApp = color;
		if (color == ColorThemeApp.LIGHT) {
			COLOR_BACKGROUND = new Color(247,249,251) ;
			COLOR_BACKGROUND2 = new Color(255,255,255) ;
			
			COLOR_EDIT_MESSAGE = new Color(237,237,237) ;
			
			COLOR_MINE_MESSAGE = new Color(212,212,212) ;
			COLOR_TEXT_MINE_MESSAGE = new Color(0,0,0);

			COLOR_SCROLL_BAR = new Color(241,242,243) ;
			COLOR_CURSOR_SCROLL = new Color(219,219,219) ;
			COLOR_CURSOR_SCROLL_HOVER = new Color(201,201,201) ;
			
			COLOR_SHADOW = new Color(165,165,165,50) ;
			
			COLOR_TEXT = new Color(0,0,0);
		} else if (color == ColorThemeApp.DARK) {
			COLOR_BACKGROUND = new Color(20,16,12) ;
			COLOR_BACKGROUND2 = new Color(0,0,0) ;
			
			COLOR_EDIT_MESSAGE = new Color(18,18,18) ;
			
			COLOR_MINE_MESSAGE = new Color(43,43,43) ;
			COLOR_TEXT_MINE_MESSAGE = new Color(255,255,255) ;

			COLOR_SCROLL_BAR = new Color(14,13,12) ;
			COLOR_CURSOR_SCROLL = new Color(36,36,36) ;
			COLOR_CURSOR_SCROLL_HOVER = new Color(54,54,54) ;
			
			COLOR_SHADOW = new Color(165,165,165,50) ;
			COLOR_TEXT = new Color (217,217,217);
		}
	}


	public static Color getBLUE() {
		return COLOR_BLUE;
	}


	public static Color getRED() {
		return COLOR_RED;
	}


	public static Color getGREEN() {
		return COLOR_GREEN;
	}

	public static Color getPURPLE() {
		return COLOR_PURPLE;
	}

	public static Color getYELLOW() {
		return COLOR_YELLOW;
	}
	
	public static Color getCOLOR_TEXT_EDIT() {
		return COLOR_TEXT_EDIT;
	}


	public static Color getCOLOR_SCROLL_BAR() {
		return COLOR_SCROLL_BAR;
	}


	public static Color getCOLOR_BACKGROUND() {
		return COLOR_BACKGROUND;
	}


	public static Color getCOLOR_CURSOR_SCROLL_HOVER() {
		return COLOR_CURSOR_SCROLL_HOVER;
	}


	public static Color getCOLOR_CURSOR_SCROLL() {
		return COLOR_CURSOR_SCROLL;
	}


	public static Color getCOLOR_TEXT() {
		return COLOR_TEXT;
	}


	public static Color getCOLOR_BACKGROUND2() {
		return COLOR_BACKGROUND2;
	}


	public static Color getCOLOR_EDIT_MESSAGE() {
		return COLOR_EDIT_MESSAGE;
	}


	public static LanguageApp getLanguageApp() {
		return languageApp;
	}


	public static void setLanguageApp(LanguageApp languageApp) {
		Application.languageApp = languageApp;
	}
}