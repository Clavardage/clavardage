package clavardage.view.main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.time.OffsetTime;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import clavardage.controller.Clavardage;
import clavardage.view.main.Application.ColorThemeApp;
import clavardage.view.main.Application.Destinataire;
import java.awt.Insets;

public class DestinataireJPanel extends JPanel {
	
	private JPanel connectPanel, namePanel;
	private JTextArea nameUser;
	private JLabel connectLabel;
	private int idUser;
	
	public DestinataireJPanel(String name, int id, boolean connect, Destinataire d, Application app) throws IOException {
		super();

		this.idUser = id;
		this.connectPanel = new JPanel();
		this.namePanel = new JPanel();
		
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
		
		Image connectImage;
		ImageIcon connectIcon;
		if (d == Destinataire.User) {
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
				app.setNameDestinataire(name);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				nameUser.setForeground(Color.RED);

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
				nameUser.setForeground(Color.RED);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				nameUser.setForeground(namePanel.getForeground());		    
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				app.setNameDestinataire(name);				
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
				nameUser.setForeground(Color.RED);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				nameUser.setForeground(namePanel.getForeground());		    
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				app.setNameDestinataire(name);
				
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
	
	
	
	public void setForegroundNamePanel(Color c) {
			namePanel.setForeground(c); //pour le mouseExited
			nameUser.setForeground(c);
	}
}