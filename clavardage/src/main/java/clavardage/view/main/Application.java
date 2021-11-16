package clavardage.view.main;

import clavardage.controller.Clavardage;
import clavardage.controller.gui.MainGUI;

import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.io.IOException;

public class Application extends JFrame implements ActionListener, MouseListener {
    private JTextField editMsg;
    //	private int nbUsers = 0 ;

    protected static Color COLOR_BACKGROUND = new Color(247,249,251) ;
    protected static Color COLOR_BACKGROUND2 = new Color(255,255,255) ;
    protected static Color COLOR_EDIT_MESSAGE = new Color(237,237,237) ;
    protected static Color COLOR_SCROLL_BAR = new Color(237,237,237,127) ;
    protected static Color COLOR_CURSOR_SCROLL = new Color(219,219,219) ;
    protected static Color COLOR_CURSOR_SCROLL_HOVER = new Color(201,201,201) ;
    protected static Color COLOR_OUR_MESSAGE = new Color(201,201,201,50) ;
    protected static Color COLOR_SHADOW = new Color(165,165,165,50) ;
    protected static Color COLOR_BLUE = new Color(72,125,244) ;

    public Application(String title, ImageIcon icon) {
        this.setTitle(title);
        this.setIconImage(icon.getImage());
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setBackground(COLOR_BACKGROUND);
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.addMouseListener(this); // test
    }



    /**
     * Create the menu bar.
     * */
    private JMenuBar createMenuBar() throws IOException {
        JMenuBar menuBar = new JMenuBar();
//		menuBar.setUI();
        menuBar.setBackground(COLOR_BACKGROUND2);
        menuBar.setBorder(new EmptyBorder(0, 20, 0, 10));
        menuBar.setPreferredSize(new Dimension(0, 50));
        menuBar.setBorderPainted(false);


        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(COLOR_BACKGROUND2);
        logoPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        logoPanel.setPreferredSize(new Dimension(0, 40));
        menuBar.add(logoPanel);
        logoPanel.setLayout(new GridLayout(0, 1, 0, 0));

        ImageIcon logoIcon = new ImageIcon(ImageIO.read(Clavardage.getResourceStream("/img/assets/Logo_title.png")),"Logo");
        logoIcon = new ImageIcon(logoIcon.getImage().getScaledInstance(130, 33, Image.SCALE_SMOOTH));
        JLabel logo = new JLabel();
        logo.setBorder(null);
        logo.setBackground(COLOR_BACKGROUND2);
        logo.setIcon(logoIcon);
        logoPanel.add(logo);

        ImageIcon settingsIcon = new ImageIcon(ImageIO.read(Clavardage.getResourceStream("/img/assets/Settings.png")),"Setting menu");
        settingsIcon = new ImageIcon(settingsIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        JMenu settings = new JMenu();
        settings.setBorder(null);
        settings.setBackground(COLOR_BACKGROUND2);
        settings.setIcon(settingsIcon);
        menuBar.add(settings);

        JMenu colorApp = new JMenu("Change the default color");
        colorApp.setBorder(null);
        colorApp.setBackground(COLOR_BACKGROUND2);
        settings.add(colorApp);

        ButtonGroup allColors = new ButtonGroup();

        JRadioButton colorAppWhite = new JRadioButton("White");
        colorAppWhite.setSelected(true);
        colorAppWhite.setMnemonic(KeyEvent.VK_W);
        colorAppWhite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                COLOR_BACKGROUND = new Color(247,249,251) ;
                COLOR_BACKGROUND2 = new Color(255,255,255) ;
                COLOR_EDIT_MESSAGE = new Color(237,237,237) ;
                COLOR_SCROLL_BAR = new Color(237,237,237,127) ;
                COLOR_CURSOR_SCROLL = new Color(219,219,219) ;
                COLOR_CURSOR_SCROLL_HOVER = new Color(201,201,201) ;
                COLOR_OUR_MESSAGE = new Color(201,201,201,50) ;
                COLOR_SHADOW = new Color(165,165,165,50) ;
                COLOR_BLUE = new Color(72,125,244) ;

            }
        });
        allColors.add(colorAppWhite);
        colorApp.add(colorAppWhite);

        JRadioButton colorAppBlack = new JRadioButton("Black");
        colorAppBlack.setMnemonic(KeyEvent.VK_B);
        colorAppBlack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                COLOR_BACKGROUND = new Color(38,38,38) ;
                COLOR_BACKGROUND2 = new Color(89,89,89) ;
                COLOR_EDIT_MESSAGE = new Color(237,237,237) ;
                COLOR_SCROLL_BAR = new Color(127,127,127,127) ;
                COLOR_CURSOR_SCROLL = new Color(64,64,64) ;
                COLOR_CURSOR_SCROLL_HOVER = new Color(62,62,62) ;
                COLOR_OUR_MESSAGE = new Color(62,62,62,50) ;
                COLOR_SHADOW = new Color(165,165,165,50) ;
                COLOR_BLUE = new Color(72,125,244) ;
                menuBar.updateUI();

            }
        });
        allColors.add(colorAppBlack);
        colorApp.add(colorAppBlack);

        JRadioButton colorAppRed = new JRadioButton("Red");
        allColors.add(colorAppRed);
        colorApp.add(colorAppRed);

        JRadioButton colorAppBlue = new JRadioButton("Blue");
        allColors.add(colorAppBlue);
        colorApp.add(colorAppBlue);

