package de_22_23.de7.bai2.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;

import de_22_23.on_lai_de_7.bai2.ProductDAO;

public class ServerRMI {
	public static void main(String[] args) throws RemoteException, ClassNotFoundException, SQLException {
		Registry server = LocateRegistry.createRegistry(1305);
		server.rebind("PRODUCT_SERVICE", new ProductDAO());
	}
}
