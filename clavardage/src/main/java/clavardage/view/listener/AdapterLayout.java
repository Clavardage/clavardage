package clavardage.view.listener;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import clavardage.view.main.Application;
import clavardage.view.main.MessageBuble;
import clavardage.view.mystyle.MyAlertMessage;
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
		if (Application.getMessageWindow().getDiscussionDisplay().isEmptyDiscussion()) {
			redimAlert();
		} else {
			redimConv();
		}		
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
	
	public static void redimAlert() {
		Dimension sizeConv = getSizeConv();	
		MyAlertMessage alert = (MyAlertMessage) Application.getMessageWindow().getDiscussionDisplay().getComponent(0);
		Dimension alertDimension = new Dimension(sizeConv.width-60, alert.getMaxSize().height);
		if (alertDimension.width > alert.getMaxSize().width) {alert.setPreferredSize(new Dimension(alert.getMaxSize()));} else {alert.setPreferredSize(alertDimension);}
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
				int nbLine = (((MessageBuble) panel).getSizeBuble().width/(maxWidthBuble-marginsHorizontal));

				if (nbLine > 10) {nbLine++;}				
				buble.setLineWrap(true);
				buble.setWrapStyleWord(true);
				buble.setPreferredSize(new Dimension(maxWidthBuble, marginsVertical+(heightWord*nbLine)));
				((MessageBuble) panel).getGridBagLayout().rowHeights = new int[]{marginsVertical+vgapsPanel+(heightWord*nbLine), 0};
				panel.setMaximumSize(new Dimension(sizeConv.width, marginsVertical+vgapsPanel+(heightWord*nbLine)));
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