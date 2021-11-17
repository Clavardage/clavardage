package clavardage.view.main;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
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
import clavardage.view.main.Application.Destinataire;

public class DestinataireJPanel extends JPanel {
	private JPanel connectPanel = new JPanel();
	private JPanel namePanel = new JPanel();
	private int idUser;

	public DestinataireJPanel(String name, int id, boolean connect, Destinataire d, Application app) throws IOException {
		super();
		this.idUser = id;
		this.setOpaque(false);
		this.setMaximumSize(new Dimension (Toolkit.getDefaultToolkit().getScreenSize().width, 30));
		this.setMinimumSize(new Dimension (0, 30));
    	this.setCursor(new Cursor(Cursor.HAND_CURSOR));

		connectPanel.setOpaque(false);
    	namePanel.setOpaque(false);
    	    	
		GridBagLayout gbl_destinataireJPanel = new GridBagLayout();
		gbl_destinataireJPanel.columnWidths = new int[]{20, 10, 10, 10, 10, 10, 0};
		gbl_destinataireJPanel.rowHeights = new int[]{20};
		gbl_destinataireJPanel.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_destinataireJPanel.rowWeights = new double[]{0.0};
		setLayout(gbl_destinataireJPanel);
				
		GridBagConstraints gbc_connectPanel = new GridBagConstraints();
		gbc_connectPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_connectPanel.gridx = 0;
		gbc_connectPanel.gridy = 0;
		add(connectPanel, gbc_connectPanel);
		
		GridBagConstraints gbc_namePanel = new GridBagConstraints();
		gbc_namePanel.anchor = GridBagConstraints.LINE_START;
		gbc_namePanel.gridwidth = 5;
		gbc_namePanel.gridx = 1;
		gbc_namePanel.gridy = 0;
		add(namePanel, gbc_namePanel);
		
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
		JLabel connectLabel = new JLabel();
		connectLabel.setHorizontalAlignment(SwingConstants.CENTER);
		connectLabel.setOpaque(false);
		connectLabel.setIcon(connectIcon);
		connectPanel.add(connectLabel);
		
		JTextArea nameUser = new JTextArea(name);
		nameUser.setCursor(new Cursor(Cursor.HAND_CURSOR));
		nameUser.setEditable(false);
		nameUser.setHighlighter(null);
		nameUser.setBorder(null);
		nameUser.setOpaque(false);
		namePanel.add(nameUser);
				
		super.addMouseListener(new MouseListener() {
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

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
        });
	}
	

}