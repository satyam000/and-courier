package client.communication.messages;

import java.net.Socket;
import java.util.LinkedList;

import client.event.ThreadListResponseEvent;

public class UnassignedParcelsRequest extends ClientRequest{

	private int page;
	
	public UnassignedParcelsRequest(Socket s, ThreadListResponseEvent eproc, int page)
	{
		super(s, eproc);
		this.page = page;
	}
	
	public UnassignedParcelsRequest(Socket s, ThreadListResponseEvent eproc)
	{
		super(s, eproc);
		this.page = 0;
	}
	
	@Override
	protected boolean communicate() {
		
		out.println("unpar:"+page);
		String input = null;
		int pos;
		try
		{
			while (true)
			{
				input = in.readLine();
				if (input.startsWith("unpar:"))
					break;
			}
		}
		catch(Exception e)
		{
			return false;
		}
		input = input.substring(6);
		String [] records = input.split(recSep);
		LinkedList<String[]>ret = new LinkedList<String[]>();
		for (String record : records)
			ret.add(record.split(unitSep));
		((ThreadListResponseEvent)event).setList(ret);
		return true;
	}

}
