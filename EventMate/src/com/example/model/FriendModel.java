package com.example.model;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;


import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;





import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.example.stores.UserStore;
import com.example.stores.eventStore;

public class FriendModel {
	
	Cluster cluster;
	String eventmate = "eventmate2";
	
	public FriendModel()
	{
		
	}
	
	public void setCluster(Cluster cluster){
		this.cluster=cluster;
	}
	
	public UserStore selectUsers(UserStore us,String friend,UserStore fus)
	{
		Session session = cluster.connect(eventmate);
		PreparedStatement statement2 = session.prepare("SELECT * from users WHERE username=?;");
		BoundStatement boundStatement2 = new BoundStatement(statement2);
		ResultSet rs2 = session.execute(boundStatement2.bind(friend));
		for(Row row2 : rs2)
		{
			fus.setUsername(row2.getString("username"));
			fus.setName(row2.getString("name"));
			fus.setBio(row2.getString("bio"));
			int age = getDate(row2.getDate("dob"));
			fus.setAge(age);
			fus.setLocation(row2.getString("location"));
			fus.setInterests(row2.getSet("interests", String.class));
			fus.setPhoto(row2.getString("photo"));
			Set<String> intersection = new HashSet<String>(us.getInterests()); // use the copy constructor
			intersection.retainAll(fus.getInterests());
			System.out.println("intersection amount " + intersection.size());
			fus = userAttending(friend,fus);
			return fus;
		}
		return null;
	}
	
	public UserStore userAttending(String username,UserStore fus)
	{
		Session session = cluster.connect(eventmate);
		PreparedStatement statement3 = session.prepare("SELECT * from userattending WHERE username=?;");
		BoundStatement boundStatement3 = new BoundStatement(statement3);
		ResultSet rs3 = session.execute(boundStatement3.bind(username));
		if(rs3.isExhausted())
		{
			return fus;
		}
		for(Row row3 : rs3)
		{
			String event = row3.getString("eventname");
			fus = selectEvents(event,fus);
			return fus;
		}
		return null;
	}
	
	public UserStore selectEvents(String eventname,UserStore fus)
	{
		Session session = cluster.connect(eventmate);
		PreparedStatement statement4 = session.prepare("SELECT * from events WHERE name= ?;");
		BoundStatement boundStatement4 = new BoundStatement(statement4);
		ResultSet rs4 = session.execute(boundStatement4.bind(eventname));
		for(Row row4 : rs4)
		{
			Date eventDate = row4.getDate("eventdate");
			Calendar events = Calendar.getInstance();  
			events.setTime(eventDate);  
	        boolean sameDayOrGreater = sameDayOrGreater(events);
			if(sameDayOrGreater == true)
			{
			
				System.out.println("Array list add");
				fus.setEventList(row4.getString("name"));
			
				System.out.println("nello");
				
			
			}
			return fus;
		}
		return null;
	}
	
	public LinkedList<UserStore> displayFriends(UserStore us)
	{
		LinkedList<UserStore> friendList = new LinkedList<UserStore>();
		Session session = cluster.connect(eventmate);
		
		PreparedStatement statement = session.prepare("SELECT * from userfriends WHERE usersname=?;");
		BoundStatement boundStatement = new BoundStatement(statement);
		ResultSet rs = session.execute(boundStatement.bind(us.getUsername()));
		if (rs.isExhausted()) 
		{
			
	
		} 
		else 
		{
			for (Row row : rs) 
			{
			
				UserStore fus = new UserStore();
				String friend = row.getString("friendsname");
				fus.setUsername(friend);
				System.out.println("name " + friend);
				fus = selectUsers(us,friend,fus);
				friendList.add(fus);
			}
		}
		PreparedStatement statement2 = session.prepare("SELECT * from userfriends WHERE friendsname=? LIMIT 10000 ALLOW FILTERING;;");
		BoundStatement boundStatement2 = new BoundStatement(statement2);
		ResultSet rs2 = session.execute(boundStatement2.bind(us.getUsername()));
		if (rs2.isExhausted()) 
		{
			
	
		} 
		else 
		{
			for (Row row2 : rs2) 
			{
			
				UserStore fus = new UserStore();
				String friend = row2.getString("usersname");
				fus.setUsername(friend);
				System.out.println("name " + friend);
				fus = selectUsers(us,friend,fus);
				friendList.add(fus);
			}
		}
		session.shutdown();
		return friendList;
	}
			
