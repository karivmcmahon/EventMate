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
import com.example.model.ProfileModel;
import com.example.stores.ProfileStore;
import com.example.stores.UserStore;
import com.example.stores.eventStore;

/**
 * Servlet implementation class Profile
 */
@WebServlet(urlPatterns = { "/Profile", "/Profile/*" })
public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Cluster cluster;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Profile() {
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
		//String args[]=Convertors.SplitRequestPath(request);
				UserStore us = new UserStore();
				ProfileModel pm = new ProfileModel();
				if(request.getRequestURI().equals(request.getContextPath() + "/Profile"))
				{
				//Get session for user currently logged in
				us = (UserStore) request.getSession().getAttribute("currentSeshUser");
				pm.setCluster(cluster);
				LinkedList<ProfileStore> profileList = pm.getProfile(us);
				request.setAttribute("Profile", profileList); //Set a bean with the list in it
				RequestDispatcher rd = request.getRequestDispatcher("/Profile.jsp"); 

				rd.forward(request, response);
				}
				else
				{
					int lastSlash = request.getRequestURI().lastIndexOf('/');
					String endOfUrl = request.getRequestURI().substring(lastSlash + 1);
					String usernames = endOfUrl.toString();
					us = (UserStore) request.getSession().getAttribute("currentSeshUser");
					pm.setCluster(cluster);
					LinkedList<ProfileStore> profileList = pm.getProfileByUsername(usernames);
					request.setAttribute("Profile", profileList); //Set a bean with the list in it
					RequestDispatcher rd = request.getRequestDispatcher("/Profile.jsp"); 

					rd.forward(request, response);				}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
