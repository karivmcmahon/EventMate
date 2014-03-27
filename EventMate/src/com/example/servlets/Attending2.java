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
 * Servlet implementation class Attending2
 */
@WebServlet(urlPatterns = { "/Attending2", "/Attending2/*" })
public class Attending2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Cluster cluster;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Attending2() {
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
	 * Gets event user wants to attend sends it model to insert to db and then redirects back to the RandomEventServlet
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		EventModel em = new EventModel();
		UserStore us = new UserStore();
		//Gets current logged in user
	    us = (UserStore) request.getSession().getAttribute("currentSeshUser");
	    //Gets event name
	    String event = request.getParameter("tick");
	    em.setCluster(cluster);
	    //Inserts attending event
	    em.setAttending(us,event);
	    //Redirect to RandomEvent servlet
	    response.sendRedirect("/EventMate/RandomEvent");
	}

}
