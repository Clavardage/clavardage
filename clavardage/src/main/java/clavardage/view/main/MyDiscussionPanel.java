package clavardage.view.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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
		editNameGroup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Application.getMessageWindow().setNameGroup();
				groupsContainer.validate();
			}
		});
		
		northDiscussion.add(createMargin(5,0));
		
		settingsGroups.add(seeMembersGroup);
		settingsGroups.add(addMemberInGroup);
		settingsGroups.add(leaveGroup);
		
		leaveGroup.addActionListener(new ActionOpenAlert(new ActionLeaveGroup()));
		
		settingsGroups.setBorder(null);
		settingsGroups.setVisible(false);
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
