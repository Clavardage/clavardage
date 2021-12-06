package clavardage.view.main;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.GridLayout;

public class test extends JFrame {
	
	public test() {
		this.setSize(1200, 800);
		this.setLocationRelativeTo(null);
		//this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		//this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(400,400));
		
		JPanel window = new JPanel();
		
		this.getContentPane().add(window);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[] {0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 2.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		window.setLayout(gridBagLayout);
		
		JPanel logPanel = new JPanel();
		
		GridBagConstraints gbc_logPanel = new GridBagConstraints();
		gbc_logPanel.insets = new Insets(20, 0, 20, 5);
		gbc_logPanel.fill = GridBagConstraints.BOTH;
		gbc_logPanel.gridx = 1;
		gbc_logPanel.gridy = 0;
		window.add(logPanel, gbc_logPanel);
		
		GridBagLayout gbl_logPanel = new GridBagLayout();
		gbl_logPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_logPanel.rowHeights = new int[] {30, 200, 30, 0};
		gbl_logPanel.columnWeights = new double[]{1.0, 3.0, 1.0, Double.MIN_VALUE};
		gbl_logPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0};
		logPanel.setLayout(gbl_logPanel);
		
		JPanel headPanel = new JPanel();
		headPanel.setPreferredSize(new Dimension(0, 0));
		GridBagConstraints gbc_headPanel = new GridBagConstraints();
		gbc_headPanel.fill = GridBagConstraints.BOTH;
		gbc_headPanel.insets = new Insets(0, 0, 5, 5);
		gbc_headPanel.gridx = 1;
		gbc_headPanel.gridy = 1;
		logPanel.add(headPanel, gbc_headPanel);
		
		GridBagLayout gbl_headPanel = new GridBagLayout();
		gbl_headPanel.columnWidths = new int[]{0, 300, 0, 0};
		gbl_headPanel.rowHeights = new int[]{10, 150, 10, 0};
		gbl_headPanel.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_headPanel.rowWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		headPanel.setLayout(gbl_headPanel);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.VERTICAL;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 1;
		headPanel.add(panel, gbc_panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel sections = new JPanel();
		JScrollPane sectionContainer = new JScrollPane(sections);
		GridBagConstraints gbc_sectionContainer = new GridBagConstraints();
		gbc_sectionContainer.insets = new Insets(0, 0, 20, 5);
		gbc_sectionContainer.fill = GridBagConstraints.BOTH;
		gbc_sectionContainer.gridx = 1;
		gbc_sectionContainer.gridy = 3;
		logPanel.add(sectionContainer, gbc_sectionContainer);
		
		
		
	}

}
