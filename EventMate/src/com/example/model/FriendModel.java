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

/**
 * This class deals with the information to do with the users friends list
 * @author Kari
 *
 */
public class FriendModel {
	
	
	
	Cluster cluster;
	//Stores database name
	String eventmate = "eventmate3";
	
	public FriendModel()
	{
		
	}
	
	//Sets up cluster
	public void setCluster(Cluster cluster)
	{
		this.cluster=cluster;
	}
	
	/**
	 * Methods gets all the users where they match the username
	 * @param us
	 * @param friend
	 * @param fus
	 * @return
	 */
	public UserStore selectUsers(UserStore us,String friend,UserStore fus)
	{
		//Connects to eventmate
		Session session = cluster.connect(eventmate);
		PreparedStatement statement2 = session.prepare("SELECT * from users WHERE username=?;");
		BoundStatement boundStatement2 = new BoundStatement(statement2);
		ResultSet rs2 = session.execute(boundStatement2.bind(friend));
		for(Row row2 : rs2)
		{
			//Sets up userstore with user infomatuon
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
			//call user attending with this fus
			fus = userAttending(friend,fus);
			//Return user
			return fus;
		}
		session.shutdown();
		return null;
	}
	
	/**
	 * Method gets all events user is attending
	 * @param username
	 * @param fus
	 * @return
	 */
	public UserStore userAttending(String username,UserStore fus)
	{
		//Select all user attending where username is the string passed in
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
			//Get the eventname then select all events to get info about event
			String event = row3.getString("eventname");
			fus = selectEvents(event,fus);
			
		}
		session.shutdown();
		return fus;
	}
	
	/**
	 * Method select all the events where it matches it's name
	 * @param eventname
	 * @param fus
	 * @return
	 */
	public UserStore selectEvents(String eventname,UserStore fus)
	{
		Session session = cluster.connect(eventmate);
		PreparedStatement statement4 = session.prepare("SELECT * from events WHERE name= ?;");
		BoundStatement boundStatement4 = new BoundStatement(statement4);
		ResultSet rs4 = session.execute(boundStatement4.bind(eventname));
		
		for(Row row4 : rs4)
		{
			//Get event date  and if todays date or greater then add to list of events friends wants to goto
			Date eventDate = row4.getDate("eventdate");
			Calendar events = Calendar.getInstance();  
			events.setTime(eventDate);  
	        boolean sameDayOrGreater = sameDayOrGreater(events);
	        //If event hasn't passed add to list to display
			if(sameDayOrGreater == true)
			{
				fus.setEventList(row4.getString("name"));
			
				
			}
		
		}

		session.shutdown();
		return fus;
	}
	
	/**
	 * Gets a list of the users friends and there information
	 * @param us
	 * @return
	 */
	public LinkedList<UserStore> displayFriends(UserStore us)
	{
		LinkedList<UserStore> friendList = new LinkedList<UserStore>();
		Session session = cluster.connect(eventmate);
		//Gets users friends 
		PreparedStatement statement = session.prepare("SELECT * from userfriends WHERE usersname=?;");
		BoundStatement boundStatement = new BoundStatement(statement);
		ResultSet rs = session.execute(boundStatement.bind(us.getUsername()));
		if (rs.isExhausted()) 
		{
			//Do nothing
	
		} 
		else 
		{
			for (Row row : rs) 
			{
			    //Then once we have friends name get more info about friends  and then add to list
				UserStore fus = new UserStore();
				String friend = row.getString("friendsname");
				fus.setUsername(friend);
				System.out.println("name " + friend);
				fus = selectUsers(us,friend,fus);
				friendList.add(fus);
			}
		}
		//Get users friends 
		PreparedStatement statement2 = session.prepare("SELECT * from userfriends WHERE friendsname=? LIMIT 10000 ALLOW FILTERING;;");
		BoundStatement boundStatement2 = new BoundStatement(statement2);
		ResultSet rs2 = session.execute(boundStatement2.bind(us.getUsername()));
		if (rs2.isExhausted()) 
		{
			//Do notjing
	
		} 
		else 
		{
			for (Row row2 : rs2) 
			{
			   //Then once we have friends name get more info about them and then get this information and add to linkeed list
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
	
	/**
	 * Gets whether  event is today or greater - so we know its in the future
	 * @param events
	 * @return
	 */
	public boolean sameDayOrGreater(Calendar events)
	{
		Calendar today = Calendar.getInstance();
        boolean sameDayOrGreater = events.get(Calendar.YEAR) >= today.get(Calendar.YEAR) && events.get(Calendar.DAY_OF_YEAR) >= today.get(Calendar.DAY_OF_YEAR);
        return sameDayOrGreater;
	}
	
	
	/**
	 * Method gets friends info by there username for RESTFUL
	 * @param us
	 * @param username
	 * @return
	 */
	public LinkedList<UserStore> displayFriendsByUsername(UserStore us,String username)
	{
		LinkedList<UserStore> friendList = new LinkedList<UserStore>();
		Session session = cluster.connect(eventmate);
		//Selects all users friends where user is username and friend is friendname
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
			    //Gets friend info and adds to list
				UserStore fus = new UserStore();
				String friend = row.getString("friendsname");
				fus.setUsername(friend);
				System.out.println("name " + friend);
				fus = selectUsers(us,friend,fus);
				friendList.add(fus);
			}
		}
		//Gets users friends where friend is username and username is friend
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
			
				//Get friend info and add to list
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
	
	/**
	 * This method gets users by name for RESTFUL and search bar 
	 * @param us
	 * @param username
	 * @return
	 */
	public LinkedList<UserStore> displayUserssByName(UserStore us,String username)
	{
		LinkedList<UserStore> friendList = new LinkedList<UserStore>();
		Session session = cluster.connect(eventmate);
		String theUsersname;
		//Selects users where name passed in matches a user in the database
		PreparedStatement statement = session.prepare("SELECT * from users WHERE name=?;");
		BoundStatement boundStatement = new BoundStatement(statement);
		ResultSet rs = session.execute(boundStatement.bind(username));
		if (rs.isExhausted()) 
		{
			//do nothing
	
		} 
		else 
		{
			for (Row row : rs) 
			{		
					//Sets up new user store with user info
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
					fus = userAttending(theUsersname,fus);
					//check if they are friends with user
					boolean friends = getUsersFriends(us.getUsername(),theUsersname);
					boolean friends2 = getUsersFriends(theUsersname,us.getUsername());
					
					//Then sets whether they are friends or not for displaying on page
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

/**
 * This method gets userfriends where username and friends name is given and returns whether they are friends or not
 * @param theUsersname
 * @param username
 * @return
 */
public boolean getUsersFriends(String theUsersname,String username)
{
	Session session = cluster.connect(eventmate);
	PreparedStatement statement5 = session.prepare("SELECT * from userfriends WHERE usersname= ? AND friendsname= ? LIMIT 1000 ALLOW FILTERING;");
	BoundStatement boundStatement5 = new BoundStatement(statement5);
	ResultSet rs5 = session.execute(boundStatement5.bind(username,theUsersname));
	session.shutdown();
	if(rs5.isExhausted())
	{
		return false;
	}
	else
	{
		return true;
	}
	
}

/**
 * This method is for adding friends when the user clicks to add friends
 * @param us
 * @param event
 */
public void getAttending(UserStore us,String event)
{
	//Map to store interests
	Map<String,Integer> map = new HashMap<String,Integer>();
	Session session = cluster.connect(eventmate);
	int counter = 0;
	//Gets all who are attending the event that was passed in
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
			//Get username of those attending
			String name = row.getString("username");
			//Check if they are friends
			boolean friends1 = getUsersFriends(us.getUsername(),name);
			boolean friends2 = getUsersFriends(name,us.getUsername());
			if(friends1 == false && friends2 == false)
			{
				//If they are not friends and its not the user
				if(!name.equals(us.getUsername()))
				{
					//Select the user where the username is equal to this
					PreparedStatement statement5 = session.prepare("SELECT * from users WHERE username=?;");
					BoundStatement boundStatement5 = new BoundStatement(statement5);
					ResultSet rs5 = session.execute(boundStatement5.bind(name));
					for(Row row2 : rs5)
					{
						//Get users date of birth and age
						Date dob = row2.getDate("dob");
						int age = getDate(dob);
						//Check if they match each users preference e.g they have the same gender pref and age pref
						if((us.getGenderPref().equals("both") || us.getGenderPref().equals(row2.getString("gender")))  && (age >= us.getAgeMin() && age <= us.getAgeMax())  && (us.getAge() >= row2.getInt("ageMinRange")  && us.getAge() <= row2.getInt("ageMaxRange")))
						{

							//Get there interests and see how many they have in common
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
						 // Add total common interests to map and sort by value
							map.put(name,totalCommonInterests);
							map = MapUtil.sortByValue(map);




						}


					}

					}
					}
				}
		//Iterate through map
		Iterator entries = map.entrySet().iterator();
		while (entries.hasNext()) {
			if(counter > 2)
			{
				break;
			}
			else
			{
			//Add the top 3 users in the map to the logged in users friend list
		    Map.Entry entry = (Map.Entry) entries.next();
		    String key = (String)entry.getKey();
		    Integer value = (Integer)entry.getValue();
		    System.out.println("Key = " + key + ", Value = " + value);
		    PreparedStatement statement4 = session.prepare("INSERT INTO userfriends(usersname,friendsname) VALUES(?,?);");
		    BoundStatement boundStatement4 = new BoundStatement(statement4);
		    ResultSet rs4 = session.execute(boundStatement4.bind(us.getUsername(),key));
		    System.out.println("Counter " + counter);
		    counter++;
			}

			}

		}
	session.shutdown();
	}




/**
 * This method is used get age from the date of birth
 * @param dateOfBirth
 * @return
 */
public int getDate(Date dateOfBirth)
{
	Calendar dob = Calendar.getInstance();  
	dob.setTime(dateOfBirth);  
	Calendar today = Calendar.getInstance();  
	int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);  
	if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH))
	{
	  age--;  
	} else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)  && today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) 
	{
	  age--;  
	}
	return age;
}


}

