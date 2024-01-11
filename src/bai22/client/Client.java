package bai22.client;

import java.awt.FontFormatException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import bai22.server.IFileService;
import bai22.server.IStudentService;
import bai22.server.IUserService;
import bai22.server.Student;

public class Client {
	private boolean isLogin;
	private String username;
	private IStudentService studentService;
	private IUserService userService;
	private IFileService fileService;
	private BufferedReader userIn;

	public Client(String host, int port) throws RemoteException, NotBoundException, UnsupportedEncodingException {
		userIn = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		Registry client = LocateRegistry.getRegistry(host, port);
		userService = (IUserService) client.lookup("USER_SERVICE");
		fileService = (IFileService) client.lookup("FILE_SERVICE");
		studentService = (IStudentService) client.lookup("STUDENT_SERVICE");
		System.out.println(userService.greeting());
		System.out.println(fileService.greeting());
		System.out.println(studentService.greeting());
	}

	public void run() throws IOException {
		String line = null;
		while (true) {
			System.out.print("Mời bạn nhập lệnh của mình: ");
			line = userIn.readLine();
			if (line == null || line.isBlank() || line.toLowerCase().equals("quit"))
				break;
			action(line);
		}
	}

	private void action(String line) throws RemoteException, IOException {
		StringTokenizer tk = new StringTokenizer(line, " ");
		String command = tk.nextToken().toLowerCase();
		if (!isLogin) {
			switch (command) {
			case "user" -> {
				if (checkToken(tk, 1)) {
					setUsername(tk.nextToken());
				}
			}
			case "password" -> {
				if (checkToken(tk, 1)) {
					login(tk.nextToken());
				}
			}
			default -> System.out.println("Lệnh không hợp lệ!");
			}
		} else {
			switch (command) {
			case "upload" -> {
				if (checkToken(tk, 2)) {
					upload(tk.nextToken(), tk.nextToken());
				}
			}
			case "download" -> {
				if (checkToken(tk, 2)) {
					download(tk.nextToken(), tk.nextToken());
				}
			}
			case "fbid" -> {
				if (checkToken(tk, 1)) {
					try {
						int id = Integer.parseInt(tk.nextToken());
						findStudentsById(id);
					} catch (NumberFormatException e) {
						System.out.println("Lệnh không hợp lệ!");
					}
				}
			}
			case "fbn" -> {
				if (tk.countTokens() >= 1) {
					findStudentsByName(line.substring(command.length() + 1, line.length()));
					return;
				}
				System.out.println("Lệnh không hợp lệ!");
			}
			case "fba" -> {
				if (checkToken(tk, 1)) {
					try {
						int age = Integer.parseInt(tk.nextToken());
						findStudentsByAge(age);
					} catch (NumberFormatException e) {
						System.out.println("Lệnh không hợp lệ!");
					}
				}
			}
			default -> System.out.println("Lệnh không hợp lệ!");
			}
		}
	}

	public boolean checkToken(StringTokenizer tk, int length) {
		if (tk.countTokens() != length) {
			System.out.println("Lệnh không hợp lệ!");
			return false;
		}

		return true;
	}

	public void setUsername(String username) throws RemoteException {
		boolean check = userService.setUserName(username);
		if (check) {
			this.username = username;
			System.out.println("username tồn tại!");
			return;
		}

		System.out.println("username không tồn tại!");
	}

	public void login(String password) throws RemoteException {
		int check = userService.login(username, password);
		switch (check) {
		case -1 -> {
			System.out.println("Đăng nhập không thành công!");
		}
		case 0 -> {
			System.out.println("Bạn chưa set username!");
		}
		case 1 -> {
			System.out.println("Đăng nhập thành công!");
			isLogin = true;
		}
		}
	}

	public void upload(String fileClientUpload, String fileServerUpload) throws RemoteException, IOException {
		DataInputStream clientDIS = new DataInputStream(new BufferedInputStream(new FileInputStream(fileClientUpload)));
		int data = 0;
		byte[] buffer = new byte[1024 * 100];
		fileService.openFileUpload(username, fileServerUpload);
		while ((data = clientDIS.read(buffer)) != -1) {
			fileService.upload(username, buffer, data);
		}

		clientDIS.close();
		fileService.closeFileUpload(username);
		System.out.println("upload done...");
	}

	public void download(String fileServerDownLoad, String fileClientDownload) throws IOException {
		DataOutputStream clientDOS = new DataOutputStream(
				new BufferedOutputStream(new FileOutputStream(fileClientDownload)));
		fileService.openFileDownload(username, fileServerDownLoad);
		byte[] buffer = new byte[1024 * 100];
		while ((buffer = fileService.download(username)) != null) {
			clientDOS.write(buffer);
			clientDOS.flush();
		}
		clientDOS.close();
		fileService.closeFileDownload(username);
		System.out.println("downloading done...");
	}

	public void findStudentsById(int id) throws RemoteException {
		List<Student> students = studentService.findById(username, id);
		showListStudent(students);
	}

	public void findStudentsByName(String name) throws RemoteException {
		List<Student> students = studentService.findByName(username, name);
		showListStudent(students);
	}

	public void findStudentsByAge(int age) throws RemoteException {
		List<Student> students = studentService.findByAge(username, age);
		showListStudent(students);
	}

	public void showListStudent(List<Student> students) {
		if(students.isEmpty()) System.out.println("Không tìm thấy sinh viên!");
		
		for (Student student : students) {
			System.out.println(student);
		}
	}

	public boolean isLogin() {
		if (!isLogin)
			System.out.println("Lệnh không hợp lệ!");
		return isLogin;
	}

	public static void main(String[] args) throws NotBoundException, IOException {
		Client client = new Client("127.0.0.1", 1305);
		client.run();
	}
}
