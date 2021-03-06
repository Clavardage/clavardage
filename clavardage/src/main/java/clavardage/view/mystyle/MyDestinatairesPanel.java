package clavardage.view.mystyle;

import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JPanel;

/**
 * @author Célestine Paillé
 */
@SuppressWarnings("serial")
public class MyDestinatairesPanel extends JPanel {
	
	public MyDestinatairesPanel(MyUsersPanel users, MyGroupsPanel groups) throws IOException {
		
		super();
		setOpaque(false);
		setLayout(new GridLayout(2, 1, 0, 0));

		add(users);
		add(groups);
	}
}
