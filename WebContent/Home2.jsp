<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List"%>
<%@page
	import="com.cloud.gatordrive.Client.StudentDetailsDTO"
	import="com.cloud.gatordrive.Client.GetFiles" %>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="Style1.css" type="text/css">
<title>Gator Drive : Home</title>
<script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.10.2.min.js"></script>
<script src="FileView.js"></script>
<script>
function inputFocus(i){
    if(i.value==i.defaultValue){ i.value=""; i.style.color="#000"; }
}
function inputBlur(i){
    if(i.value==""){ i.value=i.defaultValue; i.style.color="#888"; }
}

$(document).ready(function () {
   var location = window.location;
   var found = false;
   $("#tab-container a").each(function(){
      var href = $(this).attr("href");
      if(href==location){
         $(this).parent().addClass("selected");
         found = true;
      }
   });
   if(!found){
      $("#tab-container li:first").addClass("selected");
   }
});

$(document).ready(function() { 
    $("table") 
    .tablesorter({widthFixed: true, widgets: ['zebra']}) 
    .tablesorterPager({container: $("#pager")}); 
});

</script>

</head>
<body>

<div id="maincontainer">

<div id="topsection"><div class="innertube">

<div id="header_leftpane" align="center">
<img src="IndexPageTitle.png">
</div>

<div id="header_rightpane"  align="center" style="font-size: 12; color: gray;">
<table>
<tr> <td> Username </td>
 <td> <a href="#"> Sign out </a> </td> </tr>
</table>
</div>
</div>
</div>

<div id="contentwrapper"  align="center" style="padding-top: 0%; padding-bottom: 0%;">
<div id="button_tray">
<table>
<tr>
<td> <a href="#"> <img src="FileUploadSmall.png" /> </a> </td>
<td> <a href="/GatorDrive/downloadController?filename=gg.txt"> <img src="DownloadSmall.png" /> </a> </td>
<td> <a href="#"> <img src="ShareSmall.png" /> </a> </td>
<td> <a href="/GatorDrive/deleteFile?filename=a.txt"> <img src="DeleteSmall.png" /> </a> </td>
<td> <input type="text" name="search" title="search" style="color:#888;" value="Search" onfocus="inputFocus(this)" onblur="inputBlur(this)" /> </td>
<td> <a href="#"> <img src="SearchSmall.png" /> </a> </td>
</tr>
</table>
</div>

<div id="content">
   <div id="tab-container">
      <ul>
         <li><a href="/GatorDrive/Controller">My Drive</a></li>
         <li><a href="/GatorDrive/Controller2">Shared with me</a></li>
      </ul>
   </div>
   <div id="main-container">
   
   <h1>Pagination In Servlets</h1>

<%
	//List list = (List) session.getAttribute("studentDetails");
	GetFiles gf = new GetFiles();
	
	String type = (String) session.getAttribute("type");
	String username = (String) session.getAttribute("username");
	
	List<String> list;
	if(type != null && type.contentEquals("shared")){
		list = gf.getSharedFiles(username);
	}else{
		list = gf.getCreatedFiles(username);
	}
	
	
 %>

<%
	List pageNumbers = (List) session.getAttribute("pages");
%>

<table border="1">
	<tr bgcolor="orange">
		<td><strong>CheckBox</strong></td>
		<td><strong>Name</strong></td>
	</tr>
	<%
		for (int i = 0; i < list.size(); i++) {
	%>
	<tr>
		<%
		out.println("<td><input type=\"checkbox\" name=\"files\" value="+ list.get(i) +" /></td>");
		out.println("<td>" + list.get(i)
				+ "</td>");
		%>
	</tr>
	<%
		}
	%>

</table>

   </div>
</div>
</div>

</div>

<div id="footer">

 <table align="center">
	  <tr>
		  <td align="center"> <a href="#"> About us </a> </td>
		  <td align="center"> <a href="#"> Contact us </a> </td>
		  <td align="center"> <a href="#"> Terms and Conditions </a> </td>
	  </tr>
  
	  <tr>
	  	  <td></td>
		  <td align="center">Gator Drive: A Distributed File Storage System </td>
		  <td></td>
	 </tr>
	 </table>
</div>

</div>

</body>
</html>