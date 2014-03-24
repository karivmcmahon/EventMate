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
	
	public void setCluster(Cluster cluster){
		this.cluster=cluster;
	}
	
	public UserStore getUser(UserStore us)
	{
		UserStore user = new UserStore();
		Session session = cluster.connect("eventmate");
		String un = us.getUsername();
		String pw = us.getPassword();
		PreparedStatement statement = session.prepare("SELECT * from users WHERE username = ? AND password = ?;");
		BoundStatement boundStatement = new BoundStatement(statement);
		System.out.println("fail1");
		ResultSet rs = session.execute(boundStatement.bind(un,pw));
		if (rs.isExhausted()) {
			System.out.println("fail1");
			user.setValid(false);
		} else {
			for (Row row : rs) {
				System.out.println("fail2");
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
				Date dob = row.getDate("dob");
				user.setDateJoined(row.getDate("dateJoined"));
				FriendModel f = new FriendModel();
				int age = f.getDate(dob);
				user.setAge(age);
				user.setValid(true);
			}
		}
		session.shutdown();
		return user;
	}
	
	public void editUser(UserStore us)
	{
		System.out.println("Edit user");
		Session session = cluster.connect("eventmate");
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
		//Date dob = us.getDob();
		
		//Timestamp times = new Timestamp(dob.getTime());
	//	System.out.println("Times" + times);
		//Date dateJoined = us.getDateJoined();
		//Timestamp times2 = new Timestamp(dateJoined.getTime());
		//System.out.println("Times 2" + times2);
		PreparedStatement statement = session.prepare("UPDATE users SET name = ?, email = ?, bio = ?, gender = ?, interests = ?, location = ?, music = ?, postcode = ?, sports = ?, \"distanceAmount\" = ?, \"genderPref\" = ?, \"relationshipStatus\" = ?, \"ageMaxRange\" = ?, \"ageMinRange\" = ?, interestedIn = ? WHERE username = ? AND password = ?;");
		System.out.println("Statement prepared");
		BoundStatement boundStatement = new BoundStatement(statement);
		session.execute(boundStatement.bind(name, email, bio, gender, interests, location, music, postcode, sports, distance, genderPref, relationship, ageMax, ageMin,interestedIn, un, pw));
		System.out.println("execute");
		session.shutdown();
	}
	
	public void addUser(UserStore us)
	{
		Session session = cluster.connect("eventmate");
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
		Date dob = us.getDob();
		java.util.Date date= new java.util.Date();
		Timestamp times = new Timestamp(date.getTime());
		PreparedStatement statement = session.prepare("INSERT INTO users(name,username,password,email,bio,gender,interests,location,music,postcode,sports,\"distanceAmount\",\"genderPref\",\"relationshipStatus\",\"ageMaxRange\",\"ageMinRange\",dob, \"dateJoined\",interestedIn) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
		System.out.println("Statement prepared");
		BoundStatement boundStatement = new BoundStatement(statement);
		session.execute(boundStatement.bind(name, un, pw, email, bio, gender, interests, location, music, postcode, sports, distance, genderPref, relationship, ageMax, ageMin, dob, times,interestedIn));
		System.out.println("execute");
		session.shutdown();
	}

}
