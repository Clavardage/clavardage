package clavardage.view.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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
import javax.swing.border.EmptyBorder;

import clavardage.controller.Clavardage;
import clavardage.controller.authentification.AuthOperations;
import clavardage.controller.gui.MainGUI;
import clavardage.model.exceptions.UserNotConnectedException;
import clavardage.view.main.Application.ColorThemeApp;
import javax.swing.JMenuItem;

public class Message extends JPanel implements ActionListener, MouseListener {

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
	private JTextArea nameDestinataire;
	private JScrollPane msg;
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
		customThemeMessage(Application.ColorThemeApp.LIGHT);
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
		logo.setOpaque(false);
		logo.setBorder(null);
		logo.setIcon(logoIcon);
		logoPanel.add(logo);

		settingsImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/Settings.png")).getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		settingsIcon = new ImageIcon(settingsImage, "Setting menu");
		settings = new JMenu();
		settings.setOpaque(false);
		settings.setBorder(null);
		settings.setIcon(settingsIcon);
		menuBar.add(settings);

		colorApp = new JMenu("Change the default color");
		colorApp.setOpaque(false);
		colorApp.setBorder(null);
		settings.add(colorApp);

		allColors = new ButtonGroup();

		colorAppWhite = new JRadioButton("White");
		colorAppWhite.setSelected(true);
		colorAppWhite.setMnemonic(KeyEvent.VK_W);
		colorAppWhite.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Application.setColorTheme(ColorThemeApp.LIGHT);
			}
		});
		allColors.add(colorAppWhite);
		colorApp.add(colorAppWhite);

		colorAppBlack = new JRadioButton("Black");
		colorAppBlack.setMnemonic(KeyEvent.VK_B);
		colorAppBlack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Application.setColorTheme(ColorThemeApp.DARK);
			}
		});
		allColors.add(colorAppBlack);
		colorApp.add(colorAppBlack);

		//		settings.addSeparator();

		accountImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/Account.png")).getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		accountIcon = new ImageIcon(accountImage, "Account menu");
		account = new JMenu();
		account.setOpaque(false);
		account.setBorder(null);
		account.setIcon(accountIcon);
		menuBar.add(account);
		
		disconnect = new JMenuItem("Disconnect");
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
		/*Ajout Java FX pour l'ombre ?*/

		nameDestinataire = new JTextArea("Michel");
		customTitle(nameDestinataire);
		discussion.add(nameDestinataire, BorderLayout.NORTH);

		messages = new JPanel();
		msg = new JScrollPane(messages);
		msg.setOpaque(false);
		msg.setBorder(new EmptyBorder(0, 30, 0, 0));
		discussion.add(msg);

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
		editMsg.addMouseListener( new  MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				if (((MyRoundJTextField) editMsg).isEmpty()) {
					editMsg.setText("");
					editMsg.setForeground(Application.COLOR_TEXT);
					((MyRoundJTextField) editMsg).setEmpty(false);
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
		newMsg.add(sendMsg);

		return newMsg ;
	}

	/**
	 * Create the panel of messages.
	 * @throws IOException
	 * */
	private JScrollPane createMsgPanel() throws IOException{
		msg = new JScrollPane();
		return msg;
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
		if (c == ColorThemeApp.LIGHT) {
			Application.COLOR_BACKGROUND = new Color(247,249,251) ;
			Application.COLOR_BACKGROUND2 = new Color(255,255,255) ;
			Application.COLOR_EDIT_MESSAGE = new Color(237,237,237) ;
			Application.COLOR_SCROLL_BAR = new Color(241,242,243) ;
			Application.COLOR_CURSOR_SCROLL = new Color(219,219,219) ;
			Application.COLOR_CURSOR_SCROLL_HOVER = new Color(201,201,201) ;
			Application.COLOR_OUR_MESSAGE = new Color(212,212,212) ;
			Application.COLOR_SHADOW = new Color(165,165,165,50) ;
			Application.COLOR_TEXT = new Color(0,0,0);
			Application.COLOR_TEXT_EDIT = new Color(127,127,127);
		} else if (c == ColorThemeApp.DARK) {
			Application.COLOR_BACKGROUND = new Color(20,16,12) ;
			Application.COLOR_BACKGROUND2 = new Color(0,0,0) ;
			Application.COLOR_EDIT_MESSAGE = new Color(18,18,18) ;
			Application.COLOR_SCROLL_BAR = new Color(14,13,12) ;
			Application.COLOR_CURSOR_SCROLL = new Color(36,36,36) ;
			Application.COLOR_CURSOR_SCROLL_HOVER = new Color(54,54,54) ;
			Application.COLOR_OUR_MESSAGE = new Color(43,43,43) ;
			Application.COLOR_SHADOW = new Color(165,165,165,50) ;
			Application.COLOR_TEXT = new Color (217,217,217);
			Application.COLOR_TEXT_EDIT = new Color(127,127,127);
		}
		this.setBackground(Application.COLOR_BACKGROUND);

		/* ** Menu Bar ** */
		menuBar.setBackground(Application.COLOR_BACKGROUND2);

		/* ** Body's App ** */
		bodyApp.setBackground(Application.COLOR_BACKGROUND);
		// -- Destinataires -- //
		listUsers.setBackground(Application.COLOR_BACKGROUND);
		for (Component panel : listUsers.getComponents()) {
			((DestinataireJPanel) panel).setForegroundNamePanel(Application.COLOR_TEXT);
		}
		usersContainer.getVerticalScrollBar().setBackground(Application.COLOR_BACKGROUND);
		listGroups.setBackground(Application.COLOR_BACKGROUND);
		for (Component panel : listGroups.getComponents()) {
			((DestinataireJPanel) panel).setForegroundNamePanel(Application.COLOR_TEXT);
		}
		groupsContainer.getVerticalScrollBar().setBackground(Application.COLOR_BACKGROUND); 
		// -- Discussion -- //
		discussion.setBackground(Application.COLOR_BACKGROUND2);
		messages.setBackground(Application.COLOR_BACKGROUND2);
		editMsg.setBackground(Application.COLOR_EDIT_MESSAGE);
		editMsg.setForeground(Application.COLOR_TEXT_EDIT);
	}

	/**
	 * Add a group to the list of groups.
	 * @throws IOException 
	 * */
	private void addGroupToList(String name, Boolean connect) throws IOException {
		DestinataireJPanel group = new DestinataireJPanel(name,++nbGroups,connect,Destinataire.Group,this);
		listGroups.add(group);
		listGroups.setPreferredSize(new Dimension(0, 30*nbGroups));
		group.setForegroundNamePanel(Application.COLOR_TEXT);

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
	
	/* --------- GET and SETTER ----------- */

	public void setNameDestinataire(String newDestinataire) {
		nameDestinataire.setText(newDestinataire);
	}

	/* --------- GLOBAL LISTENERS ----------- */

	@Override
	public void mouseClicked(MouseEvent e) {		
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