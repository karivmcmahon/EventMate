package com.example.stores;

import java.util.ArrayList;
import java.util.Set;

public class UserStore {
	String username;
	String password;
	boolean valid = false;
	boolean loggedin = false;
	int distance;
	String name;
	String bio;
	int age;
	String location;
	String postcode = "";
	Set<String> interests;
	ArrayList<String> eventList = new ArrayList<String>();
	
	public UserStore()
	{
		
	}
	
	public void setUsername(String un)
	{
		username = un;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public void setDistancePref(int dist)
	{
		distance = dist;
	}
	
	public int getDistance()
	{
		return distance;
	}
	public void setPassword(String pw)
	{
		password = pw;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setValid(boolean val)
	{
		valid = val;
	}
	
	public boolean getValid()
	{
		return valid;
	}
	
	public void setLoggedIn(boolean log)
	{
		loggedin = log;
	}
	
	public boolean getLoggedIn()
	{
		return loggedin;
	}
	
	public void setPostcode(String post)
	{
		postcode = post;
	}
	
	public String getPostcode()
	{
		return postcode;
	}
	
	public void setName(String n)
	{
		name = n;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setBio(String b)
	{
		bio = b;
	}
	
	public String getBio()
	{
		return bio;
	}
	
	public void setAge(int a)
	{
		age = a;
	}
	
	public int getAge()
	{
		return age;
	}
	
	public void setLocation(String loc)
	{
		location = loc;
	}
	
	public String getLocation()
	{
		return location;
	}
	
	public void setInterests(Set<String> in)
	{
		interests = in;
	}
	
	public Set<String> getInterests()
	{
		return interests;
	}
	
	public void setEventList(String even)
	{
		eventList.add(even);
		System.out.println("Event list size " + eventList.size());
	}
	
	public ArrayList<String> getEventList()
	{
		System.out.println("Event list size " + eventList.size());
		return eventList;
		
	}
	
	

}
