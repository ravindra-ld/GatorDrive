package com.cloud.gatordrive;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import com.cloud.gatordrive.entity.Payload;

public class DataCommunicator {

	public static final String SERVER_BASE_ADDRESS = "http://192.168.0.20:8080/GatorDrive";
	
	private static final int Timeout = 15000;
	
	public static InputStream sendPostDataToServer(String server, Payload payload) throws Exception{
		InputStream is = null;
		JSONObject obj = new JSONObject();
		
		try{
			URL url = new URL(server);
			
			HttpURLConnection  urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(true);
			urlConnection.setUseCaches(false);
			urlConnection.setConnectTimeout(Timeout);
			
			OutputStream os = urlConnection.getOutputStream();
			
			obj.put("usernameTo", payload.usernameTo);
			obj.put("fileName", payload.fileName);
			
			os.write(obj.toString().getBytes("UTF-8"));
			os.close();
			
			int respCode = urlConnection.getResponseCode();
			
			if(respCode == HttpURLConnection.HTTP_OK){
				is = urlConnection.getInputStream();
			}else{
				is = urlConnection.getErrorStream();
			}
			
		}catch(Exception e){
			//Log.e("DataCommunicator:sendPostDataToServer","Exception in posting data : " + e);
		}

		return is;		
	}
	
	public static InputStream sendPostDataToServer1(String server,List<NameValuePair> payload) throws Exception{
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(server);
		
		try{
			httpPost.setEntity(new UrlEncodedFormEntity(payload));
			
			HttpResponse response = httpClient.execute(httpPost);
			
			HttpEntity entity = response.getEntity();
			//InputStream is = entity.getContent();
			
			if(entity == null){
				return null;
			}else{
				return entity.getContent();
			}
		}catch(ClientProtocolException e) {
	    	//Log.e("DataCommunicator:sendGetDataToServer", "ClientProtocolException in sendGetDataToServer");
	    	e.printStackTrace();
	    	throw e;
	    } catch (IOException e) {
	    	//Log.e("DataCommunicator:sendGetDataToServer", "IOException in sendGetDataToServer" + e);
	    	e.printStackTrace();
	    	throw e;
	    }
	}
	
	
	public static InputStream sendGetDataToServer(String server) throws Exception{
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(server);
		HttpParams params = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(params, Timeout);
		HttpConnectionParams.setSoTimeout(params, Timeout);
		
		try{
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			
			if(entity == null){
				return null;
			}else{
				return entity.getContent();
			}
		}catch (ClientProtocolException e) {
	    	//Log.e("DataCommunicator:sendGetDataToServer", "ClientProtocolException in sendGetDataToServer");
	    	e.printStackTrace();
	    	throw e;
	    } catch (IOException e) {
	    	//Log.e("DataCommunicator:sendGetDataToServer", "IOException in sendGetDataToServer" + e);
	    	e.printStackTrace();
	    	throw e;
	    }
	}
	
}
