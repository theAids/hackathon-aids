import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Date;
import java.sql.Time;

public class PostgreSQLClient {


	public PostgreSQLClient(){

	}

	public List<String> getAllLocations() throws Exception {
		String sql = "SELECT province FROM location ORDER BY locid DESC";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		
		try {
			connection = getConnection();
			statement = connection.prepareStatement(sql);
			results = statement.executeQuery();
			List<String> loc = new ArrayList<String>();
			
			while (results.next()) {
				loc.add(results.getString("province"));
			}
			
			return loc;

		} finally {
			if (results != null) {
				results.close();
			}
			
			if (statement != null) {
				statement.close();
			}
			
			if (connection != null) {
				connection.close();
			}
		}
	}

	public int addLocation() throws Exception{

		String sql = "INSERT INTO location (region, province) VALUES ('ncr', 'caloocan'),"+
					 "('ncr', 'makati'),"+
					 "('ncr', 'malabon'),"+
					 "('ncr', 'mandaluyong'),"+
					 "('ncr', 'manila'),"+
					 "('ncr', 'marikina'),"+
					 "('ncr', 'muntinlupa'),"+
					 "('ncr', 'navotas'),"+
					 "('ncr', 'paranaque'),"+
					 "('ncr', 'pasay'),"+
					 "('ncr', 'pasig'),"+
					 "('ncr', 'pateros'),"+
					 "('ncr', 'quezon'),"+
					 "('ncr', 'san juan'),"+
					 "('ncr', 'taguig'),"+
					 "('ncr', 'valenzuela');";

		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql);
			

			int rows = statement.executeUpdate();
			connection.commit();
			
