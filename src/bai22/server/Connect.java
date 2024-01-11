package bai22.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
	private static Connection connection;

	public static Connection connect(){
		if (connection != null)
			return connection;
		String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
		String url = "jdbc:ucanaccess://OnThi.mdb";
		try {
			Class.forName(driver);
			return connection = DriverManager.getConnection(url);
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e);
		}
		
		return null;
	}
}
