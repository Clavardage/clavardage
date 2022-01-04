package clavardage.view.main;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultHighlighter;

import clavardage.controller.Clavardage;

public class MyNewMsgPanel extends JPanel {
	
	public MyNewMsgPanel(MyJButton sendFile, MyJButton sendPicture, MyEditMsg editMsg, MyJButton sendMsg) throws IOException {
		super();
		setOpaque(false);
		setBorder(new EmptyBorder(10, 30, 10, 30));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		add(sendFile);

		add(createMargin(10,0));

		add(sendPicture);

		add(createMargin(10,0));
		
		editMsg.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if ( editMsg.getText().isEmpty() |  editMsg.getText().isBlank()) {
					editMsg.setEmptyText(true);
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				if (((MyRoundJTextField) editMsg).isEmptyText()) {
					editMsg.setText("");
					editMsg.setForeground(Application.COLOR_TEXT);
					editMsg.setEmptyText(false);
				}
			}
		});
		
		editMsg.addKeyListener((KeyListener) new KeyAdapter() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	            if(e.getKeyCode() == KeyEvent.VK_ENTER){
	            	Application.getMessageWindow().sendMessage(0);
	            }
	        }

	    });
		add(editMsg);

		add(createMargin(10,0));

		sendMsg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Application.getMessageWindow().sendMessage(1);
			}
		});
		add(sendMsg);
		
		setVisible(false);
	}
	
	/**
	 * Create a margin.
	 * */
	private JPanel createMargin(int x, int y) {
		JPanel marge = new JPanel();
		marge.setPreferredSize(new Dimension(x,y));
		marge.setMaximumSize(marge.getPreferredSize());
		return marge ;
	}

}