			return rows;
		} catch (SQLException e) {
			SQLException next = e.getNextException();
			
			if (next != null) {
				throw next;
			}
			
			throw e;
		} finally {
			if (statement != null) {
				statement.close();
			}
			
			if (connection != null) {
				connection.close();
			}
		}

	}

	public void createTable() throws Exception {
        String sql = "CREATE TABLE IF NOT EXISTS users (uid serial, lname text, fname text, province text," +
                     		"city text, brgy text, street text, mobile text, uname text, password text, PRIMARY KEY(uid, uname));" +
                     "CREATE TABLE IF NOT EXISTS location (locid serial primary key, region text, province text);" +
                     "CREATE TABLE IF NOT EXISTS user_loc (uname text, locid integer, PRIMARY KEY(uname, locid));" +
					 "CREATE TABLE IF NOT EXISTS event "
                + "(eID serial primary key NOT NULL, "
                + "eName varchar(45) NOT NULL UNIQUE,"
                + "eDescription varchar(45) NOT NULL,"
                + "eDate varchar(45) NOT NULL, "
                + "startTime varchar(45) NOT NULL, "
                + "endTime varchar(45) NOT NULL, "
				+ "eventLocation varchar(45) NOT NULL"
                + ");"+
				"CREATE TABLE IF NOT EXISTS event_loc "
                + "(eID integer NOT NULL, "
                + "locID integer NOT NULL,"
                + "PRIMARY KEY(eID, locID));";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } finally {         
            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
    }


	private static Connection getConnection() throws Exception {

        Map<String, String> env = System.getenv();

        if (env.containsKey("VCAP_SERVICES")) {
            // we are running on cloud foundry, let's grab the service details from vcap_services
            JSONParser parser = new JSONParser();
            JSONObject vcap = (JSONObject) parser.parse(env.get("VCAP_SERVICES"));
            JSONObject service = null;

            // We don't know exactly what the service is called, but it will contain "postgresql"
            for (Object key : vcap.keySet()) {
                String keyStr = (String) key;
                if (keyStr.toLowerCase().contains("postgresql")) {
                    service = (JSONObject) ((JSONArray) vcap.get(keyStr)).get(0);
                    break;
                }
            }

            if (service != null) {
                JSONObject creds = (JSONObject) service.get("credentials");
                String name = (String) creds.get("name");
                String host = (String) creds.get("host");
                Long port = (Long) creds.get("port");
                String user = (String) creds.get("user");
                String password = (String) creds.get("password");

                String url = "jdbc:postgresql://" + host + ":" + port + "/" + name;

                return DriverManager.getConnection(url, user, password);
            }
        }

        throw new Exception("No PostgreSQL service URL found. Make sure you have bound the correct services to your app.");
	}
	
	public void createEventTable() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        String createEventTB = "CREATE TABLE IF NOT EXISTS event "
                + "(eID serial primary key NOT NULL, "
                + "eName varchar(45) NOT NULL UNIQUE,"
                + "eDescription varchar(45) NOT NULL,"
                + "eDate varchar(45) NOT NULL, "
                + "startTime varchar(45) NOT NULL, "
                + "endTime varchar(45) NOT NULL, "
				+ "eventLocation varchar(45) NOT NULL"
                + ");";
				
		/*createquery = "CREATE TABLE IF NOT EXISTS location "
                + "(locID serial NOT NULL PRIMARY KEY, "
                + "region varchar(45) NOT NULL,"
                + "Province varchar(45) NOT NULL,"
                + ");";*/
				
		String createEventLocTB = "CREATE TABLE IF NOT EXISTS event_loc "
                + "(eID integer NOT NULL, "
                + "locID integer NOT NULL,"
                + "PRIMARY KEY(eID, locID));";
	

        try {
            connection = getConnection();
            statement = connection.prepareStatement(createEventTB);
            statement.executeUpdate();
			statement = connection.prepareStatement(createEventLocTB);
            statement.executeUpdate();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
	
	public void addEvent(String eName, String eDescription, String eDate, String startTime, String endTime, String eventLocation) throws Exception{
		
		
		String sql = "INSERT INTO event (eName, eDescription, eDate, startTime, endTime, eventLocation) VALUES (?, ?, ?, ?, ?, ?);";

		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql);
			statement.setString(1,eName);
			statement.setString(2,eDescription);
			statement.setString(3,eDate);
			statement.setString(4,startTime);
			statement.setString(5,endTime);
			statement.setString(6,eventLocation);
			statement.executeUpdate();
			connection.commit();
			
		} catch (SQLException e) {
			SQLException next = e.getNextException();
			
			if (next != null) {
				throw next;
			}
			
			throw e;
		} finally {
			if (statement != null) {
				statement.close();
			}
			
			if (connection != null) {
				connection.close();
			}
		}

	}
	
	public int getEID(String ename) throws Exception {

		String sql = "SELECT eID FROM event WHERE ename = ?";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		
		try {

			connection = getConnection();
			statement = connection.prepareStatement(sql);
			statement.setString(1, ename);
			results = statement.executeQuery();
			//List<Integer> eventID = new ArrayList<Integer>();
			int eventID = 0;
			while (results.next()) {
				//eventID.add(results.getInt("eID"));
				eventID = results.getInt("eID");
			}
			
			return eventID;

		} finally {
			if (results != null) {
				results.close();
			}
			
			if (statement != null) {
				statement.close();
			}
			
			if (connection != null) {
				connection.close();
			}
		}
	}
	
	public void addEvent_loc(int eID, int locID) throws Exception{

		//getLocID(String province);
	
		String sql = "INSERT INTO event_loc (eid, locid) VALUES (?, ?)";
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql);
			statement.setInt(1,eID);
			statement.setInt(2,locID);
			statement.executeUpdate();
		
			connection.commit();
			
		} catch (SQLException e) {
			SQLException next = e.getNextException();
			
			if (next != null) {
				throw next;
			}
			
			throw e;
		} finally {
			if (statement != null) {
				statement.close();
			}
			
			if (connection != null) {
				connection.close();
			}
		}

	}
	
	public List<EventInfo> getAllEvents() throws Exception {
		String sql = "SELECT * FROM event";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		
		try {
			connection = getConnection();
			statement = connection.prepareStatement(sql);
			results = statement.executeQuery();
			List<EventInfo> events = new ArrayList<>();
			
			
			while (results.next()) {
				EventInfo ei = new EventInfo();
				events.add(ei);
			}
			
			return events;

		} finally {
			if (results != null) {
				results.close();
			}
			
			if (statement != null) {
				statement.close();
			}
			
			if (connection != null) {
				connection.close();
			}
		}
	}
	
	public String getLocationName(int loc_id) throws Exception {
		String sql = "SELECT province FROM location where locid = ?";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		
		try {
			connection = getConnection();
			statement = connection.prepareStatement(sql);
			statement.setInt(1,loc_id);
			results = statement.executeQuery();
			String locName="";
			
			while(results.next()){
				locName = results.getString("province");
				//return results.getString("province");

			}
			
			return locName;
		} finally {
			if (results != null) {
				results.close();
			}
			
			if (statement != null) {
				statement.close();
			}
			
			if (connection != null) {
				connection.close();
			}
		}
	}

	public int addUser(HashMap<String, String> hm) throws Exception{

		String sql = "INSERT INTO users (lname, fname, province, city, brgy, street, mobile, uname, password) VALUES (?,?,?,?,?,?,?,?,?)";

		Connection connection = null;
		PreparedStatement statement = null;

		try {

			connection = getConnection();
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql);
			
			statement.setString(1, hm.get("lname"));
			statement.setString(2, hm.get("fname"));
			statement.setString(3, hm.get("province"));
			statement.setString(4, hm.get("city"));
			statement.setString(5, hm.get("brgy"));
			statement.setString(6, hm.get("street"));
			statement.setString(7, hm.get("mobile"));
			statement.setString(8, hm.get("uname"));
			statement.setString(9, hm.get("password"));


			int rows = statement.executeUpdate();
			connection.commit();
			
			return rows;
		} catch (SQLException e) {
			SQLException next = e.getNextException();
			
			if (next != null) {
				throw next;
			}
			
			throw e;
		} finally {
			if (statement != null) {
				statement.close();
			}
			
			if (connection != null) {
				connection.close();
			}
		}

	}


	public int deleteAllLoc() throws Exception {
		String sql = "DROP TABLE location";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = getConnection();
			statement = connection.prepareStatement(sql);

			return statement.executeUpdate();
		} finally {
			if (statement != null) {
				statement.close();
			}
			
			if (connection != null) {
				connection.close();
			}	
		}
	}

	public int deleteAllUser() throws Exception {
		String sql = "DROP TABLE users";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = getConnection();
			statement = connection.prepareStatement(sql);

			return statement.executeUpdate();
		} finally {
			if (statement != null) {
				statement.close();
			}
			
			if (connection != null) {
				connection.close();
			}	
		}
	}

	public int deleteAllUserLoc() throws Exception {
		String sql = "DROP TABLE user_loc";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = getConnection();
			statement = connection.prepareStatement(sql);

			return statement.executeUpdate();
		} finally {
			if (statement != null) {
				statement.close();
			}
			
			if (connection != null) {
				connection.close();
			}	
		}
	}

	public int deleteAllEvent() throws Exception {
		String sql = "DROP TABLE event";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = getConnection();
			statement = connection.prepareStatement(sql);

			return statement.executeUpdate();
		} finally {
			if (statement != null) {
				statement.close();
			}
			
			if (connection != null) {
				connection.close();
			}	
		}
	}

	public int deleteAllEventLoc() throws Exception {
		String sql = "DROP TABLE event_loc";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = getConnection();
			statement = connection.prepareStatement(sql);

			return statement.executeUpdate();
		} finally {
			if (statement != null) {
				statement.close();
			}
			
			if (connection != null) {
				connection.close();
			}	
		}
	}

	public int getLocID(String province) throws Exception {

		String sql = "SELECT locid FROM location WHERE province = ?";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		
		try {

			connection = getConnection();
			statement = connection.prepareStatement(sql);
			statement.setString(1, province);
			results = statement.executeQuery();
			List<Integer> loc = new ArrayList<Integer>();
			
			while (results.next()) {
				loc.add(results.getInt("locid"));
			}
			
			return loc.get(0);

		} finally {
			if (results != null) {
				results.close();
			}
			
			if (statement != null) {
				statement.close();
			}
			
			if (connection != null) {
				connection.close();
			}
		}
	}


	public String getPassword(String uname) throws Exception{

		String sql = "SELECT password FROM users WHERE uname = ?";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		String pass = "";
		
		try {

			connection = getConnection();
			statement = connection.prepareStatement(sql);
			statement.setString(1, uname);
			results = statement.executeQuery();
			
			while (results.next()) {
				pass = results.getString("password");
			}
			
			return pass;

		} finally {
			if (results != null) {
				results.close();
			}
			
			if (statement != null) {
				statement.close();
			}
			
			if (connection != null) {
				connection.close();
			}
		}

	}

	public int addUserLoc(String uname, int locid) throws Exception{
		
		
		String sql = "INSERT INTO user_loc (uname, locid) VALUES (?, ?);";

		Connection connection = null;
		PreparedStatement statement = null;
		int result = 0;

		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql);
			statement.setString(1, uname);
			statement.setInt(2, locid);
			result = statement.executeUpdate();
			connection.commit();

			return result;
			
		} catch (SQLException e) {
			SQLException next = e.getNextException();
			
			if (next != null) {
				throw next;
			}
			
			throw e;
		} finally {
			if (statement != null) {
				statement.close();
			}
			
			if (connection != null) {
				connection.close();
			}
		}

	}
