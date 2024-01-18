package de_22_23.on_lai_de_3.bai2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.StringTokenizer;

public class ThreadServer extends Thread {
	private Socket client;
	private PrintWriter out;
	private BufferedReader in;
	private String username;
	private boolean login;

	public ThreadServer(Socket client)
			throws UnsupportedEncodingException, IOException, ClassNotFoundException, SQLException {
		super();
		this.client = client;
		out = new PrintWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"), true);
		in = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
		new UserDAO();
	}

	@Override
	public void run() {
		String line;
		out.println("Welcome to Search Center...");
		try {
			while (true) {
				line = in.readLine();
				if (line == null || line.equals("QUIT"))
					break;
				action(line);
			}

			in.close();
			out.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			out.println("Lỗi I/O");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			out.println("Lỗi Database");
		}
	}

	private void action(String line) throws SQLException {
		StringTokenizer tk = new StringTokenizer(line, " ");
		String command = tk.nextToken();

		if (!login) {
			switch (command) {
			case "TEN" -> {
				if (!checkToken(tk, 1))
					return;
				username = tk.nextToken();
				if (UserDAO.containsUsername(username))
					out.println("Thành công!");
				else {
					username = null;
					out.println("username không tồn tại!");
				}
			}
			case "MAT_KHAU" -> {
				if (!checkToken(tk, 1))
					return;

				if (username == null) {
					out.println("Bạn chưa set username!");
					return;
				}
				if (UserDAO.login(username, tk.nextToken())) {
					login = true;
					out.println("Đăng nhập thành công!");
				} else
					out.println("Đăng nhập không thành công!");
			}
			default -> out.println("Lệnh không hợp lệ!");
			}
		} else {
			List<Student> students;
			switch (command) {
			case "TEN" -> {
				String name = line.substring(command.length() + 1, line.length());
				sendListStudents(StudentDAO.findByName(name));
			}
			case "MSSV" -> {
				if (!checkToken(tk, 1))
					return;
				try {
					int mssv = Integer.parseInt(tk.nextToken());
					sendListStudents(StudentDAO.findByID(mssv));
				} catch (NumberFormatException e) {
					out.println("Lỗi mssv");
				}
			}
			case "DIEM" -> {
				if (!checkToken(tk, 1))
					return;
				try {
					double mssv = Double.parseDouble(tk.nextToken());
					sendListStudents(StudentDAO.findByScore(mssv));
				} catch (NumberFormatException e) {
					out.println("Lỗi điểm");
				}
			}
			case "TUOI" -> {
				if (!checkToken(tk, 1))
					return;
				try {
					int tuoi = Integer.parseInt(tk.nextToken());
					sendListStudents(StudentDAO.findByAge(tuoi));
				} catch (NumberFormatException e) {
					out.println("Lỗi tuổi");
				}
			}
			case "SUA" -> {
				if (!checkToken(tk, 2))
					return;
				int mssv;
				double diem;
				try {
					mssv = Integer.parseInt(tk.nextToken());
				} catch (NumberFormatException e) {
					out.println("Lỗi mssv");
					return;
				}

				try {
					diem = Double.parseDouble(tk.nextToken());
				} catch (NumberFormatException e) {
					out.println("Lỗi điểm");
					return;
				}

				if (StudentDAO.updateScore(mssv, diem))
					out.println("Thành công!");
				else
					out.println("Sinh viên không tồn tại!");
			}
			default -> out.println("Lệnh không hợp lệ!");
			}
		}
	}

	private boolean checkToken(StringTokenizer tk, int countTk) {
		if (tk.countTokens() != countTk) {
			out.println("Lệnh không hợp lệ!");
			return false;
		}

		return true;
	}

	private void sendListStudents(List<Student> students) {
		if (students.isEmpty())
			out.println("Sinh viên không tồn tại!");

		for (Student student : students)
			out.println(student.toString());
	}
}
