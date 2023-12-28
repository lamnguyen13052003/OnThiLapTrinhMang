package bai21.server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private ServerSocket server;
    public Server(int port) throws IOException {
        server = new ServerSocket(port);
        run();
    }

    public void run() throws IOException {
        while(true){
            ThreadClient client = new ThreadClient(server.accept());
            client.start();
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(1305);
    }
}
