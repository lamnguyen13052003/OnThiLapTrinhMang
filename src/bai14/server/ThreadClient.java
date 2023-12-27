package bai14.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class ThreadClient extends Thread {
    private Socket client;
    private BufferedInputStream in;
    private BufferedOutputStream out, fileOut;
    private byte[] buffer;

    public ThreadClient(Socket socket) throws IOException {
        this.client = socket;
        in = new BufferedInputStream(socket.getInputStream());
        out = new BufferedOutputStream(socket.getOutputStream());
        buffer = new byte[1024 * 100];
    }

    @Override
    public void run() {
        String command = null;
        try {
            write("welcome to my server...");
            while(true){
                command = read();
                action(command);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void action(String command) {
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