//				settings.addSeparator();



        ImageIcon accountIcon = new ImageIcon(ImageIO.read(Clavardage.getResourceStream("/img/assets/Account.png")),"Account menu");
        accountIcon = new ImageIcon(accountIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        JMenu account = new JMenu();
        account.setBorder(null);
        account.setBackground(COLOR_BACKGROUND2);
        account.setIcon(accountIcon);
        menuBar.add(account);

        return menuBar ;
    }

    /**
     * Create the app's body.
     * */
    private JPanel createBodyApp() throws IOException {
        JPanel bodyApp = new JPanel();
        bodyApp.setBackground(COLOR_BACKGROUND);

        /* Set the GridBagLayout */
        GridBagLayout gbl_bodyApp = new GridBagLayout();
        gbl_bodyApp.columnWidths = new int[]{30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 0};
        gbl_bodyApp.rowHeights = new int[]{100};
        gbl_bodyApp.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
        gbl_bodyApp.rowWeights = new double[]{1.0};
        bodyApp.setLayout(gbl_bodyApp);

        /* Add Destinataires with good constraints */
        GridBagConstraints gbc_destinataires = new GridBagConstraints();
        gbc_destinataires.insets = new Insets(0, 0, 0, 5);
        gbc_destinataires.fill = GridBagConstraints.BOTH;
        gbc_destinataires.gridwidth = 3;
        gbc_destinataires.gridx = 0;
        gbc_destinataires.gridy = 0;
        bodyApp.add(createDestinatairesPanel(), gbc_destinataires);

        /* Add Discussion with good constraints */
        GridBagConstraints gbc_discussionContainer = new GridBagConstraints();
        gbc_discussionContainer.fill = GridBagConstraints.BOTH;
        gbc_discussionContainer.gridwidth = 7;
        gbc_discussionContainer.gridx = 3;
        gbc_discussionContainer.gridy = 0;
        bodyApp.add(createDiscussionContainerPanel(), gbc_discussionContainer);

        return bodyApp ;
    }

    /**
     * Create the panel of the destinataires.
     * */
    private JPanel createDestinatairesPanel() throws IOException {
        JPanel destinataires = new JPanel();
        destinataires.setBackground(COLOR_BACKGROUND);
        destinataires.setLayout(new GridLayout(2, 1, 0, 0));

        JPanel users = new JPanel();
        users.setBackground(COLOR_BACKGROUND);
        destinataires.add(users);
        users.setLayout(new BorderLayout(0, 0));

        JTextArea titleUsers = new JTextArea("Users");
        customTitle(titleUsers, COLOR_BACKGROUND);
        users.add(titleUsers, BorderLayout.NORTH);

        users.add(createListUsers());

        JPanel groups = new JPanel();
        groups.setBackground(COLOR_BACKGROUND);
        destinataires.add(groups);
        groups.setLayout(new BorderLayout(0, 0));

        JPanel northGroups = new JPanel();
        northGroups.setBackground(COLOR_BACKGROUND);
        groups.add(northGroups, BorderLayout.NORTH);
        GridBagLayout gbl_northGroups = new GridBagLayout();
        gbl_northGroups.columnWidths = new int[]{10, 10, 10, 10, 10, 10, 10, 10, 0};
        gbl_northGroups.rowHeights = new int[]{21};
        gbl_northGroups.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
        gbl_northGroups.rowWeights = new double[]{0.0};
        northGroups.setLayout(gbl_northGroups);


        JTextArea titleGroups = new JTextArea("Groups");
        customTitle(titleGroups, COLOR_BACKGROUND);
        GridBagConstraints gbc_titleGroups = new GridBagConstraints();
        gbc_titleGroups.gridwidth = 7;
        gbc_titleGroups.fill = GridBagConstraints.BOTH;
        gbc_titleGroups.gridx = 0;
        gbc_titleGroups.gridy = 0;
        northGroups.add(titleGroups, gbc_titleGroups);

        ImageIcon addGroupIcon = new ImageIcon(ImageIO.read(Clavardage.getResourceStream("/img/assets/addGroups.png")),"Add Group Button");
        addGroupIcon = new ImageIcon(addGroupIcon.getImage().getScaledInstance(13, 13, Image.SCALE_SMOOTH));
        ImageIcon addGroupIconHover = new ImageIcon(ImageIO.read(Clavardage.getResourceStream("/img/assets/addGroups.png")),"Add Group Button Hover");
        addGroupIconHover = new ImageIcon(addGroupIconHover.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));
        JButton addGroup = new JButton();
        addGroup.setBackground(COLOR_BACKGROUND);
        customButton(addGroup,addGroupIcon,addGroupIconHover);
        GridBagConstraints gbc_addGroup = new GridBagConstraints();
        gbc_addGroup.fill = GridBagConstraints.BOTH;
        gbc_addGroup.gridx = 7;
        gbc_addGroup.gridy = 0;
        northGroups.add(addGroup, gbc_addGroup);

        groups.add(createListGroups());

        return destinataires ;
    }

    /**
     * Create the panel of the discussion container.
     * */
    private JPanel createDiscussionContainerPanel() throws IOException {
        JPanel discussionContainer = new JPanel();
        discussionContainer.setBackground(COLOR_BACKGROUND);
        discussionContainer.setBorder(new EmptyBorder(15, 15, 15, 15));
        discussionContainer.setLayout(new GridLayout(1, 0, 0, 0));

        JPanel discussion = new MyRoundJPanel();
        discussion.setBackground(COLOR_BACKGROUND2);
        discussion.setBorder(new EmptyBorder(0, 10, 0, 10));
        discussion.setLayout(new BorderLayout(0, 0));
        discussionContainer.add(discussion);

        /*Ajout Java FX pour l'ombre ?*/

        JTextArea nameDestinataire = new JTextArea("Michel");
        customTitle(nameDestinataire, COLOR_BACKGROUND2);
        discussion.add(nameDestinataire, BorderLayout.NORTH);

        JPanel messages = new JPanel();
        messages.setBackground(COLOR_BACKGROUND2);
        JScrollPane msg = new JScrollPane(messages);
        msg.setBackground(COLOR_BACKGROUND2);
        msg.setBorder(new EmptyBorder(0, 30, 0, 0));
        discussion.add(msg);

        discussion.add(createNewMsgPanel(), BorderLayout.SOUTH);

        return discussionContainer ;
    }


    /**
     * Create the list of Users.
     * */
    private JScrollPane createListUsers() throws IOException {

        final DefaultListModel<UserJPanel> model = new DefaultListModel<UserJPanel>();

        model.addElement(new UserJPanel("Julien",true));
        model.addElement(new UserJPanel("Micheline",true));
        model.addElement(new UserJPanel("Théodore",false));
        model.addElement(new UserJPanel("Loïc",false));
        model.addElement(new UserJPanel("Arthur",true));
        model.addElement(new UserJPanel("Fantine",false));

        JList<UserJPanel> listUsers = new JList<UserJPanel>(model);
        listUsers.setBackground(COLOR_BACKGROUND);
        listUsers.setBorder(new EmptyBorder(0, 20, 10, 20));
        listUsers.setFont(new Font("Dialog", Font.BOLD, 12));
        listUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listUsers.setLayout(new BoxLayout(listUsers, BoxLayout.Y_AXIS));
        listUsers.setLayoutOrientation(JList.VERTICAL_WRAP);

        listUsers.setPreferredSize(new Dimension(0, 30*6));

        JScrollPane usersContainer = new JScrollPane();
        usersContainer.getVerticalScrollBar().setUI(new MyJScrollBarUI());
        usersContainer.getVerticalScrollBar().setBackground(COLOR_BACKGROUND);
        usersContainer.setBorder(null);
        usersContainer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        usersContainer.setViewportView(listUsers);

        return usersContainer ;

    }

