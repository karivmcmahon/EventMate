package com.example.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.datastax.driver.core.Cluster;
import com.example.libs.CassandraHosts;
import com.example.model.UserModel;
import com.example.stores.UserStore;

/**
 * Servlet implementation class Settings
 */
@WebServlet(urlPatterns = { "/Settings", "/Settings/*" })
public class Settings extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Cluster cluster;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Settings() {
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
		//Redirect to Settings.jsp
		UserStore us = new UserStore();
		us = (UserStore) request.getSession().getAttribute("currentSeshUser");
		RequestDispatcher rd = request.getRequestDispatcher("/Settings.jsp");
		request.setAttribute("user", us);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserModel um = new UserModel();
		UserStore us = (UserStore) request.getSession().getAttribute("currentSeshUser");
		//Set information for user and redirect to settings interest
		us.setName(request.getParameter("name"));
		us.setEmail(request.getParameter("email"));
		us.setBio(request.getParameter("editBio"));
		us.setGender(request.getParameter("gender"));
		us.setGenderPref(request.getParameter("editGenderPref"));
		us.setAgeMin(Integer.parseInt(request.getParameter("minAge")));
		us.setAgeMax(Integer.parseInt(request.getParameter("maxAge")));
		us.setLocation(request.getParameter("location"));
		us.setPostcode(request.getParameter("postcode"));
		us.setDistancePref(Integer.parseInt(request.getParameter("distance")));
		us.setRelationship(request.getParameter("relationshipStat"));
		us.setInterestedIn(request.getParameter("editGender"));
		response.sendRedirect("/EventMate/SettingInterests");
	}

}
