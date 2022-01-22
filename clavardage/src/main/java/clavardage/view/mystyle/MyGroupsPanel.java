package clavardage.view.mystyle;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import clavardage.controller.Clavardage;
import clavardage.view.listener.ActionSetNameGroup;
import clavardage.view.listener.MouseOpenConversation;
import clavardage.view.main.Application;
import clavardage.view.main.DestinataireJPanel;
import clavardage.view.main.MessageWindow;
import clavardage.view.main.MessageWindow.Destinataire;

@SuppressWarnings("serial")
public class MyGroupsPanel extends JPanel {
	
	public MyGroupsPanel(MessageWindow window, MyJScrollPane groupsContainer) throws IOException {
		super();
		
		setOpaque(false);
		setLayout(new BorderLayout(0, 0));
		
		JPanel northGroups = new JPanel();
		northGroups.setOpaque(false);
		
		GridBagLayout gbl_northGroups = new GridBagLayout();
		gbl_northGroups.columnWidths = new int[]{10, 10, 10, 10, 10, 10, 10, 10, 0};
		gbl_northGroups.rowHeights = new int[]{21};
		gbl_northGroups.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_northGroups.rowWeights = new double[]{0.0};
		northGroups.setLayout(gbl_northGroups);

		MyTitle titleGroups = new MyTitle("Groups");
		GridBagConstraints gbc_titleGroups = new GridBagConstraints();
		gbc_titleGroups.gridwidth = 7;
		gbc_titleGroups.fill = GridBagConstraints.BOTH;
		gbc_titleGroups.gridx = 0;
		gbc_titleGroups.gridy = 0;
		northGroups.add(titleGroups, gbc_titleGroups);
		
		Image addGroupImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/addGroups.png")).getScaledInstance(13, 13, Image.SCALE_SMOOTH);
		ImageIcon addGroupIcon = new ImageIcon(addGroupImage, "Add Group Button");
		Image addGroupImageHover =ImageIO.read(Clavardage.getResourceStream("/img/assets/addGroups.png")).getScaledInstance(15, 15, Image.SCALE_SMOOTH);
		ImageIcon addGroupIconHover = new ImageIcon(addGroupImageHover, "Add Group Button Hover");
		MyJButton addGroup = new MyJButton(addGroupIcon,addGroupIconHover);

		
		
		addGroup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!Application.getMessageWindow().isAlertOpen() ) {
					try {
						window.addNewGroupToList("New Group", true);
						DestinataireJPanel newGroup = (DestinataireJPanel) window.getListGroups().getComponent( window.getListGroups().getComponentCount()-1) ;
						MouseOpenConversation.openConversation(newGroup.getNameDestinataire(), newGroup.getIdDestinataire(), Destinataire.Group);
						ActionSetNameGroup.setNameGroup();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					groupsContainer.validate();
				}
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
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				groupsContainer.getVerticalScrollBar().setValue(0);				
			}
		});
	}
}
