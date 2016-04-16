import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "SetNotif", urlPatterns = {"/SetNotif"})
public class SetNotif extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String uname = (String)request.getSession().getAttribute("uname");

		try{
			PostgreSQLClient db = new PostgreSQLClient();

			String[] loc = request.getParameterValues("loc");

			for(String s: loc){

				int locid = db.getLocID(s);
				db.addUserLoc(uname, locid);
			}

			request.setAttribute("notifmsg", "Notification Update Successful!");

		}catch(Exception e){
			request.setAttribute("notifmsg", e.getMessage());
		}finally{
			response.setContentType("text/html");
            response.setStatus(200);
            request.getRequestDispatcher("/notif.jsp").forward(request, response);
		}
	}
}