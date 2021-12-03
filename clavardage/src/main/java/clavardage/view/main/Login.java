package clavardage.view.main;

/* TODO
 * -login au centre du bouton
 * -head panel
 * -quand on écrit dans password, ce sont des point qui apparaissent
 * -sections en gridbaglayout : élément dans des lignes de tailles fixes et séparer par des lignes grow
 * -trycash avec des fenetre popup 
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import clavardage.controller.Clavardage;
import clavardage.controller.authentification.AuthOperations;
import clavardage.view.main.Application.ColorThemeApp;

public class Login extends JPanel implements ActionListener, MouseListener {
	
	private JPanel logPanel, headPanel, sections, LogButtonPanel;
	private JButton signInButton ;
	private JLabel logoLabel;
	private JScrollPane sectionContainer;
	private JTextArea logButton;
	private Image logoImage;
	private ImageIcon logoIcon;
	private SectionTextJPanel username, password;
	private JLabel textError;
	
	public Login() {		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[] {0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 2.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		this.setLayout(gridBagLayout);
		
		
		
		/* Add the login panel */
		try {
			createLoginPanel();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		customThemeLogin(ColorThemeApp.LIGHT);
	}
	
	/**
	 * Create the login panel.
	 * @throws IOException
	 * */
	private void createLoginPanel() throws IOException {
		logPanel = new MyRoundJPanel();
		GridBagConstraints gbc_logPanel = new GridBagConstraints();
		gbc_logPanel.insets = new Insets(20, 0, 20, 5);
		gbc_logPanel.fill = GridBagConstraints.BOTH;
		gbc_logPanel.gridx = 1;
		gbc_logPanel.gridy = 0;
		this.add(logPanel, gbc_logPanel);

		GridBagLayout gbl_logPanel = new GridBagLayout();
		gbl_logPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_logPanel.rowHeights = new int[] {20, 200, 30, 0};
		gbl_logPanel.columnWeights = new double[]{1.0, 3.0, 1.0, Double.MIN_VALUE};
		gbl_logPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0};
		logPanel.setLayout(gbl_logPanel);
		
		/* ** Head's Panel ** */
		headPanel = new JPanel();
		GridBagConstraints gbc_headPanel = new GridBagConstraints();
		gbc_headPanel.fill = GridBagConstraints.BOTH;
		gbc_headPanel.insets = new Insets(0, 0, 5, 5);
		gbc_headPanel.gridx = 1;
		gbc_headPanel.gridy = 1;
		logPanel.add(headPanel, gbc_headPanel);
		createHeadPanel();

		/* ** Sections ** */
		sections = new JPanel();
		sectionContainer = new JScrollPane(sections);
		createSection();
		GridBagConstraints gbc_sectionContainer = new GridBagConstraints();
		gbc_sectionContainer.insets = new Insets(0, 0, 20, 5);
		gbc_sectionContainer.fill = GridBagConstraints.BOTH;
		gbc_sectionContainer.gridx = 1;
		gbc_sectionContainer.gridy = 3;
		logPanel.add(sectionContainer, gbc_sectionContainer);
	}
	
	/**
	 * Create the head panel.
	 * @throws IOException 
	 * */
	private void createHeadPanel() throws IOException {
		headPanel.setLayout(new BoxLayout(headPanel, BoxLayout.X_AXIS));
		headPanel.setOpaque(false);
		
		logoImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/title_below_logo.png")).getScaledInstance(90, 48, Image.SCALE_SMOOTH);
		logoIcon = new ImageIcon(logoImage, "logo");
		logoLabel = new JLabel();
		logoLabel.setMinimumSize(new Dimension(210, 210));
		logoLabel.setPreferredSize(new Dimension(210, 210));
		logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		logoLabel.setOpaque(true);
		logoLabel.setBackground(Color.CYAN);
		logoLabel.setBorder(null);
		logoLabel.setIcon(logoIcon);
		headPanel.add(logoLabel);

		signInButton = new JButton("Sign In");
		customButton(signInButton);
		headPanel.add(signInButton);		
	}

	/**
	 * Create the sections.
	 * */
	private void createSection() {
		sections.setLayout(new BoxLayout(sections, BoxLayout.Y_AXIS));
		sections.setBorder(new EmptyBorder(0, 20, 0, 20));
		
		textError = new JLabel(" ");
		textError.setFont(new Font("Dialog", Font.ITALIC, 14));
		textError.setForeground(Color.RED);
		sections.add(textError);
		
		
		username = new SectionTextJPanel("Login or Mail","mail_0@clav.com");
		sections.add(username);

		password = new SectionTextJPanel("Password","pass_0");
		sections.add(password);
		
		sections.add(createMargin(0, 20));
		
		LogButtonPanel = new JPanel();
		createLogButton();
		sections.add(LogButtonPanel);

		sections.add(createMargin(0, 40));

		sectionContainer.getVerticalScrollBar().setUI(new MyJScrollBarUI(Application.COLOR_BACKGROUND, Application.COLOR_SCROLL_BAR, Application.COLOR_CURSOR_SCROLL, Application.COLOR_CURSOR_SCROLL_HOVER));
		sectionContainer.setBorder(null);
		sectionContainer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	}
	
	/**
	 * Create the Login button.
	 * */
	private void createLogButton() { 
		logButton = new MyRoundJTextArea();
		logButton.setText("Login");
		logButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		logButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		logButton.setForeground(Color.WHITE);
		logButton.setBackground(Application.COLOR_BLUE);
		logButton.setPreferredSize(new Dimension(200, 80));
		logButton.setEditable(false);
		logButton.setHighlighter(null);
		LogButtonPanel.add(logButton);
		LogButtonPanel.setOpaque(false);
		FlowLayout flowLayout = (FlowLayout) LogButtonPanel.getLayout();
		flowLayout.setVgap(0);
		logButton.addMouseListener(this);
	}
	
	/**
	 * Create a margin.
	 * */
	private JPanel createMargin(int x, int y) {
		JPanel marge = new JPanel();
		marge.setPreferredSize(new Dimension(x,y));
		marge.setMaximumSize(marge.getPreferredSize());
		marge.setOpaque(false);
		return marge ;
	}

	/**
	 * Custom button.
	 * */
	private void customButton(JButton button) {
		button.setOpaque(false);
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setContentAreaFilled(false);
		button.setMargin(new Insets(0, 0, 0, 0));

	}

	/**
	 * Custom theme Login.
	 * */
	public void customThemeLogin(ColorThemeApp c) {
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
		logPanel.setBackground(Application.COLOR_BACKGROUND2);	

		/* ** Sections ** */
		sections.setBackground(Application.COLOR_BACKGROUND2);
		for (Component panel : sections.getComponents()) {
			if ( panel.getClass().getName().equals("clavardage.view.main.SectionTextJPanel")  ) {
				((SectionTextJPanel) panel).setColorTextSession(Application.COLOR_EDIT_MESSAGE, Application.COLOR_TEXT_EDIT);
			}
		}
		sectionContainer.getVerticalScrollBar().setBackground(Application.COLOR_BACKGROUND2); 		
	}
	

	/* --------- GLOBAL LISTENERS ----------- */

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource()==logButton) {
			try {
				AuthOperations.connectUser(username.getText(),password.getText());
				if(AuthOperations.isUserConnected()) {
					System.out.println("CONNECTED!!");
					Application.displayContent(Application.getApp(), Application.getMessageWindow());
					textError.setText(" ");

				}
			} catch (Exception e1) {
				textError.setText(e1.getMessage());

			}
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
