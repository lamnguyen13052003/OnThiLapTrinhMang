package bai22.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IStudentService extends Remote {
	default String greeting() throws RemoteException {
		return "Welcome to students service...";
	}

	List<Student> findById(String username, int id) throws RemoteException;

	List<Student> findByName(String username, String name) throws RemoteException;

	List<Student> findByAge(String username, int age) throws RemoteException;
}
