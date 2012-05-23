package server.communication.message;

import server.communication.ClientCommunicator;

public class AssignToMeProcessor extends MessageProcessor{

	private ClientCommunicator parent;
	
	public AssignToMeProcessor(ClientCommunicator parent)
	{
		this.parent = parent;
	}
	
	@Override
	public void process(String in) {
		String [] str = in.split(":");
		parent.getParent().getBackend().assignParcel(Integer.parseInt(str[1]),parent.getClientId());
	}
}
