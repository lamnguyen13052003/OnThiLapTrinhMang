package de_22_23.de7.model;

import java.io.Serializable;

public class Product implements Serializable{
	private int id, quantity;
	private String name;
	private double price;
	
	
	public Product() {
		super();
	}


	public Product(int id, String name, int quantity, double price) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.name = name;
		this.price = price;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	@Override
	public String toString() {
		return "Product [id=" + id + ", quantity=" + quantity + ", name=" + name + ", price=" + price + "]";
	}
}
