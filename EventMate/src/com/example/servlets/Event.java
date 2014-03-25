package com.example.servlets;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.datastax.driver.core.Cluster;
import com.example.libs.CassandraHosts;
import com.example.model.EventModel;
import com.example.model.FriendModel;
import com.example.stores.MessagerStore;
import com.example.stores.UserStore;
import com.example.stores.eventStore;
import com.example.stores.eventStore;
import com.example.stores.UserStore;

/**
 * Servlet implementation class Event
 */
@WebServlet(urlPatterns = { "/Event", "/Event/*" })
public class Event extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Cluster cluster;

    /**
     * Default constructor. 
     */
    public Event() {
        // TODO Auto-generated constructor stub
    	super();
    }

    public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		cluster = CassandraHosts.getCluster();
	}
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//String args[]=Convertors.SplitRequestPath(request);
		UserStore us = new UserStore();
		EventModel tm= new EventModel();
		FriendModel fm = new FriendModel();
		if(request.getRequestURI().equals(request.getContextPath() + "/Event"))
		{
		//Get session for user currently logged in
		us = (UserStore) request.getSession().getAttribute("currentSeshUser");
		tm.setCluster(cluster);
		LinkedList<eventStore> eventList = tm.getEvents(us,1,"");
		request.setAttribute("Events", eventList); //Set a bean with the list in it
		
		RequestDispatcher rd = request.getRequestDispatcher("/Homepage.jsp"); 

		rd.forward(request, response);
		}
		else
		{
			int lastSlash = request.getRequestURI().lastIndexOf('/');
			String endOfUrl = request.getRequestURI().substring(lastSlash + 1);
			String  eventname = endOfUrl.toString();
		//	eventname = eventname.replaceAll("+"," ");
			us = (UserStore) request.getSession().getAttribute("currentSeshUser");
			tm.setCluster(cluster);
			LinkedList<eventStore> eventList = tm.getEvents(us,2,eventname);
			request.setAttribute("Events", eventList); //Set a bean with the list in it
			if(eventname.equals("Sports") || eventname.equals("Concert") || eventname.equals("Food & Drink") || eventname.equals("Social"))
			{
				LinkedList<eventStore> eventsList = tm.getEvents(us,3,eventname);
				request.setAttribute("Events", eventsList); //Set a bean with the list in it
			
				RequestDispatcher rd = request.getRequestDispatcher("/Search.jsp"); 
				rd.forward(request, response);
			}
			else if(eventList.size() > 1)
			{
				
				RequestDispatcher rd = request.getRequestDispatcher("/Search.jsp"); 
				rd.forward(request, response);
			}
			else
			{
				
				RequestDispatcher rd = request.getRequestDispatcher("/Event.jsp"); 
				rd.forward(request, response);
			}
			
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserStore us = new UserStore();
		EventModel tm= new EventModel();
		FriendModel fm = new FriendModel();
		us = (UserStore) request.getSession().getAttribute("currentSeshUser");
		String eventname = request.getParameter("q");
		tm.setCluster(cluster);
		fm.setCluster(cluster);
		LinkedList<eventStore> eventList = tm.getEvents(us,2,eventname);
		request.setAttribute("Events", eventList); //Set a bean with the list in it
		LinkedList<UserStore> friendList = fm.displayUserssByName(us, eventname);
		request.setAttribute("Friends", friendList);
		RequestDispatcher rd = request.getRequestDispatcher("/Search.jsp"); 
		rd.forward(request, response);
		
	}

}
