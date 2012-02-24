package server.communication.message;

import server.communication.ClientCommunicator;

public class LoginQueryProcessor extends MessageProcessor{

	private ClientCommunicator parent;
	
	public LoginQueryProcessor(ClientCommunicator parent)
	{
		this.parent = parent;
	}
	
	@Override
	public void process(String in) {
		String [] str = in.split(":");
		String [] args = str[1].split(unitSep);
		int id;
		if (( id = parent.getParent().getBackend().login(args[0], args[1])) != -1)
		{
			parent.getOutputStream().println("login:success");
			parent.setLogin(args[0]);
			parent.setPassword(args[1]);
			parent.setId(id);
		}
		else
		{
			parent.getOutputStream().println("login:fail");
		}
		
	}

	
}
