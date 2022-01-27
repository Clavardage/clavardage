package clavardage.view.main;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import clavardage.controller.Clavardage;
import clavardage.view.Application;
import clavardage.view.Application.Destinataire;
import clavardage.view.listener.ActionConnectivity;
import clavardage.view.listener.MouseOpenConversation;

@SuppressWarnings("serial")
public class DestinataireJPanel extends JPanel {
	
	private JPanel connectPanel, namePanel;
	private JTextArea nameUser;
	private JLabel connectLabel;
	private UUID id;
	private boolean conversationOpen, connected ;
	private Image openImage, connectUserImage, disconnectUserImage, connectGroupImage, disconnectGroupImage;
	private ImageIcon openIcon, myConnectIcon, connectUserIcon, disconnectUserIcon, connectGroupIcon, disconnectGroupIcon;
	private Destinataire type;
	private MouseOpenConversation myListener;
	
	public DestinataireJPanel(String name, UUID i, boolean c, Destinataire d) throws IOException {
		super();
		this.conversationOpen = false;
		this.id = i;
		this.type = d;
		this.connected = c;
		this.connectPanel = new JPanel();
		this.namePanel = new JPanel();
		this.conversationOpen = false ;
		this.myListener = new MouseOpenConversation(this);
		
		//pastille bleue
		this.openImage = ImageIO.read(Clavardage.getResourceStream("/img/assets/userOpen.png")).getScaledInstance(11, 11, Image.SCALE_SMOOTH);
		this.openIcon = new ImageIcon(openImage, "The conversation is open");
		
		//save images and icons
		connectUserImage = ImageIO.read(Clavardage.getResourceStream("/img/assets/userConnect.png")).getScaledInstance(11, 11, Image.SCALE_SMOOTH);
		connectUserIcon = new ImageIcon(connectUserImage, "User is connected");	
		disconnectUserImage = ImageIO.read(Clavardage.getResourceStream("/img/assets/userDisconnect.png")).getScaledInstance(11, 11, Image.SCALE_SMOOTH);
		disconnectUserIcon = new ImageIcon(disconnectUserImage, "User is disconnected");
		connectGroupImage =  ImageIO.read(Clavardage.getResourceStream("/img/assets/groupConnect.png")).getScaledInstance(15, 15, Image.SCALE_SMOOTH);
		connectGroupIcon =  new ImageIcon(connectGroupImage, "At least one user is connected");
		disconnectGroupImage = ImageIO.read(Clavardage.getResourceStream("/img/assets/groupDisconnect.png")).getScaledInstance(15, 15, Image.SCALE_SMOOTH);
		disconnectGroupIcon = new ImageIcon(disconnectGroupImage, "All users are disconnected");
		
		//choose the right icon
		if (this.type == Destinataire.User) {
			if (connected) {
				myConnectIcon = connectUserIcon ;
			} else {
				myConnectIcon = disconnectUserIcon ;
			}
		} else {
			if (connected) {
				myConnectIcon = connectGroupIcon ;
			} else {
				myConnectIcon = disconnectGroupIcon ;
			}
		}
		
		/* Specific design */	
		this.setBorder(null);
		this.setOpaque(false);
		this.setMaximumSize(new Dimension (Toolkit.getDefaultToolkit().getScreenSize().width, 30));
		this.setMinimumSize(new Dimension (0, 30));

		this.connectPanel.setOpaque(false);
    	this.namePanel.setOpaque(false);
    	    	
		GridBagLayout gbl_destinataireJPanel = new GridBagLayout();
		gbl_destinataireJPanel.columnWidths = new int[]{20, 0, 0};
		gbl_destinataireJPanel.rowHeights = new int[]{30};
		gbl_destinataireJPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_destinataireJPanel.rowWeights = new double[]{0.0};
		setLayout(gbl_destinataireJPanel);
				
		GridBagConstraints gbc_connectPanel = new GridBagConstraints();
		gbc_connectPanel.insets = new Insets(0, 0, 0, 5);
		gbc_connectPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_connectPanel.gridx = 0;
		gbc_connectPanel.gridy = 0;
		add(connectPanel, gbc_connectPanel);
		
		GridBagConstraints gbc_namePanel = new GridBagConstraints();
		gbc_namePanel.anchor = GridBagConstraints.WEST;
		gbc_namePanel.gridx = 1;
		gbc_namePanel.gridy = 0;
		add(this.namePanel, gbc_namePanel);
		
		connectLabel = new JLabel();
		connectLabel.setHorizontalAlignment(SwingConstants.CENTER);
		connectLabel.setOpaque(false);
		connectLabel.setIcon(myConnectIcon);
		this.connectPanel.add(connectLabel);
		
		nameUser = new JTextArea(name);
		nameUser.setEditable(false);
		nameUser.setHighlighter(null);
		nameUser.setBorder(null);
		nameUser.setOpaque(false);
		this.namePanel.add(nameUser);
		/* ******** ****** */	

		//clickable only if connected
		if (connected) {
			super.addMouseListener(myListener);
			namePanel.addMouseListener(myListener);
			nameUser.addMouseListener(myListener);
	    	this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			nameUser.setCursor(new Cursor(Cursor.HAND_CURSOR));
		} else {
			super.removeMouseListener(myListener);
			namePanel.removeMouseListener(myListener);
			nameUser.removeMouseListener(myListener);
	    	this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			nameUser.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}	
	}
	
