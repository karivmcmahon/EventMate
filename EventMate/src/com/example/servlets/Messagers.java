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
import com.example.model.FriendModel;
import com.example.model.MessageModel;
import com.example.stores.MessagerStore;
import com.example.stores.UserStore;

/**
 * Servlet implementation class Messagers
 */
@WebServlet(urlPatterns = { "/Messagers", "/Messagers/*" })
public class Messagers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Cluster cluster;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Messagers() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init(ServletConfig config) throws ServletException {
  		// TODO Auto-generated method stub
  		cluster = CassandraHosts.getCluster();
  	}

	/**
	 * Servlet gets information from jsp and then uses it to retrieve list of users who sent logged in user a message
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserStore us = new UserStore();
		MessageModel fm= new MessageModel();
		LinkedList<MessagerStore> messageList = null;
		//If the url request is /Messagers
		if(request.getRequestURI().equals(request.getContextPath() + "/Messagers"))
		{
			//Display all the users the logged in user has messages from
			us = (UserStore) request.getSession().getAttribute("currentSeshUser");
			fm.setCluster(cluster);
			try
			{
			//Attempts to get list of people the user has messager from
			 messageList = fm.messagerList(us,1,"");
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			request.setAttribute("Message", messageList); //Set a bean with the list in it
			RequestDispatcher rd = request.getRequestDispatcher("/Messages.jsp"); 
			rd.forward(request, response);
		}
		else
		{
			//If there is a string after the slash attempt to display messager list by username
			int lastSlash = request.getRequestURI().lastIndexOf('/');
			String endOfUrl = request.getRequestURI().substring(lastSlash + 1);
			String usernames = endOfUrl.toString();
			us = (UserStore) request.getSession().getAttribute("currentSeshUser");
			fm.setCluster(cluster);
			try
			{
			 messageList = fm.messagerList(us,2,usernames);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			request.setAttribute("Message", messageList); //Set a bean with the list in it
			RequestDispatcher rd = request.getRequestDispatcher("/Messages.jsp"); 
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
