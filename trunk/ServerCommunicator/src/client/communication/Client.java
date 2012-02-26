package client.communication;

import java.net.Socket;

import client.communication.messages.ClientRequest;
import client.communication.messages.LoginRequest;
import client.communication.messages.UnassignedParcelsRequest;
import client.event.ThreadListResponseEvent;
import client.event.ThreadLoginEvent;

public class Client {

	private static int port = 666;
	private Socket socket;
	private volatile ClientRequest ongoingMessage = null;
	
	private static Client instance = null;
	
	private Client() throws Exception
	{
		socket = new Socket("localhost",port);
		socket.setKeepAlive(true);
		socket.setSoTimeout(5000);
	}
	
	public synchronized static Client getInstance()
	{
		if (instance == null || instance.socket.isClosed())
		{
			try
			{
				instance = new Client();
			}
			catch(Exception e){return null;}
		}
		return instance;
	}
	
	public void logIn(String login, String password, ThreadLoginEvent eproc)
	{
			if (ongoingMessage != null && ongoingMessage.isAlive())
				ongoingMessage.stop();
			ongoingMessage = new LoginRequest(socket, eproc, login, password);
			ongoingMessage.start();
	}
	
	public void requestUnassignedParcels(ThreadListResponseEvent eproc)
	{
			if (ongoingMessage != null && ongoingMessage.isAlive())
				ongoingMessage.stop();
			ongoingMessage = new UnassignedParcelsRequest(socket, eproc);
			ongoingMessage.start();
	}
}
