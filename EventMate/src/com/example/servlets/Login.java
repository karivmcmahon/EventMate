package com.example.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.datastax.driver.core.Cluster;
import com.example.libs.CassandraHosts;
import com.example.model.UserModel;
import com.example.stores.UserStore;

/**
 * Servlet implementation class Login
 */
@WebServlet(urlPatterns = { "/Login", "/Login/*" })
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Cluster cluster; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
		System.out.println("hello");
		UserModel um = new UserModel();
		UserStore us = new UserStore();
		um.setCluster(cluster);
		us.setUsername(request.getParameter("username"));
		us.setPassword(request.getParameter("password"));
		try
		{
			us = um.getUser(us);
		}
		catch(Exception e)
		{
			
		}
		
		if(us.getValid())
		{
			us.setLoggedIn(true);
			HttpSession session = request.getSession(true);
			session.setAttribute("currentSeshUser", us);
			//Direct to home.jsp once session true
			response.sendRedirect("/EventMate/Event");
		}
		else
		{
			System.out.println("not valid");
			us.setLoggedIn(false);
			RequestDispatcher rd = request.getRequestDispatcher("/Home.jsp"); 
		    rd.forward(request, response); 
		}
		}
	}


