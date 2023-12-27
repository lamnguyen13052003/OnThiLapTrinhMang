package bai14.server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    private ServerSocket server;

    public Server(int port) throws IOException {
        server = new ServerSocket(port);
        run();
    }

    private void run() throws IOException {
        System.out.println("Server is open...");
        while (true) {
            Socket socket = server.accept();
            ThreadClient client = new ThreadClient(socket);
            client.start();
        }
    }
}
