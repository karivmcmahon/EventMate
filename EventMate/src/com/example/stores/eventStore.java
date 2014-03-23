package com.example.stores;

import java.util.Date;



public class eventStore {
	
	String event;
	String desc;
	String datess;
	int attendeeAmount;
	String eventReq;
	String location;
	String venue;
	String category;
	boolean attending;
	
	public void setAttending(boolean attend)
	{
		attending = attend;
	}
	
	public boolean getAttending()
	{
		return attending;
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
