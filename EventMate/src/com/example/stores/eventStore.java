package com.example.stores;

import java.util.Date;

/**
 * Class sets and gets information about events and relating too 
 *
 */


public class eventStore {
	
	String event;
	String desc;
	String datess;
	int attendeeAmount;
	String eventReq;
	String location;
	String venue;
	String category;
	boolean attending = false;
	boolean notattending = false;
	boolean isdistance = false;
	boolean eventPassed = false;
	
	public void setEventPassed(boolean eventpass)
	{
		eventPassed = eventpass;
	}
	
	public boolean getEventPassed()
	{
		return eventPassed;
	}
	
	public void setAttending(boolean attend)
	{
		attending = attend;
	}
	
	public boolean getAttending()
	{
		return attending;
	}
	
	public void setNotAttending(boolean attend)
	{
		notattending = attend;
	}
	
	public boolean getNotAttending()
	{
		return notattending;
	}
	
	public void setCorrectDistance(boolean dist)
	{
		isdistance = dist;
	}
	
	public boolean getCorrectDistance()
	{
		return isdistance;
	}
	
	public String getCategory()
	{
		return category;
	}
	
	public void setCategory(String c)
	{
		category = c;
	}

	
	public String getEvent()
	{
		System.out.println("e " + event);
		return event;
	}
	
	public void setEvent(String events)
	{ 
		System.out.println("e " + events);
		event = events;
	}
	
	public String getDescription()
	{
		return desc;
	}
	
	public void setDesc(String description)
	{
		desc = description;
	}
	
	
	
	public void setDate(String dte)
	{
		datess = dte;
	}
	
	public String getDatess()
	{
		return datess;
	}
	public int getAttendee()
	{
		return attendeeAmount;
	}
	
	public void setAttendee(int attAmount)
	{
		attendeeAmount = attAmount;
	}
	
	public String getEventReq()
	{
		return eventReq;
	}
	
	public void setEventReq(String eventRequire)
	{
		eventReq = eventRequire;
	}

	public String getLocation()
	{
		return location;
	}
	
	public void setLocation(String loc)
	{
		location = loc;
	}
	
	public String getVenue()
	{
		return venue;
	}
	
	public void setVenue(String ven)
	{
		venue = ven;
	}



}
