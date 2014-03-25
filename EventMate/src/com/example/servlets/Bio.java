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
import javax.servlet.http.HttpSession;

import com.datastax.driver.core.Cluster;
import com.example.libs.CassandraHosts;
import com.example.model.UserModel;
import com.example.stores.UserStore;

/**
 * Servlet implementation class Bio
 */
@WebServlet(urlPatterns = { "/Bio", "/Bio/*" })
public class Bio extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Cluster cluster;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Bio() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		cluster = CassandraHosts.getCluster();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserStore us = new UserStore();
		us = (UserStore) request.getSession().getAttribute("possibleUser");
		RequestDispatcher rd = request.getRequestDispatcher("/BioInfo.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserModel um = new UserModel();
		UserStore us = (UserStore) request.getSession().getAttribute(
		"possibleUser");
		String name = request.getParameter("name");
		String postcode = request.getParameter("postcode");
		if (name == "" && postcode == "") {
		RequestDispatcher rd = request.getRequestDispatcher("/BioInfo.jsp");
		request.setAttribute("invalidName", "Please enter your name!");
		request.setAttribute("invalidPostcode",
		"Please enter your postcode!");
		rd.forward(request, response);
		} else if (name == "") {
		RequestDispatcher rd = request.getRequestDispatcher("/BioInfo.jsp");
		request.setAttribute("invalidName", "Please enter your name!");
		rd.forward(request, response);
		} else if (postcode == "") {
		RequestDispatcher rd = request.getRequestDispatcher("/BioInfo.jsp");
		request.setAttribute("invalidPostcode",
		"Please enter your postcode!");
		rd.forward(request, response);
		} else {
		us.setName(request.getParameter("name"));
		us.setBio(request.getParameter("bio"));
		us.setGender(request.getParameter("gender"));
		us.setGenderPref(request.getParameter("genderPref"));
		us.setAgeMin(Integer.parseInt(request.getParameter("minAge")));
		us.setAgeMax(Integer.parseInt(request.getParameter("maxAge")));
		us.setLocation(request.getParameter("location"));
		// removing spaces from postcode
		postcode = postcode.replaceAll("\\s+", "");
		System.out.println(postcode);
		us.setPostcode(postcode);
		us.setDistancePref(Integer.parseInt(request
		.getParameter("distance")));
		us.setRelationship(request.getParameter("relationshipStat"));
		us.setInterestedIn(request.getParameter("editGender"));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		String dob = request.getParameter("year") + "/"
		+ request.getParameter("month") + "/"
		+ request.getParameter("day");
		System.out.println(dob);
		String facebook = request.getParameter("uploadFacebook");
		String photoUrl = request.getParameter("uploadURL");
		String photo = "";
		if (facebook != "" && photoUrl != "") {
		RequestDispatcher rd = request
		.getRequestDispatcher("/BioInfo.jsp");
		request.setAttribute("invalidPhoto",
		"Please only enter a Facebook ID or a URL!");
		rd.forward(request, response);
		} else if (facebook != "") {
		photo = "https://graph.facebook.com/" + facebook
		+ "/picture?type=large";
		} else if (photoUrl != "") {
		photo = photoUrl;
		} else {
		if (request.getParameter("gender").equals("male")) {
		photo = "images/man-silhouette.png";
		}else{
		photo = "images/silhouette-profile.png";
		}
		}
		//us.setPhoto(photo);
		try {
		Date date = formatter.parse(dob);
		us.setDob(date);
		System.out.println(formatter.format(date));
		} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		
		// Direct to home.jsp once session true
		response.sendRedirect("/EventMate/Interests");

	}
	}
}
