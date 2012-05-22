package server.main;

import java.sql.ResultSet;

import server.backend.MySQLConnector;

public class Test {

	public static void main(String [] args) throws Exception
	{
		//MySQLConnector.deployDatabase("localhost", "miodek", "server", "password");
		MySQLConnector c = new MySQLConnector("localhost", "schoolproject", "server", "password");
		
		System.out.println(c.login("misio", "password"));
	}
}
