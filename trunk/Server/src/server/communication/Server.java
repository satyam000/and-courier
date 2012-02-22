package server.communication;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import server.filesystem.Log;

public class Server extends Thread{

	private ServerSocket servSocket;
	private int port = 666;
	LinkedList <ClientCommunicator>clients;
	private static Server instance = null;
	
	private Server()
	{
		Log.getGlobal().event("Initializing server");
		try
		{
			servSocket = new ServerSocket(port);
			Log.getGlobal().event("Server started");
		}
		catch (Exception e)
		{
			Log.getGlobal().error("Error occured while initializing server");
			return;
		}
		clients = new LinkedList<ClientCommunicator>();
	}
	
	public static Server getInstance()
	{
		if (instance == null)
			instance = new Server();
		return instance;
	}
	
	public void setPort(int port)
	{
		this.port = port;
	}
	
	protected void listen()
	{
		Log.getGlobal().event("Server started.\n\tListening for connections at port " + port);
		Socket clientSock;
		ClientCommunicator client;
		while (true)
		{
			try
			{
				clientSock = servSocket.accept();
				client = new ClientCommunicator(clientSock, this);
				addClient(client);
				client.start();
			}
			catch(Exception e)
			{
				Log.getGlobal().error("Failed to estabilish connection with new client");
			}
		}
	}
	
	@Override
	public void run()
	{
		listen();
	}
	
	public void removeClient(ClientCommunicator client)
	{
		synchronized(clients)
		{
			clients.add(client);
		}
	}
	
	protected void addClient(ClientCommunicator client)
	{
		synchronized(clients)
		{
			clients.remove(client);
		}
	}
}
