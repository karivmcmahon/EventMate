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
import com.example.model.MessageModel;
import com.example.stores.MessageStore;
import com.example.stores.UserStore;

/**
 * Servlet implementation class Messages
 */
@WebServlet(urlPatterns = { "/Messages", "/Messages/*" })
public class Messages extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Cluster cluster;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Messages() {
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
		UserStore us = new UserStore();
		UserStore friendMessaged = new UserStore();
		MessageModel mm = new MessageModel();
		us = (UserStore) request.getSession().getAttribute("currentSeshUser");
		friendMessaged.setUsername(request.getParameter("username"));
		mm.setCluster(cluster);
		LinkedList<MessageStore> messageList = mm.getMessages(us,friendMessaged);
		request.setAttribute("Messages", messageList); //Set a bean with the list in it
		RequestDispatcher rd = request.getRequestDispatcher("/Message.jsp"); 

		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
