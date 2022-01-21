package clavardage.view.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import clavardage.view.main.Application;
import clavardage.view.main.DestinataireJPanel;

public class ActionBack implements ActionListener {
	
	private int display, mode;
	private JMenuItem back, next;
	private JMenu menu;
	private ArrayList<DestinataireJPanel> list;
	
	public ActionBack(int mode) {
		this.mode = mode;
		this.display = 0 ;
		if (this.mode == 0) {
			back = Application.getMessageWindow().getBackUsers();
			next = Application.getMessageWindow().getNextUsers();
			menu = Application.getMessageWindow().getAddMemberInGroup();
			list = Application.getMessageWindow().getDiscussionDisplay().getNoMembers();
		} else if (this.mode == 1) {
			back = Application.getMessageWindow().getBackMembers();
			next = Application.getMessageWindow().getNextMembers();
			menu = Application.getMessageWindow().getSeeMembersGroup();
			list = Application.getMessageWindow().getDiscussionDisplay().getMembersConversation();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (this.mode == 0) {
			display = Application.getMessageWindow().getUsersDisplay();
		} else if (this.mode == 1) {
			display = Application.getMessageWindow().getMembersDisplay();
		}
		if (!(display == 0)) {
			display--;
			Application.getMessageWindow().setDisplay(this.mode, display);
			menu.removeAll();
			if (!(display == 0)) {
				menu.add(back);
			}
			for (int i=0; i<=8 ; i++) {
				JMenuItem item = new JMenuItem(list.get(i + 8*display).getNameDestinataire());
				menu.add(item);
				if (this.mode == 0) {
					item.addActionListener(new ActionOpenAlert(list.get(i + 8*display), new ActionAddMember(item)));
				}			}
			menu.add(next);
		}
		Application.getMessageWindow().getSettingsGroups().doClick();
		menu.doClick();
	}
	
	public void setDisplay(int i) {
		display = i;
	}

}
