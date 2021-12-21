package clavardage.view.main;

/* TODO
 * -button hover à centrer
 * -editMsg : si on clique autre par et que getText().isEmpty() ou isBlanck() alors on revient sur le truc initial
 * -rajouter heure des messages devant les buble + noms des expediters dans les conversataions de groupes
 * -nom utilisateur fixé à gauche quand réduction (pour le moment, c'est réduit au milieu)
 * -les noms de destinataires de plus de 16 caractères font bouger la mise en page à l'ouverture de leur conversation
 * -modifier MyAlertMessage pour retour à la ligne (transformer en JtextArea ?)
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultHighlighter;

import clavardage.controller.Clavardage;
import clavardage.controller.authentification.AuthOperations;
import clavardage.controller.gui.MainGUI;
import clavardage.model.exceptions.UserNotConnectedException;
import clavardage.view.main.Application.ColorThemeApp;
import clavardage.view.main.LoginWindow.TypeBuble;
import clavardage.view.main.MessageWindow.Destinataire;

import javax.swing.JMenuItem;

public class MessageWindow extends JPanel implements ActionListener, MouseListener {
	
	/* ** Menu Bar ** */
	private JMenuBar menuBar;
	private JPanel logoPanel;
	private JLabel logo;
	private JMenu settings, colorApp, account;
	private ButtonGroup allColors;
	private JRadioButton colorAppWhite, colorAppBlack;
	private JMenuItem disconnect;

	/* ** Body's App ** */
	private JPanel bodyApp;
	private JPanel destinataires, discussionContainer;
	// -- Destinataires -- //
	private JPanel users, groups, northGroups;
	private JTextArea titleUsers, titleGroups;
	private JButton addGroup;
	private JScrollPane usersContainer, groupsContainer;
	private JPanel listUsers, listGroups ;
	private int nbUsers, nbGroups;
	private ArrayList<DestinataireJPanel> allUsers, allGroups ;
	enum Destinataire {User,Group;}
	// -- Discussion -- //
	private JPanel discussion, discussionDisplay, northDiscussion, allDiscussionClose, newMsg;
	private JLabel chooseDestinataire;
	private JTextArea nameDestinataire;
	private JScrollPane messageContainer;
	private JButton editGroup, addUser, sendFile, sendPicture, sendMsg;
	private JTextField editMsg;
	private ArrayList<MessagesPanel> allMessagesUsers, allMessagesGroups;
	private boolean conversationOpen ;

	/* ** Pictures and Icons ** */
	private Image logoImage, settingsImage, accountImage, addGroupImage, addGroupImageHover, sendFileImage, sendFileImageHover, sendPictureImage, sendPictureImageHover, sendMsgImage, sendMsgImageHover, addUserImage, addUserImageHover, editGroupImage, editGroupImageHover;
	private ImageIcon logoIcon, settingsIcon, accountIcon, addGroupIcon, addGroupIconHover,sendFileIcon, sendFileIconHover, sendPictureIcon, sendPictureIconHover, sendMsgIcon, sendMsgIconHover, addUserIcon, addUserIconHover, editGroupIcon, editGroupIconHover;
	;

	public MessageWindow() throws IOException, UserNotConnectedException {
		setLayout(new BorderLayout());

		/* Add the menu bar */
		add(createBodyApp(), BorderLayout.CENTER);
		/* Add the app's body */
		add(createMenuBar(), BorderLayout.NORTH);
		customThemeMessage(Application.getColorThemeApp());
		


	}


	/**
	 * Create the menu bar.
	 * @throws IOException
	 * */
	private JMenuBar createMenuBar() throws IOException {	
		logoPanel = new JPanel();
		
		logoImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/Logo_title.png")).getScaledInstance(130, 33, Image.SCALE_SMOOTH);
		logoIcon = new ImageIcon(logoImage, "logo");
		logo = new JLabel();
		
		settingsImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/Settings.png")).getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		settingsIcon = new ImageIcon(settingsImage, "Setting menu");
		settings = new JMenu();
		
		colorApp = new JMenu("Change the default color");
		allColors = new ButtonGroup();
		colorAppWhite = new JRadioButton("White");
		colorAppBlack = new JRadioButton("Black");
		
		accountImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/Account.png")).getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		accountIcon = new ImageIcon(accountImage, "Account menu");
		account = new JMenu();
		
		disconnect = new JMenuItem("Disconnect");
		
		menuBar = new MyMenuBar(this,logoPanel, logoIcon, logo,
								settingsIcon, settings,
								colorApp,allColors,colorAppWhite,colorAppBlack,
								accountIcon, account, disconnect);
		return menuBar ;
	}

	/**
	 * Create the app's body.
	 * @throws IOException
	 * @throws UserNotConnectedException
	 * */
	private JPanel createBodyApp() throws IOException, UserNotConnectedException {
		bodyApp = new MyBodyApp(createDestinatairesPanel(), createDiscussionContainerPanel());
		return bodyApp ;
	}

	/**
	 * Create the panel of the destinataires.
	 * @throws IOException
	 * @throws UserNotConnectedException
	 * */
	private JPanel createDestinatairesPanel() throws IOException, UserNotConnectedException {
		
		titleUsers = new MyTitle("Users");
		usersContainer = createListUsers();
		users = new MyUsersPanel(titleUsers,usersContainer);
		
		northGroups = new JPanel();
		titleGroups = new MyTitle("Groups");
		addGroupImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/addGroups.png")).getScaledInstance(13, 13, Image.SCALE_SMOOTH);
		addGroupIcon = new ImageIcon(addGroupImage, "Add Group Button");
		addGroupImageHover =ImageIO.read(Clavardage.getResourceStream("/img/assets/addGroups.png")).getScaledInstance(15, 15, Image.SCALE_SMOOTH);
		addGroupIconHover = new ImageIcon(addGroupImageHover, "Add Group Button Hover");
		addGroup = new MyJButton(addGroupIcon,addGroupIconHover);
		groupsContainer = createListGroups();

		groups = new MyGroupsPanel(this, northGroups, titleGroups, addGroup, groupsContainer);

		destinataires = new MyDestinatairesPanel( users, groups);
		
		

		return destinataires ;
	}

	/**
	 * Create the panel of the discussion container.
	 * @throws IOException
	 * */
	private JPanel createDiscussionContainerPanel() throws IOException {
		
		northDiscussion = new JPanel();
		
		nameDestinataire = new MyTitle("");
		
		editGroupImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/addGroups.png")).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		editGroupIcon = new ImageIcon(editGroupImage, "Add Group Button");
		editGroupImageHover =ImageIO.read(Clavardage.getResourceStream("/img/assets/addGroups.png")).getScaledInstance(18, 18, Image.SCALE_SMOOTH);
		editGroupIconHover = new ImageIcon(editGroupImageHover, "Add Group Button Hover");
		editGroup = new MyJButton(editGroupIcon,editGroupIconHover);

		
		addUserImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/addGroups.png")).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		addUserIcon = new ImageIcon(addUserImage, "Add Group Button");
		addUserImageHover =ImageIO.read(Clavardage.getResourceStream("/img/assets/addGroups.png")).getScaledInstance(18, 18, Image.SCALE_SMOOTH);
		addUserIconHover = new ImageIcon(addUserImageHover, "Add Group Button Hover");
		addUser = new MyJButton(addUserIcon,addUserIconHover);
		
		discussion = new MyDiscussion(this, northDiscussion, nameDestinataire, editGroup, addUser, createMsgPanel(), createNewMsgPanel(), groupsContainer);
		
		discussionContainer = new MyDiscussionContainer(discussion);

		return discussionContainer ;
	}

	/**
	 * Create the list of Users.
	 * @throws IOException
	 * @throws UserNotConnectedException
	 * */
	private JScrollPane createListUsers() throws IOException, UserNotConnectedException {
		nbUsers = 0;

		listUsers = new MyListDestinataires();
		
		allUsers = new ArrayList<DestinataireJPanel>();
		allMessagesUsers = new ArrayList<MessagesPanel>();
		for (String name : MainGUI.getAllUsernamesInDatabase()) {
			addNewUserToList(name, true);
		}
		
		usersContainer = new MyJScrollPane(listUsers);

		return usersContainer ;
	}

	/**
	 * Create the list of Groups.
	 * @throws IOException
	 * */
	private JScrollPane createListGroups() throws IOException {
		nbGroups = 0;

		listGroups = new MyListDestinataires();
		
		allGroups = new ArrayList<DestinataireJPanel>();
		allMessagesGroups = new ArrayList<MessagesPanel>();

		addNewGroupToList("Clovordoge",true);
		addNewGroupToList("Les potos",true);
		addNewGroupToList("Salut c'est nous",false);
		addNewGroupToList("4IR A2 > 4IR A1",false);
		addNewGroupToList("Je suis un groupe",true);
		addNewGroupToList("Espionnage Industriel",false);
		
		groupsContainer = new MyJScrollPane(listGroups);

		return groupsContainer ;
	}

	/**
	 * Create the panel of new message.
	 * @throws IOException
	 * */
	private JPanel createNewMsgPanel() throws IOException{
		
		sendFileImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/sendFile.png")).getScaledInstance(14, 21, Image.SCALE_SMOOTH);
		sendFileIcon = new ImageIcon(sendFileImage, "Send File Button");
		sendFileImageHover =ImageIO.read(Clavardage.getResourceStream("/img/assets/sendFile.png")).getScaledInstance(16, 24, Image.SCALE_SMOOTH);
		sendFileIconHover = new ImageIcon(sendFileImageHover, "Send File Button Hover");
		sendFile = new MyJButton(sendFileIcon,sendFileIconHover);
		
		sendPictureImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/sendPicture.png")).getScaledInstance(14, 21, Image.SCALE_SMOOTH);
		sendPictureIcon = new ImageIcon(sendPictureImage, "Send Picture Button");
		sendPictureImageHover =ImageIO.read(Clavardage.getResourceStream("/img/assets/sendPicture.png")).getScaledInstance(16, 24, Image.SCALE_SMOOTH);
		sendPictureIconHover = new ImageIcon(sendPictureImageHover, "Send Picture Button Hover");
		sendPicture = new MyJButton(sendPictureIcon,sendPictureIconHover);
		
		editMsg = new MyEditMsg();
		
		sendMsgImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/sendMsg.png")).getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		sendMsgIcon = new ImageIcon(sendMsgImage, "Send Msg Button");
		sendMsgImageHover =ImageIO.read(Clavardage.getResourceStream("/img/assets/sendMsg.png")).getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		sendMsgIconHover = new ImageIcon(sendMsgImageHover, "Send Msg Button Hover");
		sendMsg = new MyJButton(sendMsgIcon,sendMsgIconHover);
				
		newMsg = new MyNewMsgPanel(this, sendFile, sendPicture,editMsg, sendMsg);
				
		return newMsg ;
	}
	


	/**
	 * Create the panel of messages.
	 * @throws IOException
	 * */
	private JScrollPane createMsgPanel() throws IOException{
		conversationOpen = false ;
		
		allDiscussionClose = new MessagesPanel();
		chooseDestinataire = new MyAlertMessage("Choose someone to start a conversation...", SwingConstants.CENTER);
		allDiscussionClose.add(chooseDestinataire);
		
		/* SOME TEST */
		
		allMessagesGroups.get(1).startConversation();
		MessageBuble msg1 = new MessageBuble(TypeBuble.THEIR,"Ceci est la largeur maximum d'un message, elle fait environ 2/3 de la zone de dialogue.  Il n'y a pas de longueur maximum pour un message.", new MyDate(1640038517402L));
		MessageBuble msg2 = new MessageBuble(TypeBuble.MINE,"Ceci est la largeur maximum d'un message, elle fait environ 2/3 de la zone de dialogue.  Il n'y a pas de longueur maximum pour un message.", new MyDate(1640039918402L));
		MessageBuble msg3 = new MessageBuble(TypeBuble.THEIR,"Hello, comment tu vas mega super bien ?", new MyDate(1640048519402L));
		msg2.setColorPanel();
		allMessagesGroups.get(1).add(msg1);
		allMessagesGroups.get(1).add(msg2);
		allMessagesGroups.get(1).add(new MyDayPanel(new MyDate(1640048519402L)));
		allMessagesGroups.get(1).add(msg3);
		
		/* **** **** */
		
		
		messageContainer = new MyJScrollPane(allDiscussionClose);
		discussionDisplay = allDiscussionClose;
		
		return messageContainer;
	}
	

	/**
	 * Custom theme App.
	 * */
	public void customThemeMessage(ColorThemeApp c) {
		((Application) Application.getApp()).changeColorThemeApp(c);
		
		this.setBackground(Application.COLOR_BACKGROUND);

		/* ** Menu Bar ** */
		menuBar.setBackground(Application.COLOR_BACKGROUND2);

		/* ** Body's App ** */
		bodyApp.setBackground(Application.COLOR_BACKGROUND);
		
		// -- Destinataires -- //
		listUsers.setBackground(Application.COLOR_BACKGROUND);
		for (Component panel : listUsers.getComponents()) {
			((DestinataireJPanel) panel).setForegroundNamePanel();
		}
		usersContainer.getVerticalScrollBar().setBackground(Application.COLOR_BACKGROUND);
		
		listGroups.setBackground(Application.COLOR_BACKGROUND);
		for (Component panel : listGroups.getComponents()) {
			((DestinataireJPanel) panel).setForegroundNamePanel();
		}
		groupsContainer.getVerticalScrollBar().setBackground(Application.COLOR_BACKGROUND); 
		
		// -- Discussion -- //
		discussion.setBackground(Application.COLOR_BACKGROUND2);
		customDiscussionDisplay(c);
		
		editMsg.setBackground(Application.COLOR_EDIT_MESSAGE);
		if (((MyRoundJTextField) editMsg).isEmptyText()) {
			editMsg.setForeground(Application.COLOR_TEXT_EDIT);
		} else {
			editMsg.setForeground(Application.COLOR_TEXT);
		}
		
		for (Component panel : messageContainer.getViewport().getComponents()) {
			if (panel.getClass().getName() == "clavardage.view.main.MessageBuble") {
				((MessageBuble) panel).setColorPanel();
			}
		}
		messageContainer.getVerticalScrollBar().setBackground(Application.COLOR_BACKGROUND2);

	}
	
	public void customDiscussionDisplay(ColorThemeApp c) {
		discussionDisplay.setBackground(Application.COLOR_BACKGROUND2);
	}

	/* --------- GET and SETTER ----------- */
	
	/**
	 * Add a new user to the list of users.
	 * @throws IOException 
	 * */
	public void addNewUserToList(String name, Boolean connect) throws IOException {
		DestinataireJPanel user = new DestinataireJPanel(name,nbUsers,connect,Destinataire.User,this) ;
		MessagesPanel noDiscussion = new MessagesPanel(user.getIdDestinataire(), Destinataire.User);
		JLabel startConversation = new MyAlertMessage("Start the conversation with " + user.getNameDestinataire() + " : send a message ! :)", SwingConstants.CENTER);
		noDiscussion.add(startConversation);
		allMessagesUsers.add(noDiscussion);
		allUsers.add(user);
		listUsers.add(user);
		nbUsers++;
		listUsers.setPreferredSize(new Dimension(0, 30*nbUsers));
			

	}
	
	/**
	 * Add a new group to the list of groups.
	 * @throws IOException 
	 * */
	public void addNewGroupToList(String name, Boolean connect) throws IOException {
//		String newName = name;
//		if (name.length() > 16) {
//			newName = name.substring(0, 16) ;
//		}
		DestinataireJPanel group = new DestinataireJPanel(name,nbGroups,connect,Destinataire.Group,this);
		MessagesPanel noDiscussion = new MessagesPanel(group.getIdDestinataire(),Destinataire.Group);
		JLabel startConversation = new MyAlertMessage("Start the conversation on " + group.getNameDestinataire() + " : send a message ! :)", SwingConstants.CENTER);
		noDiscussion.add(startConversation);
		allMessagesGroups.add(noDiscussion);
		allGroups.add(group);
		listGroups.add(group);
		nbGroups++;
		listGroups.setPreferredSize(new Dimension(0, 30*nbGroups));
		group.setForegroundNamePanel();
	}

	/**
	 * Display the new message on the discussion panel and reset JTextField.
	 * */
	public void sendMessage(int mode) {
		if   ( (!((MyRoundJTextField) editMsg).isEmptyText()) & (!(editMsg.getText().isEmpty() | editMsg.getText().isBlank())) )   {
			MyDate date = new MyDate();
			MessageBuble msg = new MessageBuble(TypeBuble.MINE,editMsg.getText(), date);
			msg.setColorPanel();
			
			boolean newDay = false;
			MyDayPanel day = new MyDayPanel(date);
			
			if (!((MessagesPanel) discussionDisplay).isEmptyDiscussion()) {
				MessageBuble lastMsg = (MessageBuble) discussionDisplay.getComponent(discussionDisplay.getComponentCount()-1);
				if (!(lastMsg.getDay().equals(msg.getDay()))) {
					newDay = true ;
				}
			} else {
				newDay = true ;
			}

			Destinataire typeCurrentConversation = ((MessagesPanel) discussionDisplay).getTypeConversation();
			int idCurrentConversation = ((MessagesPanel) discussionDisplay).getIdConversation();
			
			if (typeCurrentConversation == Destinataire.User) {
				if (((MessagesPanel) discussionDisplay).isEmptyDiscussion()) {
					allMessagesUsers.get(idCurrentConversation).startConversation();
				}
				if (newDay) {
					allMessagesUsers.get(idCurrentConversation).add(day);
				}
				allMessagesUsers.get(idCurrentConversation).add(msg);

			} else {
				if (((MessagesPanel) discussionDisplay).isEmptyDiscussion()) {
					allMessagesGroups.get(idCurrentConversation).startConversation();
				}
				if (newDay) {
					allMessagesGroups.get(idCurrentConversation).add(day);
				}
				allMessagesGroups.get(idCurrentConversation).add(msg);

			}

			discussionDisplay.repaint();
			messageContainer.validate();
			
			if (mode == 1) {
				((MyRoundJTextField) editMsg).setEmptyText(true);
			} else {
				editMsg.setText("");
			}
		}	
	}
		
	public void openConversation(String newDestinataire, int id, Destinataire d) {
		if (!conversationOpen) {
			conversationOpen = true;	
			newMsg.setVisible(true);
		}
		
		if (d == Destinataire.Group) {
			editGroup.setVisible(true);
			addUser.setVisible(true);
		} else {
			editGroup.setVisible(false);
			addUser.setVisible(false);
		}
		
		nameDestinataire.setText(newDestinataire);
		
		if (d == Destinataire.User) {
			messageContainer.setViewportView(allMessagesUsers.get(id));
			discussionDisplay = allMessagesUsers.get(id);
		} else {
			messageContainer.setViewportView(allMessagesGroups.get(id));
			discussionDisplay = allMessagesGroups.get(id);
		}
		customDiscussionDisplay(Application.getColorThemeApp());
		
		((MyRoundJTextField) editMsg).setEmptyText(true);	
	}

	public void closeConversation() {
		if (conversationOpen) {
			conversationOpen = false;
			nameDestinataire.setText("");
			newMsg.setVisible(false);
			
			for (Component panel : listUsers.getComponents()) {
				((DestinataireJPanel) panel).closeMyConversation();
			}
			for (Component panel : listGroups.getComponents()) {
				((DestinataireJPanel) panel).closeMyConversation();
			}
		}
		
		messageContainer.setViewportView(allDiscussionClose);
		discussionDisplay = allDiscussionClose;
	}
	
	public JLabel getChooseDestinataire() {
		return this.chooseDestinataire ;  
	}
	
	public void setNameGroup() {
//		nameDestinataire.setEditable(true);
//		nameDestinataire.setHighlighter(new DefaultHighlighter());		
	}		
	
	public void moveInTopOfList(Destinataire type, int id) throws IOException {
		listUsers.removeAll();
		listGroups.removeAll();

		if (type == Destinataire.User) {
			for (DestinataireJPanel user : allUsers) {
				if (user.getIdDestinataire() == id) {
					listUsers.add(user);
				}
			}
			for (DestinataireJPanel user : allUsers) {
				if (!(user.getIdDestinataire() == id)) {
					listUsers.add(user);
				}
			}
			for (DestinataireJPanel group : allGroups) {
				listGroups.add(group);
			}
		} else {
			for (DestinataireJPanel group : allGroups) {
				if (group.getIdDestinataire() == id) {
					listGroups.add(group);
				}
			}
			for (DestinataireJPanel group : allGroups) {
				if (!(group.getIdDestinataire() == id)) {
					listGroups.add(group);
				}
			}
			groupsContainer.getVerticalScrollBar().setValue(0);
			for (DestinataireJPanel user : allUsers) {
				listUsers.add(user);
			}
		}
	
	}

	public JPanel getListUsers() {
		return	listUsers;
	}
	
	public JPanel getListGroups() {
		return	listGroups;
	}
	
	public ArrayList<DestinataireJPanel> getAllUsers() {
		return	allUsers;
	}
	
	public ArrayList<DestinataireJPanel> getAllGroups() {
		return	allGroups;
	}

	/* --------- GLOBAL LISTENERS ----------- */

	@Override
	public void mouseClicked(MouseEvent e) {	
		if (e.getSource()==editMsg) {
			if (((MyRoundJTextField) editMsg).isEmptyText()) {
				editMsg.setText("");
				editMsg.setForeground(Application.COLOR_TEXT);
				((MyRoundJTextField) editMsg).setEmptyText(false);
			}
		} else {
//			if ( editMsg.getText().isEmpty() |  editMsg.getText().isBlank()) {
//				((MyRoundJTextField) editMsg).setEmptyText(true);
//			}
//			Utiliser Is Focusable !!!
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {		
	}

	@Override
	public void mouseExited(MouseEvent e) {		
	}

	@Override
	public void actionPerformed(ActionEvent e) {		
	}





}
