package com.cloud.gatordrive.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.cloud.gatordrive.RequestHandler;

public class GetFilesServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
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
		
		username = "gators";
		
		RequestHandler reqHandler = new RequestHandler(username);
		
		//int success = reqHandler.deleteFile(filename);
		
		List<String> files = reqHandler.getUserFiles();
		
		resp.setContentType("text/plain");
        try {
                JSONObject json = new JSONObject();
                //json.put("success", success);
                json.put("files", files);
                resp.getWriter().println(json.toString());
                //resp.getWriter().println("success="+success);
        } catch (Exception e1) {
                e1.printStackTrace();
        }
		
		
	}

}
