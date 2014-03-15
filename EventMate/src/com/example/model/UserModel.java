package com.example.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;

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
				user.setValid(true);
			}
		}
		session.shutdown();
		return user;
	}

}
