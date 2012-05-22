package server.communication.message;

import server.communication.ClientCommunicator;

public class DetailedParcelProcessor extends MessageProcessor{

	private ClientCommunicator parent;
	
	public DetailedParcelProcessor(ClientCommunicator parent)
	{
		this.parent = parent;
	}
	@Override
	public void process(String in) {
		String answer = "detpar:";
		String [] lol = in.split(":");
		String [] args = parent.getParent().getBackend().getDetailedParcel(Integer.parseInt(lol[1]));
		answer += args[0] + unitSep + args[1] + unitSep + args[2] + unitSep + args[3] + unitSep + args[4] + unitSep + args[5] + unitSep + args[6] + unitSep + args[7] + unitSep + args[8] + unitSep + args[9] + unitSep + args[10];
		parent.getOutputStream().println(answer);
	}

}
