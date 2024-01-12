package de_22_23_de4.bai2.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private Connection connection;

    public StudentDAO() throws SQLException {
        connection = Connect.getConnection();
    }

    public List<Student> findByScore(int id) throws SQLException {
        List<Student> result = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next())
            result.add(new Student(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("age"), resultSet.getDouble("score")));

        resultSet.close();
        statement.close();
        return result;
    }

    public List<Student> findByName(String name) throws SQLException {
        List<Student> result = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE name LIKE ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + name);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next())
            result.add(new Student(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("age"), resultSet.getDouble("score")));

        resultSet.close();
        statement.close();
        return result;
    }

    public List<Student> findByAge(int age) throws SQLException {
        List<Student> result = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE age = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, age);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next())
            result.add(new Student(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("age"), resultSet.getDouble("score")));

        resultSet.close();
        statement.close();
        return result;
    }

    public List<Student> findByScore(double score) throws SQLException {
        List<Student> result = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE score = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDouble(1, score);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next())
            result.add(new Student(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("age"), resultSet.getDouble("score")));

        resultSet.close();
        statement.close();
        return result;
    }

    public boolean updateScore(int id, double newScore) throws SQLException {
        String sql = "UPDATE students SET score = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDouble(1, newScore);
        statement.setInt(2, id);
        int check = statement.executeUpdate();
        statement.close();
        return check != 0 ? true : false;
    }
}
