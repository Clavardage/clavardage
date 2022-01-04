package clavardage.view.main;

import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.UUID;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import clavardage.controller.authentification.AuthOperations;
import clavardage.model.exceptions.UserNotConnectedException;
import clavardage.view.main.MessageWindow.Destinataire;

public class MessagesPanel extends JPanel {
	
	private boolean emptyDiscussion;
	private UUID idConversation;
	private Destinataire type;
	private ArrayList<DestinataireJPanel> membersConversation ;
	private ArrayList<DestinataireJPanel> noMembers ;

	private MessageWindow window;
	
	public MessagesPanel(String text) {
		super();
		this.emptyDiscussion = true ;
		this.setBorder(new EmptyBorder(0, 0, 0, 0));
		this.setLayout(new GridBagLayout());
		this.add(new MyAlertMessage("Choose someone to start a conversation..."));
	}
	
	@SuppressWarnings("unchecked")
	public MessagesPanel(MessageWindow w, UUID idConv, Destinataire type) {
		super();
		this.window = w; 
		this.emptyDiscussion = true ;
		this.idConversation = idConv;
		this.type = type;
		this.membersConversation = new ArrayList<DestinataireJPanel>();
		this.noMembers = (ArrayList<DestinataireJPanel>) this.window.getAllUsers().clone();
		this.setBorder(new EmptyBorder(0, 0, 0, 0));
		this.setLayout(new GridBagLayout());
	}
	
	public void startConversation() {
		this.emptyDiscussion = false ;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.remove(0);
		this.repaint();
	}
	
	public boolean isEmptyDiscussion() {
		return emptyDiscussion ;
	}
	
	public Destinataire getTypeConversation() {
		return type;
	}
	
	public UUID getIdConversation() {
		return idConversation;
	}
	
	public ArrayList<DestinataireJPanel> getMembersConversation() {
		return membersConversation;
	}
	
	public ArrayList<DestinataireJPanel> getNoMembers() {
		return noMembers;
	}
	
	public void addMemberConversation(UUID newUser) {
		if (!(type == Destinataire.User & membersConversation.size() == 2)) {
			for (DestinataireJPanel user : window.getAllUsers()) {
				if (user.getIdDestinataire() == newUser) {
					membersConversation.add(user);
					noMembers.remove(user);
					membersConversation.sort(new Comparator<DestinataireJPanel>() {
						@Override
						public int compare(DestinataireJPanel o1, DestinataireJPanel o2) {
							return o1.getNameDestinataire().compareTo(o2.getNameDestinataire());
						}
					});
				}
			}
			
		}
	}
	
	public void removeMemberConversation(UUID member) {
		for (DestinataireJPanel user : window.getAllUsers()) {
			if (user.getIdDestinataire() == member) {
				membersConversation.remove(user);
				noMembers.add(user);
				membersConversation.sort(new Comparator<DestinataireJPanel>() {
					@Override
					public int compare(DestinataireJPanel o1, DestinataireJPanel o2) {
						return o1.getNameDestinataire().compareTo(o2.getNameDestinataire());
					}
				});
			}
		}
	}
	
	public boolean isMemberConversation(UUID user) {
		boolean isMember = false;
		for (DestinataireJPanel member : membersConversation) {
			if (member.getIdDestinataire() == user) {
				isMember = true;
				break;
			} 
		}
		return isMember;
	}
	
	public int getNumberMembers() {
		return this.membersConversation.size();
	}
	
	public int getNumberNoMembers() {
		return this.noMembers.size();
	}

}
