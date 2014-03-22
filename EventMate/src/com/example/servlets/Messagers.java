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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserStore us = new UserStore();
		MessageModel fm= new MessageModel();
		//Get session for user currently logged in
		us = (UserStore) request.getSession().getAttribute("currentSeshUser");
		fm.setCluster(cluster);
		LinkedList<MessagerStore> messageList = fm.messagerList(us);
		request.setAttribute("Message", messageList); //Set a bean with the list in it
		RequestDispatcher rd = request.getRequestDispatcher("/Messages.jsp"); 

		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
