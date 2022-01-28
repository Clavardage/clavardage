package clavardage.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import clavardage.controller.Clavardage;
import clavardage.controller.authentification.AuthOperations;
import clavardage.controller.gui.MainGUI;
import clavardage.model.exceptions.UserNotConnectedException;
import clavardage.model.objects.User;
import clavardage.view.Application.Destinataire;
import clavardage.view.Application.TypeBuble;
import clavardage.view.alert.MyAlertMessage;
import clavardage.view.listener.ActionSendMessage;
import clavardage.view.main.DestinataireJPanel;
import clavardage.view.main.MessageBuble;
import clavardage.view.main.MessagesPanel;
import clavardage.view.mystyle.MyBodyApp;
import clavardage.view.mystyle.MyDate;
import clavardage.view.mystyle.MyDayInfo;
import clavardage.view.mystyle.MyDestinataireContainer;
import clavardage.view.mystyle.MyDestinatairesPanel;
import clavardage.view.mystyle.MyDiscussionContainer;
import clavardage.view.mystyle.MyDiscussionPanel;
import clavardage.view.mystyle.MyEditMsg;
import clavardage.view.mystyle.MyGroupsPanel;
import clavardage.view.mystyle.MyJButton;
import clavardage.view.mystyle.MyJScrollPane;
import clavardage.view.mystyle.MyListDestinataires;
import clavardage.view.mystyle.MyMenuBar;
import clavardage.view.mystyle.MyNewMsgPanel;
import clavardage.view.mystyle.MyTitle;
import clavardage.view.mystyle.MyUsersPanel;

/**
 * @author Célestine Paillé
 */
@SuppressWarnings("serial")
public class MessageWindow extends JPanel {
	
	/* ** Menu Bar ** */
	private MyMenuBar menuBar; 
	private JRadioButton english;

	/* ** Body's App ** */
	private MyBodyApp bodyApp;
	private MyDestinataireContainer destinataireContainer;
	private MyDiscussionContainer discussionContainer;
	// -- Destinataires -- //
	private MyDestinatairesPanel destinataires;
	private MyJScrollPane usersContainer, groupsContainer;
	private MyUsersPanel users ;
	private MyGroupsPanel groups;
	private MyListDestinataires listUsers, listGroups ;
	private ArrayList<DestinataireJPanel> allUsers, allGroups ;
	private int nbUsers, nbGroups;
	// -- Discussion -- //
	private MyDiscussionPanel discussion;
	private JMenuBar northDiscussion;
	private JMenu settingsGroups, seeMembersGroup, addMemberInGroup;
	private JMenuItem backUsers, nextUsers, backMembers,nextMembers;
	private MyTitle nameDestinataire;
	private MyJScrollPane messageContainer;
	private MessagesPanel discussionDisplay, allDiscussionClose;
	private ArrayList<MessagesPanel> allMessagesUsers, allMessagesGroups;
	private MyNewMsgPanel newMsg;
	private MyJButton editNameGroup, closeDiscussion;
	private MyEditMsg editMsg;
	private int membersDisplay, usersDisplay;
	
	private boolean conversationOpen, alertOpen;
	
	public MessageWindow() throws Exception {
		setLayout(new BorderLayout());

		/* Add the menu bar */
		add(createMenuBar(), BorderLayout.NORTH);
		/* Add the app's body */
		add(createBodyApp(), BorderLayout.CENTER);
	}


	/**
	 * Create the menu bar of the MessageWindow.
	 * @return the MenuBar
	 * @throws IOException if <code>MyMenuBar()</code> has failed
	 * @see MyMenuBar
	 */
	private MyMenuBar createMenuBar() throws IOException  {	
		english = new JRadioButton("English");			
		menuBar = new MyMenuBar(english);
		return menuBar ;
	}

	/**
	 * Create the app's body.
	 * @return the body app.
	 * @throws UserNotConnectedException
	 * @throws IOException
	 * @throws Exception
	 * */
	private MyBodyApp createBodyApp() throws IOException, UserNotConnectedException, Exception  {
		bodyApp = new MyBodyApp(createDestinatairesContainer(), createDiscussionContainer());
		return bodyApp ;
	}

