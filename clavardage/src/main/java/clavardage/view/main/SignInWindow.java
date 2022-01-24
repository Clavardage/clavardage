package clavardage.view.main;

import java.awt.event.MouseEvent;
import java.io.IOException;

import clavardage.controller.authentification.AuthOperations;

@SuppressWarnings("serial")
public class SignInWindow extends LoginWindow {

	private SectionTextJPanel login, confirmPassword;

	public SignInWindow() throws IOException {
		super();
		login = new SectionTextJPanel("Login", SectionText.LOG);
		getSections().add(login,2);

		confirmPassword = new SectionTextJPanel("Confirm Password", SectionText.PW);
		getSections().add(confirmPassword,4);
		
		getSignInButton().setText("Login");
		getSignInButton().addMouseListener(this);
		
		getLogButton().setText("Sign In");
		
		getLogButton().addMouseListener(this);
		getLogButtonPanel().addMouseListener(this);
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
		} else if (e.getSource()==getLogButton() | e.getSource()==getLogButtonPanel()) {
			connexion();
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
}
