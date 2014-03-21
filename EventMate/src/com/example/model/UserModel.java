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
				user.setDistancePref(row.getInt("distanceAmount"));
				user.setPostcode(row.getString("postcode"));
				user.setInterests(row.getSet("interests", String.class));
				user.setGenderPref(row.getString("genderPref"));
				user.setGender(row.getString("gender"));
				user.setAgeMax(row.getInt("ageMaxRange"));
				user.setAgeMin(row.getInt("ageMinRange"));
				user.setSports(row.getSet("sports", String.class));
				user.setMusic(row.getSet("music", String.class));
				Date dob = row.getDate("dob");
				FriendModel f = new FriendModel();
				int age = f.getDate(dob);
				user.setAge(age);
				user.setValid(true);
			}
		}
		session.shutdown();
		return user;
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
		Date dob = us.getDob();
		java.util.Date date= new java.util.Date();
		Timestamp times = new Timestamp(date.getTime());
		PreparedStatement statement = session.prepare("INSERT INTO users(name, username, password, email, bio, gender, interests, location, music, postcode, sports, \"distanceAmount\", \"genderPref\", \"relationshipStatus\", \"ageMaxRange\", \"ageMinRange\", dob, \"dateJoined\") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
		System.out.println("Statement prepared");
		BoundStatement boundStatement = new BoundStatement(statement);
		session.execute(boundStatement.bind(name, un, pw, email, bio, gender, interests, location, music, postcode, sports, distance, genderPref, relationship, ageMax, ageMin, dob, times));
		System.out.println("execute");
		session.shutdown();
	}

}
