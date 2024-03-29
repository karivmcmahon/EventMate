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
import com.example.stores.UserStore;
import com.example.stores.eventStore;

/**
 * Servlet implementation class RandomEvent
 */
@WebServlet(urlPatterns = { "/RandomEvent", "/RandomEvent/*" })
public class RandomEvent extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Cluster cluster;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RandomEvent() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		cluster = CassandraHosts.getCluster();
	}
    

	/**
	 *Servlet gets random event from database and then calls Random.jsp to display
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//Gets random event and displays it on random page
		UserStore us = new UserStore();
		EventModel tm= new EventModel();
		eventStore event = null;
		//Get session for user currently logged in
		us = (UserStore) request.getSession().getAttribute("currentSeshUser");
		tm.setCluster(cluster);
		//Get random event
		try
		{
			event = tm.count(us);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		request.setAttribute("Event", event); //Set a bean with the list in it
		//Redirect to Random.jsp
		RequestDispatcher rd = request.getRequestDispatcher("/Random.jsp"); 
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
