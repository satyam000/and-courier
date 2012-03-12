package client.communication.messages;

import java.net.Socket;

import client.event.ThreadLoginEvent;

public class LoginRequest extends ClientRequest{

	private String login, password;
	
	public LoginRequest(Socket s, ThreadLoginEvent event, String login, String password)
	{
		super(s, event);
		this.login = login;
		this.password = password;
	}

	@Override
	protected boolean communicate() {
		out.println("login:"+login+unitSep+password);
		String input = null;
		try
		{
			while (true)
			{
				input = in.readLine();
				if (input.startsWith("login:"))
					break;
			}
		}
		catch(Exception e)
		{
			return false;
		}
		if (input.equals("login:success"))
			((ThreadLoginEvent)event).setSuccess(true);
		else
			((ThreadLoginEvent)event).setSuccess(false);
		return true;
	}
}
