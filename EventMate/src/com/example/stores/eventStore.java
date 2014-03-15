package com.example.stores;

public class eventStore {
	
	String event;
	String desc;
	String date;
	int attendeeAmount;
	String eventReq;
	String location;
	
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
	
	public String getDate()
	{
		return date;
	}
	
	public void setDate(String dte)
	{
		date = dte;
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


}
