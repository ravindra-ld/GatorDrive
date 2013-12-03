<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.* "%>
<%@page import="com.cloud.gatordrive.Client.GetFiles"
	import="com.cloud.gatordrive.entity.Payload"%>
<%@page import="com.cloud.gatordrive.DataCommunicator"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="Style1.css" type="text/css">
<title>Gator Drive : Home</title>
<script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.10.2.min.js"></script>
<script src="FileView.js"></script>
<script>
	function colorChange() {

	}

	function inputFocus(i) {
		if (i.value == i.defaultValue) {
			i.value = "";
			i.style.color = "#000";
		}
	}
	function inputBlur(i) {
		if (i.value == "") {
			i.value = i.defaultValue;
			i.style.color = "#888";
		}
	}

	$(document).ready(function() {
		var location = window.location;
		var found = false;
		$("#tab-container a").each(function() {
			var href = $(this).attr("href");
			if (href == location) {
				$(this).parent().addClass("selected");
				found = true;
			}
		});
		if (!found) {
			$("#tab-container li:first").addClass("selected");
		}
	});

	$(document).ready(function() {
		$("table").tablesorter({
			widthFixed : true,
			widgets : [ 'zebra' ]
		}).tablesorterPager({
			container : $("#pager")
		});
	});

	function getFileName(obj) {

		if (obj.checked) {
			document.selectedFilesForm.fileName.value = obj.value;
			document.selectedFilesForm1.fileName.value = obj.value;
			document.selectedFilesForm2.fileName.value = obj.value;
		}
	}
	
	function getFileNameSearch(obj) {

			document.selectedFilesForm3.filename.value = obj.value;

	}

	function share() {
		var username = prompt(
				"Enter UserName with whom you like to share the file", "");
		document.shareForm.shareUser.value = username;
		var fileName = document.selectedFilesForm.fileName.value;

		//	alert(username + " " + fileName);
		//TODO Send to server
		document.getElementsById('shareForm').submit();

	}

	function go() {
		selectedFilesForm.submit();
	}
	function go1() {
		selectedFilesForm1.submit();
	}
	function go2() {
		selectedFilesForm2.submit();
	}
	function go3() {
		selectedFilesForm3.submit();
	}
</script>

</head>
<body>

	<div id="maincontainer">

		<div id="topsection">
			<div class="innertube">

				<div id="header_leftpane" align="center">
					<img src="IndexPageTitle.png">
				</div>

				<div id="header_rightpane" align="center"
					style="font-size: 12; color: gray;">
					<table>
						<tr>
						<%
						String username = (String) session.getAttribute("username");
						%>
							<td><% out.println(username); %></td>
							<td><a href="SignOut"> Sign out </a></td>
						</tr>
					</table>
				</div>
			</div>
		</div>

		<div id="contentwrapper" align="center"
			style="padding-top: 0%; padding-bottom: 0%;">
			<div id="button_tray">

				<table>
					<tr>
						<td><a href="FileUpload.jsp"> <img
								src="FileUploadSmall.png" alt="File upload" /></a></td>


						<td><a onClick="go2()" href="#"> <img src="DownloadSmall.png" /></a></td>
						<td>
							<form id="shareForm" name="shareForm" action="ShareFile.jsp"
								method="post">
								<a onClick="go()" href="#"> <img src="ShareSmall.png" /></a> <input
									type="hidden" name="shareUser" value="" />
							</form>
						</td>
						<td>
							<a onClick="go1()" href="#"> <img src="DeleteSmall.png" /></a>
						</td>
						<td><input type="text" name="search" title="search"
							style="color: #888;" value="Search" onfocus="inputFocus(this)"
							onblur="inputBlur(this)" onkeyup="getFileNameSearch(this)"/></td>
						<td><a onClick="go3()" href="#"> <img src="SearchSmall.png" />
						</a></td>
					</tr>
				</table>
				<form name="selectedFilesForm" method="POST" action="ShareFile.jsp">
					<input type="hidden" name="fileName" value="" />
				</form>
				<form name="selectedFilesForm1" method="GET" action="deleteFile">
					<input type="hidden" name="fileName" value="" />
				</form>
				<form name="selectedFilesForm2" method="GET" action="downloadController">
					<input type="hidden" name="fileName" value="" />
				</form>
				<form name="selectedFilesForm3" method="GET" action="Search">
					<input type="hidden" name="filename" value="" />
				</form>
				
			</div>

			<div id="content">
				<div id="tab-container">
					<ul>
						<li><a href="/GatorDrive/Controller" onclick="colorChange()">My
								Drive</a></li>
						<li><a href="/GatorDrive/Controller2" onclick="colorChange()">Shared
								with me</a></li>
					</ul>
				</div>
				<div id="main-container">
					<%
						//String username = (String) session.getAttribute("username");
						if (username == null) {
					%>
					<jsp:forward page="index.jsp"></jsp:forward>
					<%
						}
					%>
					<%
						//List list = (List) session.getAttribute("studentDetails");
						GetFiles gf = new GetFiles();

						String type = (String) session.getAttribute("type");
						username = (String) session.getAttribute("username");

						List<String> list = null;
						if (type != null && type.contentEquals("shared")) {
							list = gf.getSharedFiles(username);
						} else if (type != null && type.contentEquals("created")) {
							list = gf.getCreatedFiles(username);
						} else if (type != null && type.contentEquals("search")) {
							/*
							HttpServletResponse httpResponse = (HttpServletResponse) response;
							MyHttpServletResponseWrapper wrapper = 
							  new MyHttpServletResponseWrapper(httpResponse);
							String content = wrapper.toString();
							 */
							String content = (String) request.getAttribute("files");
							//out.println("CONTENT = "+content);
							content = content.replace("[", "");
							content = content.replace("]", "");
							String[] files = content.split(",");
							List<String> List = new ArrayList<String>();
							for (String file : files) {
								List.add(file);
							}
							list = List;

						}
					%>

					<%
						List pageNumbers = (List) session.getAttribute("pages");
					%>

					<form name="fileSelectionForm">
						<table border="0">
							<tr bgcolor="orange">
								<td></td>
								<td><strong>File Name</strong></td>
							</tr>
							<%
								for (int i = 0; i < list.size(); i++) {
							%>
							<tr>
								<%
									out.println("<td><input type=\"checkbox\" name=\"checkbox\" onclick=\"getFileName(this)\" "
												+ " value=" + list.get(i) + " /></td>");
										out.println("<td>" + list.get(i) + "</td>");
								%>
							</tr>
							<%
								}
							%>

						</table>
					</form>
				</div>
			</div>
		</div>

	</div>

	<div id="footer">

		<table align="center">
			<tr>
				<td align="center"><a href="#"> About us </a></td>
				<td align="center"><a href="#"> Contact us </a></td>
				<td align="center"><a href="#"> Terms and Conditions </a></td>
			</tr>

			<tr>
				<td></td>
				<td align="center">Gator Drive: A Distributed File Storage
					System</td>
				<td></td>
			</tr>
		</table>
	</div>

	</div>

</body>
</html>
