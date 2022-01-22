package clavardage.view.main;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import clavardage.view.listener.AdapterLayout;
import clavardage.view.main.LoginWindow.TypeBuble;
import clavardage.view.mystyle.MyDate;
import clavardage.view.mystyle.MyRoundJTextArea;

@SuppressWarnings("serial")
public class MessageBuble extends JPanel {
	
	private JPanel zoneBuble ;
	private String text ;
	private TypeBuble type ;
	private MyRoundJTextArea buble;
	private JTextArea zoneDate;
	private MyDate dateMsg;
	private Dimension sizeBuble;
	private GridBagLayout gridBagLayout;
	
	public MessageBuble(TypeBuble type, String msg, MyDate date) {
		this.setOpaque(false);
		this.type = type ;
		this.text = msg;	
		this.dateMsg = date;
		
		this.buble = new MyRoundJTextArea(20);
		buble.setText(this.text);
		buble.setEditable(false);
		buble.setSelectedTextColor(Application.getYELLOW());
		buble.setMargin(new Insets(8, 10, 8, 10));
		
		this.sizeBuble = buble.getPreferredSize();

		this.zoneBuble = new JPanel();
		FlowLayout flowLayout = (FlowLayout) zoneBuble.getLayout(); flowLayout.setHgap(0); flowLayout.setVgap(2);
		zoneBuble.setOpaque(false);
		zoneBuble.setPreferredSize(new Dimension(0,0));
		zoneBuble.add(buble);
	
		int heightPanel = buble.getPreferredSize().height + (flowLayout.getVgap()*2);
		
		this.setMaximumSize(new Dimension (Toolkit.getDefaultToolkit().getScreenSize().width, heightPanel));
		this.setMinimumSize(new Dimension (0, heightPanel));
		
		this.zoneDate = new JTextArea(dateMsg.myHoureToString());
		zoneDate.setMargin(new Insets(12, 0, 0, 0));
		zoneDate.setEditable(false);
		zoneDate.setOpaque(false);
		zoneDate.setForeground(Application.getCOLOR_TEXT_EDIT());
		zoneDate.setFont(new Font("Dialog", Font.PLAIN, 10));
		zoneDate.setMaximumSize(new Dimension(20, heightPanel));
		zoneDate.setMinimumSize(new Dimension(0, heightPanel));
				
		gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{heightPanel, 0};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};

		GridBagConstraints gbc_zoneBuble= new GridBagConstraints();
		gbc_zoneBuble.insets = new Insets(0, 0, 0, 10);
		gbc_zoneBuble.fill = GridBagConstraints.BOTH;
		gbc_zoneBuble.gridy = 0;

		double a = 1.0, b = 2.0;
		if (this.type == TypeBuble.MINE) { //à droite
			flowLayout.setAlignment(FlowLayout.RIGHT);
			gridBagLayout.columnWeights = new double[]{0.0, a, b, Double.MIN_VALUE};
			gbc_zoneBuble.gridx = 2;
		} else {
			flowLayout.setAlignment(FlowLayout.LEFT); //à gauche + couleur identique qu'importe le theme
			gridBagLayout.columnWeights = new double[]{0.0, b, a, Double.MIN_VALUE};
			gbc_zoneBuble.gridx = 1;
			buble.setBackground(Application.getBLUE());
			buble.setForeground(Application.COLOR_TEXT_THEIR_MESSAGE);
		}
		
		this.setLayout(gridBagLayout);
		add(zoneBuble, gbc_zoneBuble);
		
		GridBagConstraints gbc_zoneDate= new GridBagConstraints();
		gbc_zoneDate.insets = new Insets(0, 0, 0, 5);
		gbc_zoneDate.fill = GridBagConstraints.BOTH;
		gbc_zoneDate.gridy = 0;
		gbc_zoneDate.gridx = 0;
		
		add(zoneDate, gbc_zoneDate);
		
		AdapterLayout.redimMessage(AdapterLayout.getSizeConv(), this);
	}
	
	public void setColorPanel() {
		if (this.type == TypeBuble.MINE) {
			buble.setBackground(Application.COLOR_MINE_MESSAGE);
			buble.setForeground(Application.COLOR_TEXT_MINE_MESSAGE);
		}
	}
	
	public String getDay() {
		return dateMsg.getTheDay();
	}
	
	public String getText() {
		return text;
	}

	public MyDate getDate() {
		return dateMsg;
	}

	public MyRoundJTextArea getBuble() {
		return buble;
	}

	public Dimension getSizeBuble() {
		return sizeBuble;
	}

	public GridBagLayout getGridBagLayout() {
		return gridBagLayout;
	}

}
