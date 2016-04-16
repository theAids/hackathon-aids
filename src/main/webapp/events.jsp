<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Events</title>
    
	<!--<link rel="stylesheet" href="http://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
	<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
	<script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>-->

	</head>
	<script>
	/*$(function() {
		$( "#datepicker" ).datepicker();
	});
	*/
	function validDate(date, theInput) {
		todayDate = getTodaysDate();
		if (date > todayDate)
			theInput.value = todayDate;
	}

	function getTodaysDate(){
		date = new Date();
		day = date.getDate();
		month = date.getMonth() + 1;
		year = date.getFullYear();

		if (month < 10) month = "0" + month;
		if (day < 10) day = "0" + day;

		today = year + "-" + month + "-" + day;	
	
		return today;
	}
	
	function validateTime(){
		var currentTime = new Date()
		var hours = currentTime.getHours()
		var minutes = currentTime.getMinutes()
		if (minutes < 10){
			minutes = "0" + minutes
		}
		document.write(hours + ":" + minutes + " ")
		if(hours > 11){
			document.write("PM")
		} else {
			document.write("AM")
		}
	}
	</script>
    <body>
	<h3>Event Registration</h3>
        <form method="POST" action="EventRegServlet">
			Event Name: <input type="text" name="eventName"/></br>
			Event Description: <input type="text" name="eventDesc"/></br></br>
			Location: 
			<h5>National Capital Region</h5>
			<input type="radio" name="eventLocation" value="caloocan"/>Caloocan<br/>
			<input type="radio" name="eventLocation" value="makati"/>Makati<br/>
			<input type="radio" name="eventLocation" value="malabon"/>Malabon<br/>
			<input type="radio" name="eventLocation" value="mandaluyong"/>Mandaluyong<br/>
			<input type="radio" name="eventLocation" value="manila"/>Manila<br/>
			<input type="radio" name="eventLocation" value="marikina"/>Marikina<br/>
			<input type="radio" name="eventLocation" value="muntinlupa"/>Muntinlupa<br/>
			<input type="radio" name="eventLocation" value="navotas"/>Navotas<br/>
			<input type="radio" name="eventLocation" value="paranaque"/>Paranaque<br/>
			<input type="radio" name="eventLocation" value="pasay"/>Pasay<br/>
			<input type="radio" name="eventLocation" value="pasig"/>Pasig<br/>
			<input type="radio" name="eventLocation" value="pateros"/>Pateros<br/>
			<input type="radio" name="eventLocation" value="quezon"/>Quezon<br/>
			<input type="radio" name="eventLocation" value="san juan"/>San Juan<br/>
			<input type="radio" name="eventLocation" value="taguig"/>Taguig<br/>
			<input type="radio" name="eventLocation" value="valenzuela"/>Valenzuela<br/>
			<br/>
			Date: <input type="date" id="datepicker" name="datepicker" oninput="validDate(this.value, this)"></br>
			Start Time: <input type="time" name="startTime"></br>
			End Time: <input type="time" name="endTime"></br>
			<!--National Event <input type="checkbox" name="isNational" /></br>-->
            <input type="submit" value="Submit" />
        </form>
    </body>
</html>
