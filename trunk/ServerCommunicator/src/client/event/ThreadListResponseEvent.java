package client.event;

import java.util.LinkedList;

public abstract class ThreadListResponseEvent implements ThreadEventProcessor {

	protected LinkedList<String[]> list = null;
	
	public void setList(LinkedList<String[]> list)
	{
		this.list = list;
	}
	
	public LinkedList<String[]> getList()
	{
		return list;
	}
	
}
