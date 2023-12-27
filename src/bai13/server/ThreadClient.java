package bai13.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ThreadClient extends Thread {
    private Socket socket;
    private BufferedInputStream bis;
    private BufferedOutputStream bos;
    private byte[] buffer;

    public ThreadClient(Socket socket) throws IOException {
        this.socket = socket;
        bis = new BufferedInputStream(socket.getInputStream());
        bos = new BufferedOutputStream(socket.getOutputStream());
        buffer = new byte[1024 * 1000];
        write("welcome to my server...");
    }

    @Override
    public void run() {
        try {
            String command = null;
            while (true) {
                command = read();
                if (command.contains("quit")) break;

                action(command);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            bis.close();
            bos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void action(String command) throws IOException {
        StringTokenizer tk = new StringTokenizer(command, " ");
        String cm = tk.nextToken();
        switch (cm) {
            case "copy" -> {
                System.out.println("copy.....");
                copy();
            }
            default -> {
                System.out.println("client: " + command);
            }
        }
    }


    private String read() throws IOException {
        int data = bis.read(buffer);
        return new String(Arrays.copyOf(buffer, data));
    }

    private void copy() throws IOException {
        String dest = read();
        write("1");
        if (read().equals("-1")) return;
        write("1");
        int fileSize = 0;
        try {
            fileSize = Integer.valueOf(read());
            System.out.println(fileSize);
            write("1");
        } catch (NumberFormatException e) {
            write("-1");
        }

        BufferedOutputStream fileOut = new BufferedOutputStream(new FileOutputStream(dest));
        int size = 0;
        while (fileSize > 0) {
            int data = bis.read(buffer);
            System.out.println("copy....." + data + " KB");
            fileOut.write(buffer);
            fileOut.flush();
            fileSize -= data;
        }
        write("done");
        fileOut.close();
        System.out.println(read());
    }

    private void write(String value) throws IOException {
        bos.write(value.getBytes());
        bos.flush();
    }
}
