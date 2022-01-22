package clavardage.view.mystyle;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import clavardage.view.listener.ActionAddMember;
import clavardage.view.listener.ActionDisconnect;
import clavardage.view.listener.ActionLeaveGroup;
import clavardage.view.listener.ActionSetColorTheme;
import clavardage.view.listener.MouseFocusColor;
import clavardage.view.main.Application;
import clavardage.view.main.DestinataireJPanel;
import clavardage.view.main.MessageWindow.Destinataire;

@SuppressWarnings("serial")
public class MyAlertPanel extends JPanel {
	
	private MyJButtonText cancel, confirm;
	private JPanel container, alert, buttons;
	private AlertAction typeAlert;
	public enum AlertAction {LEAVEGROUP,  CHANGELOG, DISCONNECT, ADDMEMBER;}

	public MyAlertPanel() {
		super();
		cancel = new MyJButtonText("Cancel");
		confirm = new MyJButtonText("Confirm");
		container = new JPanel();
		alert = new JPanel();
		buttons = new JPanel();
		
		this.setBorder(new EmptyBorder(0, 0, 0, 0));
		this.setLayout(new GridBagLayout());
		this.add(container);
		
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		container.setBorder(new EmptyBorder(15, 15, 15, 15));
		container.setOpaque(false);
		container.add(alert);
		container.add(buttons);

		alert.setLayout(new BoxLayout(alert, BoxLayout.Y_AXIS));
		alert.setOpaque(false);

		buttons.setOpaque(false);
		buttons.add(cancel);
		buttons.add(createMargin(50,0));
		buttons.add(confirm);
		
		cancel.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				Application.getMessageWindow().getMessageContainer().setViewportView(Application.getMessageWindow().getDiscussionDisplay());
				ActionSetColorTheme.customDiscussionDisplay(Application.getColorThemeApp());
				if (Application.getMessageWindow().isConversationOpen()) {
					if (Application.getMessageWindow().getDiscussionDisplay().getTypeConversation() == Destinataire.Group) {
						Application.getMessageWindow().getEditNameGroup().setVisible(true);
						Application.getMessageWindow().getSettingsGroups().setVisible(true);
					}
					Application.getMessageWindow().getNewMsg().setVisible(true);
				}
				Application.getMessageWindow().getMessageContainer().validate();
				((MyJButtonText) e.getSource()).setForeground(Application.getBLUE());
				Application.getMessageWindow().getNorthDiscussion().setVisible(true);
				Application.getMessageWindow().setAlertOpen(false) ;

			}
		});
				
		confirm.addMouseListener(new MouseFocusColor());
		cancel.addMouseListener(new MouseFocusColor());
	}
	
	//leave alert & disconnect alert
	public MyAlertPanel(AlertAction type) {
		this();
		typeAlert = type;
		if (typeAlert == AlertAction.LEAVEGROUP) {
			MyAlertMessage message = new MyAlertMessage("You are about to leave the group");
			alert.add(message);
			MyAlertMessage details1 = new MyAlertMessage("You will no longer be able to send and recieve messages from this group", 18);
			alert.add(details1);
			MyAlertMessage details2 = new MyAlertMessage("You will lose acces to the messages of the conversation", 18);
			alert.add(details2);
			confirm.addActionListener(new ActionLeaveGroup());
		} else if (typeAlert == AlertAction.DISCONNECT) {
			MyAlertMessage message = new MyAlertMessage("You are about to log out");
			alert.add(message);
			MyAlertMessage details1 = new MyAlertMessage("", 0);
			alert.add(details1);
			MyAlertMessage details2 = new MyAlertMessage("", 0);
			alert.add(details2);
			confirm.addActionListener(new ActionDisconnect());
		}
	}
	
	//add user
	public MyAlertPanel(DestinataireJPanel user, JMenuItem item) {
		this();

		MyAlertMessage message = new MyAlertMessage("You are about to add " + user.getNameDestinataire() + " in the group");
		alert.add(message);
		MyAlertMessage details1 = new MyAlertMessage(user.getNameDestinataire() + " will be able to send and recieve messages from this group", 18);
		alert.add(details1);
		MyAlertMessage details2 = new MyAlertMessage(user.getNameDestinataire() + " will see all old messages of the conversation", 18);
		alert.add(details2);

		confirm.addActionListener(new ActionAddMember(item));
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

	public JPanel getAlert() {
		return alert;
	}

	public MyJButtonText getConfirm() {
		return confirm;
	}
	
	public JPanel getContainer() {
		return container;
	}

}
