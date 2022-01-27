package clavardage.view.alert.popup;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import clavardage.controller.Clavardage;
import clavardage.view.Application;

/**
 * 
 * @author Célestine Paillé
 */
public class ActionLogUpdated {
		
		private JFrame popup;
		private JLabel info;
		
		/**
		 * 
		 * @param oldName
		 * @param newName
		 */
		public ActionLogUpdated(String oldName, String newName) {

			info = new JLabel(oldName + " is now called " + newName, SwingConstants.CENTER);
			info.setFont(new Font("Tahoma", Font.PLAIN, 20));
			info.setForeground(Application.getBLUE());
			Dimension dimPopup = new Dimension(info.getPreferredSize().width+200, 100);
			
			popup = new JFrame();
			popup.setTitle("Someone changed his username"); 
			try {popup.setIconImage(ImageIO.read(Clavardage.getResourceStream("/img/icons/icon.png")));} 
			catch (IOException e1) {e1.printStackTrace();}
			popup.setSize(dimPopup);
			popup.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			popup.setMinimumSize(dimPopup);
			popup.setBackground(Application.getCOLOR_BACKGROUND());
			popup.setContentPane(info);
			popup.setResizable(false);
			popup.setLocationRelativeTo(Application.getMessageWindow());
			popup.setVisible(true);
			popup.addWindowFocusListener(new WindowFocusListener() {
				@Override
				public void windowGainedFocus(WindowEvent e) {}

				@Override
				public void windowLostFocus(WindowEvent e) {popup.requestFocus();}
			});
			
		}
}
