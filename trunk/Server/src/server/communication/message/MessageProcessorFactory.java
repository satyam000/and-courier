package server.communication.message;

import server.communication.ClientCommunicator;

public abstract class MessageProcessorFactory {

	public static MessageProcessor getMessageProcessor(String input, ClientCommunicator parent)
	{
		if (input.startsWith("login:"))
			return new LoginQueryProcessor(parent);
		return null;
	}
}