	public boolean sameDayOrGreater(Calendar events)
	{
		Calendar today = Calendar.getInstance();
        boolean sameDayOrGreater = events.get(Calendar.YEAR) >= today.get(Calendar.YEAR) &&
                  events.get(Calendar.DAY_OF_YEAR) >= today.get(Calendar.DAY_OF_YEAR);
        return sameDayOrGreater;
	}
	
	
	public LinkedList<UserStore> displayFriendsByUsername(UserStore us,String username)
	{
		LinkedList<UserStore> friendList = new LinkedList<UserStore>();
		Session session = cluster.connect(eventmate);
		
		PreparedStatement statement = session.prepare("SELECT * from userfriends WHERE usersname=? AND friendsname=?;");
		BoundStatement boundStatement = new BoundStatement(statement);
		ResultSet rs = session.execute(boundStatement.bind(us.getUsername(),username));
		if (rs.isExhausted()) 
		{
			
	
		} 
		else 
		{
			for (Row row : rs) 
			{
			
				UserStore fus = new UserStore();
				String friend = row.getString("friendsname");
				fus.setUsername(friend);
				System.out.println("name " + friend);
				fus = selectUsers(us,friend,fus);
				friendList.add(fus);
			}
		}
		PreparedStatement statement2 = session.prepare("SELECT * from userfriends WHERE friendsname=? AND usersname=? LIMIT 10000 ALLOW FILTERING;");
		BoundStatement boundStatement2 = new BoundStatement(statement2);
		ResultSet rs2 = session.execute(boundStatement2.bind(us.getUsername(),username));
		if (rs2.isExhausted()) 
		{
			
	
		} 
		else 
		{
			for (Row row2 : rs2) 
			{
			
				UserStore fus = new UserStore();
				String friend = row2.getString("usersname");
				fus.setUsername(friend);
				System.out.println("name " + friend);
				fus = selectUsers(us,friend,fus);
				friendList.add(fus);
			}
		}
		session.shutdown();
		return friendList;
	}
	
