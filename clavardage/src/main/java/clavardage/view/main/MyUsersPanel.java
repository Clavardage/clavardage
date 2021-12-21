package clavardage.view.main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MyUsersPanel extends JPanel {

	public MyUsersPanel(JTextArea titleUsers, JScrollPane usersContainer) {
		super();
		setOpaque(false);
		setLayout(new BorderLayout(0, 0));
		add(titleUsers, BorderLayout.NORTH);
		add(usersContainer);
		
		titleUsers.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				usersContainer.getVerticalScrollBar().setValue(0);				
			}
		});
	}
}
