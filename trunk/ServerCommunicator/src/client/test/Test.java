package client.test;

import client.communication.Client;
import client.event.ThreadListResponseEvent;
import client.event.ThreadLoginEvent;

public class Test {

	public static void main(String [] args) throws Exception
	{
		if (Client.testAddress("hermenegild"))
		{
			System.out.println("ok");
		}
		else
			System.out.println("fail");
		/*Client.setHostAddress("192.168.1.5");
		Client c = Client.getInstance();
		c.logIn("misio", "password", new ThreadLoginEvent(){

			public void process() {
				if (success)
					System.out.println("logged in");
				else
				{
					System.out.println ("not logged!!!");
					System.exit(1);
				}
				
			}

			public void errorOccured() {
				System.out.println("Error login");
				
			}});
		//Thread.sleep(1000);
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
	}
}
