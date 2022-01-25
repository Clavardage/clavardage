package clavardage.view.listener;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import clavardage.view.main.Application;
import clavardage.view.main.MessageBuble;
import clavardage.view.mystyle.MyAlertMessage;
import clavardage.view.mystyle.MyAlertPanel;
import clavardage.view.mystyle.MyRoundJTextArea;

public class AdapterLayout implements ComponentListener {
		
	public AdapterLayout() {
	}
		
	@Override
	public void componentResized(ComponentEvent e) {
		redimWindow();
	}
	
	public static void redimWindow() {
		if(Application.getContentDisplay().getClass().getName().equals("clavardage.view.main.MessageWindow")) {
			redimBody();	
			redimDiscussion();
		}
	}
	
	public static void redimDiscussion() {
		if (Application.getMessageWindow().getMessageContainer().getViewport().getComponent(0).getClass().getName().equals("clavardage.view.mystyle.MyAlertPanel")) {
			redimAlertPanel();
		} else if (Application.getMessageWindow().getMessageContainer().getViewport().getComponent(0).getClass().getName().equals("clavardage.view.mystyle.MyChangeLogPanel")) {
//TODO			redimChangLog();
		} else {	
			if (Application.getMessageWindow().getDiscussionDisplay().isEmptyDiscussion()) {
				MyAlertMessage alert = (MyAlertMessage) Application.getMessageWindow().getDiscussionDisplay().getComponent(0);
				redimAlert(alert);
			} else {
				redimConv();
			}
		}
	}

	public static void redimAlertPanel() {
		MyAlertPanel panel = (MyAlertPanel) Application.getMessageWindow().getMessageContainer().getViewport().getComponent(0);

		MyAlertMessage alert1 = (MyAlertMessage) panel.getAlert().getComponent(0);
		redimAlert(alert1);
		
		MyAlertMessage alert2 = (MyAlertMessage) panel.getAlert().getComponent(1);
		redimAlert(alert2);
		
		MyAlertMessage alert3 = (MyAlertMessage) panel.getAlert().getComponent(2);
		redimAlert(alert3);
		
		if (alert2.getSizeFont() < alert3.getSizeFont()) {
			alert3.setSizeFont(alert2.getSizeFont());
		} else {
			alert2.setSizeFont(alert3.getSizeFont());
		}
		
		if (alert1.getSizeFont() < alert2.getSizeFont()) {
			alert3.setSizeFont(alert3.getSizeFont()-2);
			alert2.setSizeFont(alert2.getSizeFont()-2);
		}
		
		panel.revalidate();
	}

	public static void redimConv() {
		Dimension sizeConv = getSizeConv();	
		for (Component panel : Application.getMessageWindow().getDiscussionDisplay().getComponents()) {
			redimMessage(sizeConv, panel);
		}		
	}

	public static void redimBody() {
		Dimension sizebody = getSizeBody();
		Application.getMessageWindow().getBodyApp().getGbl_bodyApp().columnWidths = new int[]{(int) (sizebody.width*0.3), (int) (sizebody.width*0.7), 0};
		Application.getMessageWindow().getBodyApp().revalidate();		
	}
	
	public static void redimAlert(MyAlertMessage alert) {
		Dimension sizeConv = getSizeConv();	
		Dimension alertDimension = new Dimension(sizeConv.width-60, alert.getMaxSize().height);
		if (alertDimension.width >= alert.getMaxSize().width) {
			if (alert.getSizeFont() < alert.getSizeFontChosen()) {
		 		JLabel alertWithBiggerFont = new JLabel(alert.getInfo());
		 		alertWithBiggerFont.setFont(new Font("Tahoma", Font.PLAIN, alert.getSizeFont()+1));
		 		int widthWithBiggerFOnt = alertWithBiggerFont.getPreferredSize().width ;
				while (alertDimension.width >= widthWithBiggerFOnt) {
					alert.setSizeFont(alert.getSizeFont()+1);
			 		alertWithBiggerFont = new JLabel(alert.getInfo());
			 		alertWithBiggerFont.setFont(new Font("Tahoma", Font.PLAIN, alert.getSizeFont()+1));
			 		widthWithBiggerFOnt = alertWithBiggerFont.getPreferredSize().width ;
				}
			}
		} else {
			while ((sizeConv.width-60) < alert.getMaxSize().width && alert.getSizeFont()>0) {alert.setSizeFont(alert.getSizeFont()-1);}
		}
		alert.setPreferredSize(alert.getMaxSize());
		alert.revalidate();
	}

	public static void redimMessage(Dimension sizeConv, Component panel) {
		Dimension size = panel.getPreferredSize();
		panel.setMaximumSize(new Dimension(sizeConv.width, size.height));
		panel.setMinimumSize(new Dimension(sizeConv.width, size.height));
		panel.revalidate();
		int maxWidthBuble = 2*(sizeConv.width - 45)/3;

		if (panel.getClass().getName().equals("clavardage.view.main.MessageBuble")) {
			MyRoundJTextArea buble = ((MessageBuble) panel).getBuble();
			if (maxWidthBuble > ((MessageBuble) panel).getSizeBuble().width) {
				buble.setPreferredSize(((MessageBuble) panel).getSizeBuble());
			} else {
				int marginsVertical = 16, marginsHorizontal = 20, vgapsPanel = 4;
				int heightWord = ((MessageBuble) panel).getSizeBuble().height-marginsVertical ;
				int nbLine = (((MessageBuble) panel).getSizeBuble().width/(maxWidthBuble-marginsHorizontal))+1;
				int heightBuble = marginsVertical+(heightWord*nbLine); int heightPanel = heightBuble+vgapsPanel;
								
				if (nbLine > 10) {nbLine++;}				
				buble.setLineWrap(true);
				buble.setWrapStyleWord(true);
				buble.setPreferredSize(new Dimension(maxWidthBuble, heightBuble));
				
				((MessageBuble) panel).getGridBagLayout().rowHeights = new int[]{heightPanel, 0};
				panel.setMaximumSize(new Dimension(sizeConv.width, heightPanel));
				panel.revalidate();
			}
			buble.revalidate();
		}
	}
	
	public static Dimension getSizeBody() {
		return new Dimension(Application.getGlobalSize().width-16, Application.getGlobalSize().height-89);
	}
	
	public static Dimension getSizeConv() {
		Dimension sizebody = getSizeBody();
		int widthConv = (int) (sizebody.width * 0.7) - 61;
		int heightConv = sizebody.height - 71;
		return new Dimension(widthConv, heightConv);	
	}
	
	@Override
	public void componentMoved(ComponentEvent e) { }

	@Override
	public void componentShown(ComponentEvent e) { }

	@Override
	public void componentHidden(ComponentEvent e) {	}
	
}