package server.communication.message;

import java.util.LinkedList;

import server.communication.ClientCommunicator;

public class AssignedToMeParcelsProcessor extends MessageProcessor{

private ClientCommunicator parent;
	
	public AssignedToMeParcelsProcessor(ClientCommunicator parent)
	{
		this.parent = parent;
	}
	
	@Override
	public void process(String in) {
		
		String answer = "aspar:";
		LinkedList<String[]> res = parent.getParent().getBackend().getAssignedToMeParcels(parent.getClientId());
		boolean first = true;
		for (String [] args : res)
		{
			if (first)
				first = false;
			else
				answer += recordSep;
			answer += args[0] + unitSep + args[1] + unitSep + args[2] + unitSep + args[3] + unitSep + args[4] + unitSep + args[5] + unitSep + args[6] + unitSep + args[7] + unitSep + args[8];
		}
		parent.getOutputStream().println(answer);
	}

}