	/**
	 * Create the panel of the destinataires.
	 * @throws Exception 
	 * */
	private MyDestinataireContainer createDestinatairesContainer() throws Exception  {
		users = new MyUsersPanel(createListUsers());
		groups = new MyGroupsPanel(this,  createListGroups());
		destinataires = new MyDestinatairesPanel(users, groups);
		destinataireContainer = new MyDestinataireContainer(destinataires);
		return destinataireContainer ;
	}

	/**
	 * Create the panel of the discussion container.
	 * @throws IOException
	 * @throws UserNotConnectedException 
	 * */
	private MyDiscussionContainer createDiscussionContainer() throws IOException, UserNotConnectedException {
		northDiscussion = new JMenuBar();	
		nameDestinataire = new MyTitle("");
		
		Image editNameGroupImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/editNameGroup.png")).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		ImageIcon editNameGroupIcon = new ImageIcon(editNameGroupImage, "Edit the name group");
		Image editNameGroupImageHover =ImageIO.read(Clavardage.getResourceStream("/img/assets/editNameGroup.png")).getScaledInstance(18, 18, Image.SCALE_SMOOTH);
		ImageIcon editNameGroupIconHover = new ImageIcon(editNameGroupImageHover, "Edit the name group");
		editNameGroup = new MyJButton(editNameGroupIcon,editNameGroupIconHover);
		
		seeMembersGroup = new JMenu("See the members of the group");
		addMemberInGroup = new JMenu("Add Someone to the group");
		settingsGroups = new JMenu();
		
		Image closeDiscussionImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/closeDiscussion.png")).getScaledInstance(14, 14, Image.SCALE_SMOOTH);
		ImageIcon closeDiscussionIcon = new ImageIcon(closeDiscussionImage, "Close the current discussion");
		Image closeDiscussionImageHover =ImageIO.read(Clavardage.getResourceStream("/img/assets/closeDiscussion.png")).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		ImageIcon closeDiscussionIconHover = new ImageIcon(closeDiscussionImageHover, "Close the current discussion");
		closeDiscussion = new MyJButton(closeDiscussionIcon,closeDiscussionIconHover);
		
		discussion = new MyDiscussionPanel(northDiscussion, nameDestinataire, editNameGroup, settingsGroups, closeDiscussion, seeMembersGroup, addMemberInGroup,  createMsgPanel(), createNewMsgPanel(), groupsContainer);
						
		discussionContainer = new MyDiscussionContainer(discussion);

		return discussionContainer ;
	}

	/**
	 * Create the list of Users.
	 * @return JScrollPane usersContainer with the user list for viewport
	 * @throws Exception if UserNotConnectedException or if <code>addNewUserToList()</code> has failed
	 * @see UserNotConnectedException
	 * @see #addNewUserToList(User, Boolean)
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
	 * @return JScrollPane groupsContainer with the group list for viewport
	 * @throws UserNotConnectedException if <code>addNewUserToList()</code> has failed
	 * @throws IOException if <code>addNewUserToList()</code> has failed
	 * @see UserNotConnectedException
	 * @see #addNewUserToList(User, Boolean)
	 * */
	private MyJScrollPane createListGroups() throws IOException, UserNotConnectedException  {
		nbGroups = 0;

		listGroups = new MyListDestinataires();
		
		/* Records all groups and their associated discussion */
		allGroups = new ArrayList<DestinataireJPanel>();
		allMessagesGroups = new ArrayList<MessagesPanel>();
		addNewGroupToList("Clovordoge",true);
		addNewGroupToList("Je suis un groupe",true);
		addNewGroupToList("Les potos de la muerte del sol",true);
		addNewGroupToList("4IR A2 > 4IR A1",false);
		addNewGroupToList("Espionnage Industriel",false);
		addNewGroupToList("Salut c'est nous",false); //for the moment, all groups are new and there is no conversation (except one, see later)
		
		groupsContainer = new MyJScrollPane(listGroups);
		groupsContainer.getVerticalScrollBar().setValue(0);

		return groupsContainer ;
	}

