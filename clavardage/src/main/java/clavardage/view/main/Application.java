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

public class Application extends JFrame implements ActionListener, MouseListener {


	/* **  ** */
	private static JPanel message, login;
	private static JFrame app;
	
	/* ** Colors ** */
	enum ColorThemeApp {LIGHT, DARK;}
	protected static Color COLOR_BACKGROUND ;
	protected static Color COLOR_BACKGROUND2 ;
	protected static Color COLOR_EDIT_MESSAGE ;
	protected static Color COLOR_SCROLL_BAR ;
	protected static Color COLOR_CURSOR_SCROLL ;
	protected static Color COLOR_CURSOR_SCROLL_HOVER ;
	protected static Color COLOR_OUR_MESSAGE ;
	protected static Color COLOR_SHADOW ;
	protected static Color COLOR_BLUE = new Color(72,125,244) ;
	protected static Color COLOR_TEXT ;
	protected static Color COLOR_TEXT_EDIT ;
	


	public Application(String title, ImageIcon icon) throws IOException {
		app = this;
		this.setTitle(title);
		this.setIconImage(icon.getImage());
		this.setSize(1200, 800);
		this.setLocationRelativeTo(null);
		//this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		//this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setMinimumSize(new Dimension(400,400));

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