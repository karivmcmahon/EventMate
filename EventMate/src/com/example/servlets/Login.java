package com.example.servlets;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
	 * Gets user info and checks if it is valid and if it is direct to front page and if not direct home
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserStore us = new UserStore();
		UserModel um = new UserModel();
		um.setCluster(cluster);
		us = (UserStore) request.getSession().getAttribute("possibleUser");
		try {
			//Gets user info
			us = um.getUser(us);
		} catch (Exception e) {

		}

		//Checks if user is valid and logs them in if it is
		if (us.getValid()) {
			us.setLoggedIn(true);
			HttpSession session = request.getSession(true);
			session.setAttribute("currentSeshUser", us);
			// Direct to home.jsp once session true
			response.sendRedirect("/EventMate/Event");
		} else {
			//If not valid redirects them to sign up page
			System.out.println("not valid");
			us.setLoggedIn(false);
			RequestDispatcher rd = request.getRequestDispatcher("/Home.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * Attempts to log user in
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("hello");
		UserModel um = new UserModel();
		UserStore us = new UserStore();
		um.setCluster(cluster);
		us.setUsername(request.getParameter("username"));
		String password = "?btY?N1&zt" + request.getParameter("password") + "4IoIS^NY!r";
		String pw = "";
		try {
			//Encrypt password
			pw = encrypt(password);
		} 
		catch (NoSuchAlgorithmException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//Set password to encrypted
		us.setPassword(pw);
		try {
			//Gets user
			us = um.getUser(us);
		} catch (Exception e) {

		}
		//Checks if user is valid and if they are direct to Event page else direct to home.jsp
		if (us.getValid()) {
			us.setLoggedIn(true);
			HttpSession session = request.getSession(true);
			session.setAttribute("currentSeshUser", us);
			// Direct to home.jsp once session true
			response.sendRedirect("/EventMate/Event");
		} else {
			System.out.println("not valid");
			us.setLoggedIn(false);
			RequestDispatcher rd = request.getRequestDispatcher("/Home.jsp");
			request.setAttribute("invalidLogin",
					"incorrect username or password");
			rd.forward(request, response);
		}
	}

	/**
	 * Password encryption - MD5
	 * @param pass
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public String encrypt(String pass) throws NoSuchAlgorithmException {
		// Set password string, and print it out
		String passwd = pass;
		System.out.println("Password is: " + passwd + ".<br>");

		// Create a new instance of MessageDigest, using MD5. SHA and other
		// digest algorithms are also available.
		MessageDigest alg = MessageDigest.getInstance("MD5");

		// Reset the digest, in case it's been used already during this section
		// of code
		// This probably isn't needed for pages of 210 simplicity
		alg.reset();

		// Calculate the md5 hash for the password. md5 operates on bytes, so
		// give
		// MessageDigest the byte verison of the string
		alg.update(passwd.getBytes());

		// Create a byte array from the string digest
		byte[] digest = alg.digest();

		// Convert the hash from whatever format it's in, to hex format
		// which is the normal way to display and report md5 sums
		// This is done byte by byte, and put into a StringBuffer
		StringBuffer hashedpasswd = new StringBuffer();
		String hx;
		for (int i = 0; i < digest.length; i++) {
			hx = Integer.toHexString(0xFF & digest[i]);
			// 0x03 is equal to 0x3, but we need 0x03 for our md5sum
			if (hx.length() == 1) {
				hx = "0" + hx;
			}
			hashedpasswd.append(hx);
		}

	
		return hashedpasswd.toString();

	}
}