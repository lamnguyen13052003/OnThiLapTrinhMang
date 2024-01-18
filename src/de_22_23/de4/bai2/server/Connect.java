package de_22_23.de4.bai2.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    private static Connection connection;
    public static Connection getConnection() throws SQLException {
        if(connection != null) return connection;
        String driver = "net.ucanaccess.jdbc.UcanaccessDriver"
                , url = "jdbc:ucanaccess://OnThi.mdb";
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return (connection = DriverManager.getConnection(url));
    }
}
