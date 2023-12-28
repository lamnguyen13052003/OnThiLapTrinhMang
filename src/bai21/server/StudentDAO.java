package bai21.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static bai21.server.Connect.getConnection;

public class StudentDAO {
    private static StudentDAO instance;

    public static StudentDAO getInstence() {
        return instance == null ? new StudentDAO() : instance;
    }

    public List<Student> getAll() throws SQLException, ClassNotFoundException {
        List<Student> result = new ArrayList<>();
        Connection connection = getConnection();
        String sql = "SELECT id, name, age, score FROM students";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            result.add(new Student(resultSet));
        }
        statement.close();
        resultSet.close();
        return result;
    }

    public List<Student> findById(int id) throws SQLException, ClassNotFoundException {
        List<Student> result = new ArrayList<>();
        Connection connection = getConnection();
        String sql = "SELECT id, name, age, score FROM students WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            result.add(new Student(resultSet));
        }
        statement.close();
        resultSet.close();
        return result;
    }

    public List<Student> findByName(String name) throws SQLException, ClassNotFoundException {
        List<Student> result = new ArrayList<>();
        Connection connection = getConnection();
        String sql = "SELECT id, name, age, score FROM students WHERE name LIKE ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + name);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            result.add(new Student(resultSet));
        }
        statement.close();
        resultSet.close();
        return result;
    }

    public List<Student> findByAge(int age) throws SQLException, ClassNotFoundException {
        List<Student> result = new ArrayList<>();
        Connection connection = getConnection();
        String sql = "SELECT id, name, age, score FROM students WHERE age = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, age);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            result.add(new Student(resultSet));
        }
        statement.close();
        resultSet.close();
        return result;
    }

    public List<Student> findByScore(double score) throws SQLException, ClassNotFoundException {
        List<Student> result = new ArrayList<>();
        Connection connection = getConnection();
        String sql = "SELECT id, name, age, score FROM students WHERE score = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDouble(1, score);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            result.add(new Student(resultSet));
        }
        statement.close();
        resultSet.close();
        return result;
    }
}
