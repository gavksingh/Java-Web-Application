package com.signup.registration;
//import java.sql.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String Email= request.getParameter("username");
		String Pwd= request.getParameter("password");
		HttpSession session= request.getSession();
		RequestDispatcher dispatcher= null;
		Connection con=null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con= DriverManager.getConnection("jdbc:mysql://localhost:3306/SignUp","root","gaurav");
			PreparedStatement pt= con.prepareStatement("select * from users where Email= ? and Pwd = ?");
			pt.setString(1, Email);
			pt.setString(2, Pwd);
			
			ResultSet rs=pt.executeQuery();
			if(rs.next()) {
				session.setAttribute("name", rs.getString("Name"));
				dispatcher= request.getRequestDispatcher("index.jsp");
//				 String site = new String("https://gavksingh.github.io/chilflix.github.io/");
//		         response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
//		         response.setHeader("Location", site); 
			}else {
				request.setAttribute("status", "failed");
				dispatcher=request.getRequestDispatcher("login.jsp");
			}
			dispatcher.forward(request, response);
			
		}catch(Exception e) {
			e.printStackTrace();
			
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