	/**
	 * Custom color for Theme
	 * */
	public void setForegroundNamePanel() {
			namePanel.setForeground(Application.getCOLOR_TEXT()); //for mouseExited in MouseOpenConversation
			nameUser.setForeground(Application.getCOLOR_TEXT());
	}
	
	/**
	 * Update the list when a conversation is open
	 * */
	public void openConversationInList() {
		this.conversationOpen = true ;
		setConnected(true);
		connectLabel.setIcon(openIcon);
		MouseOpenConversation.moveInTopOfList(this.type, this.id);
	}
	
	/**
	 * Update the list when a conversation is close
	 * @throws Exception 
	 * */
	public void closeConversationInList() throws Exception {
		this.conversationOpen = false ;
		if (this.type == Destinataire.User) {
			this.setConnected(false);
			ActionConnectivity.reorganizeListByConnectivity(Application.getMessageWindow().findMyUser(this.id), this.connected);;
		} else {
			this.myConnectIcon = connectGroupIcon;
			this.connectLabel.setIcon(myConnectIcon);
			this.repaint();
			this.validate();
		}
	}


	public void setConnected(boolean connect) {
		this.connected = connect;
		
		//clickable only if connected and choose the right icon
		if (connect) {
			this.myConnectIcon= connectUserIcon;
			super.addMouseListener(myListener);
			namePanel.addMouseListener(myListener);
			nameUser.addMouseListener(myListener);
	    	this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			nameUser.setCursor(new Cursor(Cursor.HAND_CURSOR));
		} else {
			this.myConnectIcon= disconnectUserIcon;
			super.removeMouseListener(myListener);
			namePanel.removeMouseListener(myListener);
			nameUser.removeMouseListener(myListener);
	    	this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			nameUser.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		//set the right icon
		this.connectLabel.setIcon(myConnectIcon);
		this.repaint();
		this.validate();
	}
	
	
	
	/* --------- GETTER AND SETTER ----------- */

	
	public JTextArea getPanelName() {
		return nameUser;
	}
	
	public JPanel getPanel() {
		return namePanel;
	}
	
	public Destinataire getTypeDestinataire() {
		return type;
	}
	
	public UUID getIdDestinataire() {
		return id;
	}
	
	public boolean isConnected() {
		return connected;
	}
	
	public boolean isOpen() {
		return conversationOpen ;
	}

	public String getNameDestinataire() {
		return nameUser.getText();
	}
	
	public void setNameDestinataire(String newName) {
		nameUser.setText(newName);
	}

	public ImageIcon getConnectIcon() {
		return myConnectIcon;
	}

	public Destinataire getType() {
		return type;
	}

	public void setType(Destinataire type) {
		this.type = type;
	}

}