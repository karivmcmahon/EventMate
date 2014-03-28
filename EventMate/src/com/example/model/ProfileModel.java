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
	//Gets database name
	String eventmate = "eventmate3";
	
	//Sets up cluster
	public void setCluster(Cluster cluster)
	{
		this.cluster=cluster;
	}
	
	/**
	 * Gets profile of userws
	 * @param us
	 * @param num
	 * @param username
	 * @return
	 */
	public LinkedList<ProfileStore> getProfile(UserStore us,int num,String username)
	{
		LinkedList<ProfileStore> profile = new LinkedList<ProfileStore>();
		Session session = cluster.connect(eventmate);
		PreparedStatement statement;
		BoundStatement boundStatement;
		ResultSet rs = null;
		
		if(num == 1)
		{
			//Get profile of user logged in
		    statement = session.prepare("SELECT * FROM users WHERE username =?;");
		    boundStatement = new BoundStatement(statement);
		    rs = session.execute(boundStatement.bind(us.getUsername()));
		}
		if(num == 2)
		{
			//Get profile of a user
		    statement = session.prepare("SELECT * FROM users WHERE username =?;");
		    boundStatement = new BoundStatement(statement);
		    rs = session.execute(boundStatement.bind(username));
		}
		if (rs.isExhausted()) {

			System.out.println("No profile returned");
		} else {
			for (Row row : rs) {
				//Sets up profile info
				ProfileStore p = new ProfileStore();
				p.setUsername(row.getString("username"));
				p.setName(row.getString("name"));
				p.setBio(row.getString("bio"));
				p.setLocation(row.getString("location"));
				p.setStatus(row.getString("relationshipStatus"));
				p.setMusic(row.getSet("music", String.class));
				p.setInterests(row.getSet("interests", String.class));
				p.setSports(row.getSet("sports", String.class));
				p.setInterestedIn(row.getString("interestedIn"));
				p.setPhoto(row.getString("photo"));
				Date dob = row.getDate("dob");
				FriendModel fms = new FriendModel();
				int age = fms.getDate(dob);
				p.setAge(age);
			
				//Gets if they are attending events
				PreparedStatement statement3 = session.prepare("SELECT * from userattending WHERE username=?;");
				BoundStatement boundStatement3 = new BoundStatement(statement3);
				ResultSet rs3 = session.execute(boundStatement3.bind(us.getUsername()));
				for(Row row3 : rs3)
				{
					String event = row3.getString("eventname");
					//Gets event info
					PreparedStatement statement4 = session.prepare("SELECT * from events WHERE name= ?;");
					BoundStatement boundStatement4 = new BoundStatement(statement4);
					ResultSet rs4 = session.execute(boundStatement4.bind(event));
					for(Row row4 : rs4)
					{
						//Adds event to eventlist if today or greater
						Date eventDate = row4.getDate("eventdate");
						Calendar events = Calendar.getInstance();  
						events.setTime(eventDate);  
						FriendModel fm = new FriendModel();
						EventModel em = new EventModel();
						fm.setCluster(cluster);
				        boolean sameDayOrGreater = fm.sameDayOrGreater(events);
				        boolean pastEvent = em.getEventPassed(events);
						if(sameDayOrGreater == true)
						{
							p.setEventList(row4.getString("name"));
						}
				        if(pastEvent == true)
				        {
				        	p.setPastEventList(row4.getString("name"));
				        }

					}
				}
				if(num == 2)
				{
					//Adds information to list about whether they are friends with user or not
					FriendModel fm = new FriendModel();
					fm.setCluster(cluster);
					boolean friends = fm.getUsersFriends(us.getUsername(),username);
					boolean friends2 = fm.getUsersFriends(username,us.getUsername());
					
					
					if(friends == false && friends2 == false)
					{
						p.setUserFriends(false);
					}
					else if(friends == true)
					{
						
						p.setUserFriends(true);
					}
					else if(friends2 == true)
					{
					
						p.setUserFriends(true);
					}
				}
				//Add info to profile
				profile.add(p);
			}
		}
		session.shutdown();
		return profile;
	}
	

	

	
	
}
