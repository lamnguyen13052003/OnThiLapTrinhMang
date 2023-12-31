package de23_21_22.client;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class Client {
    private PrintWriter out;
    private BufferedReader in, userIn;
    private BufferedInputStream fileIn;
    private BufferedOutputStream fileOut;
    private int id;

    public Client() throws IOException {
        Socket client = new Socket("127.0.0.1", 2000);
        out = new PrintWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"), true);
        in = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
        userIn = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        run();
    }

    private void run() throws IOException {
        System.out.println(in.readLine());
        while(true) {
            System.out.print("Nhập lệnh của bạn: ");
            if(!action(userIn.readLine())) break;
        }
        in.close();
        out.close();
        userIn.close();
    }

    private boolean action(String command) throws IOException {
        if (command.toLowerCase().startsWith("quit")) {
            out.println(command);
            return false;
        }
        StringTokenizer tk = new StringTokenizer(command, " ");
        String cm = tk.nextToken();
        if(cm.equals("upload")) upload(tk.nextToken());
        else out.println(command);
        String message = in.readLine();
        System.out.println(message);
        return true;
    }

    private void upload(String path) {
        try {
            File file = new File(path);
            fileIn = new BufferedInputStream(new FileInputStream(file));
            out.println("upload");
            out.println(file.length());
            if(in.readLine() != "1") return;
            if(in.readLine() != "1") return;
            int data = 0;
            byte[] buffer = new byte[1024 * 100];
            while ((data = fileIn.read(buffer)) != -1) {
                fileOut.write(buffer, 0, data);
                fileOut.flush();
            }
            fileIn.close();
            fileOut.close();
        } catch (FileNotFoundException e) {
            return;
        } catch (IOException e) {
            return;
        }
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
    }
}
