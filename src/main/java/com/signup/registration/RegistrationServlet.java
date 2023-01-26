package com.signup.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String Name = request.getParameter("name");
		String Email = request.getParameter("email");
		String Pwd = request.getParameter("pass");
		String Contact = request.getParameter("contact");
		RequestDispatcher dispatcher = null;
		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/SignUp?useSSL=false", "root", "gaurav");
			PreparedStatement pt = con.prepareStatement("insert into users (Name,Pwd,Email,Contact) values(?,?,?,?) ");
			pt.setString(1, Name);
			pt.setString(2, Pwd);
			pt.setString(3, Email);
			pt.setString(4, Contact);

			int rowCount = pt.executeUpdate();
			dispatcher = request.getRequestDispatcher("registration.jsp");
			if (rowCount > 0) {
				request.setAttribute("status", "Success");
//				dispatcher= request.getRequestDispatcher("login.jsp");
				response.setHeader("Refresh","1.2;login.jsp");
				
				

			} else {
				request.setAttribute("status", "Failed");
			}
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

}
