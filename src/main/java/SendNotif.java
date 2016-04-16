import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;


@WebServlet(name = "SendNotif", urlPatterns = {"/SendNotif"})
public class SendNotif extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        String loc = request.getParameter("loc");

        PostgreSQLClient db = new PostgreSQLClient();

        try{

        	

        }catch(Exception e){
        	out.println(e.getMessage());
        }
    }
}