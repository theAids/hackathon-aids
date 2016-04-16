import java.io.IOException;
import java.io.PrintWriter;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;


@WebServlet(name = "UserRegServlet", urlPatterns = {"/UserRegServlet"})
public class UserRegServlet extends HttpServlet 
{

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	PrintWriter out = response.getWriter();

        PostgreSQLClient db = new PostgreSQLClient();

    	HashMap<String, String> hm = new HashMap<String, String>();

        hm.put("lname", request.getParameter("lname"));
        hm.put("fname", request.getParameter("fname"));
        hm.put("province", request.getParameter("province"));
        hm.put("city", request.getParameter("city"));
        hm.put("brgy", request.getParameter("brgy"));
        hm.put("street", request.getParameter("street"));
        hm.put("mobile", request.getParameter("mobile"));
        hm.put("uname", request.getParameter("uname"));
        hm.put("password", request.getParameter("password"));

        int result = 0;

        try{
            result = db.addUser(hm);
            request.setAttribute("msg", "Registration Successful!");
            request.getSession().setAttribute("uname", request.getParameter("uname"));
        }catch (Exception e){
            out.println(e.getMessage());
            request.setAttribute("msg", e.getMessage());
        }finally{

            response.setContentType("text/html");
            response.setStatus(200);

            if(result > 0)
                request.getRequestDispatcher("/notif.jsp").forward(request, response);
            else
                request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
}