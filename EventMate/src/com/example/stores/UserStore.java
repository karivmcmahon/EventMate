package com.example.stores;

public class UserStore {
	String username;
	String password;
	boolean valid = false;
	boolean loggedin = false;
	int distance;
	String postcode = "";
	
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
	
	

}