	/**
	 * Create the panel of new message of the MessageWindow.
	 * @return the MyNewMsgPanel
	 * @throws IOException if <code>MyNewMsgPanel()</code> has failed
	 * @see MyNewMsgPanel
	 * */
	private MyNewMsgPanel createNewMsgPanel() throws IOException{		
		editMsg = new MyEditMsg();
		newMsg = new MyNewMsgPanel(editMsg);	
		return newMsg ;
	}
	


	/**
	 * Create the panel of messages.
	 * @return JScrollPane messageContainer with allDiscussionClose for viewport
	 * */
	private MyJScrollPane createMsgPanel(){
		conversationOpen = false ;
		alertOpen = false;
		
		allDiscussionClose = new MessagesPanel();
		
		/* SOME TEST */
		allMessagesGroups.get(1).startConversation();
		MessageBuble msg1 = new MessageBuble(TypeBuble.THEIR,"Ceci est la largeur maximum d'un message, elle fait environ 2/3 de la zone de dialogue.  Il n'y a pas de longueur maximum pour un message. Ceci est la largeur maximum d'un message, elle fait environ 2/3 de la zone de dialogue.  Il n'y a pas de longueur maximum pour un message. Ceci est la largeur maximum d'un message, elle fait environ 2/3 de la zone de dialogue.  Il n'y a pas de longueur maximum pour un message. Ceci est la largeur maximum d'un message, elle fait environ 2/3 de la zone de dialogue.  Il n'y a pas de longueur maximum pour un message.", new MyDate(1640038517402L));
		MessageBuble msg2 = new MessageBuble(TypeBuble.MINE,"Ceci est la largeur maximum d'un message, elle fait environ 2/3 de la zone de dialogue.  Il n'y a pas de longueur maximum pour un message.", new MyDate(1640039918402L));
		MessageBuble msg3 = new MessageBuble(TypeBuble.THEIR,"Hello, comment tu vas mega super bien ?", new MyDate(1640048519402L));
		msg2.setColorPanel();
		allMessagesGroups.get(1).add(new MyDayInfo(new MyDate(1640038517402L)));
		allMessagesGroups.get(1).add(msg1);
		allMessagesGroups.get(1).add(msg2);
		allMessagesGroups.get(1).add(new MyDayInfo(new MyDate(1640048519402L)));
		allMessagesGroups.get(1).add(msg3);
		
		if (allUsers.size() > 0) {
			allMessagesGroups.get(1).addMemberConversation(allUsers.get(0).getIdDestinataire());
		}
		/* **** **** */
		
		messageContainer = new MyJScrollPane(allDiscussionClose);
		discussionDisplay = allDiscussionClose; //save the displayed discussion
		return messageContainer;
	}
	
