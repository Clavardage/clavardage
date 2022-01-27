package clavardage.view.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import clavardage.view.Application;
import clavardage.view.alert.ActionOpenAlert;
import clavardage.view.main.DestinataireJPanel;

/**
 * Allows you to go next in the display of users in the groups parameters.
 * 
 * @author Célestine Paillé
 */
public class ActionNext implements ActionListener {
	
	private int display, mode;
	private JMenuItem back, next;
	private JMenu menu;
	private ArrayList<DestinataireJPanel> list;
	
	/**
	 * Create the button next when you check the users on the groups parameters. <br>
	 * Create seeMembersGroup or addMemberInGroup based on the <code>mode</code>
	 * @param mode 0 for addMemberInGroup or 1 to seeMembersGroup
	 */
	public ActionNext(int mode) {
		this.mode = mode;
		this.display = 0;
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
		}	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean end = false;
		if (this.mode == 0) {
			display = Application.getMessageWindow().getUsersDisplay();
		} else if (this.mode == 1) {
			display = Application.getMessageWindow().getMembersDisplay();
		}
		if (!(display == list.size()/8)) {
			display++;
			Application.getMessageWindow().setDisplay(this.mode, display);
			menu.removeAll();
			menu.add(back);
			int i;
			for (i=0; i<=8 ; i++) {
				if (!((i + 8*display) >= list.size())) {
					JMenuItem item = new JMenuItem(list.get(i + 8*display).getNameDestinataire());
					menu.add(item);
					if (this.mode == 0) {
						item.addActionListener(new ActionOpenAlert(list.get(i + 8*display), item));
					}
				} else {
					end = true;
					break;
				}
			}
			if (!end) {
				menu.add(next);
			}
		}
		Application.getMessageWindow().getSettingsGroups().doClick();
		menu.doClick();
	}
	
	public void setDisplay(int i) {
		display = i;
	}

}
