package bai15.server;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class ThreadClient extends Thread{
    private DataInputStream in, fileIn;
    private DataOutputStream out;
    public ThreadClient(Socket accept) throws IOException {
        in = new DataInputStream(new BufferedInputStream(accept.getInputStream()));
        out = new DataOutputStream(new BufferedOutputStream(accept.getOutputStream()));
    }

    @Override
    public void run() {
        try {
            out.writeUTF("Welcome to my server...");
            out.flush();
            String command = null;
            while(true){
                command = in.readUTF();
                if(command.toLowerCase().contains("quit")) break;
                action(command);
            }

            in.close();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void action(String command) {
        try {
            StringTokenizer tk = new StringTokenizer(command, " ");
            String cm = tk.nextToken();
            System.out.println(command);
            switch (cm){
                case "download" -> {
                    download(tk.nextToken());
                }
            }
        }catch (Exception e){

        }

    }

    private void download(String s) throws IOException {
        File file = new File(s);
        if(!file.exists()) {
            out.writeInt(-1);
            out.flush();
            return;
        }

        out.writeInt(1);
        out.flush();
        out.writeLong(file.length());
        out.flush();
        int data;
        byte[] buffer = new byte[1024*100];
        fileIn = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
        while((data = fileIn.read(buffer)) != -1){
            out.write(buffer, 0, data);
            out.flush();
        }
        fileIn.close();
        System.out.println(in.readUTF());
        out.writeUTF("Done...");
        out.flush();
    }
}
