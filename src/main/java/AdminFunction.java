import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "AdminFunction", urlPatterns = {"/AdminFunction"})
public class AdminFunction extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		String action = request.getParameter("action");

		PostgreSQLClient db = new PostgreSQLClient();

		int result = 0;

		try{

			switch(action){

				case "createtable":
					db.createTable();
					request.setAttribute("msg", "Table creation successful!");
					break;
				case "populate":
					result = db.addLocation();
					request.setAttribute("msg", "Populate location table successful! Inserted items: "+result);
					break;
				case "delloc":
					db.deleteAllLoc();
					request.setAttribute("msg", "Location table deleted!");
					break;
				case "deluser":
					db.deleteAllUser();
					request.setAttribute("msg", "User table deleted!");
					break;
				case "delevent":
					db.deleteAllEvent();
					request.setAttribute("msg", "Event table deleted!");
					break;
				case "deluserloc":
					db.deleteAllUserLoc();
					request.setAttribute("msg", "User-Location table deleted!");
					break;
				case "deleventloc":
					db.deleteAllEventLoc();
					request.setAttribute("msg", "Event-Location table deleted!");
					break;
				default: break;

			}

		}catch(Exception e){
			request.setAttribute("msg", e.getMessage());
		}

		response.setContentType("text/html");
        response.setStatus(200);
        request.getRequestDispatcher("/admin.jsp").forward(request, response);
	}
}