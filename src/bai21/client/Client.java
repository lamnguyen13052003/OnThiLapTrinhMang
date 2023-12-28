package bai21.client;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class Client {
    private Socket client;
    private PrintWriter out;
    private BufferedReader in, userIn;

    public Client(String host, int port) throws IOException {
        client = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
        out = new PrintWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"), true);
        userIn = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        run();
    }

    public void run() {
        try {
            System.out.println(in.readLine());
            while(true){
                System.out.print("Nhập lệnh của bạn: ");
                if(!action(userIn.readLine())) break;
            }
            in.close();
            out.close();
            userIn.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean action(String s) throws IOException {
        out.println(s);
        if(s.toLowerCase().startsWith("quit")) return false;
        String message = in.readLine();
        StringTokenizer tk = new StringTokenizer(s, " ");
        String command = tk.nextToken();
        if(command.equals("fbid") || command.equals("fbn") || command.equals("fba") || command.equals("fbs")){
            if(message.equals("-1")) return true;
            readListStudent();
        }else{
            System.out.println(message);
        }
        return true;
    }

    private void readListStudent() throws IOException {
        int size = Integer.parseInt(in.readLine());
        for(int i = 0; i < size; i++){
            System.out.println(in.readLine());
        }
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", 1305);
    }
}
