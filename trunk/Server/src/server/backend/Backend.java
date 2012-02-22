package server.backend;

public interface Backend {
	
	//returns true if operation succeeds
	public boolean addUser(String userName, String password, String name, String surname);
	
	//returns true if operation succeeds
	public boolean deleteUser(String userName);
	
	//returns true if login operation succeeds
	public boolean login(String userName, String password);
}
