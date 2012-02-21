package server.communication;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {

	private ServerSocket servSocket;
	private int port = 666;
	LinkedList <ClientCommunicator>clients;
	private static Server instance = null;
	
	private Server()
	{
		System.out.println("Initializing server");
		try
		{
			servSocket = new ServerSocket(port);
			System.out.println("Server started");
		}
		catch (Exception e)
		{
			System.err.println("Error occured while initializing server");
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
	
	public void start()
	{
		System.out.println("Server started.\n\tListening for connections at port " + port);
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
				System.err.println("Failed to estabilish connection with new client");
			}
		}
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
