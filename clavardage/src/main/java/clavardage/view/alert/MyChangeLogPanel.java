package clavardage.view.alert;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
import clavardage.view.Application;

@SuppressWarnings("serial")
public class MyChangeLogPanel extends MyAlertPanel {

	private JTextField newLogin;
	private JLabel error;
	
	public MyChangeLogPanel() {
		super();
		try {
			
			error = new JLabel();
			error.setAlignmentX(CENTER_ALIGNMENT);
			error.setFont(new Font("Dialog", Font.ITALIC, 14));
			error.setForeground(Application.getRED());
			getAlert().add(error);
			error.setVisible(false);
			
			MyAlertMessage message = new MyAlertMessage("Change your login :");
			getAlert().add(message);
			
			newLogin = new JTextField(AuthOperations.getConnectedUser().getLogin());
			newLogin.setFont(new Font("Tahoma", Font.BOLD, 24));
			newLogin.setForeground(Application.getPURPLE());
			newLogin.setSelectedTextColor(Application.getYELLOW());
			newLogin.setCaretColor(Application.getBLUE());
			newLogin.setOpaque(false);
			newLogin.setBorder(null);

			newLogin.addKeyListener((KeyListener) new KeyAdapter() {
		        @Override
		        public void keyTyped(KeyEvent e) {
		            if(newLogin.getText().length() > 20){
		        		e.consume();
		            }
		        	getContainer().revalidate();
		        }
		    });

			JPanel zoneEdit = new JPanel();
			getAlert().add(zoneEdit);
			zoneEdit.setLayout(new GridBagLayout());
			zoneEdit.setOpaque(false);
			zoneEdit.add(newLogin);
			zoneEdit.setCursor(new Cursor(Cursor.TEXT_CURSOR));
			
			zoneEdit.addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent e) {}	
				@Override
				public void mousePressed(MouseEvent e) {}		
				@Override
				public void mouseExited(MouseEvent e) {}			
				@Override
				public void mouseEntered(MouseEvent e) {}
				@Override
				public void mouseClicked(MouseEvent e) {
					newLogin.requestFocus();
				}
			});
			
			getConfirm().addActionListener(new ActionChangeLog());
			
		} catch (UserNotConnectedException e) {e.printStackTrace();}
	}

	public JTextField getNewLogin() {
		return newLogin;
	}

	public JLabel getError() {
		return error;
	}
}
