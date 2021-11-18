package clavardage.view.main;

/* TODO
 * -ombre avec JavaFX sur discussion et menuBar
 * -changer couleur texte dans customThemeApp (ajouter COLOR_TEXT et COLOR_TITLE ?)
 * -dans editMsg : -ajouter petite marge à gauche
 * 				   -modifier couleur Hello... mais si on écrit dedans, le texte est quand même noir
 * 				   -faire en sorte que Hello... disparaisse quand on clique dans le JTextField
 * -Bouton Hover centré et pas sur le côté 
 * -changer design MenuItem
 * -listUsers et list Group : cliquer sur la zone du JText Area n'active pas le mouseClicked du DestinataireJPanel, il faut changer ça 
 * -mettre à jour la list de group directement après avoir appuyer sur le bouton (pour le moment, il faut cliquer dedans pour refresh)
 * -pouvoir organiser les JPanel dans l'ordre alphabatique des noms + tout les connecté d'abord
 * -enlever le static des COLORS (pour MyJScrollBarUI)
 */

import clavardage.controller.Clavardage;
import clavardage.controller.gui.MainGUI;
import clavardage.model.exceptions.UserNotConnectedException;

import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.io.IOException;

public class Application extends JFrame implements ActionListener, MouseListener {

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

	/* ** Colors ** */
	enum ColorThemeApp {LIGHT, DARK;}
	protected static Color COLOR_BACKGROUND ;
	protected static Color COLOR_BACKGROUND2 ;
    protected static Color COLOR_EDIT_MESSAGE ;
    protected static Color COLOR_SCROLL_BAR ;
    protected static Color COLOR_CURSOR_SCROLL ;
    protected static Color COLOR_CURSOR_SCROLL_HOVER ;
    protected static Color COLOR_OUR_MESSAGE ;
    protected static Color COLOR_SHADOW ;
    protected static Color COLOR_BLUE = new Color(72,125,244) ;

    public Application(String title, ImageIcon icon) {
        this.setTitle(title);
        this.setIconImage(icon.getImage());
        this.setSize(1200, 400);
		this.setLocationRelativeTo(null);
        //this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        //this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setMinimumSize(new Dimension(300,300));

        /* Add the menu bar */
        try {
            this.getContentPane().add(createMenuBar(), BorderLayout.NORTH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* Add the app's body */
        try {
            this.getContentPane().add(createBodyApp(), BorderLayout.CENTER);
        } catch (IOException | UserNotConnectedException e) {
            e.printStackTrace();
        }

        this.customThemeApp(ColorThemeApp.LIGHT);
        
        this.addMouseListener(this); // test
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
        menuBar.add(logoPanel);
        logoPanel.setLayout(new GridLayout(0, 1, 0, 0));

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
                customThemeApp(ColorThemeApp.LIGHT);
            }
        });
        allColors.add(colorAppWhite);
        colorApp.add(colorAppWhite);

        colorAppBlack = new JRadioButton("Black");
        colorAppBlack.setMnemonic(KeyEvent.VK_B);
        colorAppBlack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customThemeApp(ColorThemeApp.DARK);
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

