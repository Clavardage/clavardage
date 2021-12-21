package clavardage.view.main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import clavardage.controller.Clavardage;
import clavardage.model.objects.Message;
import clavardage.view.main.MessageWindow.Destinataire;

public class DestinataireJPanel extends JPanel {
	
	private JPanel connectPanel, namePanel;
	private JTextArea nameUser;
	private JLabel connectLabel;
	private int id;
	private boolean conversationOpen, connect ;
	private Image openImage, connectImage;
	private ImageIcon openIcon, connectIcon ;
	private Destinataire type;
	 
	public DestinataireJPanel(String name, int i, boolean c, Destinataire d, MessageWindow window) throws IOException {
		super();
		
		this.conversationOpen = false;
		this.id = i;
		this.type = d;
		this.connect = c;
		this.connectPanel = new JPanel();
		this.namePanel = new JPanel();
		this.conversationOpen = false ;

		
		this.openImage = ImageIO.read(Clavardage.getResourceStream("/img/assets/addGroups.png")).getScaledInstance(11, 11, Image.SCALE_SMOOTH);
		this.openIcon = new ImageIcon(openImage, "The conversation is open");
		
		if (this.type == Destinataire.User) {
			if (connect) {
				connectImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/userConnect.png")).getScaledInstance(11, 11, Image.SCALE_SMOOTH);
				connectIcon = new ImageIcon(connectImage, "User is connected");
			} else {
				connectImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/userDisconnect.png")).getScaledInstance(11, 11, Image.SCALE_SMOOTH);
				connectIcon = new ImageIcon(connectImage, "User is disconnected");
			}
		} else {
			if (connect) {
				connectImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/groupConnect.png")).getScaledInstance(15, 15, Image.SCALE_SMOOTH);
				connectIcon = new ImageIcon(connectImage, "At least one user is connected");
			} else {
				connectImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/groupDisconnect.png")).getScaledInstance(15, 15, Image.SCALE_SMOOTH);
				connectIcon = new ImageIcon(connectImage, "All users are disconnected");
			}
		}		
		
		this.setBorder(null);
		this.setOpaque(false);
		this.setMaximumSize(new Dimension (Toolkit.getDefaultToolkit().getScreenSize().width, 30));
		this.setMinimumSize(new Dimension (0, 30));
    	this.setCursor(new Cursor(Cursor.HAND_CURSOR));

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
		connectLabel.setIcon(connectIcon);
		this.connectPanel.add(connectLabel);
		
		nameUser = new JTextArea(name);
		nameUser.setCursor(new Cursor(Cursor.HAND_CURSOR));
		nameUser.setEditable(false);
		nameUser.setHighlighter(null);
		nameUser.setBorder(null);
		nameUser.setOpaque(false);
		this.namePanel.add(nameUser);
					
		super.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {	
				window.openConversation(nameUser.getText(), id, type);
				try {
					openMyConversation(window);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				nameUser.setForeground(Application.COLOR_RED);

			}

			@Override
			public void mouseExited(MouseEvent e) {
				nameUser.setForeground(namePanel.getForeground());		    
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
        });
		
		
		namePanel.addMouseListener(new MouseListener() {
			@Override
			public void mouseEntered(MouseEvent e) {
				nameUser.setForeground(Application.COLOR_RED);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				nameUser.setForeground(namePanel.getForeground());		    
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				window.openConversation(nameUser.getText(), id, type);
				try {
					openMyConversation(window);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
        });
		
		nameUser.addMouseListener(new MouseListener() {
			@Override
			public void mouseEntered(MouseEvent e) {
				nameUser.setForeground(Application.COLOR_RED);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				nameUser.setForeground(namePanel.getForeground());		    
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				window.openConversation(nameUser.getText(), id, type);
				try {
					openMyConversation(window);
				} catch (IOException e1) {
					e1.printStackTrace();
				}				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
        });
		
	}
	
	public int getIdDestinataire() {
		return id;
	}
	
	public boolean isConnected() {
		return connect;
	}
	
	public String getNameDestinataire() {
		return nameUser.getText();
	}
	
	public void setForegroundNamePanel() {
			namePanel.setForeground(Application.COLOR_TEXT); //pour le mouseExited
			nameUser.setForeground(Application.COLOR_TEXT);
	}
	
	public void openMyConversation(MessageWindow window) throws IOException {
		for (Component panel : window.getListUsers().getComponents()) {
			((DestinataireJPanel) panel).closeMyConversation();
		}
		for (Component panel : window.getListGroups().getComponents()) {
			((DestinataireJPanel) panel).closeMyConversation();
		}
		this.conversationOpen = true ;
		connectLabel.setIcon(openIcon);
		window.moveInTopOfList(this.type, this.id);
	}
	
	public void closeMyConversation() {
		this.conversationOpen = false ;
		connectLabel.setIcon(connectIcon);	
	}
}