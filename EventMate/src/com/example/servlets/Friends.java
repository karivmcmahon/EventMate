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
import com.example.stores.UserStore;
import com.example.stores.eventStore;

/**
 * Servlet implementation class Friends
 */
@WebServlet(urlPatterns = { "/Friends", "/Friends/*" })
public class Friends extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Cluster cluster;
	LinkedList<UserStore> friendList;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Friends() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init(ServletConfig config) throws ServletException 
    {
		// TODO Auto-generated method stub
		cluster = CassandraHosts.getCluster();
	}

	/**
	 * Servlet gets information from model about user and displays there friend list
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserStore us = new UserStore();
		FriendModel fm= new FriendModel();
		LinkedList<UserStore> friendList = null;
		//If request path friends get friend list and display on Friends.jsp
		if(request.getRequestURI().equals(request.getContextPath() + "/Friends"))
		{
			//Get session for user currently logged in
			us = (UserStore) request.getSession().getAttribute("currentSeshUser");
			fm.setCluster(cluster);
			try
			{
			 friendList = fm.displayFriends(us);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			request.setAttribute("Friends", friendList); //Set a bean with the list in it
			RequestDispatcher rd = request.getRequestDispatcher("/Friends.jsp"); 
	
			rd.forward(request, response);
		}
		else
		{
			//if request path has a string after friends attempt to get friends by that username
			int lastSlash = request.getRequestURI().lastIndexOf('/');
			String endOfUrl = request.getRequestURI().substring(lastSlash + 1);
			String usernames = endOfUrl.toString();
			us = (UserStore) request.getSession().getAttribute("currentSeshUser");
			fm.setCluster(cluster);
			try 
			{
			 friendList = fm.displayFriendsByUsername(us,usernames);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			request.setAttribute("Friends", friendList); //Set a bean with the list in it
			RequestDispatcher rd = request.getRequestDispatcher("/Friends.jsp"); 

			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
