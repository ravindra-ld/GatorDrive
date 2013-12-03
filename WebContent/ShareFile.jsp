<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Share File</title>
</head>
<body>
<form method="post" action="ShareFile">
<p> Enter UserName with whom you like to share the file </p>
<input type="text" id="sharedUsername" name="sharedUsername" />
<input type="hidden" id="sharedFilename" name="sharedFilename" value=<%= request.getParameter("fileName") %> />
<input type="submit" value="Share" />
</form>
<a href = "Home2.jsp">Back</a>
</body>
</html>