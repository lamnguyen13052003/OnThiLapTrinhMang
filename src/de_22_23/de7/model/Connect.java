package de_22_23.de7.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
	private static Connection connection;
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		if(connection != null) return connection;
		String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
		String url = "jdbc:ucanaccess://OnThi.mdb";
		Class.forName(driver);
		return (connection = DriverManager.getConnection(url));
	}
}
