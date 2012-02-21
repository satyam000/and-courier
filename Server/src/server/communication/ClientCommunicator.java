package server.communication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientCommunicator extends Thread {
	
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private Server parent;
	
	public ClientCommunicator(Socket socket, Server parent)
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
			System.err.println("Failed to estabilish connection with client");
		}
	}
	
	protected void process(String input)
	{
		
	}
	
	@Override
	public void run()
	{
		String input;
		try
		{
			while(( input = in.readLine() ) != null)
				process(input);
		}
		catch(Exception e)
		{
			System.out.println("Failed to read from client");
		}
		parent.removeClient(this);
	}
}
