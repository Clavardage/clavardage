package clavardage.view.mystyle;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import javax.swing.text.Highlighter.Highlight;
import javax.swing.text.Highlighter.HighlightPainter;

import clavardage.controller.authentification.AuthOperations;
import clavardage.model.exceptions.UserNotConnectedException;
import clavardage.view.listener.ActionChangeLog;
import clavardage.view.main.Application;

@SuppressWarnings("serial")
public class MyChangeLogPanel extends MyAlertPanel {
	
	private Dimension maxSize ;
	private int sizeFontChosen, sizeFont;
	private JTextArea newLogin;
	private JPasswordField password ;
	private JPanel zonePassword;
	
	public MyChangeLogPanel() {
		super();
		try {	
			newLogin = new JTextArea(AuthOperations.getConnectedUser().getLogin());
			newLogin.setFont(new Font("Tahoma", Font.BOLD, 24));
			newLogin.setForeground(Application.getPURPLE());
			newLogin.setSelectedTextColor(Application.getYELLOW());
			newLogin.setOpaque(false);

			newLogin.addKeyListener((KeyListener) new KeyAdapter() {
		        @Override
		        public void keyTyped(KeyEvent e) {
		            if(newLogin.getText().length() > 20){
		        		e.consume();
		            }
		            newLogin.setText(newLogin.getText().replaceAll("\r", "").replaceAll("\n", "").replace(" ", ""));

		        }
		    });

			sizeFontChosen = sizeFont = 16 ;
			JPanel zoneEdit = new JPanel();
			zoneEdit.setLayout(new GridBagLayout());
			zoneEdit.setOpaque(false);
			zoneEdit.add(newLogin);
			getAlert().add(zoneEdit);
			password = new JPasswordField();
			password.setFont(new Font("Tahoma", Font.BOLD, sizeFontChosen));
			password.setForeground(Application.getPURPLE());
			password.setSelectedTextColor(Application.getYELLOW());
			password.setOpaque(false);
			password.setBorder(null);
			
			password.addKeyListener((KeyListener) new KeyAdapter() {
		        @Override
		        public void keyTyped(KeyEvent e) {
		            if( ((JTextField) password).getText().length() > 50){
		        		e.consume();
		            }
		        	getContainer().revalidate();
		        }
		    });
			
			zonePassword = new JPanel();
			zonePassword.setLayout(new BoxLayout(zonePassword, BoxLayout.X_AXIS));
			zonePassword.setOpaque(false);
			MyAlertMessage message = new MyAlertMessage("Enter your password : ", 16);
			zonePassword.add(message);
			zonePassword.add(password);
			getAlert().add(zonePassword);
			maxSize = zonePassword.getPreferredSize();
			
			getConfirm().addActionListener(new ActionChangeLog());
			
		} catch (UserNotConnectedException e) {e.printStackTrace();}
	}

	public JTextArea getNewLogin() {
		return newLogin;
	}
}
