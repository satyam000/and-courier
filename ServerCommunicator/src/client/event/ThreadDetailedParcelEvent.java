package client.event;

public abstract class ThreadDetailedParcelEvent implements ThreadEventProcessor {

	public String name;
	public String surname;
	public String city;
	public String weight;
	public String type;
	public String street;
	public String building;
	public String apartment; 
	public String postalCode;
	public String sentOn;
	public String price;
}