        return menuBar ;
    }

    /**
     * Create the app's body.
     * @throws IOException
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

        discussion = new MyRoundJPanel();
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
     * */
    private JScrollPane createListUsers() throws IOException, UserNotConnectedException {
    	nbUsers = 0;
    	
    	listUsers = new JPanel();
        listUsers.setBorder(new EmptyBorder(0, 20, 10, 20));
        listUsers.setLayout(new BoxLayout(listUsers, BoxLayout.Y_AXIS));
        
        for (String name : MainGUI.getAllUsernamesInDatabase()) {
        	listUsers.add(new DestinataireJPanel(name,++nbUsers,true,Destinataire.User,this));
        }
        listUsers.add(new DestinataireJPanel("Julien",++nbUsers,true,Destinataire.User,this));
        listUsers.add(new DestinataireJPanel("Micheline",++nbUsers,true,Destinataire.User,this));
        listUsers.add(new DestinataireJPanel("Theodore",++nbUsers,false,Destinataire.User,this));
        listUsers.add(new DestinataireJPanel("Loic",++nbUsers,false,Destinataire.User,this));
        listUsers.add(new DestinataireJPanel("Arthur",++nbUsers,true,Destinataire.User,this));
        listUsers.add(new DestinataireJPanel("Fantine",++nbUsers,false,Destinataire.User,this));
        
    	System.out.println("nbUsers =" + nbUsers);
        listUsers.setPreferredSize(new Dimension(0, 30*nbUsers));
                
        usersContainer = new JScrollPane(listUsers);
        usersContainer.getVerticalScrollBar().setUI(new MyJScrollBarUI(COLOR_BACKGROUND, COLOR_SCROLL_BAR, COLOR_CURSOR_SCROLL, COLOR_CURSOR_SCROLL_HOVER));
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
        groupsContainer.getVerticalScrollBar().setUI(new MyJScrollBarUI(COLOR_BACKGROUND, COLOR_SCROLL_BAR, COLOR_CURSOR_SCROLL, COLOR_CURSOR_SCROLL_HOVER));
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

        editMsg = new MyRoundJTextField();
        editMsg.setText("Hello...");
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
        title.setForeground(COLOR_BLUE);
        title.setFont(new Font("Dialog", Font.PLAIN, 16));
        title.setBorder(new EmptyBorder(10, 15, 10, 15));
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
    private void customThemeApp(ColorThemeApp c) {
    	if (c == ColorThemeApp.LIGHT) {
            COLOR_BACKGROUND = new Color(247,249,251) ;
            COLOR_BACKGROUND2 = new Color(255,255,255) ;
            COLOR_EDIT_MESSAGE = new Color(237,237,237) ;
            COLOR_SCROLL_BAR = new Color(237,237,237,127) ;
            COLOR_CURSOR_SCROLL = new Color(219,219,219) ;
            COLOR_CURSOR_SCROLL_HOVER = new Color(201,201,201) ;
            COLOR_OUR_MESSAGE = new Color(201,201,201,50) ;
            COLOR_SHADOW = new Color(165,165,165,50) ;
    	} else if (c == ColorThemeApp.DARK) {
    		COLOR_BACKGROUND = new Color(38,38,38) ;
            COLOR_BACKGROUND2 = new Color(89,89,89) ;
            COLOR_EDIT_MESSAGE = new Color(127,127,127) ;
            COLOR_SCROLL_BAR = new Color(127,127,127,127) ;
            COLOR_CURSOR_SCROLL = new Color(64,64,64) ;
            COLOR_CURSOR_SCROLL_HOVER = new Color(62,62,62) ;
            COLOR_OUR_MESSAGE = new Color(62,62,62,50) ;
            COLOR_SHADOW = new Color(165,165,165,50) ;
    	}
        this.setBackground(COLOR_BACKGROUND);
        
    	/* ** Menu Bar ** */
        menuBar.setBackground(COLOR_BACKGROUND2);
        
    	/* ** Body's App ** */
        bodyApp.setBackground(COLOR_BACKGROUND);
    	// -- Destinataires -- //
        listUsers.setBackground(COLOR_BACKGROUND);
        usersContainer.getVerticalScrollBar().setBackground(COLOR_BACKGROUND);
        listGroups.setBackground(COLOR_BACKGROUND);
        groupsContainer.getVerticalScrollBar().setBackground(COLOR_BACKGROUND); 
    	// -- Discussion -- //
        discussion.setBackground(COLOR_BACKGROUND2);
        messages.setBackground(COLOR_BACKGROUND2);
        editMsg.setBackground(COLOR_EDIT_MESSAGE);
    }
    
	/**
	 * Add a group to the list of groups.
	 * @throws IOException 
	 * */
	private void addGroupToList(String name, Boolean connect) throws IOException {
    	listGroups.add(new DestinataireJPanel(name,++nbGroups,connect,Destinataire.Group,this));
    	System.out.println("nbGroups =" + nbGroups);
    	listGroups.setPreferredSize(new Dimension(0, 30*nbGroups));
	}

	/**
	 * Remove a group to the list of groups.
	 * */
	private void removeDestinataireOfList(JPanel destinataire, JList<JPanel> list) {
	}


    /* --------- GLOBAL LISTENERS ----------- */

    @Override
    public void actionPerformed(ActionEvent e) {

    }

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
    
    /* --------- GET and SETTER ----------- */

    
    public void setNameDestinataire(String newDestinataire) {
    	nameDestinataire.setText(newDestinataire);
    }


}
