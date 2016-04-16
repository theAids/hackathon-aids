<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Notification</title>
</head>

<body>

	<% if (request.getSession().getAttribute("uname") != null) { 
		if(request.getAttribute("msg") != null)
	%>
        	  <h4><%= request.getAttribute("msg") %></h4>
    <% }else response.sendRedirect("/index.jsp"); %>

	<h4>Set Notification</h4>

	<% if (request.getAttribute("notifmsg") != null) { %>
        	  <h4><%= request.getAttribute("notifmsg") %></h4>
    <% } %>

	<form method="POST" action="/SetNotif">
	
		<br/>
		<h5>Selected Locations:</h5>
		<h5>National Capital Region</h5>
		<input type="checkbox" name="loc" value="caloocan"/>Caloocan<br/>
		<input type="checkbox" name="loc" value="makati"/>Makati<br/>
		<input type="checkbox" name="loc" value="malabon"/>Malabon<br/>
		<input type="checkbox" name="loc" value="mandaluyong"/>Mandaluyong<br/>
		<input type="checkbox" name="loc" value="manila"/>Manila<br/>
		<input type="checkbox" name="loc" value="marikina"/>Marikina<br/>
		<input type="checkbox" name="loc" value="muntinlupa"/>Muntinlupa<br/>
		<input type="checkbox" name="loc" value="navotas"/>Navotas<br/>
		<input type="checkbox" name="loc" value="paranaque"/>Paranaque<br/>
		<input type="checkbox" name="loc" value="pasay"/>Pasay<br/>
		<input type="checkbox" name="loc" value="pasig"/>Pasig<br/>
		<input type="checkbox" name="loc" value="pateros"/>Pateros<br/>
		<input type="checkbox" name="loc" value="quezon"/>Quezon<br/>
		<input type="checkbox" name="loc" value="san juan"/>San Juan<br/>
		<input type="checkbox" name="loc" value="taguig"/>Taguig<br/>
		<input type="checkbox" name="loc" value="valenzuela"/>Valenzuela<br/>
		<br/>
		<input type="submit" value="Submit"/>
	</form>

	<br/><br/>
	<a href="/Logout">Logout</a>
</body>

</html>