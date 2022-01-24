package clavardage.view.main;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import clavardage.controller.gui.MainGUI;

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
		
		Action connexion = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				try {
					MainGUI.createNewUser(login.getText(), getUsername().getText(), getPassword().getText(), confirmPassword.getText());
					connexion();
					login.setNoError();
					confirmPassword.setNoError();
					login.setText("");
					confirmPassword.setText("");
				} catch (Exception e1) {
					getTextError().setText(e1.getMessage());
					getUsername().setError();
					getPassword().setError();
					login.setError();
					confirmPassword.setError();
					getSectionContainer().getVerticalScrollBar().setValue(0);
					e1.printStackTrace();
				}
			}};


		InputMap im = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		im.put(KeyStroke.getKeyStroke("ENTER"), "connexion");

		ActionMap am = getActionMap();
		am.put("connexion", connexion);
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
			Application.getLoginWindow().getSectionContainer().getVerticalScrollBar().setValue(0);

			Application.displayContent(Application.getApp(), Application.getLoginWindow());
			
		} else if (e.getSource()==super.getLogButton() | e.getSource()==super.getLogPanel()) {
			try {
				MainGUI.createNewUser(login.getText(), getUsername().getText(), getPassword().getText(), confirmPassword.getText());
				connexion();
			} catch (Exception e1) {
				getTextError().setText(e1.getMessage());
				getUsername().setError();
				getPassword().setError();
				login.setError();
				confirmPassword.setError();
				getSectionContainer().getVerticalScrollBar().setValue(0);
				e1.printStackTrace();
			}
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
