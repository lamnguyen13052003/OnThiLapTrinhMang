package de_22_23.on_lai_de_3.bai2;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO extends Connect {
	public StudentDAO() throws ClassNotFoundException, SQLException {
		super();
	}

	public static List<Student> findByID(int massv) throws SQLException {
		List<Student> result = new ArrayList<>();
		String sql = "SELECT * FROM students WHERE id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, massv);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			Student student = new Student(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("age"),
					resultSet.getDouble("score"));
			result.add(student);
		}
		resultSet.close();
		statement.close();
		return result;
	}

	public static List<Student> findByName(String name) throws SQLException {
		List<Student> result = new ArrayList<>();
		String sql = "SELECT * FROM students WHERE name LIKE ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + name);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			Student student = new Student(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("age"),
					resultSet.getDouble("score"));
			result.add(student);
		}
		resultSet.close();
		statement.close();
		return result;
	}

	public static List<Student> findByAge(int age) throws SQLException {
		List<Student> result = new ArrayList<>();
		String sql = "SELECT * FROM students WHERE age = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, age);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			Student student = new Student(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("age"),
					resultSet.getDouble("score"));
			result.add(student);
		}
		resultSet.close();
		statement.close();
		return result;
	}

	public static List<Student> findByScore(double diem) throws SQLException {
		List<Student> result = new ArrayList<>();
		String sql = "SELECT * FROM students WHERE score = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setDouble(1, diem);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			Student student = new Student(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("age"),
					resultSet.getDouble("score"));
			result.add(student);
		}
		resultSet.close();
		statement.close();
		return result;
	}

	public static boolean updateScore(int mssv, double diemMoi) throws SQLException {
		String sql = "UPDATE students SET score = ? WHERE id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setDouble(1, diemMoi);
		statement.setInt(2, mssv);
		int result = statement.executeUpdate();
		return result == 0 ? false : true;
	}
}
