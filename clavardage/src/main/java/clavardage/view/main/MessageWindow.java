package clavardage.view.main;

/* TODO
 * -button hover à centrer
 * -rajouter noms des expediters dans les conversataions de groupes
 * -nom utilisateur fixé à gauche quand réduction (pour le moment, c'est réduit au milieu)
 * -les noms de destinataires de plus de 16 caractères font bouger la mise en page à l'ouverture de leur conversation
 * -modifier MyAlertMessage pour retour à la ligne (transformer en JtextArea ?)
 * -truc pour chenger mon login
 * -plusieurs fenetre ouverte (pastilles bleues) A Verifier
 * -message alert pour confirmer la déconnexion
 * -entre pour se connecter
 * -pb color SignIn et scroll login et couleur texte DestinataireJPanel
 */

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.text.DefaultHighlighter;

import clavardage.controller.Clavardage;
import clavardage.controller.authentification.AuthOperations;
import clavardage.controller.gui.MainGUI;
import clavardage.model.exceptions.UserNotConnectedException;
import clavardage.model.objects.User;
import clavardage.view.main.Application.ColorThemeApp;
import clavardage.view.main.LoginWindow.TypeBuble;

@SuppressWarnings("serial")
public class MessageWindow extends JPanel {
	
	/* ** Menu Bar ** */
	private MyMenuBar menuBar;
	private JPanel logoPanel;
	private JLabel logo;
	private JMenu settings, colorApp, account;
	private ButtonGroup allColors;
	private JRadioButton colorAppWhite, colorAppBlack;
	private JMenuItem disconnect;

	/* ** Body's App ** */
	private MyBodyApp bodyApp;
	private MyDestinatairesPanel destinataires;
	private MyDiscussionContainer discussionContainer;
	// -- Destinataires -- //
	private MyJScrollPane usersContainer, groupsContainer;
	private MyTitle titleUsers, titleGroups;
	private MyUsersPanel users ;
	private MyGroupsPanel groups;
	private JPanel northGroups;
	private MyJButton addGroup;
	private MyListDestinataires listUsers, listGroups ;
	private int nbUsers, nbGroups;
	private ArrayList<DestinataireJPanel> allUsers, allGroups ;
	public enum Destinataire {User,Group;}
	// -- Discussion -- //
	private MyDiscussionPanel discussion;
	private JMenuBar northDiscussion;
	private JMenu settingsGroups, seeMembersGroup, addMemberInGroup;
	private JMenuItem leaveGroup,backUsers, nextUsers, backMembers,nextMembers;
	private MyTitle nameDestinataire;
	private MyJScrollPane messageContainer;
	private MyAlertMessage chooseDestinataire;
	private MessagesPanel discussionDisplay, allDiscussionClose;
	private MyNewMsgPanel newMsg;
	private MyJButton editNameGroup, sendFile, sendPicture, sendMsg;
	private MyEditMsg editMsg;
	private int membersDisplay, usersDisplay;
	private boolean conversationOpen ;
	private ArrayList<MessagesPanel> allMessagesUsers, allMessagesGroups;

	/* ** Pictures and Icons ** */
	private Image logoImage, settingsImage, accountImage, addGroupImage, addGroupImageHover, sendFileImage, sendFileImageHover, sendPictureImage, sendPictureImageHover, sendMsgImage, sendMsgImageHover, editNameGroupImage, editNameGroupImageHover, settingsGroupsImage;
	private ImageIcon logoIcon, settingsIcon, accountIcon, addGroupIcon, addGroupIconHover,sendFileIcon, sendFileIconHover, sendPictureIcon, sendPictureIconHover, sendMsgIcon, sendMsgIconHover,editNameGroupIcon, editNameGroupIconHover, settingsGroupsIcon;
	
	public MessageWindow() throws Exception {
		setLayout(new BorderLayout());

		/* Add the menu bar */
		add(createBodyApp(), BorderLayout.CENTER);
		/* Add the app's body */
		add(createMenuBar(), BorderLayout.NORTH);
		
		/* Apply the chosen theme */
		customThemeMessage(Application.getColorThemeApp());
	}


