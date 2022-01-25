package clavardage.view.mystyle;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import clavardage.controller.Clavardage;
import clavardage.view.Application;
import clavardage.view.listener.ActionSendMessage;

@SuppressWarnings("serial")
public class MyNewMsgPanel extends JPanel {
	
	public MyNewMsgPanel(MyEditMsg editMsg) throws IOException {
		super();
		setOpaque(false);
		setBorder(new EmptyBorder(10, 30, 10, 30));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		Image sendFileImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/sendFile.png")).getScaledInstance(14, 21, Image.SCALE_SMOOTH);
		ImageIcon sendFileIcon = new ImageIcon(sendFileImage, "Send File Button");
		Image sendFileImageHover =ImageIO.read(Clavardage.getResourceStream("/img/assets/sendFile.png")).getScaledInstance(16, 24, Image.SCALE_SMOOTH);
		ImageIcon sendFileIconHover = new ImageIcon(sendFileImageHover, "Send File Button Hover");
		MyJButton sendFile = new MyJButton(sendFileIcon,sendFileIconHover);
		
		Image sendPictureImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/sendPicture.png")).getScaledInstance(14, 21, Image.SCALE_SMOOTH);
		ImageIcon sendPictureIcon = new ImageIcon(sendPictureImage, "Send Picture Button");
		Image sendPictureImageHover =ImageIO.read(Clavardage.getResourceStream("/img/assets/sendPicture.png")).getScaledInstance(16, 24, Image.SCALE_SMOOTH);
		ImageIcon sendPictureIconHover = new ImageIcon(sendPictureImageHover, "Send Picture Button Hover");
		MyJButton sendPicture = new MyJButton(sendPictureIcon,sendPictureIconHover);
		
		Image sendMsgImage =ImageIO.read(Clavardage.getResourceStream("/img/assets/sendMsg.png")).getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon sendMsgIcon = new ImageIcon(sendMsgImage, "Send Msg Button");
		Image sendMsgImageHover =ImageIO.read(Clavardage.getResourceStream("/img/assets/sendMsg.png")).getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		ImageIcon sendMsgIconHover = new ImageIcon(sendMsgImageHover, "Send Msg Button Hover");
		MyJButton sendMsg = new MyJButton(sendMsgIcon,sendMsgIconHover);
				
		add(sendFile);

		add(createMargin(10,0));

		add(sendPicture);

		add(createMargin(10,0));
		
		editMsg.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if ( editMsg.getText().isEmpty() |  editMsg.getText().isBlank()) {
					editMsg.setEmptyText(true);
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				if (((MyRoundJTextField) editMsg).isEmptyText()) {
					editMsg.setText("");
					editMsg.setForeground(Application.getCOLOR_TEXT());
					editMsg.setEmptyText(false);
				}
			}
		});
		
		editMsg.addKeyListener((KeyListener) new KeyAdapter() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	            if(e.getKeyCode() == KeyEvent.VK_ENTER){
	            	ActionSendMessage.sendMessage(0);
	            }
	        }

	    });
		add(editMsg);

		add(createMargin(10,0));

		sendMsg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ActionSendMessage.sendMessage(1);
			}
		});
		add(sendMsg);
		
		setVisible(false);
	}
	
	/**
	 * Create a margin.
	 * */
	private JPanel createMargin(int x, int y) {
		JPanel marge = new JPanel();
		marge.setPreferredSize(new Dimension(x,y));
		marge.setMaximumSize(marge.getPreferredSize());
		return marge ;
	}

}
