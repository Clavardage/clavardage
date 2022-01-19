package clavardage.view.main;

import java.util.Date;

@SuppressWarnings("serial")
public class MyDate extends Date {
	
	public MyDate() {
		super();
	}
	
	public MyDate(long l) {
		super(l);
	}
	
	public String myHoureToString() {
        String date = this.toString();         // "EEE MMM dd HH:mm:ss zzz yyyy";
        String myDate = date.substring(11, 16); // "HH:mm";
        return myDate;
    }
	
	public String myDateToString() {
        String date = this.toString();         // "EEE MMM dd HH:mm:ss zzz yyyy";
        String myDate = "";

        //day
        switch (date.substring(0, 3)) {
        case "Mon" :
        	myDate = myDate.concat("Monday ");
        	break;
        case "Tue" :
        	myDate = myDate.concat("Tuesday ");
        	break;
        case "Wed" :
        	myDate = myDate.concat("Wednesday ");
        	break ;
        case "Thu" :
        	myDate = myDate.concat("Thursday ");
        	break;
        case "Fri" :        	
        	myDate = myDate.concat("Friday ");
        	break;
        case "Sat" :
        	myDate = myDate.concat("Saturday ");
        	break ;
        case "Sun" :
        	myDate = myDate.concat("Sunday ");
        	break ;
        default :
        	myDate = myDate.concat("");
        }
        //number
        switch (date.substring(8, 9)) {
        case "0" :
        	myDate = myDate.concat(date.substring(9, 11));
        	break;
        default :
        	myDate = myDate.concat(date.substring(8, 11));
        }

        //month
        switch (date.substring(4, 7)) {
        case "Jan" :
        	myDate = myDate.concat("January ");
        	break;
        case "Feb" :
        	myDate = myDate.concat("February ");
        	break;
        case "Mar" :
        	myDate = myDate.concat("March ");
        	break ;
        case "Apr" :
        	myDate = myDate.concat("April ");
        	break;
        case "May" :
        	myDate = myDate.concat("May ");
        	break;
        case "Jun" :
        	myDate = myDate.concat("June ");
        	break ;
        case "Jul" :
        	myDate = myDate.concat("July ");
        	break ;
        case "Aug" :
        	myDate = myDate.concat("August ");
        	break ;
        case "Sep" :
        	myDate = myDate.concat("September ");
        	break ;
        case "Oct" :
        	myDate = myDate.concat("October ");
        	break ;
        case "Nov" :
        	myDate = myDate.concat("November ");
        	break ;
        case "Dec" :
        	myDate = myDate.concat("December ");
        	break ;
        default :
        	myDate = myDate.concat("");
        }
        

        //year
    	myDate = myDate.concat(date.substring(date.length()-4, date.length())); // "E...E dd M...M yyyy";

        return myDate;
    }
	
	public String getTheDay() {
        return this.toString().substring(0,11).concat(this.toString().substring(this.toString().length()-4, this.toString().length())); // "EEE MMM dd yyyy";
    }
	

}
