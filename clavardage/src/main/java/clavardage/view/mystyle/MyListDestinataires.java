package clavardage.view.mystyle;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * @author Célestine Paillé
 */
@SuppressWarnings("serial")
public class MyListDestinataires extends JPanel {
	
	public MyListDestinataires() {
		setBorder(new EmptyBorder(0, 20, 10, 20));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

}
