package de_22_23.de7.bai2.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de_22_23.de7.model.Connect;
import de_22_23.de7.model.Product;

public class ProductRemote extends UnicastRemoteObject implements IProductService {
	private Connection connection;

	protected ProductRemote() throws RemoteException {
		super();
		try {
			connection = Connect.getConnection();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public String add(Product product) throws RemoteException {
		String sqlSelect = "SELECT id FROM products WHERE id = ?";
		String sqlInsert = "INSERT INTO products(id, name, quantity, price) values (?, ?, ?, ?)";
		try {
			PreparedStatement statement = connection.prepareStatement(sqlSelect);
			statement.setInt(1, product.getId());
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) return "Sản phẩm đã tồn tại!";
			resultSet.close();
			statement.close();
			
			statement = connection.prepareStatement(sqlInsert);
			statement.setInt(1, product.getId());
			statement.setString(2, product.getName());
			statement.setInt(3, product.getQuantity());
			statement.setDouble(4, product.getQuantity());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return "Lỗi cơ sỡ dữ liệu!";
		}
		return "Thành công!";
	}

	@Override
	public String sell(int id) throws RemoteException {
		String sql = "SELECT quantity FROM products WHERE id = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			if (!result.next())
				return "Sản phẩm không tồn tại!";
			int quantity = 0;
			if ((quantity = result.getInt("quantity")) <= 0)
				return "Sản phẩm đã hết!";
			result.close();
			statement.close();
			sql = "UPDATE products set quantity = ? WHERE id = ?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, quantity - 1);
			statement.setInt(2, id);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return "Lỗi cơ sỡ dữ liệu!";
		}

		return "Thành công!";
	}

	@Override
	public String update(int id, double newPrice) throws RemoteException {
		String sql = "UPDATE products set price = ? WHERE id = ?";
		int lineRePlace = 0;
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement = connection.prepareStatement(sql);
			statement.setDouble(1, newPrice);
			statement.setInt(2, id);
			lineRePlace = statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return "Lỗi cơ sỡ dữ liệu!";
		}
		return lineRePlace > 0 ? "OK" : "CAN NOT UPDATE";
	}

	@Override
	public List<Product> find(String name) throws RemoteException {
		List<Product> products = new ArrayList<>();
		String sql = "SELECT * FROM products WHERE name LIKE ?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement = connection.prepareStatement(sql);
			statement.setString(1, "%"+name.toLowerCase()+"%");
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				Product product = new Product(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("quantity"), resultSet.getDouble("price"));
				products.add(product);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return products;
		}
		return products;
	}

	@Override
	public String exit() throws RemoteException {
		return "Thành công!";
	}

}
