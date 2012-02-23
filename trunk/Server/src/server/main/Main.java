package server.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import server.backend.MySQLConnector;
import server.communication.Server;

public class Main {
	
	private static Server server;
	private static MySQLConnector dbcon;
	
	private static void help(String com)
	{
		if (com == null)
		{
			System.out.println("help - lists all recognised commands");
			System.out.println("exit - exits from application");
			System.out.println("start - runs the serfer");
			System.out.println("stop - stops the server");
			System.out.println("addu - adds a new courier to database");
			System.out.println("rmu - removes courier from database");
		}
		else
		{
			if (com.equals("help"))
				System.out.println("help - lists all recognised commands");
			else if(com.equals("exit"))
				System.out.println("exit - exits from application");
			else if(com.equals("start"))
				System.out.println("start - runs the serfer");
			else if (com.equals("stop"))
				System.out.println("stop - stops the server");
			else if(com.equals("addu"))
			{
				System.out.println("addu - adds a new courier to database. Correct syntax is as follows:");
				System.out.println("\taddu name, surname, login, password");
			}
			else if(com.equals("rmu"))
			{
				System.out.println("rmu - removes courier from database. Correct syntax is as follows:");
				System.out.println("\trmu login");
			}
			else
				System.out.println("Incorrect syntax for help");
		}
	}

	private static void addUser(String sargs)
	{
		String [] args = sargs.split(" ");
		if (args.length > 3)
		{
			if (dbcon.addUser(args[0], args[1]))
				System.out.println("User successfully added");
			else
				System.out.println("An error occured while adding a user");
		}
		else
			System.out.println("Incorrect addu syntax");
	}
	
	private static boolean process(String str)
	{
		if (str == null)
			return false;
		String com = normalize(str);
		if (com.startsWith("exit"))
		{
			if (com.length() > 4)
				System.out.print("Incorrect exit syntax");
			else
			{
				server.stopServer();
				return false;
			}
		}
		else if(com.startsWith("stop"))
		{
			if (com.length() > 4)
				System.out.println("Incorrect stop syntax");
			else
			{
				if (server.isAlive())
				{
					server.stopServer();
					System.out.println("Server stopped");
				}
				else
					System.out.println("Server wasn't even running :P");
			}
		}
		else if (com.startsWith("help"))
		{
			if (com.length() > 5)
				help(com.substring(5));
			else
				help(null);
		}
		else if (com.startsWith("start"))
		{
			if (com.length() > 5)
				System.out.println("Incorrect start syntax");
			else
			{
				if (server != null && server.isAlive())
					System.out.println("Server was already running");
				else
				{
					server = Server.getInstance();
					server.setBackend(dbcon);
					server.start();
				}
			}
		}
		else if (com.startsWith("addu "))
		{
			if (com.length() < 5)
				System.out.println("Incorrect addu syntax");
			else
				addUser(com.substring(5));
		}
		else if(com.startsWith("cons"))
		{
			if (com.length() > 4)
				System.out.println("Incorrect cons syntax");
			else
			{
				System.out.println("Clients currently connected to server: " + Server.getInstance().countClients());
			}
		}
		else
		{
			String [] t = com.split(" ");
			System.out.println(t[0] + " is not recognised command");
		}
		return true;
	}
	
	private static String normalize(String str)
	{
		str = str.trim();
		String fin = "";
		for (int i = 0; i < str.length(); i++)
		{
			if (!(str.charAt(i) == ' ' && (str.length() > i+1 && str.charAt(i+1) == ' ')))
			{
				fin += str.charAt(i);
			}
		}
		return fin;
	}
	
	private static void commandLine()
	{
		dbcon = new MySQLConnector("localhost", "schoolproject", "server", "password");
		//initMySQLData();
		System.out.println("\nFor instructions type help\nFor instructions about specific command type help <command>\n");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String com = null;
		do
		{
			try
			{
				com = in.readLine();
			}
			catch(IOException e)
			{
				System.err.println("Error occured while reading command from standard input");
			}
		}while(process(com));
	}
	
	private static void initMySQLData()
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try
		{
			System.out.println("Does the database already exist? (y/n)");
			String yn;
			do
			{
				yn = in.readLine();
				if (!(yn.equals("y") || yn.equals("n")))
					System.out.print("Incorrect value. Enter 'y' or 'n'");
			}
			while(!(yn.equals("y") || yn.equals("n")));
				
			System.out.println("Enter MySQL server address");
			String address = in.readLine();
			System.out.println("Enter database name");
			String dbName = in.readLine();
			System.out.println("Enter user name for the database");
			String uName = in.readLine();
			System.out.println("Enter user password for the database");
			String pass = in.readLine();
			dbcon = new MySQLConnector(address, dbName, uName, pass);
		}
		catch(IOException e)
		{
			System.err.println("Failed to read from standard input");
		}
	}
	
	public static void main(String [] args)
	{
		System.out.println("Welcome to AndCourier 1.0");
		server = Server.getInstance();
		commandLine();
	}
}