	public LinkedList<UserStore> displayUserssByName(UserStore us,String username)
	{
		LinkedList<UserStore> friendList = new LinkedList<UserStore>();
		Session session = cluster.connect(eventmate);
		String theUsersname;
		PreparedStatement statement = session.prepare("SELECT * from users WHERE name=?;");
		BoundStatement boundStatement = new BoundStatement(statement);
		ResultSet rs = session.execute(boundStatement.bind(username));
		if (rs.isExhausted()) 
		{
			
	
		} 
		else 
		{
			for (Row row : rs) 
			{
				System.out.println("catss");
					UserStore fus = new UserStore();
					theUsersname = row.getString("username");
					fus.setUsername(theUsersname);
					fus.setName(row.getString("name"));
					fus.setBio(row.getString("bio"));
					int age = getDate(row.getDate("dob"));
					fus.setAge(age);
					fus.setLocation(row.getString("location"));
					System.out.println("photo url " + row.getString("photo") );
					fus.setPhoto(row.getString("photo"));
					fus.setInterests(row.getSet("interests", String.class));
					Set<String> intersection = new HashSet<String>(us.getInterests()); // use the copy constructor
					intersection.retainAll(fus.getInterests());
					System.out.println("intersection amount " + intersection.size());
					fus = userAttending(theUsersname,fus);
					boolean friends = getUsersFriends(us.getUsername(),theUsersname);
					boolean friends2 = getUsersFriends(theUsersname,us.getUsername());
					
					
					if(friends == false && friends2 == false)
					{
						fus.setUserFriends(false);
					}
					else if(friends == true)
					{
						
						fus.setUserFriends(true);
					}
					else if(friends2 == true)
					{
					
						fus.setUserFriends(true);
					}
					friendList.add(fus);
				}
				
			}
		
		session.shutdown();
		return friendList;
	}
	
public boolean getUsersFriends(String theUsersname,String username)
{
	Session session = cluster.connect(eventmate);
	PreparedStatement statement5 = session.prepare("SELECT * from userfriends WHERE usersname= ? AND friendsname= ? LIMIT 1000 ALLOW FILTERING;");
	BoundStatement boundStatement5 = new BoundStatement(statement5);
	ResultSet rs5 = session.execute(boundStatement5.bind(username,theUsersname));
	if(rs5.isExhausted())
	{
		return false;
	}
	else
	{
		return true;
	}
}

public void getAttending(UserStore us,String event)
{
	Map<String,Integer> map = new HashMap<String,Integer>();
	UserStore userstore = new UserStore();
	Session session = cluster.connect(eventmate);
	int counter = 0;
	PreparedStatement statement = session.prepare("SELECT * from userattending WHERE eventname=? LIMIT 1000 ALLOW FILTERING;");
	BoundStatement boundStatement = new BoundStatement(statement);
	ResultSet rs = session.execute(boundStatement.bind(event));
	if (rs.isExhausted()) 
	{
	
		System.out.println("Empty");
	} 
	else 
	{
		for (Row row : rs) 
		{
			String name = row.getString("username");
			boolean friends1 = getUsersFriends(us.getUsername(),name);
			boolean friends2 = getUsersFriends(name,us.getUsername());
			if(friends1 == false && friends2 == false)
			{
				if(!name.equals(us.getUsername()))
				{
					
					PreparedStatement statement5 = session.prepare("SELECT * from users WHERE username=?;");
					BoundStatement boundStatement5 = new BoundStatement(statement5);
					ResultSet rs5 = session.execute(boundStatement5.bind(name));
					for(Row row2 : rs5)
					{
						Date dob = row2.getDate("dob");
						int age = getDate(dob);
						if((us.getGenderPref().equals("both") || us.getGenderPref().equals(row2.getString("gender")))  && (age >= us.getAgeMin() && age <= us.getAgeMax())  && (us.getAge() >= row2.getInt("ageMinRange")  && us.getAge() <= row2.getInt("ageMaxRange")))
						{
							
							Set<String> interests = new HashSet<String>(us.getInterests());
							Set<String> sportsInterests = new HashSet<String>(us.getSports());
							Set<String> musicInterests = new HashSet<String>(us.getMusic());// use the copy constructor
							System.out.println("sports 1 " + sportsInterests);
							System.out.println("sports 2 " + row2.getSet("sports", String.class));
							interests.retainAll(row2.getSet("interests", String.class));
							sportsInterests.retainAll(row2.getSet("sports", String.class));
							musicInterests.retainAll(row2.getSet("music", String.class));
							int totalCommonInterests = interests.size() + sportsInterests.size() + musicInterests.size();
							System.out.println("Total comm interests" + totalCommonInterests);
						
							map.put(name,totalCommonInterests);
							map = MapUtil.sortByValue(map);
							System.out.println("mappy " + map);
						
						
						
						}
						
				
					}
					Iterator entries = map.entrySet().iterator();
					while (entries.hasNext()) {
						if(counter > 5)
						{
							break;
						}
						else
						{
					    Map.Entry entry = (Map.Entry) entries.next();
					    String key = (String)entry.getKey();
					    Integer value = (Integer)entry.getValue();
					    System.out.println("Key = " + key + ", Value = " + value);
					    PreparedStatement statement4 = session.prepare("INSERT INTO userfriends(usersname,friendsname) VALUES(?,?);");
					    BoundStatement boundStatement4 = new BoundStatement(statement4);
					    ResultSet rs4 = session.execute(boundStatement4.bind(us.getUsername(),key));
					    counter++;
						}
					}
					}
				}
				
			}
			
		}
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
