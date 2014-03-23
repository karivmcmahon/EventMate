package com.example.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.example.stores.UserStore;
import com.example.stores.eventStore;


public class EventModel {
	
		Cluster cluster;
		int count;
		int attendingCount;
		Set<String> events = new HashSet<String>();
		public EventModel(){
			
		}

		public void setCluster(Cluster cluster){
			this.cluster=cluster;
		}

		
		public LinkedList<eventStore> getEvents(UserStore us) {
		
			LinkedList<eventStore> eventList = new LinkedList<eventStore>();
			Session session = cluster.connect("eventmate");
			
			PreparedStatement statement = session.prepare("SELECT * from events;");
			BoundStatement boundStatement = new BoundStatement(statement);
			ResultSet rs = session.execute(boundStatement);
			if (rs.isExhausted()) {
			
				System.out.println("No Tweets returned");
			} else {
				for (Row row : rs) {
				
					eventStore ts = new eventStore();
					String name = row.getString("name");
					ts.setEvent(name);
					ts.setDesc(row.getString("description"));
					ts.setCategory(row.getString("category"));
					Calendar c =  Calendar.getInstance();
					//long timestamp = TimeUUIDUtils.getTimeFromUUID(row.getString("eventdate"));
					c.setTime(row.getDate("eventdate"));
					//Create a new date format
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
					//Formats the calendar time into a date format
					String date = dateFormat.format(c.getTime());
					System.out.println(date);
					Date eventDate = row.getDate("eventdate");
				
					Calendar eventCal = Calendar.getInstance();   
					eventCal.setTime(eventDate);
					Calendar today = Calendar.getInstance();
			        boolean eventPassed = eventCal.get(Calendar.YEAR) <= today.get(Calendar.YEAR) &&
			                  eventCal.get(Calendar.DAY_OF_YEAR) < today.get(Calendar.DAY_OF_YEAR);
					ts.setDate(date);
					String postcode = row.getString("postcode");
					int distance = parseURL(postcode,us.getPostcode()) / 1000;
					System.out.println(distance);
					int attendeeAmount = row.getInt("attendeeAmount");
					ts.setAttendee(attendeeAmount);
					ts.setEventReq(row.getString("eventRequirements"));
					ts.setLocation(row.getString("location"));
					ts.setVenue(row.getString("venue"));
					if(distance <= us.getDistance() && attendeeAmount > 2000 && eventPassed == false)
					{
						PreparedStatement statement2 = session.prepare("SELECT * from userattending WHERE username = ? AND eventname = ?;");
						BoundStatement boundStatement2 = new BoundStatement(statement2);
						ResultSet rs2 = session.execute(boundStatement2.bind(us.getUsername(),name));
						PreparedStatement statement3 = session.prepare("SELECT * from usernotattending WHERE username = ? AND eventname = ?;");
						BoundStatement boundStatement3 = new BoundStatement(statement3);
						ResultSet rs3 = session.execute(boundStatement3.bind(us.getUsername(),name));
						System.out.println(name);
						if(rs2.isExhausted() && rs3.isExhausted())
						{
							System.out.println("added");
							eventList.add(ts);
						}
						else
						{
							System.out.println("nothing");
						}
					}
				}
			}
			session.shutdown();
			return eventList;
		}
		
		public eventStore count(UserStore us)
		{
			count = 0;
			attendingCount = 1;
			eventStore event = new eventStore();
			Session session = cluster.connect("eventmate");
			PreparedStatement statement = session.prepare("SELECT * from events;");
			BoundStatement boundStatement = new BoundStatement(statement);
			ResultSet rs = session.execute(boundStatement);
			events.clear();
			if (rs.isExhausted()) {
			
				System.out.println("No Tweets returned");
			} else {
				for (Row row : rs) 
				{
					count++;
					System.out.println("count " + count);
				}
				System.out.println("Events " + events);
				event = getRandomEvent(us);
				
			
		}
			
			session.shutdown();
			return event;
		}
		
