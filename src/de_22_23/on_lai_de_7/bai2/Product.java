package de_22_23.on_lai_de_7.bai2;

import java.io.Serializable;

public class Product implements Serializable{
	public int id, quantity;
	public double price;
	public String name;
	
	
	
	@Override
	public String toString() {
		return "Product [id=" + id + ", ten=" + name + ", quantity=" + quantity + ", price=" + price  + "]";
	}
}
