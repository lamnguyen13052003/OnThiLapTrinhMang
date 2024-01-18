package de_22_23.on_lai_de_7.bai2;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductDAO extends UnicastRemoteObject implements ProductService  {
	private static Connection connection;

	public ProductDAO() throws RemoteException, SQLException, ClassNotFoundException {
		if (connection == null) {
			String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
			String url = "jdbc:ucanaccess://OnThi.mdb";
			Class.forName(driver);
			connection = DriverManager.getConnection(url);
		}
	}

	@Override
	public String add(Product product) throws RemoteException {
		try {
			String sql = "INSERT INTO(id, name, quantity, price) VALUES(?, ?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, product.id);
			statement.setString(2, product.name);
			statement.setInt(3, product.quantity);
			statement.setDouble(4, product.price);
			statement.executeUpdate();
			statement.close();
			return "OK";
		} catch (SQLException e) {
			e.printStackTrace();
			return "ERROR";
		}
	}

	@Override
	public void sell(List<Integer> listIdProduct) throws RemoteException {
		for (int id : listIdProduct) {
			try {
				sell(id);
			} catch (RemoteException | SQLException e) {
			}
		}
	}

	@Override
	public String update(int idProduct, double price) throws RemoteException {
		try {
			String sql = "UPDATE products SET price = ? WHERE id = ?";
			PreparedStatement statement;
			statement = connection.prepareStatement(sql);
			statement.setDouble(1, price);
			statement.setInt(1, idProduct);
			int result = statement.executeUpdate();
			if (result == 0)
				return "CAN NOT UPDATE";
			statement.close();
			return "OK";
		} catch (SQLException e) {
			e.printStackTrace();
			return "CAN NOT UPDATE";
		}
	}

	@Override
	public Product find(String name) throws RemoteException {
		try {
			String sql = "SELECT * FROM products WHERE name LIKE ?";
			PreparedStatement statement;
			statement = connection.prepareStatement(sql);
			statement.setString(1, "%" + name + "%");
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
				return null;
			Product product = new Product();
			product.id = resultSet.getInt("id");
			product.name = resultSet.getString("name");
			product.quantity = resultSet.getInt("quantity");
			product.price = resultSet.getDouble("price");
			resultSet.close();
			statement.close();
			return product;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void sell(int id) throws RemoteException, SQLException {
		String sql = "SELECT quantity FROM products WHERE id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, id);
		ResultSet resultSet = statement.executeQuery();
		if (!resultSet.next())
			return;
		int quantity = resultSet.getInt("quantity");
		if (quantity <= 0)
			return;
		resultSet.close();
		statement.close();
		statement = connection.prepareStatement(sql);
		sql = "UPDATE products SET quantity = ? WHERE id = ?";
		statement.setInt(1, quantity - 1);
		statement.setInt(2, id);
		statement.executeUpdate();
		statement.close();
	}
}
