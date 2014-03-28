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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
		UserStore us = new UserStore();
		UserStore user = new UserStore();
		if (request.getParameter("usernameSignUp").equals("")
				|| request.getParameter("passwordSignUp").equals("")
				|| request.getParameter("emailSignUp").equals("")) {
			RequestDispatcher rd = request.getRequestDispatcher("/Home.jsp");
			request.setAttribute("invalidSignUp", "Invalid");
			rd.forward(request, response);
		} else {
			us.setUsername(request.getParameter("usernameSignUp"));
			String password = "?btY?N1&zt" + request.getParameter("passwordSignUp") + "4IoIS^NY!r";
			System.out.println("password" + password);

			String pw = "";
			try {
				pw = encrypt(password);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			us.setPassword(pw);
			us.setEmail(request.getParameter("emailSignUp"));
			if (um.checkUsername(us) != "") {
				RequestDispatcher rd = request
						.getRequestDispatcher("/Home.jsp");
				request.setAttribute("invalidSignUp",
						"Username already in use!");
				rd.forward(request, response);
			} else if (um.checkEmail(us) != "") {
				RequestDispatcher rd = request
						.getRequestDispatcher("/Home.jsp");
				request.setAttribute("invalidSignUp", "Email already in use!");
				rd.forward(request, response);
			} else {
				HttpSession session = request.getSession(true);
				session.setAttribute("possibleUser", us);
				us = (UserStore) request.getSession().getAttribute(
						"possibleUser");
				// Direct to home.jsp once session true
				response.sendRedirect("/EventMate/Bio");
			}
		}
	}

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

		// Print out the string hex version of the md5 hash
		System.out.println("MD5 version is: " + hashedpasswd.toString()
				+ "<br>");
		return hashedpasswd.toString();

	}

}