package client.test;

import client.communication.Client;
import client.event.ThreadListResponseEvent;
import client.event.ThreadLoginEvent;

public class Test {

	public static Client c;
	public static void main(String [] args) throws Exception
	{
		Client.setHostAddress("192.168.1.4");
		c = Client.getInstance();
		c.logIn("misio", "password", new ThreadLoginEvent(){

			public void process() {
				if (success)
				{
					System.out.println("logged");
				}
				else
				{
					System.out.println ("not logged!!!");
					System.exit(1);
				}
				
			}

			public void errorOccured() {
				System.out.println("Error login");
				
			}});
		
		Thread.sleep(1000);
		/*c.requestUnassignedParcels(new ThreadListResponseEvent(){

			public void process() {
				System.out.println("processing");
				for (String [] ss : list)
					System.out.println(ss[0]);
				
			}

			public void errorOccured() {
				System.out.print("Error");
				
			}},5);*/
		c.requestAssignedUndeliveredParcels(new ThreadListResponseEvent(){

			public void process() {
				System.out.println("processing");
				for (String [] ss : list)
					System.out.println(ss[0]);
				
			}

			public void errorOccured() {
				System.out.print("Error");
				
			}});
	}
}
