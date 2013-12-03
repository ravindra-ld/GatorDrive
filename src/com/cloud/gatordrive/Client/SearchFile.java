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
import com.cloud.gatordrive.RequestHandler;

public class SearchFile extends HttpServlet {
	static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession httpSession = request.getSession();
		//httpSession.setAttribute("pages", getPages());
		//httpSession.setAttribute("studentDetails", getListByOffsetAndLength());
		String filename = request.getParameter("filename");
		
		String username = ApplicationInfo.userName; // "gators";
		RequestHandler reqHandler = new RequestHandler(username);
		List<String> files = reqHandler.searchUserFiles(filename);
		
		httpSession.setAttribute("type", "search");
		
		httpSession.setAttribute("username", ApplicationInfo.userName);
		//System.out.println("Files = "+files.toString());
		response.getWriter().write(files.toString());
		request.setAttribute("files", files.toString());
		
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("Home2.jsp");
		dispatcher.forward(request, response);
		
	}
}
