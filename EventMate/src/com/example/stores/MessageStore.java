package com.example.stores;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Class sets and gets information about those sending the messages
 * 
 *
 */
public class MessageStore {
	String to;
	String from;
	String message;
	String friendMessaging;
	String time;
	String photo;
	
	public void setPhoto(String p)
	{
		photo = p;
	}
	
	public String getPhoto()
	{
		return photo;
	}
	
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
	
	public void setDate(String d)
	{
		time = d;
	}
	
	public String getDate()
	{
	
		return time;
	}

}
