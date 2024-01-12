package de_22_23_de7.bai2.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import de_22_23_de7.model.Product;

public interface IProductService extends Remote{
	default String greenting() throws RemoteException{
		return "Welcome to product service....";
	}
	public String add(Product product) throws RemoteException;
	public String sell(int id) throws RemoteException;
	public String update(int id, double newPrice) throws RemoteException;
	public List<Product> find(String name) throws RemoteException;
	public String exit() throws RemoteException;
}
