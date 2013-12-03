package com.cloud.gatordrive.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cloud.gatordrive.DataCommunicator;
import com.cloud.gatordrive.entity.Payload;

/**
 * Servlet implementation class ShareFileServlet
 */
@WebServlet("/ShareFile")
public class ShareFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public final String SERVER_BASE_ADDRESS = "http://192.168.0.20:8080/GatorDrive/Share";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShareFileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String fileName = request.getParameter("sharedFilename");
		String userName = request.getParameter("sharedUsername");
		
		//DataCommunicator dataCommunicator = new DataCommunicator();
		Payload payload = new Payload();
		
		payload.fileName = fileName;
		payload.usernameTo = userName;
		InputStream is = null;
		
		HttpSession session = request.getSession();
		/*
		if((String)session.getAttribute("username") != null){
			session.setAttribute("username", );
		}
		*/
		try {
			is = DataCommunicator.sendPostDataToServer(SERVER_BASE_ADDRESS, payload);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String result = convertStreamToString(is);
		
		System.out.println("result = "+result);
		
	PrintWriter out = response.getWriter();
		
		boolean fileUploadStatus = false;
		
		if(result.trim().replaceAll("\\s+","").compareToIgnoreCase("success=1") == 0)
			fileUploadStatus = true;
		
		//forward to login page to login
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/ShareFile.jsp");		
		
		if(fileUploadStatus)
			out.println("<font color=green>File sharing successful.<br> If you want to share the file to others as well, please enter their username.</font>");
		else
			out.println("<font color=red>File sharing failed. Please try again.</font>");		
		
		rd.include(request, response);	
		
	}
	
	public static String convertStreamToString(InputStream is) {

		String resString = "";

		if (is == null) return "";

		try { BufferedReader br = new BufferedReader(new InputStreamReader(is)); String str = ""; while ((str = br.readLine()) != null) { resString += str + "\n"; } } catch (IOException io) { io.printStackTrace(); resString = null; }

		return resString; }

}
