package clavardage.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import clavardage.controller.Clavardage;
import clavardage.controller.authentification.AuthOperations;
import clavardage.view.Application.SectionText;
import clavardage.view.main.SectionTextJPanel;
import clavardage.view.mystyle.MyJButtonText;
import clavardage.view.mystyle.MyJScrollBarUI;
import clavardage.view.mystyle.MyLogButtonJPanel;
import clavardage.view.mystyle.MyRoundJPanel;

/**
 * Login Window, first window open
 * @author Célestine Paillé
 */
@SuppressWarnings("serial")
public class LoginWindow extends JPanel implements MouseListener {
	
	private MyRoundJPanel logPanel;
	private JPanel headPanel, logoPanel, sections;
	private MyLogButtonJPanel logButtonPanel ;
	private JButton signInButton ;
	private JScrollPane sectionContainer;
	private JLabel textError, logButton;
	private SectionTextJPanel mail, password;

	
	public LoginWindow() throws IOException {		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[] {0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 2.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		this.setLayout(gridBagLayout);
		
		/* Add the login panel */
		createLoginPanel();

		/* Press ENTER to connect*/
		Action connexion = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				connexion();
			}};
		InputMap im = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		im.put(KeyStroke.getKeyStroke("ENTER"), "connexion");
		ActionMap am = getActionMap();
		am.put("connexion", connexion);
	}
	
	/**
	 * Create the login panel.
	 * @throws IOException if <code>createHeadPanel()</code> or <code>createSection()</code> hase failed
	 * @see #createHeadPanel()
	 * @see #createSection()
	 * */
	private void createLoginPanel() throws IOException {
		logPanel = new MyRoundJPanel(30);
		GridBagConstraints gbc_logPanel = new GridBagConstraints();
		gbc_logPanel.insets = new Insets(20, 0, 20, 5);
		gbc_logPanel.fill = GridBagConstraints.BOTH;
		gbc_logPanel.gridx = 1;
		gbc_logPanel.gridy = 0;
		this.add(logPanel, gbc_logPanel);

		GridBagLayout gbl_logPanel = new GridBagLayout();
		gbl_logPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_logPanel.rowHeights = new int[] {30, 200, 0, 0};
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
		GridBagConstraints gbc_sectionContainer = new GridBagConstraints();
		gbc_sectionContainer.insets = new Insets(0, 0, 20, 5);
		gbc_sectionContainer.fill = GridBagConstraints.BOTH;
		gbc_sectionContainer.gridx = 1;
		gbc_sectionContainer.gridy = 3;
		logPanel.add(sectionContainer, gbc_sectionContainer);
		createSection();
	}
	
	/**
	 * Create the head panel.
	 * @throws IOException if we can't read the logoImage
	 * @see ImageIO#read(java.io.InputStream)
	 * */
	private void createHeadPanel() throws IOException  {
		GridBagLayout gbl_headPanel = new GridBagLayout();
		gbl_headPanel.columnWidths = new int[]{0, 300, 0, 0};
		gbl_headPanel.rowHeights = new int[]{10, 150, 10, 0};
		gbl_headPanel.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_headPanel.rowWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		headPanel.setLayout(gbl_headPanel);
		headPanel.setOpaque(false);
		headPanel.setPreferredSize(new Dimension(0, 0));
		
		GridBagConstraints gbc_logoPanel = new GridBagConstraints();
		gbc_logoPanel.insets = new Insets(0, 0, 5, 5);
		gbc_logoPanel.fill = GridBagConstraints.BOTH;
		gbc_logoPanel.gridx = 1;
		gbc_logoPanel.gridy = 1;
		Image logoImage = ImageIO.read(Clavardage.getResourceStream("/img/assets/title_below_logo.png"));
		logoPanel = new JPanel()  {
			Image img = logoImage;
			{setOpaque(false);}
			public void paintComponent(Graphics graphics) 
			{
				graphics.drawImage(img.getScaledInstance(-1, this.getSize().height, Image. SCALE_SMOOTH), 0, 0 , this);
				super.paintComponent(graphics);
			}
		};
		headPanel.add(logoPanel, gbc_logoPanel);
		
		GridBagConstraints gbc_signInButton = new GridBagConstraints();
		gbc_signInButton.insets = new Insets(0, 0, 0, 0);
		gbc_signInButton.anchor = GridBagConstraints.FIRST_LINE_END;
		gbc_signInButton.gridx = 0;
		gbc_signInButton.gridy = 0;
		gbc_signInButton.gridwidth = 3 ;
		signInButton = new MyJButtonText("Sign Up");
		signInButton.setForeground(Application.getPURPLE());
		signInButton.addMouseListener(this);
		headPanel.add(signInButton,gbc_signInButton);
	}

	/**
	 * Create the sections.
	 * @throws IOException if <code>createLogButton()</code> has failed
	 * @see #createLogButton()
	 * */
	private void createSection() throws IOException {
		sections.setLayout(new BoxLayout(sections, BoxLayout.Y_AXIS));
		sections.setBorder(new EmptyBorder(0, 20, 0, 20));
		
		textError = new JLabel(" ");
		textError.setFont(new Font("Dialog", Font.ITALIC, 14));
		textError.setForeground(Application.getRED());
		sections.add(textError);
		
		mail = new SectionTextJPanel("Mail", SectionText.LOG);
		sections.add(mail);
		
		password = new SectionTextJPanel("Password", SectionText.PW);
		sections.add(password);
		
		sections.add(Application.createMargin(0, 20));
		
		sections.add(createLogButton());

		sections.add(Application.createMargin(0, 40));

		sectionContainer.getVerticalScrollBar().setUI(new MyJScrollBarUI());
		sectionContainer.setBorder(null);
		sectionContainer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	}
	
	/**
	 * Create the Login button.
	 * @throws IOException if we can't read the logButtonImage
	 * @see ImageIO#read(java.io.InputStream)
	 */
	private JPanel createLogButton() throws IOException { 
		
		logButton = new JLabel("Login",SwingConstants.CENTER);
		logButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		logButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		logButton.setForeground(Color.WHITE);
		
		logButtonPanel = new MyLogButtonJPanel(ImageIO.read(Clavardage.getResourceStream("/img/assets/bgButtonLogBlue.png")));
		logButtonPanel.add(logButton);
		
		JPanel logButtonSection = new JPanel();
		logButtonSection.add(logButtonPanel);
		logButtonSection.setOpaque(false);
		FlowLayout flowLayout = (FlowLayout) logButtonSection.getLayout();
		flowLayout.setVgap(0);

		logButton.addMouseListener(this);
		logButtonPanel.addMouseListener(this);

		return logButtonSection;
	}
	
	/**
	 * Try the connection to enter in the MessageWindow. <br>
	 * If the connection has failed, create a error message.
	 * @see MessageWindow
	 */
	protected void connexion() {
		try {
			//try the connection with mail and password
			AuthOperations.connectUser(mail.getText(),password.getText());
			
			//if the connection is established
			if(AuthOperations.isUserConnected()) {
				Application.createMessageWindow();
				
				//open the MessageWindow
				Application.displayContent(Application.getApp(), Application.getMessageWindow());
				
				//visual details
				textError.setText(" ");
				mail.setNoError();
				password.setNoError();
				mail.setText("");
				password.setText("");
			}
		} catch (Exception e1) {
			//if the connection isn't established, inform the customers
			textError.setText(e1.getMessage());
			sectionContainer.getVerticalScrollBar().setValue(0);
			mail.setError();
			password.setError();
			e1.printStackTrace();
		}
	}
	
	/**
	 * Display the SignInWindow. <br>
	 * @see MessageWindow
	 */
	private void goToSignIn() {
		try {
			if (Application.getSignInWindow() == null) {
				//create SignInWindow if it doesn't exist
				Application.createSignInWindow();
			}
			
			//open the SignInWindow
			Application.displayContent(Application.getApp(), Application.getSignInWindow());
			sectionContainer.getVerticalScrollBar().setValue(0);

			textError.setText(" ");
			mail.setText("");
			password.setText("");
		} catch (IOException e1) {
			e1.printStackTrace();
		}		
	}

	/* --------- GLOBAL LISTENERS ----------- */

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource()==logButton | e.getSource()==logButtonPanel) {
			connexion();
		} else if (e.getSource()== signInButton) {
			goToSignIn();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}


	public JPanel getSections() {
		return sections;
	}

	public JButton getSignInButton() {
		return signInButton;
	}

	public MyRoundJPanel getLogPanel() {
		return logPanel;
	}

	public JScrollPane getSectionContainer() {
		return sectionContainer;
	}

	public JLabel getLogButton() {
		return logButton;
	}

	public SectionTextJPanel getPassword() {
		return password;
	}

	public MyLogButtonJPanel getLogButtonPanel() {
		return logButtonPanel;
	}

	public SectionTextJPanel getMail() {
		return mail;
	}

	public JLabel getTextError() {
		return textError;
	}

}
