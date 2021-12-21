package clavardage.view.main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.Font;

public class MyDayPanel extends JPanel {
	
	public MyDayPanel(Date date) {
		super();
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setVgap(13);
		this.setMaximumSize(new Dimension (Toolkit.getDefaultToolkit().getScreenSize().width, 30));
		this.setMinimumSize(new Dimension (0, 30));
		this.setOpaque(false);
		JLabel zoneDate = new JLabel(((MyDate) date).myDateToString());
		zoneDate.setFont(new Font("Dialog", Font.PLAIN, 12));
		zoneDate.setForeground(Application.COLOR_TEXT_EDIT);
		add(zoneDate);
	}

}
