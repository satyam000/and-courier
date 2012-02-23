package server.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import server.filesystem.Log;

public class MySQLConnector implements Backend{

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
		    Log.getGlobal().event("Connection with database estabilished");
		} catch (ClassNotFoundException e) {
		    Log.getGlobal().error("Failed to load MySQL driver for Java");
		} catch (SQLException e) {
		    Log.getGlobal().error("Failed to estabilish connection with database");
		}
	}
	
	//returns -1 if the user either doesn't exist in the database, or the password is incorrect
	public int userExists(String userName, String password)
	{
		Statement statement = null;
		ResultSet rs = null;
		try
		{
			statement = connection.createStatement();
			rs = statement.executeQuery("SELECT courier_id, password FROM couriers WHERE login='" + userName + "';");
			while (rs.next())
			{
				if (rs.getString(2).equals(password))
					return rs.getInt(1);
			}
		}
		catch(Exception e)
		{
			Log.getGlobal().error("Failed to retrieve user information");
			return -1;
		}
		return -1;
	}
	
	public boolean login(String userName, String password)
	{
		int id;
		if ((id = userExists(userName, password)) != -1)
		{
			try
			{
				Statement stat = connection.createStatement();
				stat.executeUpdate("INSERT INTO logins VALUES (default, " + id + ", NOW());");
			}
			catch(Exception e)
			{
				Log.getGlobal().error("Error occured while saving last login");
			}
			Log.getGlobal().event("User " + userName + " logged in");
			return true;
		}
		return false;
	}
	
	public boolean addUser(String userName, String password)
	{
		try
		{
			Statement stat = connection.createStatement();
			stat.executeUpdate("INSERT INTO couriers VALUES (default, '"+userName+"','"+password+"')");
			return true;
		}
		catch(Exception e)
		{
			Log.getGlobal().error("Failed to create new user");
		}
		return false;
	}
	
	public boolean deleteUser(String userName)
	{
		try
		{
			Statement stat = connection.createStatement();
			stat.executeUpdate("DELETE FROM couriers WHERE login ='"+userName+"';");
			return true;
		}
		catch(Exception e)
		{
			Log.getGlobal().error("Failed to delete user");
		}
		return false;
	}
	
	public static void deployDatabase(String serverAddress, String databaseName, String userName, String password)
	{
		try {
		    // Load the JDBC driver
		    String driverName = "org.gjt.mm.mysql.Driver"; // MySQL MM JDBC driver
		    Class.forName(driverName);
		    // Create a connection to the database
		    String url = "jdbc:mysql://" + serverAddress; // a JDBC url
		    Connection connection = DriverManager.getConnection(url, userName, password);
		    Log.getGlobal().error("Connection with database estabilished\n\tDeploying database");
		    
		    Statement st = connection.createStatement();
		    st.execute("CREATE SCHEMA IF NOT EXISTS `" + databaseName + "` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;");
		    st.execute("CREATE  TABLE IF NOT EXISTS `" + databaseName + "`.`Couriers` ("
		    		+ "`courier_id` INT NOT NULL AUTO_INCREMENT ,"
		    		+ "`login` VARCHAR(45) NOT NULL ,"
		    		+ "`password` VARCHAR(45) NOT NULL ,"
		    		+ "PRIMARY KEY (`courier_id`) ,"
		    		+ "UNIQUE INDEX `login_UNIQUE` (`login` ASC) );");
		    st.execute("CREATE  TABLE IF NOT EXISTS `" + databaseName + "`.`Customers` ("
		    		+ "`customer_id` INT NOT NULL AUTO_INCREMENT ,"
		    		+ "`name` VARCHAR(45) NOT NULL ,"
		    		+ "`surname` VARCHAR(45) NOT NULL ,"
		    		+ "`city` VARCHAR(45) NOT NULL ,"
		    		+ "`street` VARCHAR(45) NOT NULL ,"
		    		+ "`postal_code` VARCHAR(5) NOT NULL ,"
		    		+ "`building_num` INT NOT NULL ,"
		    		+ "`apartment_num` VARCHAR(45) NULL ,"
		    		+ "PRIMARY KEY (`customer_id`));");
		    st.execute("CREATE  TABLE IF NOT EXISTS `" + databaseName + "`.`ParcelType` ("
		    		+ "`parceltype_id` INT NOT NULL AUTO_INCREMENT ,"
		    		+ "`type` VARCHAR(45) NOT NULL ,"
		    		+ "PRIMARY KEY (`parceltype_id`) );");
		    st.execute("CREATE  TABLE IF NOT EXISTS `" + databaseName + "`.`Parcels` ("
		    		+ "`package_id` INT NOT NULL AUTO_INCREMENT ,"
		    		+ "`weight` FLOAT(5,2) NOT NULL ,"
		    		+ "`sent_on` DATE NOT NULL ,"
		    		+ "`delivered` TINYINT(1) NOT NULL DEFAULT 0 ,"
		    		+ "`recipient_customer_id` INT NOT NULL ,"
		    		+ "`sender_customer_id` INT NOT NULL ,"
		    		+ "`assigned_to` INT NULL ,"
		    		+ "`price` FLOAT(6,2) NOT NULL DEFAULT 0 ,"
		    		+ "`parceltype_id` INT NOT NULL ,"
		    		+ "PRIMARY KEY (`package_id`),"
		    		+ "INDEX `fk_Packages_Couriers1` (`assigned_to` ASC) ,CONSTRAINT `fk_Packages_Couriers1`"
		    		+ "FOREIGN KEY (`assigned_to` )"
		    		+ "REFERENCES `" + databaseName + "`.`Couriers` (`courier_id` ));");
		    st.execute("CREATE  TABLE IF NOT EXISTS `" + databaseName + "`.`Logins` ("
		    		+ "`logins_id` INT NOT NULL AUTO_INCREMENT ,"
		    		+ "`courier_id` INT NOT NULL ,"
		    		+ "`logged_on` DATETIME NOT NULL ,"
		    		+ "PRIMARY KEY (`logins_id`));");
		    st.execute("use " + databaseName);
		    st.execute("ALTER TABLE logins ADD CONSTRAINT FOREIGN KEY (courier_id) REFERENCES couriers(courier_id);");
		    st.execute("ALTER TABLE parcels ADD CONSTRAINT FOREIGN KEY (sender_customer_id) REFERENCES customers(customer_id);");
		    st.execute("ALTER TABLE parcels ADD CONSTRAINT FOREIGN KEY (recipient_customer_id) REFERENCES customers(customer_id);");
		    st.execute("ALTER TABLE parcels ADD CONSTRAINT FOREIGN KEY (parceltype_id) REFERENCES parceltype(parceltype_id);");
		    System.out.println("Database deployed");
		    Log.getGlobal().event("Database deployed");
		} catch (ClassNotFoundException e) {
		    System.err.println("Failed to deploy database to sever: "+e);
		    Log.getGlobal().error("Failed to deploy database to sever: "+e);
		} catch (SQLException e) {
			Log.getGlobal().error("Failed to deploy database to sever: "+e);
		    System.err.println("Failed to deploy database to sever: "+e);
		}
	}
}
