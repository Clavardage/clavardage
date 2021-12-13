package clavardage.view.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import clavardage.model.exceptions.UserNotConnectedException;
import clavardage.view.main.Application.ColorThemeApp;

public class Application extends JFrame implements ActionListener, MouseListener {


	/* **  ** */
	private static JPanel message, login;
	private static JFrame app;
	
	/* ** Colors ** */
	enum ColorThemeApp {LIGHT, DARK;}
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

	private static ColorThemeApp colorThemeApp ;
	


	public Application(String title, ImageIcon icon) throws IOException {
		app = this;
		this.setTitle(title);
		this.setIconImage(icon.getImage());
		this.setSize(1200, 800);
//		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(400,400));
		colorThemeApp = ColorThemeApp.LIGHT; 

		try {
			message = createMessageWindow();
			login = createLoginWindow();
		} catch (IOException | UserNotConnectedException e1) {
			e1.printStackTrace();
		}
		displayContent(app, login);
	}

	
	/**
	 * Create the login Window.
	 * @throws IOException
	 * @throws UserNotConnectedException 
	 * */
	private JPanel createLoginWindow() throws IOException, UserNotConnectedException {
		JPanel login = new Login();
		return login ;
	}
	
	public static void setColorTheme(ColorThemeApp color) {
		((Message) message).customThemeMessage(color);
		((Login) login).customThemeLogin(color);
	}
	
	/**
	 * Create the message Window.
	 * @throws IOException
	 * @throws UserNotConnectedException 
	 * */
	private JPanel createMessageWindow() throws IOException, UserNotConnectedException {
		JPanel message = new Message();
		return message ;
	}
	
	
	public static void displayContent(JFrame app, JPanel content) {
		app.setContentPane(content);
		app.revalidate();
		app.repaint();
	}
	
	public static JFrame getApp() {
		return app;
	}
	
	public static JPanel getMessageWindow() {
		return message;
	}
	
	public static JPanel getLoginWindow() {
		return login;
	}
	
	public static ColorThemeApp getColorThemeApp() {
		return colorThemeApp;
	}
	
	public void changeColorThemeApp(ColorThemeApp color) {
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
	
	/* --------- GLOBAL LISTENERS ----------- */

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}




}