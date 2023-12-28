package bai16;

import java.io.IOException;
import java.net.ServerSocket;

public class Calculator {
    private ServerSocket server;

    public Calculator(int port) throws IOException {
        this.server = new ServerSocket(port);
        run();
    }

    private void run() throws IOException {
        while (true) {
            ThreadClient client = new ThreadClient(server.accept());
            System.out.println("Have client connect...");
            client.start();
        }
    }

    public static void main(String[] args) throws IOException {
        Calculator calculator = new Calculator(1305);
    }
}
