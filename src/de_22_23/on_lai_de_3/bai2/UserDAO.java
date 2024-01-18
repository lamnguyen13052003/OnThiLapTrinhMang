package de_22_23.on_lai_de_3.bai2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends Connect{
	public UserDAO() throws ClassNotFoundException, SQLException {
		super();
	}

	public static boolean containsUsername(String username) throws SQLException {
		boolean result = false;
		String sql = "SELECT username FROM users WHERE username = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, username);
		ResultSet resultSet = statement.executeQuery();
		result = resultSet.next();
		resultSet.close();
		statement.close();
		return result;
	}
	
	public static boolean login(String username, String password) throws SQLException {
		boolean result = false;
		String sql = "SELECT username FROM users WHERE username = ? AND password = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, username);
		statement.setString(2, password);
		ResultSet resultSet = statement.executeQuery();
		result = resultSet.next();
		resultSet.close();
		statement.close();
		return result;
	}
}
