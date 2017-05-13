package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Logger;
import java.sql.ResultSet;

public class CompanyDatabase{
	
	private static Connection conn = null;
	private static Statement stmt = null;
	private static ResultSet result = null;
	private static boolean hasData = false;
	private static boolean bus_hasData = false;
	private static PreparedStatement prep = null;
	private Logger LOGGER = Logger.getLogger("InfoLogging");
	
	public CompanyDatabase() {
		this.initialise();
		this.addTest();
	}
	
	//get initial connection and create the table
	public Connection initialise()
	{
		Connection connInit = getConnection();
		createBusinessTable();
		createCompanyTable();
		return connInit;
	}
	
	//create connection to JDBC sqlite
	private Connection getConnection()
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:company.db");
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return conn;
	}
	
	private void createCompanyTable()
	{
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}						
			if(!hasData)
			{
				hasData = true;
				stmt = conn.createStatement();
				//checking if there is already a table created in the database
				result = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type= 'table' AND name='user'");
				if(!result.next())
				{
					stmt = conn.createStatement();
					String sql = "CREATE TABLE IF NOT EXISTS COMPANY ("
							+ "username text NOT NULL	,"
							+ "compName text NOT NULL	,"
							+ "fname text NOT NULL		,"
							+ "lname text NOT NULL		,"
							+ "password text 			,"
							+ "mobile text NOT NULL		,"
							+ "address text NOT NULL	,"
							+ "service text				,"
							+ "busStatus text NOT NULL);";
					stmt.executeUpdate(sql);
					stmt.close();
					conn.close();
				}
			}
		}
		catch (Exception e)
		{
			LOGGER.info(e.getClass().getName() + ": " + e.getMessage());
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	private void createBusinessTable()
	{
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}		
			if(!bus_hasData)
			{
				bus_hasData = true;
				stmt = conn.createStatement();
				//checking if there is already a table created in the database
				result = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type= 'table' AND name='user'");
				if(!result.next())
				{
					stmt = conn.createStatement();
					String sql = "CREATE TABLE IF NOT EXISTS BUSINESS ("
							+ "username text NOT NULL	,"
							+ "compName text			,"
							+ "fname text 				,"
							+ "lname text 				,"
							+ "password text NOT NULL	,"
							+ "mobile text 				,"
							+ "address text		     	,"
							+ "service text				,"
							+ "busHours text);";
					stmt.executeUpdate(sql);
					stmt.close();
					conn.close();
				}
			}
		}
		catch (Exception e)
		{
			LOGGER.info(e.getClass().getName() + ": " + e.getMessage());
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	// add business owner or employee info to a record
	public void addBusiness(String username, String cname, String bFname, String bLname, String pw,
			String mobile, String address, String service, String busHours)
	{		
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			prep = conn.prepareStatement("INSERT INTO COMPANY values(?,?,?,?,?,?,?,?,?);");
			prep.setString(1, username);
			prep.setString(2, cname);
			prep.setString(3, bFname);
			prep.setString(4, bLname);
			prep.setString(5, pw);
			prep.setString(6, mobile);
			prep.setString(7, address);
			prep.setString(8, service);
			prep.setString(9, busHours);
			prep.execute();
			prep.close();
			conn.close();
		}
		catch( Exception e)
		{
			LOGGER.info(e.getClass().getName() + ": " + e.getMessage());
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	// add business owner or employee info to a record
	public void addBusInfo(String username, String cname, String bFname, String bLname, String pw,
			String mobile, String address, String service, String busStatus)
	{		
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			prep = conn.prepareStatement("INSERT INTO COMPANY values(?,?,?,?,?,?,?,?,?);");
			prep.setString(1, username);
			prep.setString(2, cname);
			prep.setString(3, bFname);
			prep.setString(4, bLname);
			prep.setString(5, pw);
			prep.setString(6, mobile);
			prep.setString(7, address);
			prep.setString(8, service);
			prep.setString(9, busStatus);
			prep.execute();
			prep.close();
			conn.close();
		}
		catch( Exception e)
		{
			LOGGER.info(e.getClass().getName() + ": " + e.getMessage());
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	//check if value exists in table
	public Boolean checkValueExists(String col, String value, String tableName)
	{
		Boolean checkExists = null;
		String colName = null;
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			if(col.equals("username"))
			{
				colName = "username";
			}
			else if(col.equals("compName"))
			{
				colName = "compName";
			}
			else if(col.equals("fname"))
			{
				colName = "fname";
			}
			else if(col.equals("lname"))
			{
				colName = "lname";
			}
			else if(col.equals("password"))
			{
				colName = "password";
			}
			else if(col.equals("mobile"))
			{
				colName = "mobile";
			}
			else if(col.equals("address"))
			{
				colName = "address";
			}
			else if(col.equals("service"))
			{
				colName = "service";
			}
			else if(col.equals("busStatus"))
			{
				colName = "busStatus";
			}
			else if(col.equals("busHours"))
			{
				colName = "busHours";
			}
			prep = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE " + colName + " = ?;");
			prep.setString(1, value);
			result = prep.executeQuery();
			if(result.next())
			{
				checkExists = true;
			}
			else
			{
				checkExists = false;
			}
			prep.close();
			result.close();
			conn.close();
		}
		catch(Exception e)
		{
			LOGGER.info(e.getClass().getName() + ": " + e.getMessage());
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return checkExists;
	}
	
	public HashMap<String, HashMap<String,String>> storeEmpValues()
	{
		HashMap<String, HashMap<String,String>> empValues = new HashMap<String, HashMap<String,String>>();
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			stmt = conn.createStatement();
			result = stmt.executeQuery("SELECT * FROM COMPANY");
			while (result.next())
			{
				if(result.getString("busStatus").equals("employee")){
					HashMap<String,String> empInfo = new HashMap<String,String>();
					String id = result.getString("username");
					String fName = result.getString("fname");
					String lName = result.getString("lname");
					String service = result.getString("service");
					empInfo.put("fname", fName);
					empInfo.put("lname", lName);
					empInfo.put("service", service);
					empValues.put(id, empInfo);
				}
			}
			stmt.close();
			result.close();
			conn.close();
		}
		catch(Exception e)
		{
			LOGGER.info(e.getClass().getName() + ": " + e.getMessage());
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return empValues;
	}
	
	public HashMap<String, HashMap<String,String>> storeBusValues()
	{
		HashMap<String, HashMap<String,String>> busValues = new HashMap<String, HashMap<String,String>>();
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			stmt = conn.createStatement();
			result = stmt.executeQuery("SELECT * FROM BUSINESS");
			while (result.next())
			{
				HashMap<String,String> busInfo = new HashMap<String,String>();
				String id = result.getString("username");
				String compName = result.getString("compName");
				String fName = result.getString("fname");
				String lName = result.getString("lname");
				String password = result.getString("password");
				String mobile = result.getString("mobile");
				String address = result.getString("address");
				String service = result.getString("service");
				String busHours = result.getString("busHours");
				busInfo.put("username", id);
				busInfo.put("password", password);
				busInfo.put("fname", fName);
				busInfo.put("lname", lName);
				busInfo.put("mobile", mobile);
				busInfo.put("address", address);
				busInfo.put("service", service);
				busInfo.put("busHours", busHours);
				busValues.put(compName, busInfo);
			}
			stmt.close();
			result.close();
			conn.close();
		}
		catch(Exception e)
		{
			LOGGER.info(e.getClass().getName() + ": " + e.getMessage());
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return busValues;
	}
	
	//check if user is authenticated
	public Boolean checkLogin(String username, String password)
	{
		Boolean checkAuthen = null;
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			prep = conn.prepareStatement("SELECT username,password FROM COMPANY WHERE username = ? AND password = ?;");
			prep.setString(1, username);
			prep.setString(2, password);			
			result = prep.executeQuery();
			if(result.next())
			{
				checkAuthen = true;
			}
			else
			{
				checkAuthen = false;
			}
			prep.close();
			result.close();
			conn.close();
		}
		catch(Exception e)
		{
			LOGGER.info(e.getClass().getName() + ": " + e.getMessage());
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return checkAuthen;
	}
	
	public void addTest()
	{
		try
		{
			//making sure no duplicates are added when program restarts
			if(!checkValueExists("username","abcboss","COMPANY") || !checkValueExists("username","e1","COMPANY")
					|| !checkValueExists("username","e2","COMPANY"))
			{
				if(conn.isClosed())
				{
					getConnection();
				}
				prep = conn.prepareStatement("INSERT INTO COMPANY values(?,?,?,?,?,?,?,?,?);");
				prep.setString(1,"abcboss");
				prep.setString(2,"ABC");
				prep.setString(3,"John");
				prep.setString(4,"Bishop");
				prep.setString(5,"password");
				prep.setString(6,"0430202101");
				prep.setString(7,"1 Bossy Street, Bossville, 3000");
				prep.setString(8,null);
				prep.setString(9,"owner");
				prep.execute();
				prep.close();
				PreparedStatement prep2 = conn.prepareStatement("INSERT INTO COMPANY values(?,?,?,?,?,?,?,?,?);");
				prep2.setString(1,"e1");
				prep2.setString(2,"ABC");
				prep2.setString(3,"Bob");
				prep2.setString(4,"Lee");
				prep2.setString(5,null);
				prep2.setString(6,"0400123000");
				prep2.setString(7,"1 Hair Street, Hairy, 2000");
				prep2.setString(8,"Female Cut, Male Cut, Female Dye");
				prep2.setString(9,"employee");
				prep2.execute();
				prep2.close();
				PreparedStatement prep3 = conn.prepareStatement("INSERT INTO COMPANY values(?,?,?,?,?,?,?,?,?);");
				prep3.setString(1,"e2");
				prep3.setString(2,"ABC");
				prep3.setString(3,"Elissa");
				prep3.setString(4,"Smith");
				prep3.setString(5,null);
				prep3.setString(6,"0469899898");
				prep3.setString(7,"1 ChoppaChoppa Street, Choparoo, 3333");
				prep3.setString(8,"Female Cut");
				prep3.setString(9,"employee");
				prep3.execute();
				prep3.close();
			}
			conn.close();
			if(!checkValueExists("compName","ABC","BUSINESS"))
			{
				if(conn.isClosed())
				{
					getConnection();
				}
				prep = conn.prepareStatement("INSERT INTO BUSINESS values(?,?,?,?,?,?,?,?,?);");
				prep.setString(1,"abcboss");
				prep.setString(2,"ABC");
				prep.setString(3,"John");
				prep.setString(4,"Bishop");
				prep.setString(5,"password");
				prep.setString(6,"0430202101");
				prep.setString(7,"1 Bossy Street, Bossville, 3000");
				prep.setString(8,"Female Cut, Male Cut, Female Dye, Male Dye, Female Perm, Male Perm, Female Wash, Male Wash");
				prep.setString(9,"Monday-Friday=08:00,16:00");
				prep.execute();
				prep.close();
				/*PreparedStatement prep2 = conn.prepareStatement("INSERT INTO COMPANY values(?,?,?,?,?,?,?,?,?);");
				prep2.setString(1,"e1");
				prep2.setString(2,"ABC");
				prep2.setString(3,"Bob");
				prep2.setString(4,"Lee");
				prep2.setString(5,null);
				prep2.setString(6,"0400123000");
				prep2.setString(7,"1 Hair Street, Hairy, 2000");
				prep2.setString(8,"femaleCut, maleCut, femaleDye");
				prep2.setString(9,"employee");
				prep2.execute();
				prep2.close();*/
			}
			conn.close();
		}
		catch(Exception e)
		{
			LOGGER.info(e.getClass().getName() + ": " + e.getMessage());
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	//check how many employees are in the database
	public int checkEmployees()
	{
		int counter = 0;
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			stmt = conn.createStatement();
			String sql = "SELECT * FROM COMPANY WHERE busStatus = 'employee';";
			result = stmt.executeQuery(sql);
			while(result.next())
			{
				counter++;
			}
			stmt.close();
			result.close();
			conn.close();
		}
		catch(Exception e)
		{
			LOGGER.info(e.getClass().getName() + ": " + e.getMessage());
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return counter;
	}
}
