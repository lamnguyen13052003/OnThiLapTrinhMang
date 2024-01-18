package de_22_23.on_lai_de_3.bai2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class Server {
	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
		ServerSocket server = new ServerSocket(54321);
		while(true) {
			Socket client = server.accept();
			ThreadServer threadServer = new ThreadServer(client);
			threadServer.start();
		}
		
	}
}
