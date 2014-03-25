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
	String eventmate = "eventmate2";

	public EventModel() {

	}

	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}

	public void selectEvents() {

	}

	public LinkedList<eventStore> getEvents(UserStore us) {

		LinkedList<eventStore> eventList = new LinkedList<eventStore>();
		Session session = cluster.connect(eventmate);

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
				Calendar c = Calendar.getInstance();
				c.setTime(row.getDate("eventdate"));
				// Create a new date format
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"dd/MM/yyyy hh:mm aa");
				// Formats the calendar time into a date format
				String date = dateFormat.format(c.getTime());
				System.out.println(date);
				ts.setDate(date);
				boolean eventPassed = getEventPassed(c);
				String postcode = row.getString("postcode");
				int distance = parseURL(postcode, us.getPostcode()) / 1000;
				System.out.println(distance);
				int attendeeAmount = row.getInt("attendeeAmount");
				ts.setAttendee(attendeeAmount);
				ts.setEventReq(row.getString("eventRequirements"));
				ts.setLocation(row.getString("location"));
				ts.setVenue(row.getString("venue"));
				if (distance <= us.getDistance() && attendeeAmount > 2000
						&& eventPassed == false) {
					boolean attending1 = getAttending(us.getUsername(), name);
					boolean attending2 = getNotAttending(us.getUsername(), name);
					if (attending1 == false && attending2 == false) {
						eventList.add(ts);
					}
				}
			}
		}
		session.shutdown();
		return eventList;
	}

	public boolean getEventPassed(Calendar eventCal) {
		Calendar today = Calendar.getInstance();
		boolean eventPassed = eventCal.get(Calendar.YEAR) <= today
				.get(Calendar.YEAR)
				&& eventCal.get(Calendar.DAY_OF_YEAR) < today
						.get(Calendar.DAY_OF_YEAR);
		return eventPassed;

	}

	public boolean getAttending(String name, String name2) {
		Session session = cluster.connect(eventmate);
		PreparedStatement attendingStatement = session
				.prepare("SELECT * from userattending WHERE username = ? AND eventname = ?;");
		BoundStatement boundAttendingStatement = new BoundStatement(
				attendingStatement);
		ResultSet resultSetAttending = session.execute(boundAttendingStatement
				.bind(name, name2));
		if (resultSetAttending.isExhausted()) {
			return false;
		} else {
			return true;
		}
	}

	public boolean getNotAttending(String name, String name2) {
		Session session = cluster.connect(eventmate);
		PreparedStatement attendingNotStatement = session
				.prepare("SELECT * from usernotattending WHERE username = ? AND eventname = ?;");
		BoundStatement boundAttendingNotStatement = new BoundStatement(
				attendingNotStatement);
		ResultSet resultSetNotAttending = session
				.execute(boundAttendingNotStatement.bind(name, name2));
		if (resultSetNotAttending.isExhausted()) {
			return false;
		} else {
			return true;
		}

	}

	public LinkedList<eventStore> getEventByName(UserStore us, String eventname) {

		LinkedList<eventStore> eventList = new LinkedList<eventStore>();
		Session session = cluster.connect(eventmate);
		eventname = eventname.replaceAll("%20", " ");
		PreparedStatement statement = session
				.prepare("SELECT * from events WHERE name=?;");
		BoundStatement boundStatement = new BoundStatement(statement);
		ResultSet rs = session.execute(boundStatement.bind(eventname));
		if (rs.isExhausted()) {

			System.out.println("No Tweets returned");
		} else {
			for (Row row : rs) {

				eventStore ts = new eventStore();
				String name = row.getString("name");
				ts.setEvent(name);
				ts.setDesc(row.getString("description"));
				ts.setCategory(row.getString("category"));
				Calendar c = Calendar.getInstance();
				// long timestamp =
				// TimeUUIDUtils.getTimeFromUUID(row.getString("eventdate"));
				c.setTime(row.getDate("eventdate"));
				// Create a new date format
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"dd/MM/yyyy hh:mm aa");
				// Formats the calendar time into a date format
				String date = dateFormat.format(c.getTime());
				System.out.println(date);
				boolean eventPassed = getEventPassed(c);
				ts.setDate(date);
				ts.setEventPassed(eventPassed);
				String postcode = row.getString("postcode");
				int distance = parseURL(postcode, us.getPostcode()) / 1000;
				System.out.println(distance);
				int attendeeAmount = row.getInt("attendeeAmount");
				ts.setAttendee(attendeeAmount);
				ts.setEventReq(row.getString("eventRequirements"));
				ts.setLocation(row.getString("location"));
				ts.setVenue(row.getString("venue"));

				if (distance <= us.getDistance()) {
					ts.setCorrectDistance(true);
				}
				boolean attending1 = getAttending(us.getUsername(), name);
				boolean attending2 = getNotAttending(name, us.getUsername());
				if (attending1 == false && attending2 == false) {

					ts.setAttending(false);
					ts.setNotAttending(false);
				} else if (attending1 == true) {
					ts.setAttending(true);
				} else if (attending2 == true) {
					ts.setNotAttending(true);
				}

				eventList.add(ts);
			}
		}
		session.shutdown();
		return eventList;
	}

	public LinkedList<eventStore> getEventByCategory(UserStore us,
			String eventname) {

		LinkedList<eventStore> eventList = new LinkedList<eventStore>();
		Session session = cluster.connect(eventmate);
		eventname = eventname.replaceAll("%20", " ");
		PreparedStatement statement = session
				.prepare("SELECT * from events WHERE category=?;");
		BoundStatement boundStatement = new BoundStatement(statement);
		ResultSet rs = session.execute(boundStatement.bind(eventname));
		if (rs.isExhausted()) {

			System.out.println("No Tweets returned");
		} else {
			for (Row row : rs) {

				eventStore ts = new eventStore();
				String name = row.getString("name");
				ts.setEvent(name);
				ts.setDesc(row.getString("description"));
				ts.setCategory(row.getString("category"));
				Calendar c = Calendar.getInstance();
				// long timestamp =
				// TimeUUIDUtils.getTimeFromUUID(row.getString("eventdate"));
				c.setTime(row.getDate("eventdate"));
				// Create a new date format
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"dd/MM/yyyy hh:mm aa");
				// Formats the calendar time into a date format
				String date = dateFormat.format(c.getTime());
				System.out.println(date);

				boolean eventPassed = getEventPassed(c);
				ts.setDate(date);
				ts.setEventPassed(eventPassed);
				String postcode = row.getString("postcode");
				int distance = parseURL(postcode, us.getPostcode()) / 1000;
				System.out.println(distance);
				int attendeeAmount = row.getInt("attendeeAmount");
				ts.setAttendee(attendeeAmount);
				ts.setEventReq(row.getString("eventRequirements"));
				ts.setLocation(row.getString("location"));
				ts.setVenue(row.getString("venue"));
				boolean attending1 = getAttending(us.getUsername(), name);
				boolean attending2 = getNotAttending(name, us.getUsername());
				if (attending1 == false && attending2 == false) {

					ts.setAttending(false);
					ts.setNotAttending(false);
				} else if (attending1 == true) {
					ts.setAttending(true);
				} else if (attending2 == true) {
					ts.setNotAttending(true);
				}

				if (distance <= us.getDistance()) {
					ts.setCorrectDistance(true);
					eventList.add(ts);
				}
			}
		}
		session.shutdown();
		return eventList;
	}

	public eventStore count(UserStore us) {
		count = 0;
		attendingCount = 1;
		eventStore event = new eventStore();
		Session session = cluster.connect("eventmate2");
		PreparedStatement statement = session.prepare("SELECT * from events;");
		BoundStatement boundStatement = new BoundStatement(statement);
		ResultSet rs = session.execute(boundStatement);
		events.clear();
		if (rs.isExhausted()) {

			System.out.println("No Tweets returned");
		} else {
			for (Row row : rs) {
				String postcode = row.getString("postcode");
				int distance = parseURL(postcode, us.getPostcode()) / 1000;
				if (distance <= us.getDistance())
				{
				count++;
				System.out.println("count " + count);
				}
				System.out.println("count " + count);
			}
			if(count == 0)
			{
				event = null;
			}
			else
			{
				System.out.println("Events " + events);
				event = getRandomEvent(us);
			}

		}

		session.shutdown();
		return event;
	}

	public eventStore getRandomEvent(UserStore us) {

		eventStore event = new eventStore();
		Session session = cluster.connect(eventmate);
		int count2 = 1;

		Random rand = new Random();
		int randomNum = rand.nextInt(count) + 1;
		System.out.println("Random num" + randomNum);
		PreparedStatement statement2 = session.prepare("SELECT * from events;");
		BoundStatement boundStatement2 = new BoundStatement(statement2);
		ResultSet rs2 = session.execute(boundStatement2);
		for (Row row2 : rs2) {
			if (attendingCount == count) {
				System.out.println("Attending count " + attendingCount);
				event = null;
				return event;
			}
			eventStore ts = new eventStore();

			if (count2 == randomNum) {
				String name = row2.getString("name");

				ts.setEvent(name);
				ts.setDesc(row2.getString("description"));
				ts.setCategory(row2.getString("category"));
				Calendar c = Calendar.getInstance();
				// long timestamp =
				// TimeUUIDUtils.getTimeFromUUID(row.getString("eventdate"));
				c.setTime(row2.getDate("eventdate"));
				// Create a new date format
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"dd/MM/yyyy hh:mm aa");
				// Formats the calendar time into a date format
				String date = dateFormat.format(c.getTime());
				
				boolean eventPassed = getEventPassed(c);
				if (eventPassed == true) {
				//	count--;
					if(!events.contains(name))
					{
						attendingCount++;
						events.add(name);
					}
					
					getRandomEvent(us);
				}
				ts.setDate(date);
				String postcode = row2.getString("postcode");
				int distance = parseURL(postcode, us.getPostcode()) / 1000;
				System.out.println(distance);
				int attendeeAmount = row2.getInt("attendeeAmount");
				ts.setAttendee(attendeeAmount);
				ts.setEventReq(row2.getString("eventRequirements"));
				ts.setLocation(row2.getString("location"));
				ts.setVenue(row2.getString("venue"));
				if (distance <= us.getDistance() && eventPassed == false) {
					boolean attending1 = getAttending(us.getUsername(), name);
					boolean attending2 = getNotAttending(name, us.getUsername());
					
					if (attending1 == false && attending2 == false) 
					{
						if (!events.contains(name)) 
						{
							events.add(name);
						}
						System.out.println("Attending count " + attendingCount);
						event = ts;
						return event;

					} else {
						if (attendingCount == count) {
							System.out.println("Attending count " + attendingCount);
							event = null;
							return event;
						}

						if (attending1 == true) {
							if (!events.contains(name)) {
								attendingCount++;
								events.add(name);
							}

							if (attendingCount == count) {
								System.out.println("Attending count " + attendingCount);
								event = null;
								return event;
							} else {
								getRandomEvent(us);
							}
						}
						if (attending2 == true) {

							if (!events.contains(name)) {
								attendingCount++;
								events.add(name);
							}

							if (attendingCount == count) {
								System.out.println("Attending count " + attendingCount);
								event = null;
								return event;
							} 
							else 
							{
								getRandomEvent(us);
							}

						}

						getRandomEvent(us);
					}

					
				} 
				else
				{
					if (attendingCount == count) {
						System.out.println("Attending count " + attendingCount);
						event = null;
						return event;
					} else {
						getRandomEvent(us);
					}

				} 

			}

			count2++;

		}
		System.out.println("Attending count " + attendingCount);
		session.shutdown();
		return event;
	}

	public void setAttending(UserStore us, String event) {
		Session session = cluster.connect(eventmate);
		PreparedStatement statement = session
				.prepare("INSERT INTO userattending(username,eventname) VALUES(?,?);");
		BoundStatement boundStatement = new BoundStatement(statement);
		session.execute(boundStatement.bind(us.getUsername(), event));
		FriendModel fm = new FriendModel();
		fm.setCluster(cluster);
		fm.getAttending(us, event);

	}

	public void setNotAttending(UserStore us, String event) {
		Session session = cluster.connect(eventmate);
		PreparedStatement statement = session
				.prepare("INSERT INTO usernotattending(username,eventname) VALUES(?,?);");
		BoundStatement boundStatement = new BoundStatement(statement);
		session.execute(boundStatement.bind(us.getUsername(), event));
	}

	public String convertTime(long time) {
		Date date = new Date(time);
		Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
		return format.format(date).toString();
	}

	public int parseURL(String ePostcode, String uPostcode) {
		// Create JSON and Finance objects (used to convert one data type to
		// another on request)
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
			feedUrl = new URL(
					"http://maps.googleapis.com/maps/api/distancematrix/json?origins="
							+ userPostcode + "&destinations=" + eventPostcode
							+ "+BC&mode=" + mode
							+ "&language=en-EN&sensor=false");
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
		// Read from stream
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputstream, Charset.forName("UTF-8")));
		StringBuilder stringbuilder = new StringBuilder();
		int cp;
		try {
			while ((cp = reader.read()) != -1) {
				stringbuilder.append((char) cp);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Get text from stream
		String jsonText = stringbuilder.toString();
		// close stream

		try {
			inputstream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(jsonText);
		try {
			JSONObject rootObject = new JSONObject(jsonText); // Parse the JSON
																// to a
																// JSONObject
			JSONArray rows = rootObject.getJSONArray("rows"); // Get all
																// JSONArray
																// rows

			for (int i = 0; i < rows.length(); i++) { // Loop over each each row
				JSONObject row = rows.getJSONObject(i); // Get row object
				JSONArray elements = row.getJSONArray("elements"); // Get all
																	// elements
																	// for each
																	// row as an
																	// array

				for (int j = 0; j < elements.length(); j++) { // Iterate each
																// element in
																// the elements
																// array
					JSONObject element = elements.getJSONObject(j); // Get the
																	// element
																	// object

					JSONObject distances = element.getJSONObject("distance"); // Get
																				// distance
																				// sub
																				// object
					eventDistance = distances.getInt("value");

					System.out
							.println("Distance: " + distances.getInt("value")); // Print
																				// int
																				// value
				}
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return eventDistance;
	}
}
