package server.communication.message;

import server.communication.ClientCommunicator;

public abstract class MessageProcessorFactory {

	public static MessageProcessor getMessageProcessor(String input, ClientCommunicator parent)
	{
		if (input.startsWith("login:"))
			return new LoginQueryProcessor(parent);
		if (input.startsWith("unpar:"))
			return new UnassignedParcelsProcessor(parent);
		return null;
	}
}
