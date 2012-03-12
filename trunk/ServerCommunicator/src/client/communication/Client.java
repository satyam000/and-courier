package client.communication;

import java.net.Socket;

import client.communication.messages.AssignedToMeParcelsRequest;
import client.communication.messages.ClientRequest;
import client.communication.messages.DeliverParcelRequest;
import client.communication.messages.LoginRequest;
import client.communication.messages.UnassignedParcelsRequest;
import client.event.ThreadEventProcessor;
import client.event.ThreadListResponseEvent;
import client.event.ThreadLoginEvent;

public class Client {

	private static int port = 666;
	private Socket socket;
	private volatile ClientRequest ongoingMessage = null;
	
	private static Client instance = null;
	
	private static String hostAddress = "localhost";
	
	public static void setHostAddress(String address)
	{
		hostAddress = address;
	}
	
	private Client() throws Exception
	{
		socket = new Socket(hostAddress,port);
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
	
	public void requestAssignedToMeParcels(ThreadListResponseEvent eproc)
	{
			if (ongoingMessage != null && ongoingMessage.isAlive())
				ongoingMessage.stop();
			ongoingMessage = new AssignedToMeParcelsRequest(socket, eproc);
			ongoingMessage.start();
	}
	
	public void deliverParcel(int parcel_id, ThreadEventProcessor eproc)
	{
		if (ongoingMessage != null && ongoingMessage.isAlive())
			ongoingMessage.stop();
		ongoingMessage = new DeliverParcelRequest(socket, eproc, parcel_id);
		ongoingMessage.start();
	}
}
