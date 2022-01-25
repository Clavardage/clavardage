package clavardage.view.mystyle;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import clavardage.controller.Clavardage;
import clavardage.view.alert.ActionOpenAlert;
import clavardage.view.alert.ActionSetNameGroup;
import clavardage.view.alert.MyAlertPanel.AlertAction;

@SuppressWarnings("serial")
public class MyDiscussionPanel extends MyRoundJPanel {

	public MyDiscussionPanel(JMenuBar northDiscussion, MyTitle nameDestinataire, MyJButton editNameGroup, JMenu settingsGroups, MyJButton closeDiscussion,
			JMenu seeMembersGroup, JMenu addMemberInGroup,  MyJScrollPane msgPanel, MyNewMsgPanel newMsg, MyJScrollPane groupsContainer) throws IOException {
		
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
		JMenuItem leaveGroup = new JMenuItem("Leave the group");
		leaveGroup.addActionListener(new ActionOpenAlert(AlertAction.LEAVEGROUP));
		settingsGroups.add(leaveGroup);

		Image settingsGroupsImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/settingGroup.png")).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		ImageIcon settingsGroupsIcon = new ImageIcon(settingsGroupsImage, "Add Group Button");
		
		settingsGroups.setBorder(null);
		settingsGroups.setVisible(false);
		settingsGroups.setCursor(new Cursor(Cursor.HAND_CURSOR));
		settingsGroups.setIcon(settingsGroupsIcon);
		northDiscussion.add(settingsGroups);
		
		northDiscussion.add(createMargin(5,0));

		northDiscussion.add(closeDiscussion);
		closeDiscussion.setVisible(false);

		
		northDiscussion.add(createMargin(5,0));

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
