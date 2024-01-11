package bai22.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentDAO extends UnicastRemoteObject implements IStudentService {
	private Map<String, IStudentService> mapStudentService;
	private Connection connection;

	protected StudentDAO() throws RemoteException {
		super();
		mapStudentService = new HashMap<String, IStudentService>();
		connection = Connect.connect();
	}

	@Override
	public List<Student> findById(String username, int id) throws RemoteException {
		String sql = "SELECT * FROM students WHERE id = ?";
		try {
			PreparedStatement statment = connection.prepareStatement(sql);
			statment.setInt(1, id);
			ResultSet result = statment.executeQuery();
			List<Student> students = getStudent(result);
			statment.close();
			return students;
		} catch (SQLException e) {
			System.out.println(e);
		}
		return new ArrayList<>();
	}

	@Override
	public List<Student> findByName(String username, String name) throws RemoteException {
		String sql = "SELECT * FROM students WHERE name LIKE ?";
		try {
			PreparedStatement statment = connection.prepareStatement(sql);
			statment.setString(1, "%" + name);
			ResultSet result = statment.executeQuery();
			List<Student> students = getStudent(result);
			statment.close();
			return students;
		} catch (SQLException e) {
			System.out.println(e);
		}
		return new ArrayList<>();
	}

	@Override
	public List<Student> findByAge(String username, int age) throws RemoteException {
		String sql = "SELECT * FROM students WHERE age = ?";
		try {
			PreparedStatement statment = connection.prepareStatement(sql);
			statment.setInt(1, age);
			ResultSet result = statment.executeQuery();
			List<Student> students = getStudent(result);
			statment.close();
			return students;
		} catch (SQLException e) {
			System.out.println(e);
		}
		return new ArrayList<>();
	}

	public List<Student> getStudent(ResultSet result) throws SQLException {
		List<Student> students = new ArrayList<>();
		Student student;
		while (result.next()) {
			student = new Student(result.getInt("id"), result.getString("name"), result.getInt("age"),
					result.getDouble("score"));
			students.add(student);
		}
		result.close();
		return students;
	}
}
