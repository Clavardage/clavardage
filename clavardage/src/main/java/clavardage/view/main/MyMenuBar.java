package clavardage.view.main;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import clavardage.controller.authentification.AuthOperations;
import clavardage.view.main.Application.ColorThemeApp;

public class MyMenuBar extends JMenuBar {
	
	public MyMenuBar(MessageWindow window, JPanel logoPanel, ImageIcon logoIcon, JLabel logo,
			ImageIcon settingsIcon, JMenu settings,
			JMenu colorApp, ButtonGroup allColors, JRadioButton colorAppWhite, JRadioButton colorAppBlack,
			ImageIcon accountIcon, JMenu account, JMenuItem disconnect) throws IOException {
		
		super();
		setBorder(new EmptyBorder(0, 20, 0, 10));
		setPreferredSize(new Dimension(0, 50));
		setBorderPainted(false);
		
//		logoPanel = new JPanel();
		logoPanel.setOpaque(false);
		logoPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		logoPanel.setPreferredSize(new Dimension(0, 40));
		logoPanel.setLayout(new GridLayout(0, 1, 0, 0));
		add(logoPanel);
		
//		logoImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/Logo_title.png")).getScaledInstance(130, 33, Image.SCALE_SMOOTH);
//		logoIcon = new ImageIcon(logoImage, "logo");
//		logo = new JLabel();
		logo.setBorder(null);
		logo.setIcon(logoIcon);
		logoPanel.add(logo);
		
//		settingsImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/Settings.png")).getScaledInstance(30, 30, Image.SCALE_SMOOTH);
//		settingsIcon = new ImageIcon(settingsImage, "Setting menu");
//		settings = new JMenu();
		settings.setBorder(null);
		settings.setCursor(new Cursor(Cursor.HAND_CURSOR));
		settings.setIcon(settingsIcon);
		add(settings);
				
//		colorApp = new JMenu("Change the default color");
		colorApp.setBorder(null);
		colorApp.setFocusPainted(false);
		colorApp.setCursor(new Cursor(Cursor.HAND_CURSOR));
		settings.add(colorApp);

//		allColors = new ButtonGroup();
		
//		colorAppWhite = new JRadioButton("White");
		colorAppWhite.setFocusPainted(false);
		colorAppWhite.setSelected(true);
		colorAppWhite.setCursor(new Cursor(Cursor.HAND_CURSOR));
		colorAppWhite.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Application.setColorTheme(ColorThemeApp.LIGHT);
			}
		});
		allColors.add(colorAppWhite);
		colorApp.add(colorAppWhite);
		
//		colorAppBlack = new JRadioButton("Black");
		colorAppBlack.setFocusPainted(false);
		colorAppBlack.setCursor(new Cursor(Cursor.HAND_CURSOR));
		colorAppBlack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Application.setColorTheme(ColorThemeApp.DARK);
			}
		});
		allColors.add(colorAppBlack);
		colorApp.add(colorAppBlack);
		
//		accountImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/Account.png")).getScaledInstance(30, 30, Image.SCALE_SMOOTH);
//		accountIcon = new ImageIcon(accountImage, "Account menu");
//		account = new JMenu();
		account.setBorder(null);
		account.setIcon(accountIcon);
		account.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(account);
		
//		disconnect = new JMenuItem("Disconnect");
		disconnect.setCursor(new Cursor(Cursor.HAND_CURSOR));
		disconnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AuthOperations.disconnectUser();
				if(!AuthOperations.isUserConnected()) {
					window.closeConversation();
					Application.displayContent(Application.getApp(), Application.getLoginWindow());
				}
			}
		});
		account.add(disconnect);

	}

}
