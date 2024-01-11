package bai22.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
	public Server(int port) throws RemoteException {
		Registry server = LocateRegistry.createRegistry(port);
		IUserService userService = new UserDAO();
		IFileService fileService = new FileRemote();
		IStudentService studentService = new StudentDAO();
		server.rebind("USER_SERVICE", userService);
		server.rebind("FILE_SERVICE", fileService);
		server.rebind("STUDENT_SERVICE", studentService);
	}
	
	public static void main(String[] args) throws RemoteException {
		Server server = new Server(1305);
	}
}
