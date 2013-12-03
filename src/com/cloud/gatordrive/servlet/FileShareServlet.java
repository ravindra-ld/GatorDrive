package com.cloud.gatordrive.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.cloud.gatordrive.ApplicationInfo;
import com.cloud.gatordrive.GatorUtility;
import com.cloud.gatordrive.RequestHandler;
import com.cloud.gatordrive.entity.ShareObj;
import com.google.gson.Gson;

public class FileShareServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Gson gson = new Gson();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String jsonData = GatorUtility.convertStreamToString(req.getInputStream());
		ShareObj obj = gson.fromJson(jsonData, ShareObj.class);
		
		
		Cookie[] cookies = req.getCookies();
        String username = "";
        /*
        for(Cookie obj : cookies){
        	if(obj.getName().equals("username")){
        		username = obj.getValue();
        	}
        }
        
        if(username.contentEquals("")){
        	//username not present in cookies
        	//redirect to login page
        }
        */
		
		//filename = req.getParameter("filename");
		
        HttpSession httpSession = req.getSession();
        
		String usernameFrom =  ApplicationInfo.userName; // (String) httpSession.getAttribute("username") ; //"gators";
		String usernameTo = obj.usernameTo; // req.getParameter("usernameTo");
		String filename = obj.fileName; // req.getParameter("fileName");
		
		System.out.println("Username = "+ usernameTo);
		System.out.println("Filename = "+ filename);
		
		RequestHandler reqHandler = new RequestHandler(usernameFrom);
		
		
		int success = reqHandler.shareFile(filename, usernameTo);
		
		resp.setContentType("text/plain");
        try {
                JSONObject json = new JSONObject();
                json.put("success", success);
                resp.getWriter().println("success="+success);
                //resp.getWriter().println("success="+success);
                System.out.println("success="+success);
        } catch (Exception e1) {
                e1.printStackTrace();
        }
		
		
	}
}