	/**
	 * Add a new user to the list of users, display it and create his MessagePanel.
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
		} catch (Exception ex ){}
		MyAlertMessage startConversation = new MyAlertMessage("Start the conversation with " + userInDb.getLogin() + " : send a message ! :)");
		conversation.add(startConversation);
		if (!(idConvInDb == null)) {
			ArrayList<MessageBuble> allMessagesConv = MainGUI.getAllMessagesFrom(idConvInDb);
			if (!allMessagesConv.isEmpty()) {
				conversation.startConversation();
				for (int i = allMessagesConv.size()-1; i>=0; i--) {
					/* see if we need a DayPanel */
					MyDate date = allMessagesConv.get(i).getDate();
					boolean newDay = false ;
					if (i==0) {newDay=true;} else {newDay = ActionSendMessage.needDayPanel(date, conversation);}
					MyDayInfo day = new MyDayInfo(date);
					if (newDay) {
						conversation.add(day);  //we add the DayPanel if we need it
					}
					conversation.add(allMessagesConv.get(i));
				}
			}
		}
		
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
		MyAlertMessage startConversation = new MyAlertMessage("Start the conversation on " + name + " : send a message ! :)");
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
	
	/**
	 * Find a DestinataireJPanel based on the UUID of an user
	 * @param id UUID of the user
	 * @return the DestinataireJPanel of the user
	 * @see DestinataireJPanel
	 */
	public DestinataireJPanel findMyDestinataireJPanel(UUID id) {
		DestinataireJPanel myDestinataireJPanel = null ;
		for (DestinataireJPanel user : allUsers) {
			if (user.getIdDestinataire().equals(id)) {
				myDestinataireJPanel = user;
			}
		}
		return myDestinataireJPanel;
	}
	
	/**
	 * Find a User based on the UUID of an user
	 * @param id UUID of the user
	 * @return the User of the user
	 * @see User
	 */
	public User findMyUser(UUID id) throws UserNotConnectedException {
		User myUser = null ;
		for (User user : MainGUI.getAllUsersInDatabase()) {
			if (user.getUUID().equals(id)) {
				myUser = user;
			}
		}
		return myUser;
	}
	
	/* --------- GETTER AND SETTER ----------- */
	public boolean isConversationOpen() {return conversationOpen;}
	public boolean isAlertOpen() {return alertOpen;}
	public MyMenuBar getMenuBar() {return menuBar;}
	public JRadioButton getEnglish() {return english;}
	public MyBodyApp getBodyApp() {return bodyApp;}
	public MyDestinatairesPanel getDestinataires() {return destinataires;}
	public MyJScrollPane getUsersContainer() {return usersContainer;}
	public MyJScrollPane getGroupsContainer() {return groupsContainer;}
	public MyDiscussionPanel getDiscussion() {return discussion;}
	public JMenuBar getNorthDiscussion() {return northDiscussion;}
	public MyTitle getNameDestinataire() {return nameDestinataire;}
	public MyJButton getCloseDiscussion() {return closeDiscussion;}
	public MyNewMsgPanel getNewMsg() {return newMsg;}
	public MyEditMsg getEditMsg() {return editMsg;}
	public MessagesPanel getAllDiscussionClose() {return allDiscussionClose;}
	public MyListDestinataires getListUsers() {return listUsers;}
	public MyListDestinataires getListGroups() {return	listGroups;}
	public ArrayList<DestinataireJPanel> getAllUsers() {return	allUsers;}
	public ArrayList<DestinataireJPanel> getAllGroups() {return	allGroups;}
	public ArrayList<MessagesPanel> getAllMessagesUsers() {return allMessagesUsers;}
	public ArrayList<MessagesPanel> getAllMessagesGroups() {return allMessagesGroups;}
	public MessagesPanel getDiscussionDisplay() {return discussionDisplay;}
	public MyJScrollPane getMessageContainer() {return messageContainer;}
	public JMenu getAddMemberInGroup() {return addMemberInGroup;}
	public JMenu getSettingsGroups() {return settingsGroups;}
	public int getUsersDisplay() {return usersDisplay;}
	public JMenuItem getBackUsers() {return backUsers;}
	public JMenuItem getNextUsers() {return nextUsers;}
	public int getMembersDisplay() {return membersDisplay;}
	public JMenuItem getBackMembers() {return backMembers;}
	public JMenuItem getNextMembers() {return nextMembers;}
	public JMenu getSeeMembersGroup() {return seeMembersGroup;}
	public MyJButton getEditNameGroup() {return editNameGroup;}
	public void setConversationOpen(boolean conversationOpen) {this.conversationOpen = conversationOpen;}
	public void setAlertOpen(boolean alertOpen) {this.alertOpen = alertOpen;}
	public void setDiscussionDisplay(MessagesPanel discussionDisplay) {	this.discussionDisplay = discussionDisplay;}
	public void setNameDestinataire(String name) {nameDestinataire.setText(name);}
	public void setUsersDisplay(int usersDisplay) {this.usersDisplay = usersDisplay;}
	public void setNextUsers(JMenuItem nextUsers) {this.nextUsers = nextUsers;}
	public void setBackUsers(JMenuItem backUsers) {this.backUsers = backUsers;}
	public void setMembersDisplay(int membersDisplay) {this.membersDisplay = membersDisplay;}
	public void setBackMembers(JMenuItem backMembers) {this.backMembers = backMembers;}
	public void setNextMembers(JMenuItem nextMembers) {this.nextMembers = nextMembers;}
	public void setDisplay(int mode, int i) {if (mode == 0) {usersDisplay=i;} else if (mode == 1) {membersDisplay=i;}}
}
