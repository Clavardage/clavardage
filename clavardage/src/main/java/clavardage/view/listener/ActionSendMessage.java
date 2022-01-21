package clavardage.view.listener;

import java.util.UUID;

import clavardage.controller.authentification.AuthOperations;
import clavardage.controller.gui.MainGUI;
import clavardage.model.exceptions.UserNotConnectedException;
import clavardage.view.main.Application;
import clavardage.view.main.LoginWindow.TypeBuble;
import clavardage.view.main.MessageBuble;
import clavardage.view.main.MessageWindow;
import clavardage.view.main.MessageWindow.Destinataire;
import clavardage.view.main.MessagesPanel;
import clavardage.view.mystyle.MyDate;
import clavardage.view.mystyle.MyDayInfo;

public class ActionSendMessage {
	
	/**
	 * Display the new message on the discussion panel and reset JTextField.
	 * */
	public static void sendMessage(int mode) {
		MessageWindow w = Application.getMessageWindow();

		/*send only if editMsg is not in default mode and there is something to send*/
		if   ( (!w.getEditMsg().isEmptyText()) & (!(w.getEditMsg().getText().isEmpty() | w.getEditMsg().getText().isBlank())) )   {

			/* create the new message */
			MyDate date = new MyDate();
			MessageBuble msg = new MessageBuble(TypeBuble.MINE,w.getEditMsg().getText(), date);
			msg.setColorPanel(); //necessary because it is send while using the app

			/* see if we need a DayPanel */
			boolean newDay = needDayPanel(date, w.getDiscussionDisplay());
			MyDayInfo day = new MyDayInfo(date);

			/* add the msg to the discussion of the chosen destinataire */
			Destinataire typeCurrentConversation = w.getDiscussionDisplay().getTypeConversation();
			UUID idCurrentConversation = w.getDiscussionDisplay().getIdConversation();
			MessagesPanel currentConversation = null;

			if (typeCurrentConversation == Destinataire.User) {
				for (MessagesPanel conv : w.getAllMessagesUsers()) {
					if (conv.getIdConversation().equals(idCurrentConversation)) {
						currentConversation = conv ;
					}
				}
			} else {
				for (MessagesPanel conv : w.getAllMessagesGroups()) {
					if (conv.getIdConversation().equals(idCurrentConversation)) {
						currentConversation = conv ;
					}
				}
			}
			if (w.getDiscussionDisplay().isEmptyDiscussion()) {
				currentConversation.startConversation(); //we start the conversation if it is the first msg
			}
			if (newDay) {
				currentConversation.add(day);  //we add the DayPanel if we need it
			}
			currentConversation.add(msg); //we add the new msg

			w.getMessageContainer().validate();

			if (currentConversation.getTypeConversation() == Destinataire.User) {
				try {
					MainGUI.sendMessageInConversation(MainGUI.getConversationUUIDByTwoUsersUUID(AuthOperations.getConnectedUser().getUUID(), currentConversation.getIdConversation()), w.getEditMsg().getText());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (mode == 1) { // if it send with the button sendMsg 
				w.getEditMsg().setEmptyText(true);
			} else { // if it send with the keyboard 
				w.getEditMsg().setText("");
			}
		}
	}

	public static boolean needDayPanel(MyDate date, MessagesPanel conv) {
		boolean newDay = false;
		if (!conv.isEmptyDiscussion() && (conv.getComponentCount() != 0)) {
			int i = conv.getComponentCount()-1 ;
			while (!(conv.getComponent(i).getClass().getName().equals("clavardage.view.mystyle.MyDayInfo"))) {i--;}
			MyDayInfo lastDateDisplay = (MyDayInfo) conv.getComponent(i);
			if (!(lastDateDisplay.getDate().getTheDay().equals(date.getTheDay()))) {
				newDay = true ; //we need it if the date msg is not on the same day of the last one
			} else {
				newDay = false ;
			}
		} else {
			newDay = true ; //we need it if the date msg is the first msg of the discussion
		}
		return newDay;
	}
	
	public static void receiveMessage(String text, UUID idContact) throws UserNotConnectedException {
		MessageWindow w = Application.getMessageWindow();

	    /* create the new message */
	    MyDate date = new MyDate();
	    MessageBuble msg = new MessageBuble(TypeBuble.THEIR, text, new MyDate());

	    /* see if we need a DayPanel */
	    boolean newDay = ActionSendMessage.needDayPanel(date, w.getDiscussionDisplay());
	    MyDayInfo day = new MyDayInfo(date);

	    /* add the msg to the discussion of the destinataire */
	    MessagesPanel contactConversation = null;
	    for (MessagesPanel conv : w.getAllMessagesUsers()) {
	        if (conv.getIdConversation().equals(idContact)) {
	            contactConversation = conv ;
	        }
	    }

	    if (contactConversation.isEmptyDiscussion()) {
	        contactConversation.startConversation(); //we start the conversation if it is the first msg
	    }
	    if (newDay) {
	        contactConversation.add(day);  //we add the DayPanel if we need it
	    }
	    contactConversation.add(msg); //we add the new msg

	    w.getMessageContainer().validate();
	}

}
