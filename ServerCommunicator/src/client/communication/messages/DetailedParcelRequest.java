package client.communication.messages;

import java.net.Socket;

import client.event.ThreadDetailedParcelEvent;

public class DetailedParcelRequest extends ClientRequest{

	private int parcel_id;
	
	public DetailedParcelRequest(Socket s, ThreadDetailedParcelEvent eproc, int parcel_id)
	{
		super(s, eproc);
		this.parcel_id = parcel_id;
	}
	
	@Override
	protected boolean communicate() {
		
		out.println("detpar:" + parcel_id);
		String input = null;
		int pos;
		try
		{
			while (true)
			{
				input = in.readLine();
				if (input.startsWith("detpar:"))
					break;
			}
		}
		catch(Exception e)
		{
			return false;
		}
		input = input.substring(7);
		String [] records = input.split(unitSep);
		ThreadDetailedParcelEvent ev = (ThreadDetailedParcelEvent) event;
		ev.name = records[0];
		ev.surname = records[1];
		ev.city = records[2];
		ev.street = records[3];
		ev.postalCode = records[4];
		ev.building = records[5];
		ev.apartment = records[6];
		ev.type = records[7];
		ev.sentOn = records[8];
		ev.price = records[9];
		ev.weight = records[10];
		return true;
	}
}
