package clavardage.view.mystyle;

import java.awt.Insets;

@SuppressWarnings("serial")
public class MyEditMsg extends MyRoundJTextField {

	public MyEditMsg() {
		super(30);
		setText("Hello...");
		setMargin(new Insets(0, 10, 0, 10));
	}
}
