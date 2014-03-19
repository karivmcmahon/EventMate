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
	
	public FriendModel()
	{
		
	}
	
	public void setCluster(Cluster cluster){
		this.cluster=cluster;
	}
	
	public LinkedList<UserStore> displayFriends(UserStore us)
	{
		LinkedList<UserStore> friendList = new LinkedList<UserStore>();
		Session session = cluster.connect("eventmate");
		
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
				PreparedStatement statement2 = session.prepare("SELECT * from users WHERE username=?;");
				BoundStatement boundStatement2 = new BoundStatement(statement2);
				ResultSet rs2 = session.execute(boundStatement2.bind(friend));
				for(Row row2 : rs2)
				{
				
					fus.setName(row2.getString("name"));
					fus.setBio(row2.getString("bio"));
					int age = getDate(row2.getDate("dob"));
					fus.setAge(age);
					fus.setLocation(row2.getString("location"));
					fus.setInterests(row2.getSet("interests", String.class));
					Set<String> intersection = new HashSet<String>(us.getInterests()); // use the copy constructor
					intersection.retainAll(fus.getInterests());
					System.out.println("intersection amount " + intersection.size());
					PreparedStatement statement3 = session.prepare("SELECT * from userattending WHERE username=?;");
					BoundStatement boundStatement3 = new BoundStatement(statement3);
					ResultSet rs3 = session.execute(boundStatement3.bind(friend));
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
					        boolean sameDayOrGreater = events.get(Calendar.YEAR) >= today.get(Calendar.YEAR) &&
					                  events.get(Calendar.DAY_OF_YEAR) >= today.get(Calendar.DAY_OF_YEAR);
							if(sameDayOrGreater == true)
							{
							
								System.out.println("Array list add");
								fus.setEventList(row4.getString("name"));
							
								System.out.println("nello");
							
							}
							
						}
					}
					
				}
				friendList.add(fus);
				
			}
		}
		PreparedStatement statement6 = session.prepare("SELECT * from userfriends WHERE friendsname=? LIMIT 10000 ALLOW FILTERING;");
		BoundStatement boundStatement6 = new BoundStatement(statement6);
		ResultSet rs6 = session.execute(boundStatement6.bind(us.getUsername()));
		for (Row row : rs6) 
		{
			
				UserStore fus = new UserStore();
				String friend = row.getString("usersname");
				
				fus.setUsername(friend);
				System.out.println("name " + friend);
				PreparedStatement statement2 = session.prepare("SELECT * from users WHERE username=?;");
				BoundStatement boundStatement2 = new BoundStatement(statement2);
				ResultSet rs2 = session.execute(boundStatement2.bind(friend));
				for(Row row2 : rs2)
				{
				
					fus.setName(row2.getString("name"));
					fus.setBio(row2.getString("bio"));
					int age = getDate(row2.getDate("dob"));
					fus.setAge(age);
					fus.setLocation(row2.getString("location"));
					fus.setInterests(row2.getSet("interests", String.class));
					Set<String> intersection = new HashSet<String>(us.getInterests()); // use the copy constructor
					intersection.retainAll(fus.getInterests());
					System.out.println("intersection amount " + intersection.size());
					PreparedStatement statement3 = session.prepare("SELECT * from userattending WHERE username=?;");
					BoundStatement boundStatement3 = new BoundStatement(statement3);
					ResultSet rs3 = session.execute(boundStatement3.bind(friend));
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
					        boolean sameDayOrGreater = events.get(Calendar.YEAR) >= today.get(Calendar.YEAR) &&
					                  events.get(Calendar.DAY_OF_YEAR) >= today.get(Calendar.DAY_OF_YEAR);
							if(sameDayOrGreater == true)
							{
							
								System.out.println("Array list add");
								fus.setEventList(row4.getString("name"));
							
								System.out.println("nello");
							
							}
							
						}
					}
					
				}
				friendList.add(fus);
				
		}
		session.shutdown();
		return friendList;
	}
	
	
public void getAttending(UserStore us,String event)
{
	Map<String,Integer> map = new HashMap<String,Integer>();
	UserStore userstore = new UserStore();
	Session session = cluster.connect("eventmate");
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
			PreparedStatement statement2 = session.prepare("SELECT * from userfriends WHERE usersname= ? AND friendsname= ? LIMIT 1000 ALLOW FILTERING;");
			BoundStatement boundStatement2 = new BoundStatement(statement2);
			ResultSet rs2 = session.execute(boundStatement2.bind(us.getUsername(),name));
			PreparedStatement statement3 = session.prepare("SELECT * from userfriends WHERE usersname=? AND friendsname=? LIMIT 1000 ALLOW FILTERING;");
			BoundStatement boundStatement3 = new BoundStatement(statement3);
			ResultSet rs3 = session.execute(boundStatement3.bind(name,us.getUsername()));
			if(rs2.isExhausted() && rs3.isExhausted())
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
							System.out.println("breaked" + counter);
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
