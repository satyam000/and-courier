package client.communication.messages;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import client.event.ThreadEventProcessor;

public abstract class ClientRequest extends Thread{
	
	protected Socket socket;
	protected ThreadEventProcessor event;
	protected BufferedReader in;
	protected PrintWriter out;
	
	private static final char cus = 31;
	private static final char crs = 30;
	protected static final String unitSep = "" + cus;
	protected static final String recSep = "" + crs;
	
	public ClientRequest(Socket s, ThreadEventProcessor event)
	{
		this.socket = s;
		this.event = event;
		try
		{
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		}
		catch(Exception e)
		{
			System.err.println("Error occured during connection initialization");
		}
	}
	
	protected abstract boolean communicate();
	
	@Override
	public void run()
	{
		if (communicate())
			event.process();
		else
			event.errorOccured();
	}
}
