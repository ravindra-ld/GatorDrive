package com.cloud.gatordrive.Client;

import java.util.List;

import com.cloud.gatordrive.RequestHandler;

public class GetFiles {

	public List<String> getCreatedFiles(String username) {
		
		//String username = "gators";
		
		RequestHandler reqHandler = new RequestHandler(username);

		List<String> files = reqHandler.getUserFiles();
		
		return files;
	}
	
	public List<String> getSharedFiles(String username) {
		
		//String username = "gators";
		
		RequestHandler reqHandler = new RequestHandler(username);
	
		List<String> files = reqHandler.getSharedFiles();
		
		return files;
	}
	
}
