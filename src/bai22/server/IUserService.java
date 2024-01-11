package bai22.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IUserService extends Remote{
	default String greeting() throws RemoteException {
		return "Welcome to user service...";
	}

	boolean setUserName(String userName) throws RemoteException;

	int login(String userName, String password) throws RemoteException;
}
