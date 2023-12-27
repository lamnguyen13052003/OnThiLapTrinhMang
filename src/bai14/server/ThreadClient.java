package bai14.server;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.StringTokenizer;

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
            while (true) {
                command = read();
                if(command.contains("quit")) break;
                action(command);
            }

            in.close();
            out.flush();
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
            read();
            System.out.println("Lỗi cú pháp");
        }
    }

    private void upload(String dest) throws IOException {
        write("1");
        if(read().equals("-1")) return;
        fileOut = new BufferedOutputStream(new FileOutputStream(dest));
        write("1");
        int fileSize = 0;
        try {
            fileSize = Integer.valueOf(read());
            write("1");
        }catch (NumberFormatException e){
            write("-1");
            return;
        }

        System.out.println(fileSize);
        int data = 0;
        while(fileSize > 0) {
            data = in.read(buffer);
            fileOut.write(buffer, 0, data);
            fileOut.flush();
            fileSize -= data;
        }
        fileOut.close();
        write("done");
        System.out.println(read());
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
