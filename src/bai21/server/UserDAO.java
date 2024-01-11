package bai21.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
	private static UserDAO instance;

	public static UserDAO getInstence() {
		return instance == null ? new UserDAO() : instance;
	}

	public boolean hasUsername(String username) throws SQLException, ClassNotFoundException {
		String sql = "SELECT username FROM users WHERE username = ?";
		Connection connection = Connect.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, username);
		ResultSet resultSet = preparedStatement.executeQuery();
		boolean result = resultSet.next();
		preparedStatement.close();
		resultSet.close();
		return result;
	}

	public User login(String username, String password) throws SQLException, ClassNotFoundException {
		User user = new User();
		Connection connection = Connect.getConnection();
		String sql = "SELECT username, password FROM users WHERE username = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, username);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			user.setData(resultSet);
		} else {
			user = null;
		}
		preparedStatement.close();
		resultSet.close();
		return user;
	}
}
