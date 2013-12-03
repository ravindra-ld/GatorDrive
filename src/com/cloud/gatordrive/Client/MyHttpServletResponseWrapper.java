package com.cloud.gatordrive.Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class MyHttpServletResponseWrapper extends HttpServletResponseWrapper{

	private StringWriter sw = new StringWriter();
	
	public MyHttpServletResponseWrapper(HttpServletResponse response) {
		super(response);
		
	}

	public PrintWriter getWriter() throws IOException {
	    return new PrintWriter(sw);
	  }

	  public ServletOutputStream getOutputStream() throws IOException {
	    throw new UnsupportedOperationException();
	  }

	  public String toString() {
	    return sw.toString();
	  }
}
