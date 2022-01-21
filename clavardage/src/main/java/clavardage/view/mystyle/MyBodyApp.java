package clavardage.view.mystyle;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import clavardage.view.main.Application;

@SuppressWarnings("serial")
public class MyBodyApp extends JPanel {
	
	private GridBagLayout gbl_bodyApp;
	private GridBagConstraints gbc_destinataires, gbc_discussionContainer;
	
	public MyBodyApp(MyDestinatairesPanel destinatairePanel, MyDiscussionContainer discussionContainer ) {
		super();
		
//		/* Set the GridBagLayout */
//		gbl_bodyApp = new GridBagLayout();
//		gbl_bodyApp.columnWidths = new int[]{30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 0};
//		gbl_bodyApp.rowHeights = new int[]{100};
//		gbl_bodyApp.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
//		gbl_bodyApp.rowWeights = new double[]{1.0};
//		setLayout(gbl_bodyApp);
//
//		/* Add Destinataires with good constraints */
//		gbc_destinataires = new GridBagConstraints();
//		gbc_destinataires.fill = GridBagConstraints.BOTH;
//		gbc_destinataires.gridwidth = 3;
//		gbc_destinataires.gridx = 0;
//		gbc_destinataires.gridy = 0;
//		add(destinatairePanel, gbc_destinataires);
//
//		/* Add Discussion with good constraints */
//		gbc_discussionContainer = new GridBagConstraints();
//		gbc_discussionContainer.fill = GridBagConstraints.BOTH;
//		gbc_discussionContainer.gridwidth = 7;
//		gbc_discussionContainer.gridx = 3;
//		gbc_discussionContainer.gridy = 0;
//		add(discussionContainer, gbc_discussionContainer);
		
		
		/* Set the GridBagLayout */
		gbl_bodyApp = new GridBagLayout();
		gbl_bodyApp.columnWidths = new int[]{(int) (Application.getGlobalSize().width*0.3), (int) (Application.getGlobalSize().width*0.7), 0};
		gbl_bodyApp.rowHeights = new int[]{100};
		gbl_bodyApp.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_bodyApp.rowWeights = new double[]{1.0};
		setLayout(gbl_bodyApp);

		/* Add Destinataires with good constraints */
		gbc_destinataires = new GridBagConstraints();
		gbc_destinataires.fill = GridBagConstraints.BOTH;
		gbc_destinataires.gridwidth = 1;
		gbc_destinataires.gridx = 0;
		gbc_destinataires.gridy = 0;
		add(destinatairePanel, gbc_destinataires);

		/* Add Discussion with good constraints */
		gbc_discussionContainer = new GridBagConstraints();
		gbc_discussionContainer.fill = GridBagConstraints.BOTH;
		gbc_discussionContainer.gridwidth = 1;
		gbc_discussionContainer.gridx = 1;
		gbc_discussionContainer.gridy = 0;
		add(discussionContainer, gbc_discussionContainer);
		
	}

	public GridBagLayout getGbl_bodyApp() {
		return gbl_bodyApp;
	}


}
