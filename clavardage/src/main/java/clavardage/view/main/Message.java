package clavardage.view.main;

/* TODO
 * -button hover à centrer
 * -editMsg : si on clique autre par et que getText().isEmpty() ou isBlanck() alors on revient sur le truc initial
 * -rajouter heure des messages devant les buble + noms des expediters dans les conversataions de groupes
 * -rond bleu pour signifier conversation ouverte + mettre la conversation en haut
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

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

import clavardage.controller.Clavardage;
import clavardage.controller.authentification.AuthOperations;
import clavardage.controller.gui.MainGUI;
import clavardage.model.exceptions.UserNotConnectedException;
import clavardage.view.main.Application.ColorThemeApp;
import clavardage.view.main.Login.TypeBuble;

import javax.swing.JMenuItem;

public class Message extends JPanel implements ActionListener, MouseListener {

	private boolean conversationOpen ;
	
	/* ** Menu Bar ** */
	private JMenuBar menuBar;
	private JPanel logoPanel;
	private JLabel logo;
	private JMenu settings, colorApp, account;
	private ButtonGroup allColors;
	private JRadioButton colorAppWhite, colorAppBlack;

	/* ** Body's App ** */
	private JPanel bodyApp;
	private GridBagLayout gbl_bodyApp;
	private GridBagConstraints gbc_destinataires, gbc_discussionContainer;
	private JPanel destinataires, discussionContainer;
	// -- Destinataires -- //
	private JPanel users, groups, northGroups;
	private GridBagLayout gbl_northGroups;
	private GridBagConstraints gbc_titleGroups, gbc_addGroup;
	private JTextArea titleUsers, titleGroups;
	private JButton addGroup;
	private JScrollPane usersContainer, groupsContainer;
	private JPanel listUsers, listGroups ;
	private int nbUsers, nbGroups;
	enum Destinataire {User,Group;}
	// -- Discussion -- //
	private JPanel discussion, messages, newMsg;
	private JLabel chooseDestinataire;
	private JTextArea nameDestinataire;
	private JScrollPane messageContainer;
	private JButton sendFile, sendPicture, sendMsg;
	private JTextField editMsg;

	/* ** Pictures and Icons ** */
	private Image logoImage, settingsImage, accountImage, addGroupImage, addGroupImageHover, sendFileImage, sendFileImageHover, sendPictureImage, sendPictureImageHover, sendMsgImage, sendMsgImageHover;
	private ImageIcon logoIcon, settingsIcon, accountIcon, addGroupIcon, addGroupIconHover,sendFileIcon, sendFileIconHover, sendPictureIcon, sendPictureIconHover, sendMsgIcon, sendMsgIconHover;
	private JMenuItem disconnect;



	public Message() throws IOException, UserNotConnectedException {
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
		menuBar = new JMenuBar();
		menuBar.setBorder(new EmptyBorder(0, 20, 0, 10));
		menuBar.setPreferredSize(new Dimension(0, 50));
		menuBar.setBorderPainted(false);

		logoPanel = new JPanel();
		logoPanel.setOpaque(false);
		logoPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		logoPanel.setPreferredSize(new Dimension(0, 40));
		logoPanel.setLayout(new GridLayout(0, 1, 0, 0));
		menuBar.add(logoPanel);

		logoImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/Logo_title.png")).getScaledInstance(130, 33, Image.SCALE_SMOOTH);
		logoIcon = new ImageIcon(logoImage, "logo");
		logo = new JLabel();
		logo.setBorder(null);
		logo.setIcon(logoIcon);
		logoPanel.add(logo);

		settingsImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/Settings.png")).getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		settingsIcon = new ImageIcon(settingsImage, "Setting menu");
		settings = new JMenu();
		settings.setBorder(null);
		settings.setCursor(new Cursor(Cursor.HAND_CURSOR));
		settings.setIcon(settingsIcon);
		menuBar.add(settings);

		colorApp = new JMenu("Change the default color");
		colorApp.setBorder(null);
		colorApp.setFocusPainted(false);

		colorApp.setCursor(new Cursor(Cursor.HAND_CURSOR));

		settings.add(colorApp);

		allColors = new ButtonGroup();

		colorAppWhite = new JRadioButton("White");
		colorAppWhite.setFocusPainted(false);
		colorAppWhite.setSelected(true);
		colorAppWhite.setCursor(new Cursor(Cursor.HAND_CURSOR));
		colorAppWhite.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Application.setColorTheme(ColorThemeApp.LIGHT);
			}
		});
		allColors.add(colorAppWhite);
		colorApp.add(colorAppWhite);

		colorAppBlack = new JRadioButton("Black");
		colorAppBlack.setFocusPainted(false);
		colorAppBlack.setCursor(new Cursor(Cursor.HAND_CURSOR));
		colorAppBlack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Application.setColorTheme(ColorThemeApp.DARK);
			}
		});
		allColors.add(colorAppBlack);
		colorApp.add(colorAppBlack);


		accountImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/Account.png")).getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		accountIcon = new ImageIcon(accountImage, "Account menu");
		account = new JMenu();
		account.setBorder(null);
		account.setIcon(accountIcon);
		account.setCursor(new Cursor(Cursor.HAND_CURSOR));
		menuBar.add(account);
		
		disconnect = new JMenuItem("Disconnect");
		disconnect.setCursor(new Cursor(Cursor.HAND_CURSOR));
		disconnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AuthOperations.disconnectUser();
				if(!AuthOperations.isUserConnected()) {
					System.out.println("DISCONNECTED!!");
					Application.displayContent(Application.getApp(), Application.getLoginWindow());
				}
			}
		});
		account.add(disconnect);
		
		
		

		return menuBar ;
	}

	/**
	 * Create the app's body.
	 * @throws IOException
	 * @throws UserNotConnectedException
	 * */
	private JPanel createBodyApp() throws IOException, UserNotConnectedException {
		bodyApp = new JPanel();

		/* Set the GridBagLayout */
		gbl_bodyApp = new GridBagLayout();
		gbl_bodyApp.columnWidths = new int[]{30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 0};
		gbl_bodyApp.rowHeights = new int[]{100};
		gbl_bodyApp.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_bodyApp.rowWeights = new double[]{1.0};
		bodyApp.setLayout(gbl_bodyApp);

		/* Add Destinataires with good constraints */
		gbc_destinataires = new GridBagConstraints();
		gbc_destinataires.insets = new Insets(0, 0, 0, 5);
		gbc_destinataires.fill = GridBagConstraints.BOTH;
		gbc_destinataires.gridwidth = 3;
		gbc_destinataires.gridx = 0;
		gbc_destinataires.gridy = 0;
		bodyApp.add(createDestinatairesPanel(), gbc_destinataires);

		/* Add Discussion with good constraints */
		gbc_discussionContainer = new GridBagConstraints();
		gbc_discussionContainer.fill = GridBagConstraints.BOTH;
		gbc_discussionContainer.gridwidth = 7;
		gbc_discussionContainer.gridx = 3;
		gbc_discussionContainer.gridy = 0;
		bodyApp.add(createDiscussionContainerPanel(), gbc_discussionContainer);

		return bodyApp ;
	}

	/**
	 * Create the panel of the destinataires.
	 * @throws IOException
	 * @throws UserNotConnectedException
	 * */
	private JPanel createDestinatairesPanel() throws IOException, UserNotConnectedException {
		destinataires = new JPanel();
		destinataires.setOpaque(false);
		destinataires.setLayout(new GridLayout(2, 1, 0, 0));

		users = new JPanel();
		users.setOpaque(false);
		destinataires.add(users);
		users.setLayout(new BorderLayout(0, 0));

		titleUsers = new JTextArea("Users");
		customTitle(titleUsers);
		users.add(titleUsers, BorderLayout.NORTH);

		users.add(createListUsers());

		groups = new JPanel();
		groups.setOpaque(false);
		destinataires.add(groups);
		groups.setLayout(new BorderLayout(0, 0));

		northGroups = new JPanel();
		northGroups.setOpaque(false);
		groups.add(northGroups, BorderLayout.NORTH);
		gbl_northGroups = new GridBagLayout();
		gbl_northGroups.columnWidths = new int[]{10, 10, 10, 10, 10, 10, 10, 10, 0};
		gbl_northGroups.rowHeights = new int[]{21};
		gbl_northGroups.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_northGroups.rowWeights = new double[]{0.0};
		northGroups.setLayout(gbl_northGroups);

		titleGroups = new JTextArea("Groups");
		customTitle(titleGroups);
		gbc_titleGroups = new GridBagConstraints();
		gbc_titleGroups.gridwidth = 7;
		gbc_titleGroups.fill = GridBagConstraints.BOTH;
		gbc_titleGroups.gridx = 0;
		gbc_titleGroups.gridy = 0;
		northGroups.add(titleGroups, gbc_titleGroups);

		addGroupImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/addGroups.png")).getScaledInstance(13, 13, Image.SCALE_SMOOTH);
		addGroupIcon = new ImageIcon(addGroupImage, "Add Group Button");
		addGroupImageHover =ImageIO.read(Clavardage.getResourceStream("/img/assets/addGroups.png")).getScaledInstance(15, 15, Image.SCALE_SMOOTH);
		addGroupIconHover = new ImageIcon(addGroupImageHover, "Add Group Button Hover");
		addGroup = new JButton();
		customButton(addGroup,addGroupIcon,addGroupIconHover);
		addGroup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("test add group");
				try {
					addGroupToList("New Group", true);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				groupsContainer.validate();
			}
		});
		gbc_addGroup = new GridBagConstraints();
		gbc_addGroup.fill = GridBagConstraints.BOTH;
		gbc_addGroup.gridx = 7;
		gbc_addGroup.gridy = 0;
		northGroups.add(addGroup, gbc_addGroup);

		groups.add(createListGroups());

		return destinataires ;
	}

	/**
	 * Create the panel of the discussion container.
	 * @throws IOException
	 * */
	private JPanel createDiscussionContainerPanel() throws IOException {
		discussionContainer = new JPanel();
		discussionContainer.setOpaque(false);
		discussionContainer.setBorder(new EmptyBorder(15, 15, 15, 15));
		discussionContainer.setLayout(new GridLayout(1, 0, 0, 0));

		discussion = new MyRoundJPanel(30);
		discussion.setBorder(new EmptyBorder(0, 10, 0, 10));
		discussion.setLayout(new BorderLayout(0, 0));
		discussionContainer.add(discussion);

		nameDestinataire = new JTextArea();
		
		/*In first, there is no conversation*/
		nameDestinataire.setText("");
		customTitle(nameDestinataire);
		discussion.add(nameDestinataire, BorderLayout.NORTH);
			
		discussion.add(createMsgPanel(), BorderLayout.CENTER);
		
		discussion.add(createNewMsgPanel(), BorderLayout.SOUTH);

		return discussionContainer ;
	}

	/**
	 * Create the list of Users.
	 * @throws IOException
	 * @throws UserNotConnectedException
	 * */
	private JScrollPane createListUsers() throws IOException, UserNotConnectedException {
		nbUsers = 0;

		listUsers = new JPanel();
		listUsers.setBorder(new EmptyBorder(0, 20, 10, 20));
		listUsers.setLayout(new BoxLayout(listUsers, BoxLayout.Y_AXIS));

		for (String name : MainGUI.getAllUsernamesInDatabase()) {
			addUserToList(name, true);
		}
		addUserToList("Julien", true);
		addUserToList("Micheline", true);
		addUserToList("Micheline", false);
		addUserToList("Theodore", false);
		addUserToList("Loic", true);
		addUserToList("Arthur", true);
		addUserToList("Fantine", false);

		usersContainer = new JScrollPane(listUsers);
		usersContainer.getVerticalScrollBar().setUI(new MyJScrollBarUI(Application.COLOR_BACKGROUND, Application.COLOR_SCROLL_BAR, Application.COLOR_CURSOR_SCROLL, Application.COLOR_CURSOR_SCROLL_HOVER));
		usersContainer.setBorder(null);
		usersContainer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		return usersContainer ;
	}

	/**
	 * Create the list of Groups.
	 * @throws IOException
	 * */
	private JScrollPane createListGroups() throws IOException {
		nbGroups = 0;

		listGroups = new JPanel();
		listGroups.setBorder(new EmptyBorder(0, 20, 10, 20));
		listGroups.setLayout(new BoxLayout(listGroups, BoxLayout.Y_AXIS));

		addGroupToList("Clovordoge", true);
		addGroupToList("Les potos", true);
		addGroupToList("Salut c'est nous", false);
		addGroupToList("4IR A2 > 4IR A1", false);
		addGroupToList("Je suis un groupe", true);
		addGroupToList("Espionnage Industriel", false);

		groupsContainer = new JScrollPane();
		groupsContainer.getVerticalScrollBar().setUI(new MyJScrollBarUI(Application.COLOR_BACKGROUND, Application.COLOR_SCROLL_BAR, Application.COLOR_CURSOR_SCROLL, Application.COLOR_CURSOR_SCROLL_HOVER));
		groupsContainer.setBorder(null);
		groupsContainer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		groupsContainer.setViewportView(listGroups);

		return groupsContainer ;
	}

	/**
	 * Create the panel of new message.
	 * @throws IOException
	 * */
	private JPanel createNewMsgPanel() throws IOException{
		newMsg = new JPanel();
		newMsg.setOpaque(false);
		newMsg.setBorder(new EmptyBorder(10, 30, 10, 30));
		newMsg.setLayout(new BoxLayout(newMsg, BoxLayout.X_AXIS));


		sendFileImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/sendFile.png")).getScaledInstance(14, 21, Image.SCALE_SMOOTH);
		sendFileIcon = new ImageIcon(sendFileImage, "Send File Button");
		sendFileImageHover =ImageIO.read(Clavardage.getResourceStream("/img/assets/sendFile.png")).getScaledInstance(16, 24, Image.SCALE_SMOOTH);
		sendFileIconHover = new ImageIcon(sendFileImageHover, "Send File Button Hover");
		sendFile = new JButton();
		customButton(sendFile,sendFileIcon,sendFileIconHover);
		newMsg.add(sendFile);

		newMsg.add(createMargin(10,0));

		sendPictureImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/sendPicture.png")).getScaledInstance(14, 21, Image.SCALE_SMOOTH);
		sendPictureIcon = new ImageIcon(sendPictureImage, "Send Picture Button");
		sendPictureImageHover =ImageIO.read(Clavardage.getResourceStream("/img/assets/sendPicture.png")).getScaledInstance(16, 24, Image.SCALE_SMOOTH);
		sendPictureIconHover = new ImageIcon(sendPictureImageHover, "Send Picture Button Hover");
		sendPicture = new JButton();
		customButton(sendPicture,sendPictureIcon,sendPictureIconHover);
		newMsg.add(sendPicture);

		newMsg.add(createMargin(10,0));

		editMsg = new MyRoundJTextField(30);
		editMsg.setText("Hello...");
		editMsg.setMargin(new Insets(0, 10, 0, 10));
		editMsg.addMouseListener(this);
		editMsg.addKeyListener((KeyListener) new KeyAdapter() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	            if(e.getKeyCode() == KeyEvent.VK_ENTER){
	                sendMessage(0);
	            }
	        }

	    });
		newMsg.add(editMsg);

		newMsg.add(createMargin(10,0));

		sendMsgImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/sendMsg.png")).getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		sendMsgIcon = new ImageIcon(sendMsgImage, "Send Msg Button");
		sendMsgImageHover =ImageIO.read(Clavardage.getResourceStream("/img/assets/sendMsg.png")).getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		sendMsgIconHover = new ImageIcon(sendMsgImageHover, "Send Msg Button Hover");
		sendMsg = new JButton();
		customButton(sendMsg,sendMsgIcon,sendMsgIconHover);
		sendMsg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                sendMessage(1);
			}
		});
		newMsg.add(sendMsg);
		
		newMsg.setVisible(false);
		
		return newMsg ;
	}
	


	/**
	 * Create the panel of messages.
	 * @throws IOException
	 * */
	private JScrollPane createMsgPanel() throws IOException{
		conversationOpen = false ;
		
		messages = new JPanel();
		messages.setBorder(new EmptyBorder(0, 30, 0, 0));
		messages.setLayout(new GridBagLayout());
		
		nameDestinataire.setText("");
		chooseDestinataire = new JLabel("Choose someone to start a conversation...", SwingConstants.CENTER);
		chooseDestinataire.setFont(new Font("Tahoma", Font.PLAIN, 24));
		chooseDestinataire.setForeground(Application.COLOR_BLUE);
		
		messages.add(chooseDestinataire);
			
		messageContainer = new JScrollPane(messages);
		messageContainer.getVerticalScrollBar().setUI(new MyJScrollBarUI(Application.COLOR_BACKGROUND, Application.COLOR_SCROLL_BAR, Application.COLOR_CURSOR_SCROLL, Application.COLOR_CURSOR_SCROLL_HOVER));
		messageContainer.setBorder(null);
		messageContainer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		
		return messageContainer;
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


	/**
	 * Custom title.
	 * */
	private void customTitle(JTextArea title) {
		title.setOpaque(false);
		//  title.setCaretColor(bg);
		title.setForeground(Application.COLOR_BLUE);
		title.setFont(new Font("Dialog", Font.PLAIN, 16));
		title.setBorder(new EmptyBorder(10, 15, 10, 15));
		title.setEditable(false);
		title.setHighlighter(null);
	}

	/**
	 * Custom button.
	 * */
	private void customButton(JButton button, ImageIcon icon, ImageIcon iconHover) {
		button.setOpaque(false);
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setIgnoreRepaint(true);
		button.setContentAreaFilled(false);
		button.setIcon(icon);
		button.setRolloverIcon(iconHover);
		button.setPreferredSize(new Dimension(button.getIcon().getIconWidth(),button.getIcon().getIconHeight()));
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
		messages.setBackground(Application.COLOR_BACKGROUND2);
		
		editMsg.setBackground(Application.COLOR_EDIT_MESSAGE);
		if (((MyRoundJTextField) editMsg).isEmptyText()) {
			editMsg.setForeground(Application.COLOR_TEXT_EDIT);
		} else {
			editMsg.setForeground(Application.COLOR_TEXT);
		}
		
		messages.setBackground(Application.COLOR_BACKGROUND2);
		for (Component panel : messages.getComponents()) {
			if (panel.getClass().getName() == "clavardage.view.main.MessageBuble") {
				((MessageBuble) panel).setColorPanel();
			}
		}
		messageContainer.getVerticalScrollBar().setBackground(Application.COLOR_BACKGROUND2); 


	}

	/**
	 * Add a group to the list of groups.
	 * @throws IOException 
	 * */
	private void addGroupToList(String name, Boolean connect) throws IOException {
		DestinataireJPanel group = new DestinataireJPanel(name,++nbGroups,connect,Destinataire.Group,this);
		listGroups.add(group);
		listGroups.setPreferredSize(new Dimension(0, 30*nbGroups));
		group.setForegroundNamePanel();

	}

	/**
	 * Add an user to the list of users.
	 * @throws IOException 
	 * */
	private void addUserToList(String name, Boolean connect) throws IOException {
		/*La Bdd a tous les users à jour (sauf celui là)*/

		/*Supprime Tous les élement de listUsers*/
		//		listUsers.removeAll();

		/*Ajoute le nouvel élement dans la BDD*/

		/*Trie la BDD*/

		/*Rajoute tous les éléments dans listUser*/
		//        for (String names : MainGUI.getUsernames()) {
		//        	listGroups.add(new DestinataireJPanel(names,++nbGroups,connect,Destinataire.Group,this));
		//        	listGroups.setPreferredSize(new Dimension(0, 30*nbGroups));
		//        }

		listUsers.add(new DestinataireJPanel(name,++nbUsers,connect,Destinataire.User,this));
		listUsers.setPreferredSize(new Dimension(0, 30*nbUsers));
	}

	/**
	 * Remove a group to the list of groups.
	 * */
	private void removeDestinataireOfList(JPanel destinataire, JList<JPanel> list) {
	}
	
	/**
	 * Display the new message on the discussion panel and reset JTextField.
	 * */
	private void sendMessage(int mode) {
		if   ( (!((MyRoundJTextField) editMsg).isEmptyText()) & (!(editMsg.getText().isEmpty() | editMsg.getText().isBlank())) )   {
			MessageBuble msg = new MessageBuble(TypeBuble.MINE,editMsg.getText());
			msg.setColorPanel();
			messages.add(msg);
			messageContainer.validate();
			if (mode == 1) {
				((MyRoundJTextField) editMsg).setEmptyText(true);
			} else {
				editMsg.setText("");
			}
		}	
	}
	
	/* --------- GET and SETTER ----------- */
	
	public void openConversation(String newDestinataire) {
		if (!conversationOpen) {
			conversationOpen = true;
			
			messages.remove(chooseDestinataire);
			messages.setLayout(new BoxLayout(messages, BoxLayout.Y_AXIS));
			
			MessageBuble msg1 = new MessageBuble(TypeBuble.THEIR,"Ceci est la largeur maximum d'un message, elle fait environ 2/3 de la zone de dialogue.  Il n'y a pas de longueur maximum pour un message.");
			MessageBuble msg2 = new MessageBuble(TypeBuble.MINE,"Ceci est la largeur maximum d'un message, elle fait environ 2/3 de la zone de dialogue.  Il n'y a pas de longueur maximum pour un message.");
			MessageBuble msg3 = new MessageBuble(TypeBuble.THEIR,"Hello, comment tu vas mega super bien ?");
			msg2.setColorPanel();
			messages.add(msg1);
			messages.add(msg2);
			messages.add(msg3);
			
			newMsg.setVisible(true);
		}
		nameDestinataire.setText(newDestinataire);
		((MyRoundJTextField) editMsg).setEmptyText(true);	
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
