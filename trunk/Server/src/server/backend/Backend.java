package server.backend;

import java.util.LinkedList;

public interface Backend {
	
	//returns true if operation succeeds
	public boolean addUser(String userName, String password);
	
	//returns true if operation succeeds
	public boolean deleteUser(String userName);
	
	//if login succeeded, returns his id, otherwise returns -1
	public int login(String userName, String password);
	
	//returns list of string arrays representing records from backend
	public LinkedList<String[]> getUnassignedParcels();
	
	public LinkedList<String[]> getAssignedToMeParcels(int id);
	
	public void deliverParcel(int parecel_id);
}
