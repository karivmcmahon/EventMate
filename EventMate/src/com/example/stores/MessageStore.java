package com.example.stores;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MessageStore {
	String to;
	String from;
	String message;
	String friendMessaging;
	Date time;
	
	public void setMessage(String m)
	{
		message = m;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public void setFriendMessaging(String f)
	{
		friendMessaging = f;
	}
	
	public String getFriendMessaging()
	{
		return friendMessaging;
	}
	
	public void setTo(String t)
	{
		to = t;
	}
	
	public String getTo()
	{
		return to;
	}
	
	public void setFrom(String f)
	{
		from = f;
	}
	
	public String getFrom()
	{
		return from;
	}
	
	public void setDate(Date d)
	{
		time = d;
	}
	
	public String getDate()
	{
		Calendar c =  Calendar.getInstance();
		c.setTime(time);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yy hh:mm aa");
		//Formats the calendar time into a date format
		String date = dateFormat.format(c.getTime());
		return date;
	}

}
