package com.example.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
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
import com.example.stores.eventStore;


public class EventModel {
	
		Cluster cluster;
		public EventModel(){
			
		}

		public void setCluster(Cluster cluster){
			this.cluster=cluster;
		}

		
		public LinkedList<eventStore> getEvents() {
			
			LinkedList<eventStore> eventList = new LinkedList<eventStore>();
			Session session = cluster.connect("eventmate");

			PreparedStatement statement = session.prepare("SELECT * from events");
			BoundStatement boundStatement = new BoundStatement(statement);
			ResultSet rs = session.execute(boundStatement);
			if (rs.isExhausted()) {
			
				System.out.println("No Tweets returned");
			} else {
				for (Row row : rs) {
				
					eventStore ts = new eventStore();
					ts.setEvent(row.getString("name"));
					ts.setDesc(row.getString("description"));
					Calendar c =  Calendar.getInstance();
					//long timestamp = TimeUUIDUtils.getTimeFromUUID(row.getString("eventdate"));
					c.setTime(row.getDate("eventdate"));
					//Create a new date format
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
					//Formats the calendar time into a date format
					String date = dateFormat.format(c.getTime());
					System.out.println(date);
					ts.setDate(date);
					String postcode = row.getString("postcode");
					int distance = parseURL(postcode) / 1000;
					System.out.println(distance);
					int attendeeAmount = row.getInt("attendeeAmount");
					ts.setAttendee(attendeeAmount);
					ts.setEventReq(row.getString("eventRequirements"));
					ts.setLocation(row.getString("location"));
					ts.setVenue(row.getString("venue"));
					if(distance <= 150 && attendeeAmount > 10000)
					{
						eventList.add(ts);
					}
				}
			}
			session.shutdown();
			return eventList;
		}

public String convertTime(long time){
		    Date date = new Date(time);
		    Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
		    return format.format(date).toString();
		}
public int  parseURL(String ePostcode)
{
	// Create JSON and Finance objects (used to convert one data type to another on request)
			JSONObject jObject = null;
			String distance = "";
			JSONArray json = null;
			String userPostcode = "DD15DL";
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