	/**
	 * Create the menu bar.
	 * @throws IOException
	 * */
	private MyMenuBar createMenuBar() throws IOException {	
		logoPanel = new JPanel();
		
		logoImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/Logo_title.png")).getScaledInstance(130, 33, Image.SCALE_SMOOTH);
		logoIcon = new ImageIcon(logoImage, "logo");
		logo = new JLabel();
		
		settingsImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/Settings.png")).getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		settingsIcon = new ImageIcon(settingsImage, "Setting menu");
		settings = new JMenu();
		
		colorApp = new JMenu("Change the default color");
		allColors = new ButtonGroup(); //Only one button pressed at the same time
		colorAppWhite = new JRadioButton("White");
		colorAppBlack = new JRadioButton("Black");
		
		accountImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/Account.png")).getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		accountIcon = new ImageIcon(accountImage, "Account menu");
		account = new JMenu();
		
		disconnect = new JMenuItem("Disconnect");
		
		menuBar = new MyMenuBar(logoPanel, logoIcon, logo,
								settingsIcon, settings,
								colorApp,allColors,colorAppWhite,colorAppBlack,
								accountIcon, account, disconnect);
		return menuBar ;
	}

	/**
	 * Create the app's body.
	 * @throws Exception 
	 * */
	private MyBodyApp createBodyApp() throws Exception {
		
		bodyApp = new MyBodyApp(createDestinatairesPanel(), createDiscussionContainer());
		return bodyApp ;
	}

