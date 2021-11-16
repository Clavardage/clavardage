package clavardage.view.main;

import clavardage.controller.Clavardage;

import java.awt.*;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class UserJPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JPanel connectPanel = new JPanel();
	private JPanel namePanel = new JPanel();

	public UserJPanel(String name, boolean connect) throws IOException {
		super();
    	setBackground(Application.COLOR_BACKGROUND);
		setMaximumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, 30));
		setMinimumSize(new Dimension(0, 30));

		connectPanel.setBackground(Application.COLOR_BACKGROUND);
    	namePanel.setBackground(Application.COLOR_BACKGROUND);

		GridBagLayout gbl_userjpanel = new GridBagLayout();
		gbl_userjpanel.columnWidths = new int[]{20, 10, 10, 10, 10, 10, 0};
		gbl_userjpanel.rowHeights = new int[]{21};
		gbl_userjpanel.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_userjpanel.rowWeights = new double[]{0.0};
		setLayout(gbl_userjpanel);
				
		GridBagConstraints gbc_connectPanel = new GridBagConstraints();
		gbc_connectPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_connectPanel.gridx = 0;
		gbc_connectPanel.gridy = 0;
		add(connectPanel, gbc_connectPanel);
		
		GridBagConstraints gbc_namePanel = new GridBagConstraints();
		gbc_namePanel.anchor = GridBagConstraints.LINE_START;
		gbc_namePanel.gridwidth = 5;
		gbc_namePanel.gridx = 1;
		gbc_namePanel.gridy = 0;
		add(namePanel, gbc_namePanel);
		
		ImageIcon connectIcon;
		if (connect) {
			connectIcon = new ImageIcon(ImageIO.read(Clavardage.getResourceStream("/img/assets/userConnect.png")),"User is connected");
			connectIcon = new ImageIcon(connectIcon.getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH));
		} else {
			connectIcon = new ImageIcon(ImageIO.read(Clavardage.getResourceStream("/img/assets/userDisconnect.png")),"User is disconnected");
			connectIcon = new ImageIcon(connectIcon.getImage().getScaledInstance(12, 12, Image.SCALE_SMOOTH));
		}
		JLabel connectLabel = new JLabel();
		connectLabel.setBackground(Application.COLOR_BACKGROUND);
		connectLabel.setIcon(connectIcon);
		connectPanel.add(connectLabel);
		
		JTextArea nameUser = new JTextArea(name);
		nameUser.setBackground(Application.COLOR_BACKGROUND);
		namePanel.add(nameUser);
	}
}