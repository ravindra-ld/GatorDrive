package com.cloud.gatordrive.Client;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cloud.gatordrive.ApplicationInfo;

public class SignOutServlet extends HttpServlet {
	static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession httpSession = request.getSession();

		httpSession.setAttribute("username", null);
		ApplicationInfo.userName = "";
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("Home2.jsp");
		dispatcher.forward(request, response);
		
	}
}
