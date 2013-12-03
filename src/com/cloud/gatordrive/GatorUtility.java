package com.cloud.gatordrive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GatorUtility {

	public static String convertStreamToString(InputStream is) {

		String resString = "";

		if (is == null)
			return "";

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String str = "";
			while ((str = br.readLine()) != null) {
				resString += str + "\n";
			}
		} catch (IOException io) {
			io.printStackTrace();
			resString = null;
		}

		return resString;
	}
	
}
