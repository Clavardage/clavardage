package clavardage.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import clavardage.view.listener.ActionSendMessage;
import clavardage.view.listener.ActionSetColorTheme;
import clavardage.view.listener.AdapterLayout;
import clavardage.view.main.MessageBuble;
import clavardage.view.main.SectionTextJPanel;

/**
 * Main view
 * @author Célestine Paillé
 */
@SuppressWarnings("serial")
public class Application extends JFrame {

	private static MessageWindow message;
	private static LoginWindow login;
	private static SignInWindow signIn;
	
	/**The current JFrame of the application.*/
	private static JFrame app;
	
	/**
	 * The current panel display in the App's JFrame.
	 * <p>
	 * Necessary for <code>AdapterLayout</code>.
	 */
	private static JPanel displayContent = new JPanel();
	
	/**
	 * The current language of the application. <br>
	 * For the moment, <code>languageApp</code> is always equals to <code>EGLISH</code>.
	 * @see LanguageApp
	 */
	private static LanguageApp languageApp ;
	
	/**
	 * The current theme of the application
	 * @see ColorThemeApp
	 * */
	private static ColorThemeApp colorThemeApp ;
	
	/* ** Colors ** */
	private static Color COLOR_BACKGROUND ;
	private static Color COLOR_BACKGROUND2 ;
	/** Color of the background of JTextField*/
	private static Color COLOR_EDIT_MESSAGE ;
	/**	Color of the bubble of mine message*/
	private static Color COLOR_MINE_MESSAGE ;
	private static Color COLOR_TEXT_MINE_MESSAGE ;
	private static Color COLOR_SCROLL_BAR ;
	private static Color COLOR_CURSOR_SCROLL ;
	private static Color COLOR_CURSOR_SCROLL_HOVER ;
	private static Color COLOR_TEXT ;
	private static Color COLOR_TEXT_EDIT = new Color(127,127,127);
	private static Color COLOR_TEXT_THEIR_MESSAGE = new Color(255,255,255);
	private static Color COLOR_BLUE = new Color(72,125,244) ;
	private static Color COLOR_RED = new Color(238,34,34) ;
	private static Color COLOR_GREEN = new Color(11,177,58) ;
	private static Color COLOR_PURPLE = new Color(157,22,180) ;
	private static Color COLOR_YELLOW = new Color(247,234,69) ;

	/**
	 * To define the theme of the App <br>
	 * Can be <code>LIGHT</code> or <code>DARK</code>.
	 */
	public enum ColorThemeApp {LIGHT, DARK;}
	
	/**
	 * To define the language of the App <br>
	 * Can be <code>FRENCH</code>, <code>ENGLISH</code>, <code>SPANISH</code>, 
	 * <code>GERMAN</code>, <code>CHINESE</code> or <code>JAPANESE</code><br>
	 * For the moment, we really use only the language <code>EGLISH</code>.
	 */
	public enum LanguageApp {FRENCH, ENGLISH, SPANISH, GERMAN, CHINESE, JAPANESE;}
	
	/**
	 * To define the type of the Sections text in Login and Sign In window. <br>
	 * Can be <code>LOG</code> (login) if we can see the typed text or <code>PW</code> (password) if he typed text is hided.
	 * @see LoginWindow
	 * @see LoginWindow#createSection
	 * @see SectionTextJPanel
	 */
	public enum SectionText {LOG, PW;}
	
	/**
	 * To define the type of the message MessageWindow. <br>
	 * Can be <code>MINE</code> if we send it or <code>THEIR</code> if we receive it.
	 * @see MessageWindow
	 * @see MessageBuble
	 * @see ActionSendMessage
	 */
	public enum TypeBuble {MINE, THEIR;}
	
	/**
	 * To define the type of the conversation or the destinataire. <br>
	 * Can be <code>User</code> or <code>Group</code>.
	 * @see MessageWindow
	 * @see MessageBuble
	 * @see ActionSendMessage
	 */
	public enum Destinataire {User,Group;}


