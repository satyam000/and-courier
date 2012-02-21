package server.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnector {

	private Connection connection = null;
	
	public MySQLConnector(String serverAddress, String databaseName, String userName, String password)
	{
		try {
		    // Load the JDBC driver
		    String driverName = "org.gjt.mm.mysql.Driver"; // MySQL MM JDBC driver
		    Class.forName(driverName);

		    // Create a connection to the database
		    String url = "jdbc:mysql://" + serverAddress +  "/" + databaseName; // a JDBC url
		    connection = DriverManager.getConnection(url, userName, password);
		    System.out.println("Connection with database estabilished");
		} catch (ClassNotFoundException e) {
		    System.err.println("Failed to load MySQL driver for Java");
		} catch (SQLException e) {
		    System.err.println("Failed to estabilish connection with database");
		}
	}
	
	public boolean userExists(String userName, String password)
	{
		Statement statement = null;
		ResultSet rs = null;
		try
		{
			statement = connection.createStatement();
			rs = statement.executeQuery("SELECT password FROM couriers WHERE login='" + userName + "';");
			while (rs.next())
			{
				if (rs.getString(1).equals(password))
					return true;
			}
		}
		catch(Exception e)
		{
			System.err.println("Failed to retrieve user information");
			return false;
		}
		return false;
	}
}
