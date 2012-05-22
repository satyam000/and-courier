package client.communication.messages;

import java.net.Socket;
import java.util.LinkedList;

import client.event.ThreadListResponseEvent;

public class AssignedUndeliveredParcelsRequest extends ClientRequest {

	public AssignedUndeliveredParcelsRequest(Socket s, ThreadListResponseEvent eproc)
	{
		super(s, eproc);
	}
	
	@Override
	protected boolean communicate() {
		
		out.println("asund:");
		String input = null;
		int pos;
		try
		{
			while (true)
			{
				input = in.readLine();
				if (input.startsWith("asund:"))
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
