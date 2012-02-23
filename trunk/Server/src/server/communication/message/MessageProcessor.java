package server.communication.message;

public abstract class MessageProcessor {

	protected static String bd ="<>";
	
	public abstract void process(String in);
}
