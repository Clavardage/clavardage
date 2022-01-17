package clavardage.view.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import clavardage.view.main.LoginWindow.SectionText;
import clavardage.view.main.LoginWindow.TypeBuble;

public class SectionTextJPanel extends JPanel {
	private JTextField textSection;
	private JTextArea titleSection;
	private SectionText type;
	private JPanel password;
	
	public SectionTextJPanel(String title, String text, SectionText t) {
		super();
		this.type = t ;
		this.setOpaque(false);
		this.setMaximumSize(new Dimension (Toolkit.getDefaultToolkit().getScreenSize().width, 120));
		this.setMinimumSize(new Dimension (0, 120));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	
		this.add(createMargin(0,5));

		titleSection = new JTextArea(title);
		titleSection.setPreferredSize(new Dimension(0,50));
		titleSection.setEditable(false);
		titleSection.setHighlighter(null);
		titleSection.setBorder(null);
		titleSection.setOpaque(false);
		titleSection.setForeground(Application.COLOR_BLUE);
		titleSection.setFont(new Font("Dialog", Font.PLAIN, 20));
		titleSection.setBorder(new EmptyBorder(10, 15, 10, 15));
		this.add(titleSection);
		
		this.add(createMargin(0,5));
		
		if (type == SectionText.PW) {
			password = new MyRoundJPanel(40);
			password.setPreferredSize(new Dimension(0,40));
			password.setLayout(new BoxLayout(password, BoxLayout.X_AXIS));
			this.add(password);
			textSection = new JPasswordField(40);
			textSection.setPreferredSize(new Dimension(0,40));
			textSection.setOpaque(false);
			textSection.setBorder(null);
			password.add(createMargin(15,0));
			password.add(textSection);
			password.add(createMargin(15,0));
		} else {
			textSection = new MyRoundJTextField(40);
			textSection.setPreferredSize(new Dimension(0,40));
			textSection.setMargin(new Insets(0, 15, 0, 15));
			this.add(textSection);
		}

		textSection.setText(text);
		
		this.add(createMargin(0,20));

		
		
	}
	
	/**
	 * Create a margin.
	 * */
	private JPanel createMargin(int x, int y) {
		JPanel marge = new JPanel();
		marge.setPreferredSize(new Dimension(x,y));
		marge.setMaximumSize(marge.getPreferredSize());
		marge.setOpaque(false);
		return marge ;
	}
	
	public void setColorTextSession(Color bg, Color fg) {
		if (type == SectionText.PW) {
			password.setBackground(bg);
		} else {
			textSection.setBackground(bg);
		}
		textSection.setForeground(fg);
	}
	
	public String getText() {
		return textSection.getText();
	}
	
	public void setText(String t) {
		textSection.setText(t);
	}
	
	
}
