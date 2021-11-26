package clavardage.view.main;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;

public class SectionTextJPanel extends JPanel {
	private JTextField textSection;
	private JTextArea titleSection;
	
	public SectionTextJPanel(String title) {
		super();
		
		this.setOpaque(false);
		this.setMaximumSize(new Dimension (Toolkit.getDefaultToolkit().getScreenSize().width, 120));
		this.setMinimumSize(new Dimension (0, 120));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	
		this.add(createMargin(0,5));

		titleSection = new JTextArea(title);
		titleSection.setOpaque(false);
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
		
		textSection = new MyRoundJTextField(40);
		textSection.setPreferredSize(new Dimension(0,40));
		textSection.setMargin(new Insets(0, 10, 0, 10));
		this.add(textSection);
		
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
		textSection.setBackground(bg);
		textSection.setForeground(fg);
	}
}
