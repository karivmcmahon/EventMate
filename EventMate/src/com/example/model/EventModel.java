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

/**
 * This class deals with all methods relating to retrieving and setting up event details in the database
 * @author Kari
 *
 */
public class EventModel {

	Cluster cluster;
	//Counters for random
	int count;
	int attendingCount;
	int randomCounter;
	//Stores events to track which ones we have seen in the random method
	Set<String> events = new HashSet<String>();
	//Database name
	String eventmate = "eventmate3";

	public EventModel() 
	{

	}
	
	/**
	 * Sets up cluster
	 * @param cluster
	 */
	public void setCluster(Cluster cluster) 
	{
		this.cluster = cluster;
	}

	public void selectEvents() 
	{

	}

	/**
	 * Retrieves a list of events
	 * If num param is 1 we retrieve a list of events based on it's popularity if it has an attendee amount over 2000 and is within the users distance pref
	 * If num param is 2 we retrieve an event where the name matches the name the user has sent in - mainly used for searching and restful
	 * If num param is 3 we retrieve events based on category - used for the search bar in webpage
	 * @param us
	 * @param num
	 * @param event
	 * @return
	 */
	public LinkedList<eventStore> getEvents(UserStore us,int num,String event)
	{
		//Stores event list
		LinkedList<eventStore> eventList = new LinkedList<eventStore>();
		//Sets up session
		Session session = cluster.connect(eventmate);
		//Sets up statement stuff
		PreparedStatement statement;
		BoundStatement boundStatement;
		ResultSet rs = null;
		//If the event sent in from the http has %20 turn that into spaces as that is what it represents
		event = event.replaceAll("%20", " ");
		//If num is 1 just select all events
		if(num == 1)
		{
			statement = session.prepare("SELECT * from events;");
		    boundStatement = new BoundStatement(statement);
			rs = session.execute(boundStatement);
		}
		//If num is 2 select all events where the name equals the string passed into the method
		else if(num == 2)
		{
			statement = session.prepare("SELECT * from events WHERE name=?;");
			boundStatement = new BoundStatement(statement);
			rs = session.execute(boundStatement.bind(event));
		}
		//If num is 3 select all events where the category equals  the category sent in
		else if(num == 3)
		{
			statement = session.prepare("SELECT * from events WHERE category=?;");
		    boundStatement = new BoundStatement(statement);
			 rs = session.execute(boundStatement.bind(event));
		}
		if (rs.isExhausted()) 
		{

			System.out.println("No events returned");
		} 
		else 
		{
			for (Row row : rs) 
			{
				//Sets up an event store and then sets event info from the the table to it
				eventStore ts = new eventStore();
				String name = row.getString("name");
				ts.setEvent(name);
				ts.setDesc(row.getString("description"));
				ts.setCategory(row.getString("category"));
				Calendar c = Calendar.getInstance();
				c.setTime(row.getDate("eventdate"));
				// Create a new date format
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
				// Formats the calendar time into a date format
				String date = dateFormat.format(c.getTime());
				ts.setDate(date);
				boolean eventPassed = getEventPassed(c);
				ts.setEventPassed(eventPassed);
				String postcode = row.getString("postcode");
				//Gets distance from the Google API by using the users postcode and events postcode
				int distance = parseURL(postcode, us.getPostcode()) / 1000;
				int attendeeAmount = row.getInt("attendeeAmount");
				ts.setAttendee(attendeeAmount);
				ts.setEventReq(row.getString("eventRequirements"));
				ts.setLocation(row.getString("location"));
				ts.setVenue(row.getString("venue"));
				
				//If num 1
				if(num == 1)
				{
					//If the distance is less than or equal to distance and the attendee amount is greater than 2000 and the event has not passed
					if (distance <= us.getDistance() && attendeeAmount > 2000	&& eventPassed == false) 
					{
						//Check if the user is attending or not attending event
						boolean attending1 = getAttending(us.getUsername(), name);
						boolean attending2 = getNotAttending(us.getUsername(), name);
						ts.setCorrectDistance(true);
						//If they are not
						if (attending1 == false && attending2 == false) 
						{
							//Then add to event list to display on front page - popular events for you
							eventList.add(ts);
						}
					}
				}
				//If num 2
				if(num == 2)
				{
					//Check if distance matches users prefs
					if (distance <= us.getDistance()) 
					{
						//If so set correct distance to true so we know its within there distance range
						ts.setCorrectDistance(true);
					}
					//Check if user is or isn't attending the event, then set it based on outcome so we can display on page whether they are attending or not
					boolean attending1 = getAttending(us.getUsername(), name);
					boolean attending2 = getNotAttending(name, us.getUsername());
					if (attending1 == false && attending2 == false) 
					{

						ts.setAttending(false);
						ts.setNotAttending(false);
					} 
					else if (attending1 == true) 
					{
						ts.setAttending(true);
					} 
					else if (attending2 == true) 
					{
						ts.setNotAttending(true);
					}
					//Then add event to list
					eventList.add(ts);
				}
				if(num == 3)
				{
					//Check if user is or isn't attending the event, then set it based on outcome so we can display on page whether they are attending or not
					boolean attending1 = getAttending(us.getUsername(), name);
					boolean attending2 = getNotAttending(name, us.getUsername());
					if (attending1 == false && attending2 == false) 
					{

						ts.setAttending(false);
						ts.setNotAttending(false);
					} else if (attending1 == true) 
					{
						ts.setAttending(true);
					} 
					else if (attending2 == true) 
					{
						ts.setNotAttending(true);
					}

					//If event within users distance prefs add to list
					if (distance <= us.getDistance()) {
						ts.setCorrectDistance(true);
						eventList.add(ts);
					}
				}
			}
		}
		session.shutdown();
		return eventList;
	}

