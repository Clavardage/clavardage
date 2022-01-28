package clavardage.view.mystyle;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * @author Célestine Paillé
 */
@SuppressWarnings("serial")
public class MyDestinataireContainer extends JPanel {
	
	public MyDestinataireContainer(MyDestinatairesPanel d) {
		super();
		setOpaque(false);
		setBorder(new EmptyBorder(5, 0, 15, 0));
		setLayout(new GridLayout(1, 0, 0, 0));
		add(d);
	}
}