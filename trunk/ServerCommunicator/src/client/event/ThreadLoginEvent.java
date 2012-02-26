package client.event;

public abstract class ThreadLoginEvent implements ThreadEventProcessor {

	protected boolean success = false;
	
	public void setSuccess(boolean success)
	{
		this.success = success;
	}
	
	public boolean getSuccess()
	{
		return success;
	}
	
	/*@Override
	public void process()
	{
		if (this.success)
			System.out.println("Successfully logged in");
		else
			System.out.println("Failed to log in");
	}*/

}
