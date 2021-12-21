package clavardage.view.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class MyDiscussion extends MyRoundJPanel {

	public MyDiscussion(MessageWindow window, JPanel northDiscussion, JTextArea nameDestinataire, JButton editGroup, JButton addUser, 
			JScrollPane msgPanel, JPanel newMsg, JScrollPane groupsContainer) {
		
		super(30);
		setBorder(new EmptyBorder(0, 10, 0, 10));
		setLayout(new BorderLayout(0, 0));

		northDiscussion.setOpaque(false);
		northDiscussion.setBorder(new EmptyBorder(0, 0, 0, 15));
		northDiscussion.setLayout(new BoxLayout(northDiscussion, BoxLayout.X_AXIS));
		
		northDiscussion.add(nameDestinataire);
	
		northDiscussion.add(editGroup);
		editGroup.setVisible(false);
		editGroup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.setNameGroup();
				groupsContainer.validate();
			}
		});
		
		northDiscussion.add(createMargin(5,0));
		
		northDiscussion.add(addUser);
		addUser.setVisible(false);

		
		add(northDiscussion, BorderLayout.NORTH);
		
		add(msgPanel, BorderLayout.CENTER);

		add(newMsg, BorderLayout.SOUTH);
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
