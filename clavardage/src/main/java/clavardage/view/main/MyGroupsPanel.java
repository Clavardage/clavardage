package clavardage.view.main;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import clavardage.model.exceptions.UserNotConnectedException;
import clavardage.view.main.MessageWindow.Destinataire;

public class MyGroupsPanel extends JPanel {
	
	public MyGroupsPanel(JPanel northGroups, MyTitle titleGroups, MyJButton addGroup, MyJScrollPane groupsContainer) throws IOException {
		super();
		
		MessageWindow window = Application.getMessageWindow(); 
		setOpaque(false);
		setLayout(new BorderLayout(0, 0));
		
		northGroups.setOpaque(false);
		
		GridBagLayout gbl_northGroups = new GridBagLayout();
		gbl_northGroups.columnWidths = new int[]{10, 10, 10, 10, 10, 10, 10, 10, 0};
		gbl_northGroups.rowHeights = new int[]{21};
		gbl_northGroups.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_northGroups.rowWeights = new double[]{0.0};
		northGroups.setLayout(gbl_northGroups);

		GridBagConstraints gbc_titleGroups = new GridBagConstraints();
		gbc_titleGroups.gridwidth = 7;
		gbc_titleGroups.fill = GridBagConstraints.BOTH;
		gbc_titleGroups.gridx = 0;
		gbc_titleGroups.gridy = 0;
		northGroups.add(titleGroups, gbc_titleGroups);
		
		addGroup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					window.addNewGroupToList("New Group", true);
					DestinataireJPanel newGroup = (DestinataireJPanel) window.getListGroups().getComponent( window.getListGroups().getComponentCount()-1) ;
					window.openConversation(newGroup.getNameDestinataire(), newGroup.getIdDestinataire(), Destinataire.Group);
					window.setNameGroup();
				} catch (IOException | UserNotConnectedException e1) {
					e1.printStackTrace();
				}
				groupsContainer.validate();
			}
		});

		GridBagConstraints gbc_addGroup = new GridBagConstraints();
		gbc_addGroup.fill = GridBagConstraints.BOTH;
		gbc_addGroup.gridx = 7;
		gbc_addGroup.gridy = 0;
		northGroups.add(addGroup, gbc_addGroup);
		
		add(northGroups, BorderLayout.NORTH);

		add(groupsContainer);
		
		titleGroups.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				groupsContainer.getVerticalScrollBar().setValue(0);				
			}
		});
	}
}