	/**
	 * This method takes todays date and the events date and checks if it has passed
	 * @param eventCal
	 * @return eventPassed
	 */
	public boolean getEventPassed(Calendar eventCal) 
	{
		Calendar today = Calendar.getInstance();
		boolean eventPassed = eventCal.get(Calendar.YEAR) <= today.get(Calendar.YEAR)&& eventCal.get(Calendar.DAY_OF_YEAR) < today.get(Calendar.DAY_OF_YEAR);
		return eventPassed;

	}

	/**
	 * This method checks if user is attending event
	 * @param name
	 * @param name2
	 * @return true/false
	 */
	public boolean getAttending(String name, String name2) {
		//Checks if user attending event based on there username and the eventname
		Session session = cluster.connect(eventmate);
		PreparedStatement attendingStatement = session.prepare("SELECT * from userattending WHERE username = ? AND eventname = ?;");
		BoundStatement boundAttendingStatement = new BoundStatement(attendingStatement);
		ResultSet resultSetAttending = session.execute(boundAttendingStatement.bind(name, name2));
		session.shutdown();
		if (resultSetAttending.isExhausted()) 
		{
			return false;
		} 
		else 
		{
			return true;
		}
	}

	/**
	 * This method checks if user is not attending event
	 * @param name
	 * @param name2
	 * @return true/false
	 */
	public boolean getNotAttending(String name, String name2) 
	{
		//Checks if user not attending event based on there username and the eventname
		Session session = cluster.connect(eventmate);
		PreparedStatement attendingNotStatement = session.prepare("SELECT * from usernotattending WHERE username = ? AND eventname = ?;");
		BoundStatement boundAttendingNotStatement = new BoundStatement(attendingNotStatement);
		ResultSet resultSetNotAttending = session.execute(boundAttendingNotStatement.bind(name, name2));
		session.shutdown();
		if (resultSetNotAttending.isExhausted()) 
		{
			return false;
		} 
		else 
		{
			return true;
		}

	}


