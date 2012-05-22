package server.communication.message;

import server.communication.ClientCommunicator;

public abstract class MessageProcessorFactory {

	public static MessageProcessor getMessageProcessor(String input, ClientCommunicator parent)
	{
		if (input.startsWith("login:"))
			return new LoginQueryProcessor(parent);
		if (input.startsWith("unpar:"))
			return new UnassignedParcelsProcessor(parent);
		if (input.startsWith("aspar:"))
			return new AssignedToMeParcelsProcessor(parent);
		if (input.startsWith("del:"))
			return new DeliverParcelProcessor(parent);
		if (input.startsWith("asund:"))
			return new AssignedUndeliveredParcelsProcessor(parent);
		return null;
	}
}
