package bai21.server;


import java.sql.*;

public class Connect {
    private static Connection connection;

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        if (connection != null) return connection;
        String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
        Class.forName(driver);
        String url = "jdbc:ucanaccess://OnThi.mdb";
        connection = DriverManager.getConnection(url);
        return connection;
    }
}