	/**
	 * This method counts the amount of events and then starts the random method for the random page
	 * @param us
	 * @return eventstore
	 */
	public eventStore count(UserStore us) {
		count = 0;
		attendingCount = 0;
		//Create new event
		eventStore event = new eventStore();
		Session session = cluster.connect(eventmate);
		//Select all events 
		PreparedStatement statement = session.prepare("SELECT * from events;");
		BoundStatement boundStatement = new BoundStatement(statement);
		ResultSet rs = session.execute(boundStatement);
		//Clear event list before starting
		events.clear();
		if (rs.isExhausted()) 
		{

			System.out.println("No events returned");
		} 
		else 
		{
			for (Row row : rs) 
			{
				//Gets postcode
				String postcode = row.getString("postcode");
				///Gets distance for the google api
				int distance = parseURL(postcode, us.getPostcode()) / 1000;
				//If distance less than the users pref
				if (distance <= us.getDistance())
				{
				   //Add to event count
				   count++;
			
				}
				
			}
			//If count is 0 
			if(count == 0)
			{
				//just make event null
				event = null;
			}
			else
			{
				//Set randomCounter to count
				randomCounter = count;
				//Get random event
				event = getRandomEvent(us);
			}

		}

		session.shutdown();
		return event;
	}
	
	/**
	 * This method gets an event that is not within the users distance prefs and they are not attending
	 * @param us
	 * @return
	 */
	public eventStore getRandomEvent(UserStore us)
	{
		//Create a new event store set up equal to null
		eventStore event = new eventStore();
		event = null;
		
	    //Set count2 to 0 this will count the number of events in the loop
		int count2 = 0;
		
	    //Get all events
		Session session = cluster.connect(eventmate);
		PreparedStatement statement2 = session.prepare("SELECT * from events;");
		BoundStatement boundStatement2 = new BoundStatement(statement2);
		ResultSet rs2 = session.execute(boundStatement2);
		for(Row row2 : rs2)
		{
				
						//Create a new event store and set event details to it
						eventStore ts = new eventStore();
					    String name = row2.getString("name");
					    ts.setEvent(name);
					    ts.setDesc(row2.getString("description"));
						Calendar c =  Calendar.getInstance();
						//long timestamp = TimeUUIDUtils.getTimeFromUUID(row.getString("eventdate"));
						c.setTime(row2.getDate("eventdate"));
						//Create a new date format
						SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
						//Formats the calendar time into a date format
						String date = dateFormat.format(c.getTime());
						System.out.println(date);
						ts.setDate(date);
					    boolean eventPassed = getEventPassed(c);
					    String postcode = row2.getString("postcode");
					    int distance = parseURL(postcode,us.getPostcode()) / 1000;
						System.out.println(distance);
						int attendeeAmount = row2.getInt("attendeeAmount");
						ts.setAttendee(attendeeAmount);
						ts.setEventReq(row2.getString("eventRequirements"));
						ts.setLocation(row2.getString("location"));
						ts.setVenue(row2.getString("venue")); 
						ts.setCategory(row2.getString("category"));
						//If event has passed - 
						if(eventPassed == true)
						{
							//if already in event list do nothing
							if(events.contains(name))
							{
								
							}
							else
							{
								//Take one off count and add to list
								count--;
								events.add(name);
								
							}
							
			
						}
						//If event has passed and is within users distance preferences
						if(eventPassed == false && distance <= us.getDistance())
						{
							ts.setCorrectDistance(true);
							boolean getAttending = getAttending(us.getUsername(),name);
							boolean getNotAttending = getNotAttending(us.getUsername(),name);
								//If attending count equals count return null
								if(attendingCount == count)
								{
									event = null;
									return event;
								}
								if(getAttending == false && getNotAttending == false )
								{
									if(events.contains(name))
									{
										
									}
									else
									{
										//Add event to list if not already on it
										events.add(name);
									}
								    //Set event to ts and return it
									event = ts;
									return event;
									
								}
								else
								{
										if(getAttending == true)
										{
											if(events.contains(name))
											{
												
											}
											else
											{
												//If not already on list add to list and increment attending counter
												events.add(name);
												attendingCount++;
											
											}
											
										}
										//If attending count equals count return null
										if(attendingCount == count)
										{
											event = null;
											return event;
										}
										if(getNotAttending == true)
										{
											if(events.contains(name))
											{
												
											}
											else
											{
												//If not already on list add to list and increment attending counter
												events.add(name);
												attendingCount++;
											
											}
										}
										//If attending count equals count return null
										if(attendingCount == count)
										{
											
											event = null;
											return event;
										}
		
								}
						}
				
		//Increment count2
		count2++;
			
		}
		//If attending is not equal call the method again 
		if(attendingCount != count)
		{
			getRandomEvent(us);
		}
		//return event
	    session.shutdown();
		return event;
		
	}
	




