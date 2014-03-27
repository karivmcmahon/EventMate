package com.example.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Set;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.example.stores.UserStore;
import com.example.stores.eventStore;

public class UserModel {
	
	Cluster cluster;
	//Database name
	String eventmate = "eventmate2";
	
	//Sets up cluster
	public void setCluster(Cluster cluster)
	{
		this.cluster=cluster;
	}
	
	/**
	 * Method gets user info when logging in
	 * @param us
	 * @return
	 */
	public UserStore getUser(UserStore us)
	{
		UserStore user = new UserStore();
		Session session = cluster.connect(eventmate);
		String un = us.getUsername();
		String pw = us.getPassword();
		PreparedStatement statement = session.prepare("SELECT * from users WHERE username = ? AND password = ?;");
		BoundStatement boundStatement = new BoundStatement(statement);
		ResultSet rs = session.execute(boundStatement.bind(un,pw));
		//if result set empty set user valid to false
		if (rs.isExhausted()) 
		{
			
			user.setValid(false);
		} 
		else 
		{
			
			for (Row row : rs) 
			{
				//Set up user into
				user.setUsername(row.getString("username"));
				user.setPassword(row.getString("password"));
				user.setName(row.getString("name"));
				user.setBio(row.getString("bio"));
				user.setEmail(row.getString("email"));
				user.setLocation(row.getString("location"));
				user.setRelationship(row.getString("relationshipStatus"));
				user.setDistancePref(row.getInt("distanceAmount"));
				user.setPostcode(row.getString("postcode"));
				user.setInterests(row.getSet("interests", String.class));
				user.setGenderPref(row.getString("genderPref"));
				user.setGender(row.getString("gender"));
				user.setAgeMax(row.getInt("ageMaxRange"));
				user.setAgeMin(row.getInt("ageMinRange"));
				user.setInterestedIn(row.getString("interestedIn"));
				user.setSports(row.getSet("sports", String.class));
				user.setMusic(row.getSet("music", String.class));
				user.setPhoto(row.getString("photo"));
				Date dob = row.getDate("dob");
				user.setDateJoined(row.getDate("dateJoined"));
				FriendModel f = new FriendModel();
				int age = f.getDate(dob);
				user.setAge(age);
				user.setValid(true);
			}
		}
		//return user
		session.shutdown();
		return user;
	}
	
	/** 
	 * Checks if username is already in database returns that user is invalid if it is
	 * @param us
	 * @return
	 */
	public String checkUsername(UserStore us)
	{
		UserStore user = new UserStore();
		Session session = cluster.connect(eventmate);
		String username = "";
		String un = us.getUsername();
		PreparedStatement statement = session.prepare("SELECT * from users WHERE username = ?;");
		BoundStatement boundStatement = new BoundStatement(statement);
		ResultSet rs = session.execute(boundStatement.bind(un));
		if (rs.isExhausted()) 
		{
			
			user.setValid(false);
		} 
		else 
		{
			for (Row row : rs) 
			{
			
				username = row.getString("username");
			}
		}
		session.shutdown();
		return username;
	}

	/**
	 * Checks if email is already used in database and returns user invalid if it it is
	 * @param us
	 * @return
	 */
	public String checkEmail(UserStore us)
	{
		UserStore user = new UserStore();
		Session session = cluster.connect(eventmate);
		String email = "";
		String em = us.getEmail();
		PreparedStatement statement = session.prepare("SELECT email from users WHERE email = ?;");
		BoundStatement boundStatement = new BoundStatement(statement);
		ResultSet rs = session.execute(boundStatement.bind(em));
		if (rs.isExhausted()) 
		{
			user.setValid(false);
		} 
		else 
		{
			for (Row row : rs) 
			{
				email = row.getString("email");
			}
		}
		session.shutdown();
		return email;
	}
	
	/**
	 * This method is used for when the user wants update there information
	 * @param us
	 */
	public void editUser(UserStore us)
	{
		Session session = cluster.connect(eventmate);
		String name = us.getName();
		String un = us.getUsername();
		String pw = us.getPassword();
		String email = us.getEmail();
		String bio = us.getBio();
		int distance = us.getDistance();
		String gender = us.getGender();
		String genderPref = us.getGenderPref();
		Set<String> interests = us.getInterests();
		String location = us.getLocation();
		Set<String> music = us.getMusic();
		String postcode = us.getPostcode();
		String relationship = us.getRelationship();
		Set<String> sports = us.getSports();
		int ageMin = us.getAgeMin();
		int ageMax = us.getAgeMax();
		String interestedIn = us.getInterestedIn();
		String photo = us.getPhoto();
		PreparedStatement statement = session.prepare("UPDATE users SET name = ?, email = ?, bio = ?, gender = ?, interests = ?, location = ?, music = ?, postcode = ?, sports = ?, \"distanceAmount\" = ?, \"genderPref\" = ?, \"relationshipStatus\" = ?, \"ageMaxRange\" = ?, \"ageMinRange\" = ?, interestedIn = ?, photo = ? WHERE username = ? AND password = ?;");
		BoundStatement boundStatement = new BoundStatement(statement);
		session.execute(boundStatement.bind(name, email, bio, gender, interests, location, music, postcode, sports, distance, genderPref, relationship, ageMax, ageMin,interestedIn,photo, un, pw));
		System.out.println("execute");
		session.shutdown();
	}
	
	/**
	 * This method is used for when a user signs up and we want to add them to the database
	 * @param us
	 */
	public void addUser(UserStore us)
	{
		Session session = cluster.connect(eventmate);
		String name = us.getName();
		String un = us.getUsername();
		String pw = us.getPassword();
		String email = us.getEmail();
		String bio = us.getBio();
		int distance = us.getDistance();
		String gender = us.getGender();
		String genderPref = us.getGenderPref();
		Set<String> interests = us.getInterests();
		String location = us.getLocation();
		Set<String> music = us.getMusic();
		String postcode = us.getPostcode();
		String relationship = us.getRelationship();
		Set<String> sports = us.getSports();
		int ageMin = us.getAgeMin();
		int ageMax = us.getAgeMax();
		String interestedIn = us.getInterestedIn();
		String photo = us.getPhoto();
		Date dob = us.getDob();
		java.util.Date date= new java.util.Date();
		Timestamp times = new Timestamp(date.getTime());
		PreparedStatement statement = session.prepare("INSERT INTO users(name,username,password,email,bio,gender,interests,location,music,postcode,sports,\"distanceAmount\",\"genderPref\",\"relationshipStatus\",\"ageMaxRange\",\"ageMinRange\",dob, \"dateJoined\",interestedIn,photo) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
		BoundStatement boundStatement = new BoundStatement(statement);
		session.execute(boundStatement.bind(name, un, pw, email, bio, gender, interests, location, music, postcode, sports, distance, genderPref, relationship, ageMax, ageMin, dob, times,interestedIn,photo));
		System.out.println("execute");
		session.shutdown();
	}

}
