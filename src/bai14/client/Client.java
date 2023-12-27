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
        buffer = new byte[1024 * 100];
        run();
    }

    private void run() throws IOException {
        System.out.println(read());
        String command = null;
        while (true) {
            System.out.print("Nhập lệnh của bạn: ");
            command = userIn.readLine();
            action(command);
            if(command.contains("quit")) break;
        }

        in.close();
        out.close();
        userIn.close();
        client.close();
    }

    private void action(String command) throws IOException {
        try {
            StringTokenizer tk = new StringTokenizer(command, " ");
            String cm = tk.nextToken();
            switch (cm) {
                case "upload" -> {
                    String source = tk.nextToken();
                    write(cm + " " + tk.nextToken());
                    read();
                    upload(source);
                }
                default -> {
                    write(command);
                }
            }
        } catch (Exception e) {
            read();
            System.out.println("Lỗi cú pháp");
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

    private void upload(String source) throws IOException {
        File file = new File(source);
        if (!file.exists()) {
            write("-1");
            return;
        }
        write("1");
        read();
        write(String.valueOf(file.length()));
        if (read().equals("-1")) return;
        int data = 0;
        fileIn = new BufferedInputStream(new FileInputStream(file));
        while ((data = fileIn.read(buffer)) != -1) {
            out.write(buffer, 0, data);
            out.flush();
        }
        fileIn.close();

        System.out.println(read());
        write("done");
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", 1305);
    }
}
