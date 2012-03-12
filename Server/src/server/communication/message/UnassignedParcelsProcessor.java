package server.communication.message;

import java.util.LinkedList;

import server.communication.ClientCommunicator;

public class UnassignedParcelsProcessor extends MessageProcessor{

	private ClientCommunicator parent;
	
	public UnassignedParcelsProcessor(ClientCommunicator parent)
	{
		this.parent = parent;
	}
	
	@Override
	public void process(String in) {
		
		String answer = "unpar:";
		LinkedList<String[]> res = parent.getParent().getBackend().getUnassignedParcels();
		boolean first = true;
		for (String [] args : res)
		{
			if (first)
				first = false;
			else
				answer += recordSep;
			answer += args[0] + unitSep + args[1] + unitSep + args[2] + unitSep + args[3] + unitSep + args[4] + unitSep + args[5] + unitSep + args[6] + unitSep + args[7] + unitSep + args[8] + unitSep;
		}
		parent.getOutputStream().println(answer);
	}

	
}
