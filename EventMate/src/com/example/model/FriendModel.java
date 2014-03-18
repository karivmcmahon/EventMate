package com.example.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;


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
	
	public LinkedList<UserStore> getFriends(UserStore us) {
		
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
					PreparedStatement statement3 = session.prepare("SELECT * from userattending WHERE username=?;");
					BoundStatement boundStatement3 = new BoundStatement(statement3);
					ResultSet rs3 = session.execute(boundStatement3.bind(friend));
					for(Row row3 : rs3)
					{
						String event = row3.getString("eventname");
						System.out.println("Eventss " + event);
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
							//if(!rs4.isExhausted())
							//{
								System.out.println("Array list add");
								fus.setEventList(row4.getString("name"));
							//}
							//else
							//{
								System.out.println("nello");
							//}
							}
							
						}
						}
					}
				friendList.add(fus);
				}
				
			}
			
	
	session.shutdown();
	return friendList;
}
	
public void getAttending(UserStore us,String event)
{
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
				
					if(counter <= 3)
					{
						PreparedStatement statement4 = session.prepare("INSERT INTO userfriends(usersname,friendsname) VALUES(?,?);");
						BoundStatement boundStatement4 = new BoundStatement(statement4);
						ResultSet rs4 = session.execute(boundStatement4.bind(us.getUsername(),name));
						System.out.println("added");
						counter++;
			
						
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
