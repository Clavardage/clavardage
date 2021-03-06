package clavardage.view.alert;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.text.DefaultHighlighter;

import clavardage.model.exceptions.UserNotConnectedException;
import clavardage.view.Application;
import clavardage.view.MessageWindow;
import clavardage.view.listener.ActionSendMessage;
import clavardage.view.listener.AdapterLayout;
import clavardage.view.main.DestinataireJPanel;
import clavardage.view.mystyle.MyDate;
import clavardage.view.mystyle.MyDayInfo;
import clavardage.view.mystyle.MyInfoPanel;
/**
 * @author Célestine Paillé
 */
public class ActionSetNameGroup implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		setNameGroup();
		Application.getMessageWindow().getGroupsContainer().validate();		
	}
	
	public static void setNameGroup() {
		MessageWindow w = Application.getMessageWindow();
		int maxLength = 40;
		String currentName = w.getNameDestinataire().getText();
		
		w.getNameDestinataire().addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
            	/* remove new line due to key Enter */
				w.getNameDestinataire().setText(w.getNameDestinataire().getText().replaceAll("\r", "").replaceAll("\n", ""));
            	
            	/* change the name only if the new name respect constraints */
            	if (w.getNameDestinataire().getText().isBlank() | w.getNameDestinataire().getText().isEmpty() | (w.getNameDestinataire().getText().length() > maxLength) ) {
            		w.getNameDestinataire().setText(currentName);
            	}
            	
            	/* change the name in database */
            	for (DestinataireJPanel group : w.getAllGroups()) {
            		if (group.getIdDestinataire() == w.getDiscussionDisplay().getIdConversation()) {
            			group.setNameDestinataire(w.getNameDestinataire().getText());
            		}
            	}
            	
            	/* add an InfoPanel if the name has changed with a DayPanel if it is necessary */
            	if (!(currentName.equals(w.getNameDestinataire().getText()))) {
					try {
						MyDate date = new MyDate();
						boolean newDay = ActionSendMessage.needDayPanel(date, w.getDiscussionDisplay());
						if (newDay) {
		            		if (w.getDiscussionDisplay().isEmptyDiscussion()) {
		            			w.getDiscussionDisplay().startConversation();
		            		}
		            		w.getDiscussionDisplay().add(new MyDayInfo(date));
						}
						w.getDiscussionDisplay().add(new MyInfoPanel(currentName, w.getNameDestinataire().getText()));
					} catch (UserNotConnectedException e1) {
						e1.printStackTrace();
					}

            	}
            	w.getNameDestinataire().setEditable(false);
            	w.getNameDestinataire().setHighlighter(null);
				w.getNameDestinataire().setForeground(Application.getBLUE());
            	w.getNameDestinataire().removeFocusListener(this);
    			AdapterLayout.redimConv();

			}
			
			@Override
			public void focusGained(FocusEvent e) {
				w.getNameDestinataire().setHighlighter(new DefaultHighlighter());	
				w.getNameDestinataire().setForeground(Application.getPURPLE());
				w.getNameDestinataire().selectAll();
				w.getNameDestinataire().setEditable(true);
				w.getNameDestinataire().setSelectedTextColor(Application.getYELLOW());
				w.getNameDestinataire().getCaret().setVisible(true);
				w.getNameDestinataire().setCaretColor(Application.getBLUE());
			}
		});
		
		w.getNameDestinataire().addKeyListener((KeyListener) new KeyAdapter() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	            if(e.getKeyCode() == KeyEvent.VK_ENTER){
	            	w.getNorthDiscussion().requestFocus();
	            	w.getNameDestinataire().removeKeyListener(this);
	            }
	        }
	        @Override
	        public void keyTyped(KeyEvent e) {
	            if(w.getNameDestinataire().getText().length() > maxLength){
	        		e.consume();
	            }
	        }
	    });
		
		w.getNameDestinataire().requestFocus();
	}			

}
