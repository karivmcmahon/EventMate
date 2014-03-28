package com.example.model;



import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.example.stores.MessageStore;
import com.example.stores.MessagerStore;
import com.example.stores.UserStore;
import com.example.stores.eventStore;

public class MessageModel {
	
	Cluster cluster;
	
	//Database name
	String eventmate = "eventmate3";
	public MessageModel(){
		
	}
	
	/**
	 * Sets up cluster
	 * @param cluster
	 */
	public void setCluster(Cluster cluster)
	{
		this.cluster=cluster;
	}
	
	/**
	 * Gets users messages from friend
	 * @param us
	 * @param friendMessaged
	 * @return
	 */
	public LinkedList<MessageStore> getMessages(UserStore us,UserStore friendMessaged)
	{
		LinkedList<MessageStore> messageList = new LinkedList<MessageStore>();
		Session session = cluster.connect(eventmate);
		
		//Select all messages where the user to is either the current logged in user or friend they want to message
		PreparedStatement statement = session.prepare("SELECT * FROM messages WHERE  userto in (?,?) ORDER BY time ASC;");
		BoundStatement boundStatement = new BoundStatement(statement);
		ResultSet rs = session.execute(boundStatement.bind(us.getUsername(),friendMessaged.getUsername()));
		if (rs.isExhausted()) {
			
			System.out.println("No messages returned");
		} else {
			for (Row row : rs) {
				MessageStore m = new MessageStore();
				//Set up user to, user from and message and the time of message
				String userto = row.getString("userto");
				m.setTo(userto);
				String userfrom = row.getString("userfrom");
				m.setFrom(userfrom);
				m.setMessage(row.getString("message"));
			
				Calendar c =  Calendar.getInstance();
				//long timestamp = TimeUUIDUtils.getTimeFromUUID(row.getString("eventdate"));
				c.setTime(row.getDate("time"));
				//Create a new date format
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
				//Formats the calendar time into a date format
				String date = dateFormat.format(c.getTime());
				m.setDate(date);
				//If userto is friend messaged or logged in user or userfrom is friend messaged or logged in user - then add to message list else ignore it
				if((userto.equals(us.getUsername()) || userto.equals(friendMessaged.getUsername())) && userfrom.equals(us.getUsername()) || userfrom.equals(friendMessaged.getUsername()))
				{
					messageList.add(m);
				}
			}
			}
		session.shutdown();
		return messageList;
	}
	
	/**
	 * This method called when the user sends new message, so we can add new message to database
	 * @param us
	 * @param friendMessaged
	 * @param message
	 */
	public void insertMessage(UserStore us,UserStore friendMessaged,String message)
	{
		Session session = cluster.connect(eventmate);
		//Gets current time message was inserted
		java.util.Date date= new java.util.Date();
		Timestamp times = new Timestamp(date.getTime());

		PreparedStatement statement = session.prepare("INSERT INTO messages(userto,userfrom,message,time) VALUES(?,?,?,?);");
		BoundStatement boundStatement = new BoundStatement(statement);
		ResultSet rs = session.execute(boundStatement.bind(friendMessaged.getUsername(),us.getUsername(),message,times));
	    session.shutdown();
	}
	

	/**
	 * Gets users photos for message conversations
	 * @param us
	 * @param friendMessaged
	 * @return
	 */
	public String getMessagePhotos(UserStore us,UserStore friendMessaged) {
		LinkedList<String> photoList = new LinkedList<String>();
		String userfrom = "";
		Session session = cluster.connect(eventmate);
		MessageStore f = null;
		//Gets user 
		PreparedStatement statement2 = session.prepare("SELECT * FROM users WHERE  username = ?;");
		BoundStatement boundStatement2 = new BoundStatement(statement2);
		ResultSet rs2 = session.execute(boundStatement2.bind(friendMessaged.getUsername()));
		if (rs2.isExhausted()) {

			System.out.println("No Tweets returned");
		} else {
			for (Row row : rs2) 
			{
				//gets photo for user
				f = new MessageStore();
				userfrom = row.getString("photo");
				System.out.println("photo " + userfrom);
				//sets photo and adds to photo list
				f.setPhoto(userfrom);
				photoList.add(userfrom);
			}
		}
		session.shutdown();
		return userfrom;
	}
	
	/**
	 * Gets user information for display on the messager list
	 * @param name
	 * @param m
	 * @return
	 */
	public MessagerStore selectUsers(String name,MessagerStore m)
	{
		Session session = cluster.connect(eventmate);
		PreparedStatement statement3 = session.prepare("SELECT * FROM users WHERE username=?");
		BoundStatement boundStatement3 = new BoundStatement(statement3);
		ResultSet rs3 = session.execute(boundStatement3.bind(name));
		for(Row row3 : rs3)
		{
			 m.setName(row3.getString("name"));
			 m.setPhoto(row3.getString("photo"));
			 System.out.println("M Photo" + row3.getString("photo") );
		}
		return m;
	}
	
