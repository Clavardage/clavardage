package clavardage.view.main;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class MyJScrollPane extends JScrollPane {
	
	public MyJScrollPane(Component o) {
		super(o);
		getVerticalScrollBar().setUI(new MyJScrollBarUI());
		setBorder(null);
		setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	}
}
