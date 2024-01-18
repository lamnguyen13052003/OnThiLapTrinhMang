package de_22_23.on_lai_de_7.bai2;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ProductService extends Remote {
	default String greeting() throws RemoteException {
		return "Welcome to Product Service Center..";
	}

	public String add(Product product) throws RemoteException;

	public void sell(List<Integer> listIdProduct) throws RemoteException;

	public String update(int idProduct, double price) throws RemoteException;

	public Product find(String name) throws RemoteException;
}
