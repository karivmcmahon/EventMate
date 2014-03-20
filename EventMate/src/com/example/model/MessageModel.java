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

}
