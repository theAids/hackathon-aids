import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;


@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PostgreSQLClient db = new PostgreSQLClient();

        String uname = request.getParameter("uname");
        String password = request.getParameter("password");
        boolean verify = false;


        try{
            String pass = db.getPassword(uname);

            if(pass.equals(password))
                verify = true;

            request.setAttribute("msg", "Hello "+uname+"!");
            request.getSession().setAttribute("uname", uname);
        }catch (Exception e){
            request.setAttribute("msg", e.getMessage());
        }finally{

            response.setContentType("text/html");
            response.setStatus(200);

            if(verify)
                request.getRequestDispatcher("/notif.jsp").forward(request, response);
            else
                request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
}