package bai21.server;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private String name, password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setData(ResultSet result) throws SQLException {
        this.name = result.getString("username");
        this.password = result.getString("password");
    }
}
