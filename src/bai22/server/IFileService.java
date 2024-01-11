package bai22.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IFileService extends Remote {
	default String greeting() throws RemoteException {
		return "Welcome to file service...";
	}

	public boolean openFileDownload(String username, String path) throws RemoteException;

	public boolean openFileUpload(String username, String path) throws RemoteException;

	public byte[] download(String username) throws RemoteException;

	public int upload(String username, byte[] buffer, int data) throws RemoteException;

	public void closeFileDownload(String username) throws RemoteException;

	void closeFileUpload(String username) throws RemoteException;

}
