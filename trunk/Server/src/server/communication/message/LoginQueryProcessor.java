package server.communication.message;

import server.communication.ClientCommunicator;

public class LoginQueryProcessor extends MessageProcessor{

	private ClientCommunicator parent;
	
	public LoginQueryProcessor(ClientCommunicator backend)
	{
		this.parent = backend;
	}
	
	@Override
	public void process(String in) {
		String [] str = in.split(":");
		String [] args = str[1].split(bd);
		if (parent.getParent().getBackend().login(args[0], args[1]))
		{
			parent.getOutputStream().println("login:success");
			parent.setLogin(args[0]);
			parent.setPassword(args[1]);
		}
		else
		{
			parent.getOutputStream().println("login:fail");
		}
		
	}

	
}
