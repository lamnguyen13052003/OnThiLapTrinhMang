package bai18.client;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class Client {
    private BufferedReader in, userIn;
    private PrintWriter out;

    public Client(String host, int port) throws IOException {
        Socket client = new Socket(host, port);
        userIn = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        in = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
        out = new PrintWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"), true);
        run();
    }

    private void run() throws IOException {
        System.out.println(in.readLine());
        String command;
        while (true) {
            System.out.print("Nhập lệnh của bạn: ");
            command = userIn.readLine();
            out.println(command);
            if (command.contains("quit")) break;
            action(command);
        }

        in.close();
        out.close();
        userIn.close();
    }

    private void action(String command) throws IOException {
        StringTokenizer tk = new StringTokenizer(command, " ");
        String cm = tk.nextToken();
        if (cm.equals("fbn") || cm.equals("fba") || cm.equals("fbs")) {
            readListStudent();
        } else {
            System.out.println(in.readLine());
        }
    }

    private void readListStudent() throws IOException {
        String message = in.readLine();
        if (!message.equals("1")) {
            System.out.println(message);
            return;
        }
        int size = Integer.parseInt(in.readLine());
        for (int i = 0; i < size; i++) {
            System.out.println(in.readLine());
        }
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", 1305);
    }
}
