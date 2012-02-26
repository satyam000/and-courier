package client.test;

import client.communication.Client;
import client.event.ThreadListResponseEvent;

public class Test {

	public static void main(String [] args)
	{
		Client c = Client.getInstance();
		c.requestUnassignedParcels(new ThreadListResponseEvent(){

			@Override
			public void process() {
				for (String [] s : list)
					System.out.println(s[0]);
				
			}

			@Override
			public void errorOccured() {
				System.out.println("Error");
				
			}});
	}
}
