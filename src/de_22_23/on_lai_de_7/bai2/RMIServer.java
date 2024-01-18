package de_22_23.on_lai_de_7.bai2;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.List;

public class RMIServer {
	public static void main(String[] args) throws RemoteException, ClassNotFoundException, SQLException {
		Registry server = LocateRegistry.createRegistry(54321);
		server.rebind("PRODUCT_SERVICE", new ProductDAO());
	}
}