	/**
	 * Create the panel of the destinataires.
	 * @throws Exception 
	 * */
	private MyDestinatairesPanel createDestinatairesPanel() throws Exception {
		
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
	 * @throws UserNotConnectedException 
	 * */
	private MyDiscussionContainer createDiscussionContainer() throws IOException, UserNotConnectedException {
		
		northDiscussion = new JMenuBar();
		
		nameDestinataire = new MyTitle("");
		
		editNameGroupImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/addGroups.png")).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		editNameGroupIcon = new ImageIcon(editNameGroupImage, "Add Group Button");
		editNameGroupImageHover =ImageIO.read(Clavardage.getResourceStream("/img/assets/addGroups.png")).getScaledInstance(18, 18, Image.SCALE_SMOOTH);
		editNameGroupIconHover = new ImageIcon(editNameGroupImageHover, "Add Group Button Hover");
		editNameGroup = new MyJButton(editNameGroupIcon,editNameGroupIconHover);
		
		settingsGroupsImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/addGroups.png")).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		settingsGroupsIcon = new ImageIcon(settingsGroupsImage, "Add Group Button");
		settingsGroups = new JMenu();
		
		seeMembersGroup = new JMenu("See the members of the group");
		addMemberInGroup = new JMenu("Add Someone to the group");
		leaveGroup = new JMenuItem("Leave the group");
		
		discussion = new MyDiscussionPanel(northDiscussion, nameDestinataire, editNameGroup, settingsGroups, settingsGroupsIcon, seeMembersGroup, addMemberInGroup, leaveGroup, createMsgPanel(), createNewMsgPanel(), groupsContainer);
						
		discussionContainer = new MyDiscussionContainer(discussion);

		return discussionContainer ;
	}

	/**
	 * Create the list of Users.
	 * @throws Exception 
	 * */
	private MyJScrollPane createListUsers() throws Exception {
		nbUsers = 0;
		listUsers = new MyListDestinataires();
		
		/* Records all users (except myself) and their associated discussion */
		allUsers = new ArrayList<DestinataireJPanel>();
		allMessagesUsers = new ArrayList<MessagesPanel>();
		for (User user : MainGUI.getAllUsersInDatabase()) {
			if (!(user.getUUID().equals(AuthOperations.getConnectedUser().getUUID()))) {
				addNewUserToList(user, false); //all users are new and no connected, for the moment there is no conversation
			}
		}
		
		usersContainer = new MyJScrollPane(listUsers);

		return usersContainer ;
	}

	/**
	 * Create the list of Groups.
	 * @throws IOException
	 * @throws UserNotConnectedException 
	 * */
	private MyJScrollPane createListGroups() throws IOException, UserNotConnectedException {
		nbGroups = 0;

		listGroups = new MyListDestinataires();
		
		/* Records all groups and their associated discussion */
		allGroups = new ArrayList<DestinataireJPanel>();
		allMessagesGroups = new ArrayList<MessagesPanel>();
		addNewGroupToList("Clovordoge",true);
		addNewGroupToList("Je suis un groupe",true);
		addNewGroupToList("Les potos",true);
		addNewGroupToList("4IR A2 > 4IR A1",false);
		addNewGroupToList("Espionnage Industriel",false);
		addNewGroupToList("Salut c'est nous",false); //for the moment, all groups are new and there is no conversation (except one, see later)
		
		groupsContainer = new MyJScrollPane(listGroups);
		return groupsContainer ;
	}

	/**
	 * Create the panel of new message.
	 * @throws IOException
	 * */
	private MyNewMsgPanel createNewMsgPanel() throws IOException{
		
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
				
		newMsg = new MyNewMsgPanel(sendFile, sendPicture,editMsg, sendMsg);
				
		return newMsg ;
	}
	


	/**
	 * Create the panel of messages.
	 * @throws IOException
	 * @throws UserNotConnectedException 
	 * */
	private MyJScrollPane createMsgPanel() throws IOException{
		conversationOpen = false ;
		
		allDiscussionClose = new MessagesPanel();
		
		/* SOME TEST */
		allMessagesGroups.get(1).startConversation();
		MessageBuble msg1 = new MessageBuble(TypeBuble.THEIR,"Ceci est la largeur maximum d'un message, elle fait environ 2/3 de la zone de dialogue.  Il n'y a pas de longueur maximum pour un message.", new MyDate(1640038517402L));
		MessageBuble msg2 = new MessageBuble(TypeBuble.MINE,"Ceci est la largeur maximum d'un message, elle fait environ 2/3 de la zone de dialogue.  Il n'y a pas de longueur maximum pour un message.", new MyDate(1640039918402L));
		MessageBuble msg3 = new MessageBuble(TypeBuble.THEIR,"Hello, comment tu vas mega super bien ?", new MyDate(1640048519402L));
		msg2.setColorPanel();
		allMessagesGroups.get(1).add(new MyDayInfo(new MyDate(1640038517402L)));
		allMessagesGroups.get(1).add(msg1);
		allMessagesGroups.get(1).add(msg2);
		allMessagesGroups.get(1).add(new MyDayInfo(new MyDate(1640048519402L)));
		allMessagesGroups.get(1).add(msg3);
		
		allMessagesGroups.get(1).addMemberConversation(allUsers.get(0).getIdDestinataire());
		/* **** **** */
		
		messageContainer = new MyJScrollPane(allDiscussionClose);
		discussionDisplay = allDiscussionClose; //save the displayed discussion

		return messageContainer;
	}

	/* --------- GET and SETTER ----------- */
	
	/**
	 * Add a new user to the list of users.
	 * @throws Exception 
	 * */
	public void addNewUserToList(User userInDb, Boolean connect) throws Exception {
		/* Create the user and the future conversation */
		DestinataireJPanel user = new DestinataireJPanel(userInDb.getLogin(),userInDb.getUUID(),connect,Destinataire.User) ;
		MessagesPanel conversation = new MessagesPanel(this, userInDb.getUUID(), Destinataire.User); //WARNING in GUI, the UUID conv == UUID user dest
		
		/* Saves the users on the conversation */
		conversation.addMemberConversation(AuthOperations.getConnectedUser().getUUID());
		conversation.addMemberConversation(userInDb.getUUID());
		
		/* Get messages on the conversation */
		UUID idConvInDb = null;
		try {
			idConvInDb = MainGUI.getConversationUUIDByTwoUsersUUID(AuthOperations.getConnectedUser().getUUID(), userInDb.getUUID());
		} catch (Exception ex ){
			System.out.println("PAS ENCORE DE CONVERSATION EXISTANTE ENTRE " + AuthOperations.getConnectedUser().getLogin() + " ET " + userInDb.getLogin());
		}
		MyAlertMessage startConversation = new MyAlertMessage("Start the conversation with " + userInDb.getLogin() + " : send a message ! :)");
		conversation.add(startConversation);
		if (!(idConvInDb == null)) {
			ArrayList<MessageBuble> allMessagesConv = MainGUI.getAllMessagesFrom(idConvInDb);
			if (!allMessagesConv.isEmpty()) {
				conversation.startConversation();
				for (MessageBuble msg : allMessagesConv) {
					/* see if we need a DayPanel */
					MyDate date = msg.getDate();
					boolean newDay = needDayPanel(date, conversation);
					MyDayInfo day = new MyDayInfo(date);
					if (newDay) {
						conversation.add(day);  //we add the DayPanel if we need it
					}
					conversation.add(msg);
				}
			}
		} else 
		
		/* Saves both and displays the user in the list */
		allUsers.add(user);
		listUsers.add(user);
		allMessagesUsers.add(conversation);
		
		/* Modify the list for a suitable display */
		nbUsers++;
		listUsers.setPreferredSize(new Dimension(0, 30*nbUsers));
	}
	
	/**
	 * Add a new group to the list of groups.
	 * @throws IOException 
	 * @throws UserNotConnectedException 
	 * */
	public void addNewGroupToList(String name, Boolean connect) throws IOException, UserNotConnectedException {	
		/* Create the user and his associated discussion */
		UUID idGroup = new UUID(nbGroups, nbGroups);
		DestinataireJPanel group = new DestinataireJPanel(name,idGroup,connect,Destinataire.Group);
		MessagesPanel noDiscussion = new MessagesPanel(this, group.getIdDestinataire(),Destinataire.Group);
		MyAlertMessage startConversation = new MyAlertMessage("Start the conversation on " + group.getNameDestinataire() + " : send a message ! :)");
		noDiscussion.add(startConversation);
		
		/* Saves the users on the conversation */
		noDiscussion.addMemberConversation(AuthOperations.getConnectedUser().getUUID());
		
		/* Saves both and displays the user in the list */
		allMessagesGroups.add(noDiscussion);
		allGroups.add(group);
		listGroups.add(group);
		
		/* Modify the list for a suitable display */
		nbGroups++;
		listGroups.setPreferredSize(new Dimension(0, 30*nbGroups));
		group.setForegroundNamePanel(); //necessary because it can be added while using the app
	}
	
	public void setConnected(UUID idUser, Boolean connect) {
		for (DestinataireJPanel u : allUsers) {
			if (u.getIdDestinataire().equals(idUser)) {
				if (!u.isOpen()) {
					u.setConnected(connect) ;
					u.revalidate();
				}
			}
		}
		usersContainer.revalidate();
	}
	
	public void reorganiseListByConnectivity(User userUpdated, boolean connect) throws Exception {
		listUsers.removeAll();
		boolean isNew = true ;
		for (DestinataireJPanel user : allUsers) {
			if (user.getIdDestinataire().equals(userUpdated.getUUID())) {
				isNew = false;
			}
		} 
		if (isNew && !userUpdated.getUUID().equals(AuthOperations.getConnectedUser().getUUID())) {
			addNewUserToList(userUpdated, connect);
		}
		for (DestinataireJPanel user : allUsers) {
			if (user.isOpen()) {
				listUsers.add(user);
			}
		}
		for (DestinataireJPanel user : allUsers) {
			if (!user.isOpen() && user.isConnected()) {
				listUsers.add(user);
			}
		}
		for (DestinataireJPanel user : allUsers) {
			if (!user.isOpen() && !user.isConnected()) {
				listUsers.add(user);
			}
		}	
	}

	/**
	 * Display the new message on the discussion panel and reset JTextField.
	 * */
	public void sendMessage(int mode) {
		/*send only if editMsg is not in default mode and there is something to send*/
		if   ( (!editMsg.isEmptyText()) & (!(editMsg.getText().isEmpty() | editMsg.getText().isBlank())) )   {
			
			/* create the new message */
			MyDate date = new MyDate();
			MessageBuble msg = new MessageBuble(TypeBuble.MINE,editMsg.getText(), date);
			msg.setColorPanel(); //necessary because it is send while using the app
			
			/* see if we need a DayPanel */
			boolean newDay = needDayPanel(date, discussionDisplay);
			MyDayInfo day = new MyDayInfo(date);

			/* add the msg to the discussion of the chosen destinataire */
			Destinataire typeCurrentConversation = discussionDisplay.getTypeConversation();
			UUID idCurrentConversation = discussionDisplay.getIdConversation();
			MessagesPanel currentConversation = null;
			
			if (typeCurrentConversation == Destinataire.User) {
				for (MessagesPanel conv : allMessagesUsers) {
					if (conv.getIdConversation().equals(idCurrentConversation)) {
						currentConversation = conv ;
					}
				}
			} else {
				for (MessagesPanel conv : allMessagesGroups) {
					if (conv.getIdConversation().equals(idCurrentConversation)) {
						currentConversation = conv ;
					}
				}
			}
			if (discussionDisplay.isEmptyDiscussion()) {
				currentConversation.startConversation(); //we start the conversation if it is the first msg
			}
			if (newDay) {
				currentConversation.add(day);  //we add the DayPanel if we need it
			}
			currentConversation.add(msg); //we add the new msg
			
			messageContainer.validate();

			if (currentConversation.getTypeConversation() == Destinataire.User) {
				try {
					MainGUI.sendMessageInConversation(MainGUI.getConversationUUIDByTwoUsersUUID(AuthOperations.getConnectedUser().getUUID(), currentConversation.getIdConversation()), editMsg.getText());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			if (mode == 1) { // if it send with the button sendMsg 
				editMsg.setEmptyText(true);
			} else { // if it send with the keyboard 
				editMsg.setText("");
			}
		}	
	}
	
	public void receiveMessage(String text, UUID idContact) throws UserNotConnectedException {

	    /* create the new message */
	    MyDate date = new MyDate();
	    MessageBuble msg = new MessageBuble(TypeBuble.THEIR, text, new MyDate());

	    /* see if we need a DayPanel */
	    boolean newDay = needDayPanel(date, discussionDisplay);
	    MyDayInfo day = new MyDayInfo(date);

	    /* add the msg to the discussion of the destinataire */
	    MessagesPanel contactConversation = null;
	    for (MessagesPanel conv : allMessagesUsers) {
	        if (conv.getIdConversation().equals(idContact)) {
	            contactConversation = conv ;
	        }
	    }

	    if (contactConversation.isEmptyDiscussion()) {
	        contactConversation.startConversation(); //we start the conversation if it is the first msg
	    }
	    if (newDay) {
	        contactConversation.add(day);  //we add the DayPanel if we need it
	    }
	    contactConversation.add(msg); //we add the new msg

	    messageContainer.validate();
	}
	
	public boolean needDayPanel(MyDate date, MessagesPanel conv) {
		boolean newDay = false;
		if (!conv.isEmptyDiscussion() && (conv.getComponentCount() != 0)) {
			int i = conv.getComponentCount()-1 ;
			while (!(conv.getComponent(i).getClass().getName().equals("clavardage.view.main.MyDayInfo"))) {i--;}
			MyDayInfo lastDateDisplay = (MyDayInfo) conv.getComponent(i);
			if (!(lastDateDisplay.getDate().getTheDay().equals(date.getTheDay()))) {
				newDay = true ; //we need it if the date msg is not on the same day of the last one
			} else {
				newDay = false ;
			}
		} else {
			newDay = true ; //we need it if the date msg is the first msg of the discussion
		}
		return newDay;
	}
		
	public void openConversation(String newDestinataire, UUID id, Destinataire d) throws UserNotConnectedException, Exception {
		/* display newMsg if it is necessary */
		if (!conversationOpen) {
			conversationOpen = true;	
			newMsg.setVisible(true);
		}
		
		/* display the name Destinataire */
		nameDestinataire.setText(newDestinataire);
		
		/* open the chosen discussion */
		MessagesPanel chosenConversation = null;
		if (d == Destinataire.User) {
			for (MessagesPanel conv : allMessagesUsers) {
				if (conv.getIdConversation().equals(id)) {
					chosenConversation = conv ;
				}
			}
		} else {
			for (MessagesPanel conv : allMessagesGroups) {
				if (conv.getIdConversation().equals(id)) {
					chosenConversation = conv ;
				}
			}
		}
		messageContainer.setViewportView(chosenConversation);
		discussionDisplay = chosenConversation;
		customDiscussionDisplay(Application.getColorThemeApp()); //necessary because it is open while using the app
		
		/* display or hide the buttons of settings groups if it is necessary */
		if (d == Destinataire.Group) {
			editNameGroup.setVisible(true);
			settingsGroups.setVisible(true);
			seeMembersGroup.removeAll();
			createSeeMembersGroup();
			createAddMemberInGroup();
		} else {
			editNameGroup.setVisible(false);
			settingsGroups.setVisible(false);
		}
		editMsg.setEmptyText(true);
	}

	public void createAddMemberInGroup() {
		usersDisplay = 0;
		addMemberInGroup.removeAll();

		if (discussionDisplay.getNumberNoMembers() <= 10) {
			for (DestinataireJPanel user : discussionDisplay.getNoMembers()) {
				JMenuItem item = new JMenuItem(user.getNameDestinataire());
				addMemberInGroup.add(item);
				item.addActionListener(new ActionOpenAlert(user,new ActionAddMember(item)));
			}
		} else {
			backUsers = new JMenuItem("See back");
			int i;
			for (i=0; i<=8 ; i++) {
				JMenuItem item = new JMenuItem(discussionDisplay.getNoMembers().get(i + 8*usersDisplay).getNameDestinataire());
				addMemberInGroup.add(item);				
				item.addActionListener(new ActionOpenAlert(discussionDisplay.getNoMembers().get(i + 8*usersDisplay),new ActionAddMember(item)));

			}
			nextUsers = new JMenuItem("See next");
			addMemberInGroup.add(nextUsers);
			
			backUsers.addActionListener(new ActionBack(0));
						
			nextUsers.addActionListener(new ActionNext(0));
		}	
	}


	public void createSeeMembersGroup() {
		membersDisplay = 0;
		seeMembersGroup.removeAll();

		if (discussionDisplay.getNumberMembers() <= 10) {
			for (DestinataireJPanel user : discussionDisplay.getMembersConversation()) {
				JMenuItem item = new JMenuItem(user.getNameDestinataire());
				seeMembersGroup.add(item);
			}
		} else {
			backMembers = new JMenuItem("See back");
			int i;
			for (i=0; i<=8 ; i++) {
				JMenuItem item = new JMenuItem(discussionDisplay.getMembersConversation().get(i + 8*membersDisplay).getNameDestinataire());
				seeMembersGroup.add(item);
			}
			nextMembers = new JMenuItem("See next");
			seeMembersGroup.add(nextMembers);
			
			backMembers.addActionListener(new ActionBack(1));		
			
			nextMembers.addActionListener(new ActionNext(1));
		}		
	}


	public void closeConversation() throws Exception {
		/* hide newMsg and nameDestinataire if it is necessary */
		if (conversationOpen) {
			conversationOpen = false;
			nameDestinataire.setText("");
			newMsg.setVisible(false);
			
			for (Component panel : listUsers.getComponents()) {
				((DestinataireJPanel) panel).closeConversationInList();
			}
			for (Component panel : listGroups.getComponents()) {
				((DestinataireJPanel) panel).closeConversationInList();
			}
		}
		
		messageContainer.setViewportView(allDiscussionClose);
		discussionDisplay = allDiscussionClose;
	}
	
	public void setNameGroup() {
		String currentName = nameDestinataire.getText();
		
		nameDestinataire.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
            	/* remove new line due to key Enter */
            	nameDestinataire.setText(nameDestinataire.getText().replaceAll("\r", "").replaceAll("\n", ""));
            	
            	/* change the name only if the new name respect constraints */
            	if (nameDestinataire.getText().isBlank() | nameDestinataire.getText().isEmpty() | (nameDestinataire.getText().length() > 20) ) {
            		nameDestinataire.setText(currentName);
            	}
            	
            	/* change the name in database */
            	for (DestinataireJPanel group : allGroups) {
            		if (group.getIdDestinataire() == discussionDisplay.getIdConversation()) {
            			group.setNameDestinataire(nameDestinataire.getText());
            		}
            	}
            	
            	/* add an InfoPanel if the name has changed with a DayPanel if it is necessary */
            	if (!(currentName.equals(nameDestinataire.getText()))) {
					try {
						MyDate date = new MyDate();
						boolean newDay = needDayPanel(date, discussionDisplay);
						if (newDay) {
		            		if (discussionDisplay.isEmptyDiscussion()) {
		            			discussionDisplay.startConversation();
		            		}
							discussionDisplay.add(new MyDayInfo(date));
						}
						discussionDisplay.add(new MyInfoPanel(currentName, nameDestinataire.getText()));
					} catch (UserNotConnectedException e1) {
						e1.printStackTrace();
					}

            	}
				nameDestinataire.setEditable(false);
				nameDestinataire.setHighlighter(null);
				nameDestinataire.removeFocusListener(this);
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				nameDestinataire.setHighlighter(new DefaultHighlighter());		
				nameDestinataire.selectAll();
				nameDestinataire.setEditable(true);
				nameDestinataire.setCaretColor(getForeground());
				
			}
		});
		
		nameDestinataire.addKeyListener((KeyListener) new KeyAdapter() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	            if(e.getKeyCode() == KeyEvent.VK_ENTER){
	        		northDiscussion.requestFocus();
	        		nameDestinataire.removeKeyListener(this);
	            }
	        }
	        @Override
	        public void keyTyped(KeyEvent e) {
	            if(nameDestinataire.getText().length() > 20){
	        		e.consume();
	            }
	        }
	    });
		
		nameDestinataire.requestFocus();
	}		
	
	public void moveInTopOfList(Destinataire type, UUID id) {
//		listUsers.removeAll();
//		listGroups.removeAll();
//		
//		if (type == Destinataire.User) {
//			for (DestinataireJPanel user : allUsers) {
//				if (user.getIdDestinataire().equals(id)) {
//					listUsers.add(user);
//				}
//			} 
//			for (DestinataireJPanel user : allUsers) {
//				if (!(user.getIdDestinataire().equals(id))) {
//					listUsers.add(user);
//				}
//			}
//			//remove puis add(0)
//			for (DestinataireJPanel group : allGroups) {
//				listGroups.add(group);
//			}
//		} else {
//			for (DestinataireJPanel group : allGroups) {
//				if (group.getIdDestinataire().equals(id)) {
//					listGroups.add(group);
//				}
//			}
//			for (DestinataireJPanel group : allGroups) {
//				if (!(group.getIdDestinataire().equals(id))) {
//					listGroups.add(group);
//				}
//			}
//			groupsContainer.getVerticalScrollBar().setValue(0);
//			for (DestinataireJPanel user : allUsers) {
//				listUsers.add(user);
//			}
//		}

		if (type == Destinataire.User) {
			DestinataireJPanel userConcerned = null;
			for (DestinataireJPanel user : allUsers) {
				if (user.getIdDestinataire().equals(id)) {
					userConcerned = user ;
					listUsers.remove(userConcerned);
				}
			}
			listUsers.add(userConcerned, 0);
		} else {
			DestinataireJPanel groupConcerned = null;
			for (DestinataireJPanel group : allGroups) {
				if (group.getIdDestinataire().equals(id)) {
					groupConcerned = group ;
					listGroups.remove(groupConcerned);
				}
			}
			listGroups.add(groupConcerned, 0);
		}
	
	}
	
	public void closeAllConversation() {
		nameDestinataire.setText("");
		editNameGroup.setVisible(false);
		settingsGroups.setVisible(false);
		newMsg.setVisible(false);
		messageContainer.setViewportView(allDiscussionClose);
		discussionDisplay = allDiscussionClose;
		customDiscussionDisplay(Application.getColorThemeApp());
		conversationOpen = false ;
	}
	
	public MyAlertMessage getChooseDestinataire() {
		return this.chooseDestinataire ;  
	}

	public MyListDestinataires getListUsers() {
		return	listUsers;
	}
	
	public MyListDestinataires getListGroups() {
		return	listGroups;
	}
	
	public ArrayList<DestinataireJPanel> getAllUsers() {
		return	allUsers;
	}
	
	public ArrayList<DestinataireJPanel> getAllGroups() {
		return	allGroups;
	}
	
	public MessagesPanel getDiscussionDisplay() {
		return discussionDisplay;
	}
	
	public MyJScrollPane getMessageContainer() {
		return messageContainer;
	}
	
	public ArrayList<MessagesPanel> getAllMessagesGroups() {
		return allMessagesGroups;
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
		
		messageContainer.getVerticalScrollBar().setBackground(Application.COLOR_BACKGROUND2);

		editMsg.setBackground(Application.COLOR_EDIT_MESSAGE);
		if (((MyRoundJTextField) editMsg).isEmptyText()) {
			editMsg.setForeground(Application.COLOR_TEXT_EDIT);
		} else {
			editMsg.setForeground(Application.COLOR_TEXT);
		}
	}
	
	public void customDiscussionDisplay(ColorThemeApp c) {
	//	discussionDisplay.setBackground(Application.COLOR_BACKGROUND2);
		for (Component panel : discussionDisplay.getComponents()) {
			if (panel.getClass().getName() == "clavardage.view.main.MessageBuble") {
				((MessageBuble) panel).setColorPanel();
			}
		}
		messageContainer.getViewport().getView().setBackground(Application.COLOR_BACKGROUND2);
	}

	/* --------- GLOBAL LISTENERS ----------- */

	public MessagesPanel getAllDiscussionClose() {
		return allDiscussionClose;
	}


	public JMenu getAddMemberInGroup() {
		return addMemberInGroup;
	}


	public JMenu getSettingsGroups() {
		return settingsGroups;
	}


	public int getUsersDisplay() {
		return usersDisplay;
	}


	public JMenuItem getBackUsers() {
		return backUsers;
	}


	public JMenuItem getNextUsers() {
		return nextUsers;
	}


	public int getMembersDisplay() {
		return membersDisplay;
	}


	public JMenuItem getBackMembers() {
		return backMembers;
	}


	public JMenuItem getNextMembers() {
		return nextMembers;
	}


	public JMenu getSeeMembersGroup() {
		return seeMembersGroup;
	}


	public void setDisplay(int mode, int i) {
		if (mode == 0) {
			usersDisplay=i;
		} else if (mode == 1) {
			membersDisplay=i;
		}
		
	}


	public MyJButton getEditNameGroup() {
		return editNameGroup;
	}


	public MyNewMsgPanel getNewMsg() {
		return newMsg;
	}


	public MyJScrollPane getUsersContainer() {
		return usersContainer;
	}


	public void setUsersContainer() throws Exception {
		nbUsers = 0;

		listUsers.removeAll();
		
		/* Records all users (except myself) and their associated discussion */
		allUsers.clear();
		allMessagesUsers.clear();
		for (User user : MainGUI.getAllUsersInDatabase()) {
			if (!(user.getUUID().equals(AuthOperations.getConnectedUser().getUUID()))) {
				addNewUserToList(user, false); //all users are new and no connected, for the moment there is no conversation
			}
		}
		
		usersContainer.setViewportView(listUsers);
	}


	public MyJScrollPane getGroupsContainer() {
		return groupsContainer;
	}


	public void setGroupsContainer() throws IOException, UserNotConnectedException {
		nbGroups = 0;

		listGroups.removeAll();
		
		/* Records all groups and their associated discussion */
		allGroups.clear();
		allMessagesGroups.clear();
		addNewGroupToList("Clovordoge",true);
		addNewGroupToList("Les potos",true);
		addNewGroupToList("Salut c'est nous",false);
		addNewGroupToList("4IR A2 > 4IR A1",false);
		addNewGroupToList("Je suis un groupe",true);
		addNewGroupToList("Espionnage Industriel",false); //for the moment, all groups are new and there is no conversation (except one, see later)
		
		groupsContainer.setViewportView(listGroups);

	}


	public ArrayList<MessagesPanel> getAllMessagesUsers() {
		return allMessagesUsers;
	}


	public void resetAllMessages() {
		this.allMessagesUsers.clear();
		this.allMessagesGroups.clear();
	}
	
	public DestinataireJPanel findMyDestinataireJPanel(UUID id) {
		DestinataireJPanel myDestinataireJPanel = null ;
		for (DestinataireJPanel user : allUsers) {
			if (user.getIdDestinataire().equals(id)) {
				myDestinataireJPanel = user;
			}
		}
		return myDestinataireJPanel;
	}
	
	public User findMyUser(UUID id) throws UserNotConnectedException {
		User myUser = null ;
		for (User user : MainGUI.getAllUsersInDatabase()) {
			if (user.getUUID().equals(id)) {
				myUser = user;
			}
		}
		return myUser;
	}

}
