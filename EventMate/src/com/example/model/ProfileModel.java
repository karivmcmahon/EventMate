package com.example.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.example.stores.UserStore;
import com.example.stores.eventStore;
import com.example.stores.ProfileStore;

public class ProfileModel {
	
	Cluster cluster;
	
	public void setCluster(Cluster cluster){
		this.cluster=cluster;
	}
	
	public LinkedList<ProfileStore> getProfile(UserStore us)
	{
		LinkedList<ProfileStore> profile = new LinkedList<ProfileStore>();
		Session session = cluster.connect("eventmate");
		
		//do stuff to get the profile of user logged in
		PreparedStatement statement = session.prepare("SELECT * FROM users WHERE username =?;");
		BoundStatement boundStatement = new BoundStatement(statement);
		ResultSet rs = session.execute(boundStatement.bind(us.getUsername()));
		if (rs.isExhausted()) {

			System.out.println("No profile returned");
		} else {
			System.out.println("this could work");
			for (Row row : rs) {
				ProfileStore p = new ProfileStore();
				p.setUsername(row.getString("username"));
				p.setName(row.getString("name"));
				p.setBio(row.getString("bio"));
				p.setLocation(row.getString("location"));
				p.setStatus(row.getString("relationshipStatus"));
				p.setMusic(row.getSet("music", String.class));
				p.setInterests(row.getSet("interests", String.class));
				p.setSports(row.getSet("sports", String.class));
				
				Date dob = row.getDate("dob");
				int age = getDate(dob);
				p.setAge(age);
			
				PreparedStatement statement3 = session.prepare("SELECT * from userattending WHERE username=?;");
				BoundStatement boundStatement3 = new BoundStatement(statement3);
				ResultSet rs3 = session.execute(boundStatement3.bind(us.getUsername()));
				for(Row row3 : rs3)
				{
					String event = row3.getString("eventname");
					PreparedStatement statement4 = session.prepare("SELECT * from events WHERE name= ?;");
					BoundStatement boundStatement4 = new BoundStatement(statement4);
					ResultSet rs4 = session.execute(boundStatement4.bind(event));
					for(Row row4 : rs4)
					{
						Date eventDate = row4.getDate("eventdate");
						Calendar events = Calendar.getInstance();  
						events.setTime(eventDate);  
						Calendar today = Calendar.getInstance();
				        boolean sameDayOrGreater = events.get(Calendar.YEAR) <= today.get(Calendar.YEAR) &&
				                  events.get(Calendar.DAY_OF_YEAR) < today.get(Calendar.DAY_OF_YEAR);
						if(sameDayOrGreater == true)
						{
							p.setEventList(row4.getString("name"));
						}

					}
				}
				
				profile.add(p);
			}
		}
		session.shutdown();
		return profile;
	}
	
	public LinkedList<ProfileStore> getProfileByUsername(String username)
	{
		LinkedList<ProfileStore> profile = new LinkedList<ProfileStore>();
		Session session = cluster.connect("eventmate");
		
		//do stuff to get the profile of user logged in
		PreparedStatement statement = session.prepare("SELECT * FROM users WHERE username =?;");
		BoundStatement boundStatement = new BoundStatement(statement);
		ResultSet rs = session.execute(boundStatement.bind(username));
		if (rs.isExhausted()) {

			System.out.println("No profile returned");
		} else {
			System.out.println("this could work");
			for (Row row : rs) {
				ProfileStore p = new ProfileStore();
				p.setUsername(row.getString("username"));
				p.setName(row.getString("name"));
				p.setBio(row.getString("bio"));
				p.setLocation(row.getString("location"));
				p.setStatus(row.getString("relationshipStatus"));
				p.setMusic(row.getSet("music", String.class));
				p.setInterests(row.getSet("interests", String.class));
				p.setSports(row.getSet("sports", String.class));
				
				Date dob = row.getDate("dob");
				int age = getDate(dob);
				p.setAge(age);
			
				PreparedStatement statement3 = session.prepare("SELECT * from userattending WHERE username=?;");
				BoundStatement boundStatement3 = new BoundStatement(statement3);
				ResultSet rs3 = session.execute(boundStatement3.bind(username));
				for(Row row3 : rs3)
				{
					String event = row3.getString("eventname");
					PreparedStatement statement4 = session.prepare("SELECT * from events WHERE name= ?;");
					BoundStatement boundStatement4 = new BoundStatement(statement4);
					ResultSet rs4 = session.execute(boundStatement4.bind(event));
					for(Row row4 : rs4)
					{
						Date eventDate = row4.getDate("eventdate");
						Calendar events = Calendar.getInstance();  
						events.setTime(eventDate);  
						Calendar today = Calendar.getInstance();
				        boolean sameDayOrGreater = events.get(Calendar.YEAR) <= today.get(Calendar.YEAR) &&
				                  events.get(Calendar.DAY_OF_YEAR) < today.get(Calendar.DAY_OF_YEAR);
						if(sameDayOrGreater == true)
						{
							p.setEventList(row4.getString("name"));
						}

					}
				}
				
				profile.add(p);
			}
		}
		session.shutdown();
		return profile;
	}
	
	public int getDate(Date dateOfBirth)
	{
	Calendar dob = Calendar.getInstance(); 
	dob.setTime(dateOfBirth); 
	Calendar today = Calendar.getInstance(); 
	int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR); 
	if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
	age--; 
	} else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
	&& today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
	age--; 
	}
	return age;
	}

	
	
}
