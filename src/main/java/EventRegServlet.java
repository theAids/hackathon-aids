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

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.sdk.resource.instance.Sms;
import java.util.HashMap;
import java.util.Map;

import java.util.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

@WebServlet(name = "EventRegServlet", urlPatterns = {"/EventRegServlet"})
public class EventRegServlet extends HttpServlet 
{

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	PrintWriter out = response.getWriter();
		try{
			String eName = request.getParameter("eventName");
	    	String eDesc = request.getParameter("eventDesc");
			String eventLocation = request.getParameter("eventLocation");
			
	    	String eDate = request.getParameter("datepicker").toString();
			String startTime = request.getParameter("startTime").toString();
	    	String endTime = request.getParameter("endTime").toString();
		

			PostgreSQLClient db = new PostgreSQLClient();

			db.addEvent(eName, eDesc, eDate, startTime, endTime, eventLocation);
			
			String message = "Event: " + eName+". "+eDesc+". Where: "+eventLocation+". When: "+eDate+" from "+startTime+" to "+endTime+".";

			

			int ehID = db.getEID(eName);

			int locid = db.getLocID(eventLocation);

			db.addEvent_loc(ehID, locid);
			out.println(eName);
			out.println(ehID);
			out.println(eventLocation);

			List<String> mob = db.getMobile(ehID);


			
        	out.println("result:");

        	for(String s: mob)
        		out.println("+63"+s);

        	out.println(message);

        	Sms msg = null;
	        TwilioConnect connect = new TwilioConnect();
			
	        String authToken = connect.getAuthToken();
	        String accountSID = connect.getAcctSID();
	        TwilioRestClient tw_client = new TwilioRestClient(accountSID, authToken);
	        
	        Map<String, String> params = new HashMap<String, String>();
	        
			String twilionum = "+12675441449";

			for(String numstring: mob){
				params.put("From", twilionum);
				params.put("Body", message);
				//params.put("To", "+63"+voter[i]);
				params.put("To", "+63"+numstring);
				
				SmsFactory msgFactory = tw_client.getAccount().getSmsFactory();
				try {
					msg = msgFactory.create(params);
				}
				catch (TwilioRestException e) {
					throw new ServletException(e);
				}
			}

			/*
        	response.setContentType("text/html");
            response.setStatus(200);
            request.getRequestDispatcher("/SMS.jsp").forward(request, response);
            */
				
			}catch(Exception e){
				out.println(e.getMessage());
			}
		
    }
}