	public Application(String title, ImageIcon icon) {
		
		Application.app = this;
		this.setTitle(title); 
		this.setIconImage(icon.getImage());
		this.setSize(1200,800);
		this.setLocationRelativeTo(null); //center
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH); //full size
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(800,600));
		colorThemeApp = ColorThemeApp.LIGHT; //default theme
		languageApp = LanguageApp.ENGLISH; //default language
		
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
	 * @see LoginWindow
	 * */
	public static void createLoginWindow() throws IOException {
		login = new LoginWindow();
	}
	
	/**
	 * Create the message Window.
	 * @throws Exception
	 * @see MessageWindow
	 * */
	public static void createMessageWindow() throws Exception {
		message = new MessageWindow();
	}
	
	/**
	 * Create the Sign In Window.
	 * @throws IOException 
	 * @see SignInWindow
	 * */
	public static void createSignInWindow() throws IOException {
		signIn = new SignInWindow();		
	}

	
	/**
	 * Display <code>content</code> in the JFrame <code>app</code>. <br>
	 * Modify attribute <code>displayContent</code> and adapt the theme of <code>content</code> with <code>colorThemeApp</code>
	 * @param app the App's JFrame
	 * @param content the panel we want to display
	 * */
	public static void displayContent(JFrame app, JPanel content) {
		displayContent = content;
		app.setContentPane(content);
		
		/* Apply the chosen theme */
		if(content.getClass().getName().equals("clavardage.view.LoginWindow")) {
			ActionSetColorTheme.customThemeLogin(colorThemeApp);
		} else if (content.getClass().getName().equals("clavardage.view.MessageWindow")) {
			ActionSetColorTheme.customThemeMessage(colorThemeApp);
		} else if (content.getClass().getName().equals("clavardage.view.SignInWindow")) {
			ActionSetColorTheme.customThemeSignIn(colorThemeApp);
		}
		
		app.revalidate();
		app.repaint();
	}
	
	/**
	 * Return the size of the window open in the user's computer
	 * @return the size of the App's JFrame
	 */
	public static Dimension getGlobalSize() {return app.getSize();}	
	
	/**
	 * Create a translucent margin based on <code>x</code> and <code>y</code>.
	 * @param x the horizontal size
	 * @param y the vertical size
	 * @return a JPanel translucent with (x,y) for size
	 */
	public static JPanel createMargin(int x, int y) {
		JPanel marge = new JPanel();
		marge.setPreferredSize(new Dimension(x,y));
		marge.setMaximumSize(marge.getPreferredSize());
		marge.setOpaque(false);
		return marge ;
	}
	
	/* ** GETTER AND SETTER ** */	
	public static MessageWindow getMessageWindow() {return message;}
	public static LoginWindow getLoginWindow() {return login;}
	public static SignInWindow getSignInWindow() {return signIn;}
	public static JFrame getApp() {return app;}
	public static Component getContentDisplay() {return displayContent;}
	public static LanguageApp getLanguageApp() {return languageApp;}
	public static void setLanguageApp(LanguageApp languageApp) {Application.languageApp = languageApp;}
	public static ColorThemeApp getColorThemeApp() {return colorThemeApp;}
	public static void setColorThemeApp(ColorThemeApp colorThemeApp) {Application.colorThemeApp = colorThemeApp;}

	public static Color getCOLOR_BACKGROUND() {return COLOR_BACKGROUND;}
	public static void setCOLOR_BACKGROUND(Color cOLOR_BACKGROUND) {COLOR_BACKGROUND = cOLOR_BACKGROUND;}
	public static Color getCOLOR_BACKGROUND2() {return COLOR_BACKGROUND2;}
	public static void setCOLOR_BACKGROUND2(Color cOLOR_BACKGROUND2) {COLOR_BACKGROUND2 = cOLOR_BACKGROUND2;}
	public static Color getCOLOR_EDIT_MESSAGE() {return COLOR_EDIT_MESSAGE;}
	public static void setCOLOR_EDIT_MESSAGE(Color cOLOR_EDIT_MESSAGE) {COLOR_EDIT_MESSAGE = cOLOR_EDIT_MESSAGE;}
	public static Color getCOLOR_MINE_MESSAGE() {return COLOR_MINE_MESSAGE;}
	public static void setCOLOR_MINE_MESSAGE(Color cOLOR_MINE_MESSAGE) {COLOR_MINE_MESSAGE = cOLOR_MINE_MESSAGE;}
	public static Color getCOLOR_TEXT_MINE_MESSAGE() {return COLOR_TEXT_MINE_MESSAGE;}
	public static void setCOLOR_TEXT_MINE_MESSAGE(Color cOLOR_TEXT_MINE_MESSAGE) {COLOR_TEXT_MINE_MESSAGE = cOLOR_TEXT_MINE_MESSAGE;}
	public static Color getCOLOR_SCROLL_BAR() {return COLOR_SCROLL_BAR;}
	public static void setCOLOR_SCROLL_BAR(Color cOLOR_SCROLL_BAR) {COLOR_SCROLL_BAR = cOLOR_SCROLL_BAR;}
	public static Color getCOLOR_CURSOR_SCROLL() {return COLOR_CURSOR_SCROLL;}
	public static void setCOLOR_CURSOR_SCROLL(Color cOLOR_CURSOR_SCROLL) {COLOR_CURSOR_SCROLL = cOLOR_CURSOR_SCROLL;}
	public static Color getCOLOR_CURSOR_SCROLL_HOVER() {return COLOR_CURSOR_SCROLL_HOVER;}
	public static void setCOLOR_CURSOR_SCROLL_HOVER(Color cOLOR_CURSOR_SCROLL_HOVER) {COLOR_CURSOR_SCROLL_HOVER = cOLOR_CURSOR_SCROLL_HOVER;}
	public static Color getCOLOR_TEXT() {return COLOR_TEXT;}
	public static void setCOLOR_TEXT(Color cOLOR_TEXT) {COLOR_TEXT = cOLOR_TEXT;}
	public static Color getCOLOR_TEXT_EDIT() {return COLOR_TEXT_EDIT;}
	public static Color getCOLOR_TEXT_THEIR_MESSAGE() {return COLOR_TEXT_THEIR_MESSAGE;}
	public static Color getBLUE() {return COLOR_BLUE;}
	public static Color getRED() {return COLOR_RED;}
	public static Color getGREEN() {return COLOR_GREEN;}
	public static Color getPURPLE() {return COLOR_PURPLE;}
	public static Color getYELLOW() {return COLOR_YELLOW;}
}