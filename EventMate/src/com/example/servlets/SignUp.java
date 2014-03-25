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

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.ResultSet;
import com.example.libs.CassandraHosts;
import com.example.model.UserModel;
import com.example.stores.UserStore;

/**
 * Servlet implementation class SignUp
 */
@WebServlet(urlPatterns = { "/SignUp", "/SignUp/*" })
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Cluster cluster;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUp() {
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
		UserModel um = new UserModel();
		um.setCluster(cluster);
		UserStore us = new UserStore();
		UserStore user = new UserStore();
		if(request.getParameter("usernameSignUp").equals("") || request.getParameter("passwordSignUp").equals("") || request.getParameter("emailSignUp").equals(""))
		{
			RequestDispatcher rd = request.getRequestDispatcher("/Home.jsp");
			request.setAttribute("invalidSignUp", "Invalid");
			rd.forward(request, response);
		}
		else
		{
		us.setUsername(request.getParameter("usernameSignUp"));
		us.setPassword(request.getParameter("passwordSignUp"));
		us.setEmail(request.getParameter("emailSignUp"));
		if (um.checkUsername(us) != "") {
		RequestDispatcher rd = request.getRequestDispatcher("/Home.jsp");
		request.setAttribute("invalidSignUp", "Username already in use!");
		rd.forward(request, response);
		}else if(um.checkEmail(us) != ""){
		RequestDispatcher rd = request.getRequestDispatcher("/Home.jsp");
		request.setAttribute("invalidSignUp", "Email already in use!");
		rd.forward(request, response);
		}else{
		HttpSession session = request.getSession(true);
		session.setAttribute("possibleUser", us);
		us = (UserStore) request.getSession().getAttribute("possibleUser");
		// Direct to home.jsp once session true
		response.sendRedirect("/EventMate/Bio");
		}
		}
	}

}
