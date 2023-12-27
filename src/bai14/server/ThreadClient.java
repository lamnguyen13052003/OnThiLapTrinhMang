package bai14.server;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ThreadClient extends Thread {
    private Socket client;
    private DataInputStream in;
    private DataOutputStream out, fileOut;
    private byte[] buffer;

    public ThreadClient(Socket socket) throws IOException {
        this.client = socket;
        in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        buffer = new byte[1024 * 100];
    }

    @Override
    public void run() {
        String command = null;
        try {
            out.writeUTF("welcome to my server...");
            out.flush();
            while (true) {
                command = in.readUTF();
                if (command.contains("quit")) break;
                action(command);
            }

            in.close();
            out.close();
            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void action(String command) throws IOException {
        try {
            StringTokenizer tk = new StringTokenizer(command, " ");
            String cm = tk.nextToken();
            switch (cm) {
                case "upload" -> {
                    upload(tk.nextToken());
                }
                default -> {
                    System.out.println(command);
                }
            }
        } catch (Exception e) {
        }
    }

    private void upload(String dest) throws IOException {
        if (in.readInt() == -1) return;
        Long fileSize = in.readLong();
        int data = 0;
        fileOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dest)));
        while (fileSize > 0) {
            data = in.read(buffer);
            fileOut.write(buffer, 0, data);
            fileOut.flush();
            fileSize -= data;
        }
        fileOut.close();
        out.writeUTF("Done...");
        out.flush();
    }
}
