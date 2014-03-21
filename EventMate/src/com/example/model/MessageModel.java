package com.example.model;



import java.sql.Timestamp;
import java.util.LinkedList;

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
	public MessageModel(){
		
	}
	
	public void setCluster(Cluster cluster){
		this.cluster=cluster;
	}
	
	public LinkedList<MessageStore> getMessages(UserStore us,UserStore friendMessaged)
	{
		LinkedList<MessageStore> messageList = new LinkedList<MessageStore>();
		Session session = cluster.connect("eventmate");
		
		PreparedStatement statement = session.prepare("SELECT * FROM eventmate.messages WHERE  userto in (?,?) ORDER BY time ASC;");
		BoundStatement boundStatement = new BoundStatement(statement);
		ResultSet rs = session.execute(boundStatement.bind(us.getUsername(),friendMessaged.getUsername()));
		if (rs.isExhausted()) {
			
			System.out.println("No Tweets returned");
		} else {
			for (Row row : rs) {
				MessageStore m = new MessageStore();
				String userto = row.getString("userto");
				m.setTo(userto);
				String userfrom = row.getString("userfrom");
				m.setFrom(userfrom);
				m.setMessage(row.getString("message"));
				m.setDate(row.getDate("time"));
				if((userto.equals(us.getUsername()) || userto.equals(friendMessaged.getUsername())) && userfrom.equals(us.getUsername()) || userfrom.equals(friendMessaged.getUsername()))
				{
					messageList.add(m);
				}
			}
			}
		session.shutdown();
		return messageList;
	}
	
	public void insertMessage(UserStore us,UserStore friendMessaged,String message)
	{
		Session session = cluster.connect("eventmate");
		java.util.Date date= new java.util.Date();
		Timestamp times = new Timestamp(date.getTime());

		PreparedStatement statement = session.prepare("INSERT INTO messages(userto,userfrom,message,time) VALUES(?,?,?,?);");
		BoundStatement boundStatement = new BoundStatement(statement);
		ResultSet rs = session.execute(boundStatement.bind(friendMessaged.getUsername(),us.getUsername(),message,times));
	}
	
	//SELECT of user logged in friends
	//Then once they are selected check if they have messages to or from that friend if so
	//Then add to messages list
	
	public LinkedList<MessagerStore> messagerList(UserStore us)
	{
		Session session = cluster.connect("eventmate");
		LinkedList<MessagerStore> messagerList = new LinkedList<MessagerStore>();
		MessagerStore m = new MessagerStore();
	/**	PreparedStatement statement = session.prepare("SELECT * FROM userfriends where usersname = ?;");
		BoundStatement boundStatement = new BoundStatement(statement);
		ResultSet rs = session.execute(boundStatement.bind(us.getUsername()));
		MessagerStore m = new MessagerStore();
		for(Row row : rs)
		{
			String friend = row.getString("friendsname");
			String friend2 = null;
	
			
			for(Row row3 : rs3)
			{
				String userto = row3.getString("userto");
				String userfrom = row3.getString("userfrom");
				if(friend.equals(userto) || friend.equals(userfrom))
				{
					m.setMessager(friend);
				}
				if(friend2.equals(userto) || friend2.equals(userfrom))
				{
					m.setMessager(friend2);
				}
			}
			messagerList.add(m);
		} **/
		
		PreparedStatement statement2 = session.prepare("SELECT * FROM userfriends where friendsname = ? LIMIT 1000 ALLOW FILTERING;");
		BoundStatement boundStatement2 = new BoundStatement(statement2);
		ResultSet rs2 = session.execute(boundStatement2.bind(us.getUsername()));
		for(Row row2 : rs2)
		{
			 String friend2 = row2.getString("usersname");
			 PreparedStatement statement3 = session.prepare("SELECT * FROM messages;");
				BoundStatement boundStatement3 = new BoundStatement(statement3);
				ResultSet rs3 = session.execute(boundStatement3);
				for(Row row3 : rs3)
				{
					String userto = row3.getString("userto");
					String userfrom = row3.getString("userfrom");
					
					if(friend2.equals(userto) || friend2.equals(userfrom))
					{
						m.setMessager(friend2);
					}
				}
				messagerList.add(m);
			

		}
				
		
		session.shutdown();
		return messagerList;
		
	}

}
