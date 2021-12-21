package clavardage.view.main;

import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import clavardage.view.main.MessageWindow.Destinataire;

public class MessagesPanel extends JPanel {
	
	private boolean emptyDiscussion;
	private int idConversation;
	private Destinataire type;
	
	public MessagesPanel() {
		super();
		emptyDiscussion = true ;
		this.setBorder(new EmptyBorder(0, /*3*/0, 0, 0));
		this.setLayout(new GridBagLayout());
	}
	
	public MessagesPanel(int id, Destinataire t) {
		super();
		emptyDiscussion = true ;
		idConversation = id;
		type = t;
		this.setBorder(new EmptyBorder(0, /*3*/0, 0, 0));
		this.setLayout(new GridBagLayout());
	}
	
	public void startConversation() {
		this.emptyDiscussion = false ;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.remove(0);
	}
	
	public boolean isEmptyDiscussion() {
		return emptyDiscussion ;
	}
	
	public Destinataire getTypeConversation() {
		return type;
	}
	
	public int getIdConversation() {
		return idConversation;
	}
}
