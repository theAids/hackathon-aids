<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Message Send</title>
</head>

<body>

    <h4>Message</h4>

    <% if (request.getSession().getAttribute("smsmsg") != null) { %>
        <div><%= request.getSession().getAttribute("smsmsg") %></div>
    <% } %>
    <br/>
    <a href="/TwilioServlet">Send Message!</a>
</body>

</html>