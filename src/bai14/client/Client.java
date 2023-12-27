package bai14.client;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Client {
    private Socket client;
    private BufferedInputStream in, fileIn;
    private BufferedOutputStream out;
    private BufferedReader userIn;
    private byte[] buffer;

    public Client(String host, int port) throws IOException {
        client = new Socket(host, port);
        userIn = new BufferedReader(new InputStreamReader(System.in));
        in = new BufferedInputStream(client.getInputStream());
        out = new BufferedOutputStream(client.getOutputStream());
        run();
    }

    private void run() throws IOException {
        System.out.println(read());
        String command = null;
        while (true) {
            System.out.print("Nhập lệnh của bạn.");
            command = userIn.readLine();
            action(command);
        }
    }

    private void action(String command) throws IOException {
        StringTokenizer tk = new StringTokenizer(command, " ");
        String cm = tk.nextToken();
        switch (cm){
            default -> {
                write(command);
            }
        }
    }

    private void write(String value) throws IOException {
        out.write(value.getBytes());
        out.flush();
    }

    private String read() throws IOException {
        int data = in.read(buffer);
        return new String(Arrays.copyOf(buffer, data));
    }
}
