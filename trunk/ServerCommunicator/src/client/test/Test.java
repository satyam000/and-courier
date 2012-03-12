package client.test;

import client.communication.Client;
import client.event.ThreadListResponseEvent;
import client.event.ThreadLoginEvent;

public class Test {

	public static void main(String [] args) throws Exception
	{
		Client c = Client.getInstance();
		c.logIn("misio", "password", new ThreadLoginEvent(){

			@Override
			public void process() {
				if (success)
					System.out.println("logged in");
				else
				{
					System.out.println ("not logged!!!");
					System.exit(1);
				}
				
			}

			@Override
			public void errorOccured() {
				System.out.println("Error login");
				
			}});
		Thread.sleep(1000);
		/*c.requestAssignedToMeParcels(new ThreadListResponseEvent(){

			@Override
			public void process() {
				for (String [] ss : list)
					System.out.println(ss[0]);
				
			}

			@Override
			public void errorOccured() {
				System.out.print("Error");
				
			}});*/
		c.deliverParcel(1,null);
	}
}
