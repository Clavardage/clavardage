package clavardage.view.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import clavardage.view.main.Application.ColorThemeApp;
import clavardage.view.main.Login.TypeBuble;

public class MessageBuble extends JPanel {
	
	private JPanel zone ;
	private String text ;
	private TypeBuble type ;
	private int heightPanel ;
	private JTextArea buble;
	private Dimension sizeBuble;
	
	public MessageBuble(TypeBuble type, String msg) {
		this.type = type ;
		this.text = msg;		
		this.setOpaque(false);

		buble = new MyRoundJTextArea(20);
		buble.setText(this.text);
		buble.setEditable(false);
		buble.setMargin(new Insets(8, 10, 8, 10));
//			buble.setLineWrap(true);
//			buble.setWrapStyleWord(true);

		zone = new JPanel();
		//zone.setLayout(new BoxLayout(zone, BoxLayout.X_AXIS));

		FlowLayout flowLayout = (FlowLayout) zone.getLayout();
		flowLayout.setHgap(0);
		flowLayout.setVgap(2);
		zone.setOpaque(false);
		zone.setPreferredSize(new Dimension(0,0));
		zone.add(buble);
	
		this.heightPanel = buble.getPreferredSize().height + (flowLayout.getVgap()*2);
		
		this.setMaximumSize(new Dimension (Toolkit.getDefaultToolkit().getScreenSize().width, this.heightPanel));
		this.setMinimumSize(new Dimension (0, this.heightPanel));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{this.heightPanel, 0};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		
		GridBagConstraints gbc_textPane = new GridBagConstraints();
		gbc_textPane.insets = new Insets(0, 0, 0, 10);
		gbc_textPane.fill = GridBagConstraints.BOTH;
		gbc_textPane.gridy = 0;

		double a = 1.0;
		double b = 2.0;
		if (this.type == TypeBuble.MINE) {
			flowLayout.setAlignment(FlowLayout.RIGHT);
			gridBagLayout.columnWeights = new double[]{a, b, Double.MIN_VALUE};
			gbc_textPane.gridx = 1;
		} else {
			flowLayout.setAlignment(FlowLayout.LEFT);
			gridBagLayout.columnWeights = new double[]{b, a, Double.MIN_VALUE};
			gbc_textPane.gridx = 0;
			buble.setBackground(Application.COLOR_BLUE);
			buble.setForeground(Application.COLOR_TEXT_THEIR_MESSAGE);
		}

		this.setLayout(gridBagLayout);
		add(zone, gbc_textPane);		
	}
	
	public void setColorPanel() {
		if (this.type == TypeBuble.MINE) {
			buble.setBackground(Application.COLOR_MINE_MESSAGE);
			buble.setForeground(Application.COLOR_TEXT_MINE_MESSAGE);
		}
	}
	

}