/*
	public List<String> getMobile(String location) throws Exception {

		String sql = "SELECT mobile FROM users, user_loc, location  WHERE location.province = ? AND location.locid = user_loc.locid AND users.uname = user_loc.uname";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		
		try {
			connection = getConnection();
			statement = connection.prepareStatement(sql);
			statement.setString(1, location);
			results = statement.executeQuery();
			List<String> loc = new ArrayList<String>();
			
			while (results.next()) {
				loc.add(results.getString("mobile"));
			}
			
			return loc;

		} finally {
			if (results != null) {
				results.close();
			}
			
			if (statement != null) {
				statement.close();
			}
			
			if (connection != null) {
				connection.close();
			}
		}
	}*/

	public List<String> getMobile(int eid) throws Exception {

		String sql = "SELECT mobile FROM users, user_loc, location, event_loc  WHERE event_loc.eID = ? AND event_loc.locID = location.locid AND location.locid = user_loc.locid AND users.uname = user_loc.uname";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		
		try {
			connection = getConnection();
			statement = connection.prepareStatement(sql);
			statement.setInt(1, eid);
			results = statement.executeQuery();
			List<String> loc = new ArrayList<String>();
			
			while (results.next()) {
				loc.add(results.getString("mobile"));
			}
			
			return loc;

		} finally {
			if (results != null) {
				results.close();
			}
			
			if (statement != null) {
				statement.close();
			}
			
			if (connection != null) {
				connection.close();
			}
		}
	}

}