package bai15.server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private ServerSocket server;
    public Server(int port) throws IOException {
        server = new ServerSocket(port);
        run();
    }

    private void run() throws IOException {
        System.out.println("Server is open...");
        while(true){
            ThreadClient client = new ThreadClient(server.accept());
            client.run();
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(1305);
    }
}
