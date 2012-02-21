package server.main;

import server.communication.Server;

public class Main {

	public static void main(String [] args) throws Exception
	{		
		Server server = Server.getInstance();
		server.start();
	}
}
