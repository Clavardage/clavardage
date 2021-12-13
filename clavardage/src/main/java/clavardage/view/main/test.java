package clavardage.view.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class test extends JPanel {
	
	private JFrame frame ;
	private JPanel winfow ;
	
	private JPanel message1 ;
	private JPanel zone1 ;
	private JTextArea buble1;
	
	private JPanel message2 ;
	private JPanel zone2 ;
	private JTextArea buble2;
	private JPanel panel;
	private JPanel panel_1;
	
	
	public test() {
		
		frame = new JFrame();
		frame.setSize(818, 527);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setMinimumSize(new Dimension(400,400));
		frame.setVisible(true);
		
		winfow = new JPanel();
		frame.setContentPane(winfow);
		winfow.setLayout(new BoxLayout(winfow, BoxLayout.Y_AXIS));
		
		message1 = new JPanel();
		message1.setMinimumSize(new Dimension (0, 36));
		GridBagLayout gbl_message1 = new GridBagLayout();
		gbl_message1.columnWidths = new int[]{10, 10, 0};
		gbl_message1.rowHeights = new int[]{264, 0};
		gbl_message1.columnWeights = new double[]{2.0, 1.0, Double.MIN_VALUE};
		gbl_message1.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		message1.setLayout(gbl_message1);

		winfow.add(message1);
		
				buble1 = new JTextArea();
				buble1.setLineWrap(true);
				buble1.setText("Hello, comment tu vas mega super bien ?");
				buble1.setMargin(new Insets(8, 10, 8, 10));
				buble1.setBackground(Application.COLOR_BLUE);
				buble1.setForeground(Application.COLOR_TEXT_THEIR_MESSAGE);
				
						zone1 = new JPanel();
						zone1.setPreferredSize(new Dimension(0, 10));
						zone1.setMinimumSize(new Dimension(0, 0));
						zone1.setOpaque(false);
						GridBagConstraints gbc_zone1 = new GridBagConstraints();
						gbc_zone1.fill = GridBagConstraints.BOTH;
						gbc_zone1.insets = new Insets(0, 0, 0, 5);
						gbc_zone1.gridx = 0;
						gbc_zone1.gridy = 0;
						message1.add(zone1, gbc_zone1);
						zone1.setLayout(new BoxLayout(zone1, BoxLayout.X_AXIS));
						zone1.add(buble1);
		
		panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		panel_1.setPreferredSize(new Dimension(50, 50));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.anchor = GridBagConstraints.WEST;
		gbc_panel_1.fill = GridBagConstraints.VERTICAL;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 0;
		message1.add(panel_1, gbc_panel_1);

		buble2 = new JTextArea();
		buble2.setLineWrap(true);
		buble2.setWrapStyleWord(true);
		buble2.setSize(new Dimension(190, 22));
		buble2.setText("Ceci est la largeur maximum d'un message, elle fait environ 2/3 de la zone de dialogue.  Il n'y a pas de longueur maximum pour un message.");
		buble2.setMargin(new Insets(8, 10, 8, 10));
		buble2.setBackground(Application.COLOR_BLUE);
		buble2.setForeground(Application.COLOR_TEXT_THEIR_MESSAGE);

		zone2 = new JPanel();
		zone2.setOpaque(false);
		zone2.setPreferredSize(new Dimension(0,0));
		zone2.setLayout(new BoxLayout(zone2, BoxLayout.X_AXIS));
		
		zone2.add(buble2);
		
		message2 = new JPanel();
		message2.setMinimumSize(new Dimension (0, 36));
		message2.setLayout(new BorderLayout(0, 0));
		message2.add(zone2);
		
		winfow.add(message2);
		
		panel = new JPanel();
		panel.setMinimumSize(new Dimension(50, 50));
		message2.add(panel, BorderLayout.EAST);

		
		

	}

}
