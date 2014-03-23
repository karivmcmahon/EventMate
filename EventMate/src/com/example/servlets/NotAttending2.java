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
 * Servlet implementation class NotAttending2
 */
@WebServlet(urlPatterns = { "/NotAttending2", "/NotAttending2/*" })
public class NotAttending2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Cluster cluster;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NotAttending2() {
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		EventModel em = new EventModel();
		UserStore us = new UserStore();
	    us = (UserStore) request.getSession().getAttribute("currentSeshUser");
	    String event = request.getParameter("cross");
	    em.setCluster(cluster);
	    em.setNotAttending(us,event);
	    
	    response.sendRedirect("/EventMate/RandomEvent");
	}

}
