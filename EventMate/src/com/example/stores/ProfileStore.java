package com.example.stores;

import java.util.ArrayList;
import java.util.Set;

public class ProfileStore {
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
	String status;
	Set<String> interests;
	Set<String> music;
	Set<String> sports;
	String interestedIn;
	ArrayList<String> eventList = new ArrayList<String>();

	public ProfileStore()
	{

	}
	
	public String getInterestedIn()
	{
		return interestedIn;
	}
	
	public void setInterestedIn(String interested)
	{
		interestedIn = interested;
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
	
	public void setMusic(Set<String> mus)
	{
		music = mus;
	}

	public Set<String> getMusic()
	{
		return music;
	}
	
	public void setSports(Set<String> sp)
	{
		sports = sp;
	}

	public Set<String> getSports()
	{
		return sports;
	}
	
	public void setStatus(String stat)
	{
		status = stat;
	}

	public String getStatus()
	{
		return status;
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
