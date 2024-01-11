package bai22.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FileRemote extends UnicastRemoteObject implements IFileService {
	private Map<String, DataOutputStream> mapDOS;
	private Map<String, DataInputStream> mapDIS;

	protected FileRemote() throws RemoteException {
		mapDOS = new HashMap<>();
		mapDIS = new HashMap<>();
	}

	@Override
	public boolean openFileDownload(String username, String path) throws RemoteException {
		try {
			DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(path)));
			mapDIS.put(username, dis);
		} catch (IOException e) {
			throw new RemoteException(e.getMessage());
		}

		return true;
	}

	@Override
	public boolean openFileUpload(String username, String path) throws RemoteException {
		try {
			DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(path)));
			mapDOS.put(username, dos);
		} catch (IOException e) {
			throw new RemoteException(e.getMessage());
		}

		return true;
	}

	@Override
	public byte[] download(String username) throws RemoteException {
		DataInputStream dis = mapDIS.get(username);
		byte[] buffer = new byte[1024 * 100];
		int data = 0;
		try {
			data = dis.read(buffer);
			if (data == -1)
				return null;
			buffer = data == buffer.length ? buffer : Arrays.copyOf(buffer, data);
		} catch (IOException e) {
			throw new RemoteException(e.getMessage());
		}

		return buffer;
	}

	@Override
	public int upload(String username, byte[] buffer, int data) throws RemoteException {
		DataOutputStream dos = mapDOS.get(username);
		try {
			dos.write(buffer, 0, data);
			dos.flush();
		} catch (IOException e) {
			throw new RemoteException(e.getMessage());
		}
		return 1;
	}

	@Override
	public void closeFileDownload(String username) throws RemoteException {
		DataInputStream dis = mapDIS.get(username);
		if (dis == null)
			return;
		try {
			dis.close();
		} catch (IOException e) {
			throw new RemoteException(e.getMessage());
		}
		mapDIS.remove(username);
	}

	@Override
	public void closeFileUpload(String username) throws RemoteException {
		DataOutputStream dos = mapDOS.get(username);
		if (dos == null)
			return;
		try {
			dos.close();
		} catch (IOException e) {
			throw new RemoteException(e.getMessage());
		}
		mapDOS.remove(username);
	}
}
