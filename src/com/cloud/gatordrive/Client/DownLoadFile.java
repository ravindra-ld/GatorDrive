
package com.cloud.gatordrive.Client;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownLoadFile {
	
	public void downloadFile(String filename){
		Frame myFrame = new Frame();
		try {

			String request = "http://192.168.0.20:8080/GatorDrive/download/"+filename;
			URL url = new URL(request);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("GET");
			//connection.setRequestProperty("Content-Type",
			//		"application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Type","application/json");
			connection.setRequestProperty("charset", "utf-8");
			
			//connection.setRequestProperty("Content-Length",
			//		"" + Integer.toString(urlParameters.getBytes().length));
			connection.setUseCaches(false);

			int responseCode = connection.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
	 
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
				response.append("\n");
			}
			in.close();
	    
			FileDialog choo = new FileDialog(myFrame, "Choose your file destination",FileDialog.SAVE);
			choo.setDirectory(null);
			choo.setFile("enter file name here");
	        choo.setVisible(true);
	        
	        String targetFile = choo.getDirectory() + choo.getFile();
	        
	        BufferedWriter out = new BufferedWriter(new FileWriter(new File(targetFile)));
			out.write(response.toString());
			out.close();
			
			//print result
			//System.out.println(response.toString());
			// connection.disconnect();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
