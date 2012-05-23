package client.test;

import client.communication.Client;
import client.event.ThreadDetailedParcelEvent;
import client.event.ThreadEventProcessor;
import client.event.ThreadListResponseEvent;
import client.event.ThreadLoginEvent;

public class Test {

	public static Client c;
	public static void main(String [] args) throws Exception
	{
		Client.setHostAddress("192.168.1.2");
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
		System.out.println();
		c.requestUnassignedParcels(new ThreadListResponseEvent(){

			public void process() {
				System.out.println("processing - unassigned parcels");
				for (String [] ss : list)
					System.out.println(ss[0]);
				
			}

			public void errorOccured() {
				System.out.print("Error 1");
				
			}},5);
		Thread.sleep(1000);
		System.out.println();
		c.requestAssignedUndeliveredParcels(new ThreadListResponseEvent(){

			public void process() {
				System.out.println("processing - assigned undelivered parcels");
				for (String [] ss : list)
					System.out.println(ss[0]);
				
			}

			public void errorOccured() {
				System.out.print("Error 2");
				
			}});
		
		Thread.sleep(1000);
		System.out.println();
		c.requestAssignedToMeParcels(new ThreadListResponseEvent(){

			public void process() {
				System.out.println("processing - assigned parcels");
				for (String [] ss : list)
					System.out.println(ss[0]);
				
			}

			public void errorOccured() {
				System.out.print("Error 2");
				
			}});
		Thread.sleep(1000);
		System.out.println();
		c.requestParcelDetails(1, new ThreadDetailedParcelEvent()
		{

			public void process() {
				System.out.println("name = " + name);
				System.out.println("surname = " + surname);
				System.out.println("postalCode = " + postalCode);
				System.out.println("city = " + city);
				System.out.println("street = " + street);
				System.out.println("weight = " + weight);
				System.out.println("type = " + type);
				System.out.println("building = " + building);
				System.out.println("apartment = " + apartment);
				System.out.println("senton = " + sentOn);
				System.out.println("price = " + price);
				
			}

			public void errorOccured() {
				System.out.print("Error 3");
				
			}
		
		});
		
		Thread.sleep(1000);
		System.out.println();
		c.AssignParcel(1, new ThreadEventProcessor(){

			public void process() {
				System.out.print("Parcel assigned");
				
			}

			public void errorOccured() {
				System.out.print("Error 4");
				
			}});
	}
}
