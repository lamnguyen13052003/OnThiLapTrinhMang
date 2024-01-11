	package bai22.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserDAO extends UnicastRemoteObject implements IUserService {
	Map<String, String> mapUser;
	private Connection connection;

	protected UserDAO() throws RemoteException {
		mapUser = new HashMap<>();
		connection = Connect.connect();
	}

	@Override
	public boolean setUserName(String userName) throws RemoteException {
		String sql = "SELECT username FROM users WHERE username = ?;";
		try {
			PreparedStatement statment = connection.prepareStatement(sql);
			statment.setString(1, userName);
			ResultSet result = statment.executeQuery();
			boolean check = result.next();
			if (check)
				mapUser.put(userName, null);
			statment.close();
			result.close();
			return check;
		} catch (SQLException e) {
			System.out.println(e);
		}
		return false;
	}

	@Override
	public int login(String userName, String password) throws RemoteException {
		if (!mapUser.containsKey(userName))
			return 0;
		String sql = "SELECT username FROM users WHERE username = ? AND password = ?";
		try {
			PreparedStatement statment = connection.prepareStatement(sql);
			statment.setString(1, userName);
			statment.setString(2, password);
			ResultSet result = statment.executeQuery();
			boolean check = result.next();
			statment.close();
			result.close();
			if (check)
				mapUser.put(userName, password);
			return check ? 1 : -1;
		} catch (SQLException e) {
			System.out.println(e);
		}
		return -1;
	}
}
