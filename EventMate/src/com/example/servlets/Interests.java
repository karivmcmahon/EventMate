package com.example.servlets;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.datastax.driver.core.Cluster;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.libs.CassandraHosts;
import com.example.model.UserModel;
import com.example.stores.UserStore;

/**
 * Servlet implementation class Interests
 */
@WebServlet(urlPatterns = { "/Interests", "/Interests/*" })
public class Interests extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Cluster cluster;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Interests() {
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
		RequestDispatcher rd = request.getRequestDispatcher("/Interests.jsp");
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
		um.setCluster(cluster);
		UserStore us = (UserStore) request.getSession().getAttribute(
				"possibleUser");
		Set<String> interests = new HashSet<String>();
		Set<String> sports = new HashSet<String>();
		Set<String> musics = new HashSet<String>();
		String[] interest = request.getParameterValues("interest");
		String[] sport = request.getParameterValues("sport");
		String[] music = request.getParameterValues("music");
		if (interest != null) {
			for (int i = 0; i < interest.length; i++) {
				interests.add(interest[i]);
			}
			us.setInterests(interests);
		}
		if (sport != null) {
			for (int i = 0; i < sport.length; i++) {
				sports.add(sport[i]);
			}
			us.setSports(sports);
		}
		if (music != null) {
			for (int i = 0; i < music.length; i++) {
				musics.add(music[i]);
			}
			us.setMusic(musics);
		}
		// Direct to home.jsp once session true
		try {
			um.addUser(us);
			response.sendRedirect("/EventMate/Login");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
