package clavardage.view.main;

import clavardage.controller.gui.MainGUI;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

@SuppressWarnings("serial")
public class SignInWindow extends LoginWindow {

	private SectionTextJPanel confirmPassword;

	public SignInWindow() throws IOException {
		super();
		confirmPassword = new SectionTextJPanel("Confirm Password", SectionText.PW);
		super.getSections().add(confirmPassword,3);
		
		super.getSignInButton().setText("Login");
		getSignInButton().addMouseListener(this);
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
			
		} else if (e.getSource()==super.getLogButton() | e.getSource()==super.getLogPanel()) {
			//MainGUI.createNewUser(username.getText(), );
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
