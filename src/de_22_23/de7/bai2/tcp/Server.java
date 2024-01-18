package de_22_23.de7.bai2.tcp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.sql.SQLException;

public class Server {
	public static void main(String[] args) throws UnsupportedEncodingException, ClassNotFoundException, IOException, SQLException {
		ServerSocket server = new ServerSocket(1305);
		ProductDAO productDAO = new ProductDAO();
		while(true) {
			ThreadClient client = new ThreadClient(server.accept(), productDAO);
			client.run();
		}
	}
}
