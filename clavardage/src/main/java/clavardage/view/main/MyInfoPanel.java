package clavardage.view.main;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;

import clavardage.controller.authentification.AuthOperations;
import clavardage.model.exceptions.UserNotConnectedException;

public class MyInfoPanel extends JPanel {
	
	private JLabel zoneText ;
	
	//Abstract
	public MyInfoPanel(int height, int Vgap, String text) {
		super();
		this.setOpaque(false);
		this.setMaximumSize(new Dimension (Toolkit.getDefaultToolkit().getScreenSize().width, height));
		this.setMinimumSize(new Dimension (0, height));
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setVgap(Vgap);

		zoneText = new JLabel(text);
		zoneText.setFont(new Font("Dialog", Font.PLAIN, 12));
		zoneText.setForeground(Application.COLOR_TEXT_EDIT);
		add(zoneText);
	}
	
	//Leave Group
	public MyInfoPanel() throws UserNotConnectedException {
		this(20, 0, AuthOperations.getConnectedUser().getLogin() + " left the group");
	}
	
	//Add someone on the Group
	public MyInfoPanel(DestinataireJPanel user) throws UserNotConnectedException {
		this(20, 0, AuthOperations.getConnectedUser().getLogin() + " added " + user.getNameDestinataire() + " on the group");
	}
	
	//Change the name of the group
	public MyInfoPanel(String oldName, String newName) throws UserNotConnectedException {
		this(20, 0, AuthOperations.getConnectedUser().getLogin() + " changed the name of the group : " + oldName + " becomes " + newName);
	}

}