		public eventStore getRandomEvent(UserStore us) {
			
			eventStore event = new eventStore();
			//event = null;
			Session session = cluster.connect("eventmate");
			int count2 = 1;
			//event = null;
			
				   Random rand = new Random();
				   int randomNum = rand.nextInt(count) + 1;
				   System.out.println("Random num" + randomNum);
				    PreparedStatement statement2 = session.prepare("SELECT * from events;");
					BoundStatement boundStatement2 = new BoundStatement(statement2);
					ResultSet rs2 = session.execute(boundStatement2);
					for(Row row2 : rs2)
					{
						if(attendingCount == count)
						{
							event = null;
							return event;
						}
						eventStore ts = new eventStore();
						
						System.out.println("count 2" + count2);
						if(count2 == randomNum)
						{
					
							
						System.out.println("Hallo");
						String name = row2.getString("name");
					/*	if(events.contains(name))
						{
							System.out.println("Contained");
						}
						else
						{
							System.out.println("Added");
							events.add(name);
						} */
						
						ts.setEvent(name);
						ts.setDesc(row2.getString("description"));
						ts.setCategory(row2.getString("category"));
						Calendar c =  Calendar.getInstance();
						//long timestamp = TimeUUIDUtils.getTimeFromUUID(row.getString("eventdate"));
						c.setTime(row2.getDate("eventdate"));
						//Create a new date format
						SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
						//Formats the calendar time into a date format
						String date = dateFormat.format(c.getTime());
						System.out.println(date);
						Date eventDate = row2.getDate("eventdate");
						Calendar eventCal = Calendar.getInstance();
						eventCal.setTime(eventDate);
						Calendar today = Calendar.getInstance();
				        boolean eventPassed = eventCal.get(Calendar.YEAR) <= today.get(Calendar.YEAR) &&
				                  eventCal.get(Calendar.DAY_OF_YEAR) < today.get(Calendar.DAY_OF_YEAR);
						if(eventPassed == false)
						{
							count--;
							events.add(name);
							getRandomEvent(us);
						}
				        ts.setDate(date);
						String postcode = row2.getString("postcode");
						int distance = parseURL(postcode,us.getPostcode()) / 1000;
						System.out.println(distance);
						int attendeeAmount = row2.getInt("attendeeAmount");
						ts.setAttendee(attendeeAmount);
						ts.setEventReq(row2.getString("eventRequirements"));
						ts.setLocation(row2.getString("location"));
						ts.setVenue(row2.getString("venue")); 
						if(distance <= us.getDistance() && eventPassed == false)
						{
							PreparedStatement statement3 = session.prepare("SELECT * from userattending WHERE username = ? AND eventname = ?;");
							BoundStatement boundStatement3 = new BoundStatement(statement3);
							ResultSet rs3 = session.execute(boundStatement3.bind(us.getUsername(),name));
							PreparedStatement statement4 = session.prepare("SELECT * from usernotattending WHERE username = ? AND eventname = ?;");
							BoundStatement boundStatement4 = new BoundStatement(statement4);
							ResultSet rs4 = session.execute(boundStatement4.bind(us.getUsername(),name));
							System.out.println(name);
							System.out.println(" attending count " + attendingCount);
							if(rs3.isExhausted() && rs4.isExhausted())
							{
								if(events.contains(name))
								{
								
								}
								else
								{
									
								events.add(name);
								}
							
								System.out.println("hello" + name);
							
								System.out.println("added");
								event = ts;
								return event;
								
							}
							else
							{
								if(attendingCount == count)
								{
									event = null;
									return event;
								}
						
								if(!rs3.isExhausted())
								{
									if(events.contains(name))
									{
									
									}
									else
									{
									attendingCount++;
									events.add(name);
									}
									
									if(attendingCount == count)
									{
										event = null;
										return event;
									}
									else
									{
									getRandomEvent(us);
									}
								}
								if(!rs4.isExhausted())
								{
							
									if(events.contains(name))
									{
									
									}
									else
									{
									attendingCount++;
									events.add(name);
									}
									
									if(attendingCount == count)
									{
										event = null;
										return event;
									}
									else
									{
									getRandomEvent(us);
									}
						
								}
								
								
								System.out.println(" attending count "+ attendingCount);
								getRandomEvent(us);
							}
							
								
						//	}
							//else
						//	{
							//	if(attendingCount == count)
							//	{
							//		event = null;
							//	}
							//	getRandomEvent(us);
							//	System.out.println("hello2");
								
								
								
							//}

							
								
						
						}
					   else
						{
							System.out.println("hello2 just now");
							if(attendingCount == count)
							{
								event = null;
								return event;
							}
							else
							{
								getRandomEvent(us);
							}
							
							
						}
						
						
						
					}
				 
						count2++;
						   
					}
			session.shutdown();
			return event;
		}
		
public void setAttending(UserStore us, String event)
{
	Session session = cluster.connect("eventmate");
	PreparedStatement statement = session.prepare("INSERT INTO userattending(username,eventname) VALUES(?,?);");
	BoundStatement boundStatement = new BoundStatement(statement);
    session.execute(boundStatement.bind(us.getUsername(),event));
    FriendModel fm = new FriendModel();
    fm.setCluster(cluster);
    fm.getAttending(us,event);
	
}

public void setNotAttending(UserStore us,String event)
{
	Session session = cluster.connect("eventmate");
	PreparedStatement statement = session.prepare("INSERT INTO usernotattending(username,eventname) VALUES(?,?);");
	BoundStatement boundStatement = new BoundStatement(statement);
    session.execute(boundStatement.bind(us.getUsername(),event));
}

public String convertTime(long time){
		    Date date = new Date(time);
		    Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
		    return format.format(date).toString();
		}
public int  parseURL(String ePostcode,String uPostcode)
{
	// Create JSON and Finance objects (used to convert one data type to another on request)
			JSONObject jObject = null;
			String distance = "";
			JSONArray json = null;
			String userPostcode = uPostcode;
			String eventPostcode = ePostcode;
			String mode = "walking";
			int eventDistance = 0;
			// Generate URL from which to read the stock details
			
			URL feedUrl = null;
			try {
				feedUrl = new URL("http://maps.googleapis.com/maps/api/distancematrix/json?origins=" + userPostcode + "&destinations=" + eventPostcode + "+BC&mode=" + mode + "&language=fr-FR&sensor=false");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				InputStream inputstream = null;
				try {
					inputstream = feedUrl.openStream();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//Read from stream
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputstream, Charset.forName("UTF-8")));
				StringBuilder stringbuilder = new StringBuilder();
				int cp;
				try {
					while ((cp = reader.read()) != -1)
					{
						stringbuilder.append((char) cp);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//Get text from stream
				String jsonText = stringbuilder.toString();
				//close stream
				
				try {
					inputstream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(jsonText);
				  try {
			            JSONObject rootObject = new JSONObject(jsonText); // Parse the JSON to a JSONObject
			            JSONArray rows = rootObject.getJSONArray("rows"); // Get all JSONArray rows

			            for(int i=0; i < rows.length(); i++) { // Loop over each each row
			                JSONObject row = rows.getJSONObject(i); // Get row object
			                JSONArray elements = row.getJSONArray("elements"); // Get all elements for each row as an array

			                for(int j=0; j < elements.length(); j++) { // Iterate each element in the elements array
			                    JSONObject element =  elements.getJSONObject(j); // Get the element object
			                    
			                    JSONObject distances = element.getJSONObject("distance"); // Get distance sub object
			                    eventDistance = distances.getInt("value");
			                  
			                    System.out.println("Distance: " + distances.getInt("value")); // Print int value
			                }
			            }
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
//				//Try to get value
//				try
//				{
////					//Gets string where l is
//					JSONArray stringNowWithoutComma = jObject.getJSONArray("rows");
//					String distances = "";
////					//Replace where commas are with nothing 
//					//System.out.println(stringNowWithoutComma.get(0).toString());
//					JSONObject j = new JSONObject();
//					for(int i = 0;i < stringNowWithoutComma.length();i++)
//					{
//						j = stringNowWithoutComma.getJSONObject(0);
//						System.out.println(j.toString());
//						 distances = j.getJSONObject("elements").getString("distance");
//						 System.out.println("no");
//					}
//					System.out.println("hello " + distances);
//				//	distance =  stringNowWithoutComma.getJSONObject(0).getString("distance").toString();
//				//	System.out.println("dist " + distance);
////					//Then calculate float divided by 100 - so it is in pounds
////					float lastValue = Float.parseFloat(stringNowWithoutComma) / 100f;
////					// Set 'Last' value
////					toPopulate.setLast(lastValue);
//			}
//				catch(Exception e)
//				{
////					//Set that value is na
////					toPopulate.setNA("vna");
//		}
return eventDistance;
}
}




