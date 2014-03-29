package com.example.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.datastax.driver.core.Cluster;
import com.example.libs.CassandraHosts;
import com.example.model.EventModel;
import com.example.stores.UserStore;

/**
 * Servlet implementation class Attending
 */
@WebServlet(urlPatterns = { "/Attending", "/Attending/*" })
public class Attending extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Cluster cluster;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Attending() {
        super();
        // TODO Auto-generated constructor stub
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
	}

	/**
	 * Sets that user is attending event sent in from jsp file
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		EventModel em = new EventModel();
		UserStore us = new UserStore();
		//Get current logged in user
	    us = (UserStore) request.getSession().getAttribute("currentSeshUser");
	    //Get Parameter that has event name
	    String event = request.getParameter("tick");
	    //Set attending
	    em.setCluster(cluster);
	    try
	    {
	    	//Insert event user wants to attend
	    	em.setAttending(us,event);
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    	System.out.println("Error setting attending at setAttending()");
	    }
	    //Redirect to event servlet
	    response.sendRedirect("/EventMate/Event");

	}

}
