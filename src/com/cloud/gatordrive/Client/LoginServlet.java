package com.cloud.gatordrive.Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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

import com.cloud.gatordrive.ApplicationInfo;
import com.cloud.gatordrive.db.DBConnectionManager;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String errorMsg = null;
		if(username == null || username.equals("")){
			errorMsg ="Username can't be null or empty";
		}
		if(password == null || password.equals("")){
			errorMsg = "Password can't be null or empty";
		}
		
		if(errorMsg != null){
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
			PrintWriter out= response.getWriter();
			out.println("<font color=red>"+errorMsg+"</font>");
			rd.include(request, response);
		}else{
		
		Connection con = (Connection) getServletContext().getAttribute("DBConnection");
		/*
		String dbURL = "jdbc:mysql://localhost:3306/gatordrive";
		String dbUser = "root";
		String pwd = "abcd@1234";
			*/
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//DBConnectionManager connectionManager = new DBConnectionManager(dbURL, dbUser, pwd);
			//Connection con = connectionManager.getConnection();	
			
			ps = con.prepareStatement("select id, name, email from Users where name=? and password=? limit 1");
			ps.setString(1, username);
			ps.setString(2, password);
			rs = ps.executeQuery();
			
			if(rs.next()){
				//rs.next();
				User user = new User(rs.getString("name"), rs.getString("email"), rs.getInt("id"));
				//logger.info("User found with details="+user);
				HttpSession session = request.getSession();
				session.setAttribute("User", user);
				session.setAttribute("username", user.getName());
				ApplicationInfo.userName = user.getName();
				session.setAttribute("type", "created");
				//response.sendRedirect("Home2.jsp");;
				//RequestDispatcher dispatcher = request
				//		.getRequestDispatcher("Home2.jsp");
				//System.out.println("LOGS");
				/*
				if(dispatcher == null){
					System.out.println("Dispatcher is NULL");
				}
				if(request == null){
					System.out.println("request is NULL");
				}
				if(response == null){
					System.out.println("response is NULL");
				}
				dispatcher.forward(request, response);
				*/
				/*
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/Home2.jsp");
				rd.include(request, response);
				*/
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("Home2.jsp");
				dispatcher.forward(request, response);
			}else{
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
				PrintWriter out= response.getWriter();
				//logger.error("User not found with email="+email);
				out.println("<font color=red>No user found with given email id, please register first.</font>");
				rd.include(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			//logger.error("Database connection problem");
			throw new ServletException("DB Connection problem.");
		}/*catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}*/finally{
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				//logger.error("SQLException in closing PreparedStatement or ResultSet");;
			}
			
		}
		}
	}

}

