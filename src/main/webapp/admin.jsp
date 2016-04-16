<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin</title>
    </head>
    <body>
    	
    	<h4>Admin Functions</h4>
    	<a href="/AdminFunction?action=createtable">Create Tables</a> <br/>
    	<a href="/AdminFunction?action=populate">Populate Location Table</a> <br/>
    	<a href="/AdminFunction?action=delloc">Delete Location Table</a> <br/>
    	<a href="/AdminFunction?action=deluser">Delete User Table</a> <br/>
    	<a href="/AdminFunction?action=delevent">Delete Event Table</a> <br/>
    	<a href="/AdminFunction?action=deluserloc">Delete User-Location Table</a> <br/>
    	<a href="/AdminFunction?action=deleventloc">Delete Event-Location Table</a> <br/>
        
        <% if(request.getAttribute("msg") != null) {%>
        		<div> <%=request.getAttribute("msg")%>
        <%}%>
    </body>
</html>
