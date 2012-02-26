package client.event;

public interface ThreadEventProcessor {

	public void process();
	
	public void errorOccured();
}
