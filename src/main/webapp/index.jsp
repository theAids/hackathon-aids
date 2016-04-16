<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Log in!</title>
    </head>
    <body>
    
        <h2>Comelec </h2>

        <form method="POST" action="/Login">

        	Username: <input type="text" name="uname"/> <br/>
        	Password: <input type="password" name="password"/> <br/>
        	<input type="submit" value="Log in">
        </form>
        <br/>
        <a href="/register.jsp">Register!</a>
        <a href="/events.jsp">Publicize Event</a>
        
    </body>
</html>
