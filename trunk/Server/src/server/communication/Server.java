package server.communication;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import server.backend.Backend;
import server.filesystem.Log;

public class Server extends Thread{

	private ServerSocket servSocket;
	private int port = 666;
	LinkedList <ClientCommunicator>clients;
	private static Server instance = null;
	private Backend backend = null;
	
	private Server()
	{
		Log.getGlobal().event("Initializing server...");
		System.out.println("Initializing server...");
		try
		{
			servSocket = new ServerSocket(port);
			Log.getGlobal().event("Server started");
		}
		catch (Exception e)
		{
			Log.getGlobal().error("Error occured while initializing server");
			System.err.println("Error occured while initializing server");
			return;
		}
		clients = new LinkedList<ClientCommunicator>();
		Log.getGlobal().event("Server initialized");
		System.out.println("Server initialized");
	}
	
	public void setBackend(Backend backend)
	{
		this.backend = backend;
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
		System.out.println("Server started.\n\tListening for connections at port " + port);
		Socket clientSock;
		ClientCommunicator client;
		while (true)
		{
			try
			{
				clientSock = servSocket.accept();
				client = new ClientCommunicator(clientSock, this, backend);
				addClient(client);
				client.start();
				Log.getGlobal().event("New client connected");
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
	
	public void stopServer()
	{
		this.stop();
		try
		{
			this.join(200);
		}
		catch(Exception e )
		{
			
		}
		Log.getGlobal().event("Server stopped");
	}
	
	public int countClients()
	{
		int c;
		synchronized(clients)
		{
			c = clients.size();
		}
		return c;
	}
	
	public Backend getBackend()
	{
		return backend;
	}
}
