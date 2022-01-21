package clavardage.view.mystyle;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import clavardage.view.listener.ActionLeaveGroup;
import clavardage.view.listener.ActionOpenAlert;
import clavardage.view.listener.ActionSetNameGroup;

@SuppressWarnings("serial")
public class MyDiscussionPanel extends MyRoundJPanel {

	public MyDiscussionPanel(JMenuBar northDiscussion, MyTitle nameDestinataire, MyJButton editNameGroup, JMenu settingsGroups, ImageIcon settingsGroupsIcon, 
			JMenu seeMembersGroup, JMenu addMemberInGroup, JMenuItem leaveGroup, MyJScrollPane msgPanel, MyNewMsgPanel newMsg, MyJScrollPane groupsContainer) {
		
		super(30);
		setBorder(new EmptyBorder(0, 10, 0, 10));
		setLayout(new BorderLayout(0, 0));

		northDiscussion.setOpaque(false);
		northDiscussion.setBorder(new EmptyBorder(0, 0, 0, 0));
		
		northDiscussion.add(nameDestinataire);
	
		northDiscussion.add(editNameGroup);
		editNameGroup.setVisible(false);
		editNameGroup.addActionListener(new ActionSetNameGroup());
		
		northDiscussion.add(createMargin(5,0));
		
		settingsGroups.add(seeMembersGroup);
		settingsGroups.add(addMemberInGroup);
		settingsGroups.add(leaveGroup);
		
		leaveGroup.addActionListener(new ActionOpenAlert(new ActionLeaveGroup()));
		
		settingsGroups.setBorder(null);
		settingsGroups.setVisible(false);
		settingsGroups.setCursor(new Cursor(Cursor.HAND_CURSOR));
		settingsGroups.setIcon(settingsGroupsIcon);
		northDiscussion.add(settingsGroups);
				
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
