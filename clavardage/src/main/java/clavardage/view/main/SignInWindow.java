package clavardage.view.main;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import clavardage.controller.Clavardage;
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
		
		for (int i = 1; i<5 ; i++) {
			((SectionTextJPanel) getSections().getComponent(i)).getTitleSection().setForeground(Application.getPURPLE());
		}
		
		getSignInButton().setText("Login");
		getSignInButton().setForeground(Application.getBLUE());
		getSignInButton().addMouseListener(this);

		getLogButtonPanel().setImg(ImageIO.read(Clavardage.getResourceStream("/img/assets/bgButtonLogPurple.png")));	
		getLogButton().setText("Sign In");
		
		getLogButton().addMouseListener(this);
		getLogButtonPanel().addMouseListener(this);
		
		Action connexion = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				connexionSignIn();
			}};

		InputMap im = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		im.put(KeyStroke.getKeyStroke("ENTER"), "connexion");

		ActionMap am = getActionMap();
		am.put("connexion", connexion);
		
		getSectionContainer().getVerticalScrollBar().revalidate();
		getSectionContainer().getVerticalScrollBar().validate();
		getSectionContainer().getVerticalScrollBar().repaint();



	}
	
	private void connexionSignIn() {
		try {
			MainGUI.createNewUser(login.getText(), getMail().getText(), getPassword().getText(), confirmPassword.getText());
			connexion();
			login.setNoError();
			confirmPassword.setNoError();
			login.setText("");
			confirmPassword.setText("");
		} catch (Exception e1) {
			getTextError().setText(e1.getMessage());
			getMail().setError();
			getPassword().setError();
			login.setError();
			confirmPassword.setError();
			getSectionContainer().getVerticalScrollBar().setValue(0);
			e1.printStackTrace();
		}
	}
	
	private void goToLogin() {
		if (Application.getLoginWindow() == null) {
			try {
				Application.createLoginWindow();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		getSectionContainer().getVerticalScrollBar().setValue(0);

		Application.displayContent(Application.getApp(), Application.getLoginWindow());		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource()== getSignInButton()) {
			goToLogin();
		} else if (e.getSource()==super.getLogButton() | e.getSource()==super.getLogPanel()) {
			connexionSignIn();
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
