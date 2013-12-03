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

public class DownloadController extends HttpServlet {
	static final long serialVersionUID = 1L;
	int offset;
	int length;
	List<StudentDetailsDTO> list;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	
		String filename = request.getParameter("fileName");
		
		HttpSession httpSession = request.getSession();
		
		String username = ApplicationInfo.userName; // (String) httpSession.getAttribute("username");
		
		httpSession.setAttribute("username",  username/*"gators"*/);
		
		DownLoadFile df = new DownLoadFile();
		
		df.downloadFile(filename, username);
		
		
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("Home2.jsp");
		dispatcher.forward(request, response);
		
	}
}
