package clavardage.view.main;

@SuppressWarnings("serial")
public class MyDayInfo extends MyInfoPanel {
	private MyDate date ;
	
	public MyDayInfo(MyDate date) {
		super(30, 10, ((MyDate) date).myDateToString());
		this.date = date;
	}
	
	public MyDate getDate() {
		return date;
	}

}