	/**
	 * Gets users who have sent the logged in user messages - so we can display it on the messages list
	 * @param friendName
	 * @param us
	 * @param messagerList
	 * @param list
	 * @param m
	 * @return
	 */
	public LinkedList<MessagerStore> selectMessages(String friendName,UserStore us,LinkedList<MessagerStore> messagerList,Set<String> list,MessagerStore m)
	{
		Session session = cluster.connect(eventmate);
		//Select messages which involved logged in user or friend
		PreparedStatement statement2 = session.prepare("SELECT * FROM messages WHERE userto in (?,?);");
		BoundStatement boundStatement2 = new BoundStatement(statement2);
		ResultSet rs2 = session.execute(boundStatement2.bind(us.getUsername(),friendName));
		for(Row row2 : rs2)
		{
			//The messages where it is both user logged in and friend
			if(row2.getString("userto").equals(us.getUsername()) || row2.getString("userfrom").equals(us.getUsername()))
			{
				if(row2.getString("userto").equals(us.getUsername()) && row2.getString("userfrom").equals(friendName))
				{
					//Add to messager list or ignore if already contained in
					if(list.contains(row2.getString("userfrom")))
					{
						System.out.print("Contained In List" + friendName);
					}
					else
					{
						System.out.print("Add to list " + friendName);
						m.setMessager(row2.getString("userfrom"));
						list.add(row2.getString("userfrom"));
						m = selectUsers(friendName,m);
						messagerList.add(m);
				
					}
				}
				//The messages where it is both user logged in and friend
				else if(row2.getString("userfrom").equals(us.getUsername()) && row2.getString("userto").equals(friendName))
				{
					if(list.contains(row2.getString("userto")))
					{
						System.out.print("Contained In List" + friendName);
					}
					else
					{
						//Add messager to list
						System.out.print("Add to list " + friendName);
						m.setMessager(row2.getString("userto"));
						list.add(row2.getString("userto"));
						m = selectUsers(friendName,m);
						messagerList.add(m);
						
		
					}
				}
			}
			
		}
		
		return messagerList;
		
	}
	
	/**
	 * Gets and returns the list of people that have messaged the user
	 * @param us
	 * @param num
	 * @param name
	 * @return
	 */
	public LinkedList<MessagerStore> messagerList(UserStore us,int num,String name)
	{
		Session session = cluster.connect(eventmate);
		LinkedList<MessagerStore> messagerList = new LinkedList<MessagerStore>();
		Set<String> list = new HashSet<String>();
		PreparedStatement statement;
		BoundStatement boundStatement;
		ResultSet rs = null;
		PreparedStatement statement3;
		BoundStatement boundStatement3;
		ResultSet rs3 = null;
		if(num == 1)
		{
		//Selects all of users friends where username is user logged in
		 statement = session.prepare("SELECT * FROM userfriends where usersname = ?;");
		 boundStatement = new BoundStatement(statement);
		 rs = session.execute(boundStatement.bind(us.getUsername()));
		}
		if(num == 2)
		{
		     statement = session.prepare("SELECT * FROM userfriends where usersname = ? AND friendsname=?;");
			 boundStatement = new BoundStatement(statement);
			 rs = session.execute(boundStatement.bind(us.getUsername(),name));
		}
		for(Row row : rs)
		{
			//Create a new messagerstore
			MessagerStore m = new MessagerStore();
			String friendName = row.getString("friendsname");
			messagerList = selectMessages(friendName,us,messagerList,list,m);
		}
		if(num == 1)
		{
		 statement3 = session.prepare("SELECT * FROM userfriends where friendsname = ?  LIMIT 1000 ALLOW FILTERING;");
	     boundStatement3 = new BoundStatement(statement3);
		 rs3 = session.execute(boundStatement3.bind(us.getUsername()));
		}
		if(num == 2)
		{
			 statement3 = session.prepare("SELECT * FROM userfriends where friendsname = ? AND usersname = ? LIMIT 1000 ALLOW FILTERING;");
			 boundStatement3 = new BoundStatement(statement3);
			 rs3 = session.execute(boundStatement3.bind(us.getUsername(),name));

		}
		for(Row row3 : rs3)
		{
			MessagerStore m2 = new MessagerStore();
			String friendsName = row3.getString("usersname");
			messagerList = selectMessages(friendsName,us,messagerList,list,m2);
		}
	 
		return messagerList;
	}
	
	
	
	
	}



