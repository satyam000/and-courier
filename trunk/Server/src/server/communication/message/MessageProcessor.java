package server.communication.message;

public abstract class MessageProcessor {

	private static char crs = 30;
	private static char cus = 31;
	
	protected static String unitSep = "" + cus;
	protected static String recordSep = "" + crs;
	
	public abstract void process(String in);
}
