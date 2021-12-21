package clavardage.view.main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

public class MyBodyApp extends JPanel {
	
	public MyBodyApp(JPanel destinatairePanel,JPanel discussionContainer ) {
		super();
		
		/* Set the GridBagLayout */
		GridBagLayout gbl_bodyApp = new GridBagLayout();
		gbl_bodyApp.columnWidths = new int[]{30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 0};
		gbl_bodyApp.rowHeights = new int[]{100};
		gbl_bodyApp.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_bodyApp.rowWeights = new double[]{1.0};
		setLayout(gbl_bodyApp);

		/* Add Destinataires with good constraints */
		GridBagConstraints gbc_destinataires = new GridBagConstraints();
		gbc_destinataires.insets = new Insets(0, 0, 0, 5);
		gbc_destinataires.fill = GridBagConstraints.BOTH;
		gbc_destinataires.gridwidth = 3;
		gbc_destinataires.gridx = 0;
		gbc_destinataires.gridy = 0;
		add(destinatairePanel, gbc_destinataires);

		/* Add Discussion with good constraints */
		GridBagConstraints gbc_discussionContainer = new GridBagConstraints();
		gbc_discussionContainer.fill = GridBagConstraints.BOTH;
		gbc_discussionContainer.gridwidth = 7;
		gbc_discussionContainer.gridx = 3;
		gbc_discussionContainer.gridy = 0;
		add(discussionContainer, gbc_discussionContainer);
		
	}


}
