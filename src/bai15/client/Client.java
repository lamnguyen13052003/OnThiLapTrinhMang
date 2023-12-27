package bai15.client;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class Client {
    private Socket client;
    private DataInputStream in;
    private DataOutputStream out, fileOut;
    private BufferedReader userIn;

    public Client(String host, int port) throws IOException {
        this.client = new Socket(host, port);
        in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
        out = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));
        userIn = new BufferedReader(new InputStreamReader(System.in));
        run();
    }

    private void run() throws IOException {
        System.out.println(in.readUTF());
        String command = null;
        while (true) {
            System.out.print("Mời bạn nhập lệnh: ");
            command = userIn.readLine();
            action(command);
            if(command.toLowerCase().contains("quit")) break;
        }

        userIn.close();
        client.close();
    }

    private void action(String command) {
        try {
            StringTokenizer tk = new StringTokenizer(command, " ");
            String cm = tk.nextToken();
            switch (cm) {
                case "download" -> {
                    download(tk.nextToken(), tk.nextToken());
                }
                default -> {
                    out.writeUTF(command);
                    out.flush();
                }
            }
        }catch (Exception e){

        }
    }

    private void download(String s, String dest) throws IOException {
        out.writeUTF("download " + s);
        out.flush();
        if(in.readInt() == -1) return;
        long fileSize = in.readLong();
        fileOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dest)));
        int data;
        byte[] buffer = new byte[1024*100];
        while(fileSize > 0){
            data = in.read(buffer);
            fileOut.write(buffer, 0, data);
            fileSize -= data;
        }
        out.writeUTF("Done...");
        out.flush();
        fileOut.close();
        System.out.println(in.readUTF());
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", 1305);
    }
}
