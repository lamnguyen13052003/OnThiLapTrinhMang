package bai14.client;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Client {
    private Socket client;
    private DataInputStream in, fileIn;
    private DataOutputStream out;
    private BufferedReader userIn;
    private byte[] buffer;

    public Client(String host, int port) throws IOException {
        client = new Socket(host, port);
        userIn = new BufferedReader(new InputStreamReader(System.in));
        in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
        out = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));
        buffer = new byte[1024 * 100];
        run();
    }

    private void run() throws IOException {
        System.out.println(in.readUTF());
        String command = null;
        while (true) {
            System.out.print("Nhập lệnh của bạn: ");
            command = userIn.readLine();
            action(command);
            if (command.contains("quit")) break;
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
                    out.writeUTF(cm + " " + tk.nextToken());
                    out.flush();
                    upload(source);
                }
                default -> {
                    out.writeUTF(command);
                    out.flush();
                }
            }
        } catch (Exception e) {
        }
    }

    private void upload(String source) throws IOException {
        File file = new File(source);
        if (!file.exists()) {
            out.writeInt(-1);
            out.flush();
            return;
        }
        out.writeInt(1);
        out.flush();
        out.writeLong(file.length());
        out.flush();
        fileIn = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
        int data = 0;
        while ((data = fileIn.read(buffer)) != -1) {
            out.write(buffer, 0, data);
            out.flush();
        }
        fileIn.close();
        System.out.println(in.readUTF());
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", 1305);
    }
}
