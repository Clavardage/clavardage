package clavardage.view.listener;

import java.util.UUID;

import clavardage.controller.authentification.AuthOperations;
import clavardage.controller.gui.MainGUI;
import clavardage.model.exceptions.UserNotConnectedException;
import clavardage.model.objects.User;
import clavardage.view.Application;
import clavardage.view.Application.Destinataire;
import clavardage.view.Application.TypeBuble;
import clavardage.view.MessageWindow;
import clavardage.view.main.MessageBuble;
import clavardage.view.main.MessagesPanel;
import clavardage.view.mystyle.MyDate;
import clavardage.view.mystyle.MyDayInfo;
/**
 * Gathers the methods useful to display a new message.
 * 
 * @see #sendMessage(int)
 * @see #needDayPanel(MyDate, MessagesPanel)
 * @see #receiveMessage(String, UUID)
 * 
 * @author Célestine Paillé
 */
public class ActionSendMessage {
	
	/**
	 * Displays the new message on the discussion panel and reset JTextField. <br>
	 * Displays a day panel if we need it. <br>
	 * Keep the focus on the JTextField based on <code>mode</code>.
	 *
	 * @param mode 0 when JTextField loose the focus (when send with the button) or 1 when JTextField keep the focus (when send with keyboard ENTER)
	 *
	 * @see #needDayPanel(MyDate, MessagesPanel)
	 */
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
			
			w.getMessageContainer().getVerticalScrollBar().setValue(w.getMessageContainer().getVerticalScrollBar().getVisibleAmount());
		}
	}
	
	/**
	 * Displays the new message on the conversation associated with the user who send it.
	 * Displays a day panel if we need it.
	 * 
	 * @param text the text of the new message receive.
	 * @param idContact the UUID of the user who send this message
	 * 
	 * @see #needDayPanel(MyDate, MessagesPanel)
	 */
	public static void receiveMessage(String text, UUID idContact) {
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
	
	/**
	 * Compares the date of the message <code>date</code> to the last date displayed on <code>conv</code>.
	 * If <code>conv</code> doesn't have messages yet, we need a day panel. 
	 * 
	 * @param date of the message we want to display
	 * @param conv the conversation we want to display the message
	 * 
	 * @return true if we need a day panel or false if not
	 */
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

}