	/**
	 * When user selects to attend event this adds it into the database
	 * @param us
	 * @param event
	 */
	public void setAttending(UserStore us, String event) {
		Session session = cluster.connect(eventmate);
		PreparedStatement statement = session.prepare("INSERT INTO userattending(username,eventname) VALUES(?,?);");
		BoundStatement boundStatement = new BoundStatement(statement);
		session.execute(boundStatement.bind(us.getUsername(), event));
		session.shutdown();
		FriendModel fm = new FriendModel();
		fm.setCluster(cluster);
		fm.getAttending(us, event);
		

	}

	/**
	 * When user selects not to attend event this adds it into database
	 * @param us
	 * @param event
	 */
	public void setNotAttending(UserStore us, String event) {
		Session session = cluster.connect(eventmate);
		PreparedStatement statement = session.prepare("INSERT INTO usernotattending(username,eventname) VALUES(?,?);");
		BoundStatement boundStatement = new BoundStatement(statement);
		session.execute(boundStatement.bind(us.getUsername(), event));
		session.shutdown();
	}

	/**
	 * This method converts time
	 * @param time
	 * @return
	 */
	public String convertTime(long time) 
	{
		Date date = new Date(time);
		Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
		return format.format(date).toString();
	}

	/**
	 * This method parses the google maps api for getting distance between to places by postcode, we parse the json
	 * @param ePostcode
	 * @param uPostcode
	 * @return
	 */
	public int parseURL(String ePostcode, String uPostcode) {
	
        //Stores user and event postcode
		String userPostcode = uPostcode;
		String eventPostcode = ePostcode;
		//Stores mode
		String mode = "walking";
		int eventDistance = 0;

		URL feedUrl = null;
		try 
		{
			//Gets URL
			feedUrl = new URL(
					"http://maps.googleapis.com/maps/api/distancematrix/json?origins="
							+ userPostcode + "&destinations=" + eventPostcode
							+ "+BC&mode=" + mode
							+ "&language=en-EN&sensor=false");
		} catch (MalformedURLException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Attempts to read feed into input stream
		InputStream inputstream = null;
		try 
		{
			inputstream = feedUrl.openStream();
		} catch (IOException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Read from stream
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputstream, Charset.forName("UTF-8")));
		//New string builder
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

		// Get text from stream
		String jsonText = stringbuilder.toString();
		// close stream
		try 
		{
			inputstream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			//Parse the JSON to a JSON Object
			JSONObject rootObject = new JSONObject(jsonText); 
			//Get all JSONArray rows
			JSONArray rows = rootObject.getJSONArray("rows"); 
            //Loop over each row
			for (int i = 0; i < rows.length(); i++) 
			{ 
				//Get row object
				JSONObject row = rows.getJSONObject(i); 
				//Get all elements for each row as an array
				JSONArray elements = row.getJSONArray("elements"); 
                //Iterate each element in the elements array
				for (int j = 0; j < elements.length(); j++) 
				{
					//Get the element object									
					JSONObject element = elements.getJSONObject(j); 
					//Get distance sub object
					JSONObject distances = element.getJSONObject("distance"); 
					//Get distance valye
					eventDistance = distances.getInt("value");
					//Print distance
					System.out.println("Distance: " + distances.getInt("value")); 
				}
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//Return distance
		return eventDistance;
	}
}
