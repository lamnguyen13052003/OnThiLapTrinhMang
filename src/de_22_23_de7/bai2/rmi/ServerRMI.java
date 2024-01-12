package de_22_23_de7.bai2.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerRMI {
	public static void main(String[] args) throws RemoteException {
		Registry server = LocateRegistry.createRegistry(1305);
		server.rebind("PRODUCT_SERVICE", new ProductRemote());
	}
}
