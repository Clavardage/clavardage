package clavardage.view.main;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import clavardage.controller.authentification.AuthOperations;
import clavardage.view.main.Application.ColorThemeApp;

@SuppressWarnings("serial")
public class MyMenuBar extends JMenuBar {
	
	public MyMenuBar(JPanel logoPanel, ImageIcon logoIcon, JLabel logo,
			ImageIcon settingsIcon, JMenu settings,
			JMenu colorApp, ButtonGroup allColors, JRadioButton colorAppWhite, JRadioButton colorAppBlack,
			ImageIcon accountIcon, JMenu account, JMenuItem disconnect) throws IOException {
		
		super();
		setBorder(new EmptyBorder(0, 20, 0, 10));
		setPreferredSize(new Dimension(0, 50));
		setBorderPainted(false);
		
		logoPanel.setOpaque(false);
		logoPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		logoPanel.setPreferredSize(new Dimension(0, 40));
		logoPanel.setLayout(new GridLayout(0, 1, 0, 0));
		add(logoPanel);
		
		logo.setBorder(null);
		logo.setIcon(logoIcon);
		logoPanel.add(logo);

		settings.setBorder(null);
		settings.setCursor(new Cursor(Cursor.HAND_CURSOR));
		settings.setIcon(settingsIcon);
		add(settings);
				
		colorApp.setBorder(null);
		colorApp.setFocusPainted(false);
		colorApp.setCursor(new Cursor(Cursor.HAND_CURSOR));
		settings.add(colorApp);

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
		
		account.setBorder(null);
		account.setIcon(accountIcon);
		account.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(account);
		
		disconnect.setCursor(new Cursor(Cursor.HAND_CURSOR));
		disconnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AuthOperations.disconnectUser();
				if(!AuthOperations.isUserConnected()) {
					try {
						Application.getMessageWindow().closeConversation();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					Application.displayContent(Application.getApp(), Application.getLoginWindow());
				}
			}
		});
		account.add(disconnect);

	}

}
