package clavardage.view.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.UUID;

import javax.swing.JMenuItem;

import clavardage.controller.gui.MainGUI;
import clavardage.model.exceptions.UserNotConnectedException;
import clavardage.view.main.Application;
import clavardage.view.main.DestinataireJPanel;
import clavardage.view.main.MessageWindow;
import clavardage.view.main.MessagesPanel;
import clavardage.view.main.MessageWindow.Destinataire;

public class MouseOpenConversation implements MouseListener {
	
	private DestinataireJPanel dest;

	public MouseOpenConversation(DestinataireJPanel dest) {
		this.dest = dest;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!Application.getMessageWindow().isAlertOpen() ) {
			try {		
				if (dest.getType() == Destinataire.User && !dest.isOpen()) {
					// TODO: display a loading in thread
					// TODO seulement si pastille pas bleue
					MainGUI.openConversation(dest.getIdDestinataire()); // open network communication
					// TODO: remove loading in thread
				}
				openConversation(dest.getNameDestinataire(), dest.getIdDestinataire(), dest.getTypeDestinataire());
				dest.openConversationInList();
				Application.getMessageWindow().getMessageContainer().getVerticalScrollBar().setValue(Application.getMessageWindow().getMessageContainer().getVerticalScrollBar().getMaximum());

			} catch (Exception ex) {
				ex.printStackTrace();
				// TODO: remove loading in thread
			}
		}
		
	}
	
	public static void openConversation(String newDestinataire, UUID id, Destinataire d) throws UserNotConnectedException, Exception {
		MessageWindow w = Application.getMessageWindow();
		
		/* display newMsg if it is necessary */
		if (!w.isConversationOpen()) {
			w.setConversationOpen(true);	
			w.getNewMsg().setVisible(true);
		}
		
		/* display the name Destinataire */
		w.getNameDestinataire().setText(newDestinataire);
		
		/* open the chosen discussion */
		MessagesPanel chosenConversation = null;
		if (d == Destinataire.User) {
			for (MessagesPanel conv : w.getAllMessagesUsers()) {
				if (conv.getIdConversation().equals(id)) {
					chosenConversation = conv ;
				}
			}
		} else {
			for (MessagesPanel conv : w.getAllMessagesGroups()) {
				if (conv.getIdConversation().equals(id)) {
					chosenConversation = conv ;
				}
			}
		}
		w.getMessageContainer().setViewportView(chosenConversation);
		w.setDiscussionDisplay(chosenConversation);
		ActionSetColorTheme.customDiscussionDisplay(Application.getColorThemeApp()); //necessary because it is open while using the app
		
		/* display or hide the buttons of settings groups if it is necessary */
		if (d == Destinataire.Group) {
			w.getEditNameGroup().setVisible(true);
			w.getSettingsGroups().setVisible(true);
			w.getSeeMembersGroup().removeAll();
			createSeeMembersGroup();
			createAddMemberInGroup();
		} else {
			w.getEditNameGroup().setVisible(false);
			w.getSettingsGroups().setVisible(false);
		}
		w.getEditMsg().setEmptyText(true);
		
		AdapterLayout.redimDiscussion();
	}
	
	
	public static void createAddMemberInGroup() {
		MessageWindow w = Application.getMessageWindow();

		w.setUsersDisplay(0);
		w.getAddMemberInGroup().removeAll();

		if (w.getDiscussionDisplay().getNumberNoMembers() <= 10) {
			for (DestinataireJPanel user : w.getDiscussionDisplay().getNoMembers()) {
				JMenuItem item = new JMenuItem(user.getNameDestinataire());
				w.getAddMemberInGroup().add(item);
				item.addActionListener(new ActionOpenAlert(user,item));
			}
		} else {
			w.setBackUsers(new JMenuItem("See back"));
			int i;
			for (i=0; i<=8 ; i++) {
				JMenuItem item = new JMenuItem(w.getDiscussionDisplay().getNoMembers().get(i + 8*w.getUsersDisplay()).getNameDestinataire());
				w.getAddMemberInGroup().add(item);				
				item.addActionListener(new ActionOpenAlert(w.getDiscussionDisplay().getNoMembers().get(i + 8*w.getUsersDisplay()),item));

			}
			w.setNextUsers(new JMenuItem("See next"));
			w.getAddMemberInGroup().add(w.getNextUsers());
			
			w.getBackUsers().addActionListener(new ActionBack(0));
						
			w.getNextUsers().addActionListener(new ActionNext(0));
		}	
	}

	public static void createSeeMembersGroup() {
		MessageWindow w = Application.getMessageWindow();

		w.setMembersDisplay(0);
		w.getSeeMembersGroup().removeAll();

		if (w.getDiscussionDisplay().getNumberMembers() <= 10) {
			for (DestinataireJPanel user : w.getDiscussionDisplay().getMembersConversation()) {
				JMenuItem item = new JMenuItem(user.getNameDestinataire());
				w.getSeeMembersGroup().add(item);
			}
		} else {
			w.setBackMembers(new JMenuItem("See back"));
			int i;
			for (i=0; i<=8 ; i++) {
				JMenuItem item = new JMenuItem(w.getDiscussionDisplay().getMembersConversation().get(i + 8*w.getMembersDisplay()).getNameDestinataire());
				w.getSeeMembersGroup().add(item);
			}
			w.setNextMembers(new JMenuItem("See next"));
			w.getSeeMembersGroup().add(w.getNextMembers());
			
			w.getBackMembers().addActionListener(new ActionBack(1));		
			
			w.getNextMembers().addActionListener(new ActionNext(1));
		}		
	}
	
	public static void moveInTopOfList(Destinataire type, UUID id) {
		MessageWindow w = Application.getMessageWindow();
		if (type == Destinataire.User) {
			DestinataireJPanel userConcerned = null;
			for (DestinataireJPanel user : w.getAllUsers()) {
				if (user.getIdDestinataire().equals(id)) {
					userConcerned = user ;
					w.getListUsers().remove(userConcerned);
				}
			}
			w.getListUsers().add(userConcerned, 0);
		} else {
			DestinataireJPanel groupConcerned = null;
			for (DestinataireJPanel group : w.getAllGroups()) {
				if (group.getIdDestinataire().equals(id)) {
					groupConcerned = group ;
					w.getListGroups().remove(groupConcerned);
				}
			}
			w.getListGroups().add(groupConcerned, 0);
		}
	}
	
	
	@Override
	public void mouseEntered(MouseEvent e) {
		dest.getPanelName().setForeground(Application.getRED());
	}

	@Override
	public void mouseExited(MouseEvent e) {
		dest.getPanelName().setForeground(dest.getPanel().getForeground());		    
	}

	@Override
	public void mousePressed(MouseEvent e) { }

	@Override
	public void mouseReleased(MouseEvent e) { }

}
