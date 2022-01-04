package clavardage.view.main;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

import clavardage.controller.authentification.AuthOperations;
import clavardage.view.main.Application.ColorThemeApp;
import clavardage.view.main.LoginWindow.SectionText;

public class SignInWindow extends LoginWindow {

	private SectionTextJPanel confirmPassword;

	public SignInWindow() throws IOException {
		super();
		confirmPassword = new SectionTextJPanel("Confirm Password","pass_0", SectionText.PW);
		super.getSections().add(confirmPassword,3);
		
		super.getSignInButton().setText("Login");
		getSignInButton().addMouseListener(this);
		customThemeLogin(Application.getColorThemeApp());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource()== getSignInButton()) {
			if (Application.getLoginWindow() == null) {
				try {
					Application.createLoginWindow();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			Application.displayContent(Application.getApp(), Application.getLoginWindow());
			
		}
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

	@Override
	public void actionPerformed(ActionEvent e) {
	}
}