//	/**
//	 * Add a destinataire to a list of destinataire.
//	 * */
//	private void addUserToList(String name, Boolean connect, JList<UserJPanel> list) {
//    	UserJPanel user = new UserJPanel(name,connect);
//    	list.add(user);
//    	nbUsers++;
//		list.setPreferredSize(new Dimension(0, user.getHeight()*nbUsers));
//
//	}
//
//	/**
//	 * Remove a destinataire of a list of destinataire.
//	 * */
//	private void removeDestinataireOfList(JPanel destinataire, JList<JPanel> list) {
//	}

    /**
     * Create the list of Groups.
     * */
    private JList<JPanel> createListGroups()  throws IOException {
        JList<JPanel> listGroups = new JList<JPanel>();
        listGroups.setBackground(COLOR_BACKGROUND);
        listGroups.setBorder(new EmptyBorder(10, 20, 10, 20));
        return listGroups ;
    }

    /**
     * Create the panel of new message.
     * */
    private JPanel createNewMsgPanel() throws IOException{
        JPanel newMsg = new JPanel();
        newMsg.setBackground(COLOR_BACKGROUND2);
        newMsg.setBorder(new EmptyBorder(10, 30, 10, 30));
        newMsg.setLayout(new BoxLayout(newMsg, BoxLayout.X_AXIS));

        ImageIcon sendFileIcon = new ImageIcon(ImageIO.read(Clavardage.getResourceStream("/img/assets/sendFile.png")),"Send File Button");
        sendFileIcon = new ImageIcon(sendFileIcon.getImage().getScaledInstance(14, 21, Image.SCALE_SMOOTH));
        ImageIcon sendFileIconHover = new ImageIcon(ImageIO.read(Clavardage.getResourceStream("/img/assets/sendFile.png")),"Send File Button Hover");
        sendFileIconHover = new ImageIcon(sendFileIconHover.getImage().getScaledInstance(16, 24, Image.SCALE_SMOOTH));
        JButton sendFile = new JButton();
        customButton(sendFile,sendFileIcon,sendFileIconHover);
        newMsg.add(sendFile);

        newMsg.add(createMargin(10,0));

        ImageIcon sendPictureIcon = new ImageIcon(ImageIO.read(Clavardage.getResourceStream("/img/assets/sendPicture.png")),"Send Picture Button");
        sendPictureIcon = new ImageIcon(sendPictureIcon.getImage().getScaledInstance(14, 21, Image.SCALE_SMOOTH));
        ImageIcon sendPictureIconHover = new ImageIcon(ImageIO.read(Clavardage.getResourceStream("/img/assets/sendPicture.png")),"Send Picture Button Hover");
        sendPictureIconHover = new ImageIcon(sendPictureIconHover.getImage().getScaledInstance(16, 24, Image.SCALE_SMOOTH));
        JButton sendPicture = new JButton();
        customButton(sendPicture,sendPictureIcon,sendPictureIconHover);
        newMsg.add(sendPicture);

        newMsg.add(createMargin(10,0));

        editMsg = new RoundJTextField();
        editMsg.setText("Hello...");
        editMsg.setBackground(COLOR_EDIT_MESSAGE);
        newMsg.add(editMsg);

        newMsg.add(createMargin(10,0));

        ImageIcon sendMsgIcon = new ImageIcon(ImageIO.read(Clavardage.getResourceStream("/img/assets/sendMsg.png")),"Send Msg Button");
        sendMsgIcon = new ImageIcon(sendMsgIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon sendMsgIconHover = new ImageIcon(ImageIO.read(Clavardage.getResourceStream("/img/assets/sendMsg.png")),"Send Msg Button Hover");
        sendMsgIconHover = new ImageIcon(sendMsgIconHover.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        JButton sendMsg = new JButton();
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
    private void customTitle(JTextArea title, Color bg) {
        title.setBackground(bg);
        title.setCaretColor(bg);
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

    /* --------- GLOBAL LISTENERS ----------- */

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("test");
        MainGUI.getUsernames().forEach(System.out::println);
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
}
