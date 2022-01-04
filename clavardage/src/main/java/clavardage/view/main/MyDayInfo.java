package clavardage.view.main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.Font;

public class MyDayInfo extends MyInfoPanel {
	private MyDate date ;
	
	public MyDayInfo(MyDate date) {
		super(30, 10, ((MyDate) date).myDateToString());
		this.date = date;
	}
	
	public MyDate getDate() {
		return date;
	}

}
