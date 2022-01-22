package clavardage.view.mystyle;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import clavardage.controller.Clavardage;
import clavardage.view.listener.ActionDisconnect;
import clavardage.view.listener.ActionOpenAlert;
import clavardage.view.listener.ActionSetColorTheme;
import clavardage.view.listener.ActionSetLanguage;
import clavardage.view.main.Application.ColorThemeApp;
import clavardage.view.main.Application.LanguageApp;
import clavardage.view.mystyle.MyAlertPanel.AlertAction;

@SuppressWarnings("serial")
public class MyMenuBar extends JMenuBar {
	
	public MyMenuBar(JRadioButton english) throws IOException {
		
		super();
		setBorder(new EmptyBorder(0, 20, 0, 10));
		setPreferredSize(new Dimension(0, 50));
		setBorderPainted(false);
		
		JPanel logoPanel = new JPanel();
		logoPanel.setOpaque(false);
		logoPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		logoPanel.setPreferredSize(new Dimension(0, 40));
		logoPanel.setLayout(new GridLayout(0, 1, 0, 0));
		add(logoPanel);
		
		Image logoImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/Logo_title.png")).getScaledInstance(130, 33, Image.SCALE_SMOOTH);
		ImageIcon logoIcon = new ImageIcon(logoImage, "logo");
		
		JLabel logo = new JLabel();
		logo.setBorder(null);
		logo.setIcon(logoIcon);
		logoPanel.add(logo);

		Image settingsImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/Settings.png")).getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon settingsIcon = new ImageIcon(settingsImage, "Setting menu");
		
		JMenu settings = new JMenu();
		settings.setBorder(null);
		settings.setCursor(new Cursor(Cursor.HAND_CURSOR));
		settings.setIcon(settingsIcon);
		add(settings);
		
		JMenu colorApp = new JMenu("Change the default color");
		ButtonGroup allColors = new ButtonGroup(); //Only one button pressed at the same time
		JRadioButton colorAppWhite = new JRadioButton("White");
		JRadioButton colorAppBlack = new JRadioButton("Black");
				
		colorApp.setBorder(null);
		colorApp.setFocusPainted(false);
		colorApp.setCursor(new Cursor(Cursor.HAND_CURSOR));
		settings.add(colorApp);		

		colorAppWhite.setFocusPainted(false);
		colorAppWhite.setSelected(true);
		colorAppWhite.setCursor(new Cursor(Cursor.HAND_CURSOR));
		colorAppWhite.addActionListener(new ActionSetColorTheme(ColorThemeApp.LIGHT));
		allColors.add(colorAppWhite);
		colorApp.add(colorAppWhite);
		
		colorAppBlack.setFocusPainted(false);
		colorAppBlack.setCursor(new Cursor(Cursor.HAND_CURSOR));
		colorAppBlack.addActionListener(new ActionSetColorTheme(ColorThemeApp.DARK));
		allColors.add(colorAppBlack);
		colorApp.add(colorAppBlack);
		
		JMenu languageApp = new JMenu("Change the default language");
		ButtonGroup allLanguages = new ButtonGroup();
		JRadioButton french = new JRadioButton("French");
		JRadioButton spanish = new JRadioButton("Spanish");
		JRadioButton german = new JRadioButton("German");
		JRadioButton chinese = new JRadioButton("Chinese");
		JRadioButton japanese = new JRadioButton("Japanese");

		languageApp.setBorder(null);
		languageApp.setFocusPainted(false);
		languageApp.setCursor(new Cursor(Cursor.HAND_CURSOR));
		settings.add(languageApp);
		
		english.setFocusPainted(false);
		english.setSelected(true);
		english.setCursor(new Cursor(Cursor.HAND_CURSOR));
		english.addActionListener(new ActionSetLanguage(LanguageApp.ENGLISH));
		allLanguages.add(english);
		languageApp.add(english);
		
		french.setFocusPainted(false);
		french.setCursor(new Cursor(Cursor.HAND_CURSOR));
		french.addActionListener(new ActionSetLanguage(LanguageApp.FRENCH));
		allLanguages.add(french);
		languageApp.add(french);
		
		spanish.setFocusPainted(false);
		spanish.setCursor(new Cursor(Cursor.HAND_CURSOR));
		spanish.addActionListener(new ActionSetLanguage(LanguageApp.SPANISH));
		allLanguages.add(spanish);
		languageApp.add(spanish);
		
		german.setFocusPainted(false);
		german.setCursor(new Cursor(Cursor.HAND_CURSOR));
		german.addActionListener(new ActionSetLanguage(LanguageApp.GERMAN));
		allLanguages.add(german);
		languageApp.add(german);
		
		chinese.setFocusPainted(false);
		chinese.setCursor(new Cursor(Cursor.HAND_CURSOR));
		chinese.addActionListener(new ActionSetLanguage(LanguageApp.CHINESE));
		allLanguages.add(chinese);
		languageApp.add(chinese);
		
		japanese.setFocusPainted(false);
		japanese.setCursor(new Cursor(Cursor.HAND_CURSOR));
		japanese.addActionListener(new ActionSetLanguage(LanguageApp.JAPANESE));
		allLanguages.add(japanese);
		languageApp.add(japanese);
		
		Image accountImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/Account.png")).getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon accountIcon = new ImageIcon(accountImage, "Account menu");
		JMenuItem changeLog = new JMenuItem("Change my user name");
		JMenuItem disconnect = new JMenuItem("Disconnect");
		
		JMenu account = new JMenu();
		account.setBorder(null);
		account.setIcon(accountIcon);
		account.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(account);
		
		changeLog.setCursor(new Cursor(Cursor.HAND_CURSOR));
		changeLog.addActionListener(new ActionOpenAlert(AlertAction.CHANGELOG));
		account.add(changeLog);
		
		disconnect.setCursor(new Cursor(Cursor.HAND_CURSOR));
		disconnect.addActionListener(new ActionOpenAlert(AlertAction.DISCONNECT));
		account.add(disconnect);

	}

}
