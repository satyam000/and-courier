package server.communication.message;

import server.communication.ClientCommunicator;

public class DeliverParcelProcessor extends MessageProcessor{

	private ClientCommunicator parent;
	
	public DeliverParcelProcessor(ClientCommunicator parent)
	{
		this.parent = parent;
	}
	
	@Override
	public void process(String in) {
		String [] str = in.split(":");
		parent.getParent().getBackend().deliverParcel(Integer.parseInt(str[1]));
	}

	
}
