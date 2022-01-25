package clavardage.view.alert.popup;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import clavardage.controller.Clavardage;
import clavardage.view.Application;
import clavardage.view.Application.LanguageApp;

public class ActionSetLanguage implements ActionListener {
	
	private LanguageApp language;
	private JFrame popup;
	private JLabel info;
	
	public ActionSetLanguage(LanguageApp l) {
		language = l;

		info = new JLabel("Do you really think we've had enough time?", SwingConstants.CENTER);
		info.setFont(new Font("Tahoma", Font.PLAIN, 20));
		info.setForeground(Application.getBLUE());
		Dimension dimPopup = new Dimension(info.getPreferredSize().width+200, 100);
		
		popup = new JFrame();
		popup.setTitle("Are-you kidding ?"); 
		try {popup.setIconImage(ImageIO.read(Clavardage.getResourceStream("/img/icons/icon.png")));} 
		catch (IOException e1) {e1.printStackTrace();}
		popup.setSize(dimPopup);
		popup.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		popup.setMinimumSize(dimPopup);
		popup.setBackground(Application.getCOLOR_BACKGROUND());
		popup.setContentPane(info);
		popup.setResizable(false);	
		popup.addWindowFocusListener(new WindowFocusListener() {
			@Override
			public void windowGainedFocus(WindowEvent e) {}

			@Override
			public void windowLostFocus(WindowEvent e) {popup.requestFocus();}
		});
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (language != LanguageApp.ENGLISH) {
			Application.setLanguageApp(language);
			Application.getMessageWindow().getEnglish().setSelected(true);
			((AbstractButton) e.getSource()).setSelected(false);
			popup.setLocationRelativeTo(Application.getMessageWindow());
			popup.setVisible(true);
		}
	}

}
