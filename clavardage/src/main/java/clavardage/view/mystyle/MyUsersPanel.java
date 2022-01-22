package clavardage.view.mystyle;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MyUsersPanel extends JPanel {

	public MyUsersPanel(MyJScrollPane usersContainer) {
		super();
		setOpaque(false);
		setLayout(new BorderLayout(0, 0));

		MyTitle titleUsers = new MyTitle("Users");
		titleUsers.addMouseListener(new MouseListener() {
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
				usersContainer.getVerticalScrollBar().setValue(0);				
			}
		});
		
		add(titleUsers, BorderLayout.NORTH);
		add(usersContainer);
	}
}
