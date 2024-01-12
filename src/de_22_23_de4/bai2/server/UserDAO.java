package de_22_23_de4.bai2.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private Connection connection;

    public UserDAO() throws SQLException {
        connection = Connect.getConnection();
    }

    public boolean hasUser(String username) throws SQLException {
        String sql = "SELECT username FROM users WHERE username = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();
        boolean check = resultSet.next();
        statement.close();
        resultSet.close();
        return check;
    }

    public boolean login(String username, String password) throws SQLException {
        String sql = "SELECT username FROM users WHERE username = ? AND password = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();
        boolean check = resultSet.next();
        statement.close();
        resultSet.close();
        return check;
    }
}
