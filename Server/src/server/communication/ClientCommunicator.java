package server.communication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import server.backend.Backend;
import server.communication.message.MessageProcessor;
import server.communication.message.MessageProcessorFactory;
import server.filesystem.Log;

public class ClientCommunicator extends Thread {
	
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private Server parent;
	private volatile String login = null;
	private volatile String password = null;
	private volatile int id=0;
	
	public ClientCommunicator(Socket socket, Server parent, Backend backend)
	{
		this.socket = socket;
		this.parent = parent;
		try
		{
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		}
		catch(Exception e)
		{
			Log.getGlobal().error("Failed to estabilish connection with client");
		}
	}
	
	protected void process(String input)
	{
		MessageProcessor mp = MessageProcessorFactory.getMessageProcessor(input, this);
		if (mp == null)
			Log.getGlobal().error("Unrecognized input from client: " + input);
		else
			mp.process(input);
	}
	
	public Server getParent()
	{
		return parent;
	}
	
	public void setLogin(String login)
	{
		this.login = login;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public PrintWriter getOutputStream()
	{
		return out;
	}
	
	@Override
	public void run()
	{
		String input;
		try
		{
			while(( input = in.readLine() ) != null)
			{
				process(input);
				Log.getGlobal().event("Received message: " + input);
			}
				
		}
		catch(Exception e)
		{
		}
		if (login != null)
			Log.getGlobal().event("Client " + login + " disconnected");
		else
			Log.getGlobal().event("Unknown client disconnected");
		parent.removeClient(this);
	}
}
