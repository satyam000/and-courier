package client.communication;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import client.communication.messages.AssignedToMeParcelsRequest;
import client.communication.messages.AssignedUndeliveredParcelsRequest;
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
	
	private static volatile Client instance = null;
	
	private static String hostAddress = "localhost";
	
	public static void setHostAddress(String address)
	{
		if (instance != null)
		{
			try
			{
				instance.socket.close();
			}
			catch(Exception e){}
			instance = null;
		}
		hostAddress = address;
	}
	
	public static String getHostAddress()
	{
		return hostAddress;
	}
	
	public static boolean testAddress(String address)
	{
		try
		{
			Socket socket = new Socket();
			SocketAddress sockaddr = new InetSocketAddress(address, port);
			socket.connect(sockaddr, 4000);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	
	private Client() throws Exception
	{
		socket = new Socket();
		SocketAddress sockaddr = new InetSocketAddress(hostAddress, port);
		socket.connect(sockaddr, 4000);
		socket.setSoTimeout(4000);
		socket.setKeepAlive(true);
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
	
	public synchronized static Client resetInstance()
	{
		try
		{
			instance = new Client();
		}
		catch(Exception e)
		{
			instance = null;
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
	
	public void requestUnassignedParcels(ThreadListResponseEvent eproc, int page)
	{
			if (ongoingMessage != null && ongoingMessage.isAlive())
				ongoingMessage.stop();
			ongoingMessage = new UnassignedParcelsRequest(socket, eproc,page);
			ongoingMessage.start();
	}
	
	public void requestAssignedToMeParcels(ThreadListResponseEvent eproc)
	{
			if (ongoingMessage != null && ongoingMessage.isAlive())
				ongoingMessage.stop();
			ongoingMessage = new AssignedToMeParcelsRequest(socket, eproc);
			ongoingMessage.start();
	}
	
	public void requestAssignedUndeliveredParcels(ThreadListResponseEvent eproc)
	{
			if (ongoingMessage != null && ongoingMessage.isAlive())
				ongoingMessage.stop();
			ongoingMessage = new AssignedUndeliveredParcelsRequest(socket, eproc);
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
