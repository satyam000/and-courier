package server.backend;

public interface Backend {
	
	//returns true if operation succeeds
	public boolean addUser(String userName, String password);
	
	//returns true if operation succeeds
	public boolean deleteUser(String userName);
	
	//returns true if login operation succeeds
	public boolean login(String userName, String password);
}
