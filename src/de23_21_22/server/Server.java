package de23_21_22.server;

import java.io.*;
import java.net.ServerSocket;

public class Server {
    private ServerSocket server;
    private RandomAccessFile file;

    public Server() throws IOException {
        this.server = new ServerSocket(2000);
        file = new RandomAccessFile("thisinh.dat", "rw");
        run();
    }

    private void run() throws IOException {
        while (true) {
            ThreadClient client = new ThreadClient(server.accept(), file);
            client.start();
        }
    }

    public static void writeString(RandomAccessFile out, String st, int size) throws IOException {
        int i = 0;
        for (; i < st.length() && i < size; i++) {
            out.writeChar(st.charAt(i));
        }

        for (; i < size; i++) {
            out.writeChar(0);
        }
    }

    public static String readString(RandomAccessFile file, int size) throws IOException {
        String result = "";
        char c;
        for (int i = 0; i < size; i++) {
            c = file.readChar();
            result += c != 0 ? c : "";
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
    }
}
