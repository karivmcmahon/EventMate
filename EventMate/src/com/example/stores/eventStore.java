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

	
	public String getEvent()
	{
		return event;
	}
	
	public void setEvent(String events)
	{
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
