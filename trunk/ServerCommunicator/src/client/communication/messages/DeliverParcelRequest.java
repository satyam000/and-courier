package client.communication.messages;

import java.net.Socket;

import client.event.ThreadEventProcessor;

public class DeliverParcelRequest extends ClientRequest {
	
	private int parcel_id;
	
	public DeliverParcelRequest(Socket s, ThreadEventProcessor event, int parcel_id) {
		super(s, event);
		this.parcel_id = parcel_id;
	}

	@Override
	protected boolean communicate() {
		out.println("del:" + parcel_id);
		return true;
	}

}
