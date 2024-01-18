package de_22_23.on_lai_de_3.bai2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
	protected static Connection connection;
	
	public Connect() throws ClassNotFoundException, SQLException {
		connection = getConnection();
	}
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		if(connection != null) return connection;
		String driver = "net.ucanaccess.jdbc.UcanaccessDriver",
				url = "jdbc:ucanaccess://OnThi.mdb";
		Class.forName(driver);
		return DriverManager.getConnection(url);
	}
}
