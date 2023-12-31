package de23_21_22.server;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.util.StringTokenizer;

public class ThreadClient extends Thread {
    private PrintWriter out;
    private BufferedReader in;
    private RandomAccessFile file;
    private BufferedInputStream fileIn;
    private int id;

    public ThreadClient(Socket accept, RandomAccessFile file) throws IOException {
        out = new PrintWriter(new OutputStreamWriter(accept.getOutputStream(), "UTF-8"), true);
        in = new BufferedReader(new InputStreamReader(accept.getInputStream(), "UTF-8"));
        fileIn = new BufferedInputStream(accept.getInputStream());
        this.file = file;
    }

    @Override
    public void run() {
        try {
            out.println("Welcome to my server");
            while (true) {
                if (!action(in.readLine())) break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean action(String command) throws IOException {
        if (command == null || command.toLowerCase().startsWith("quit")) return false;
        StringTokenizer tk = new StringTokenizer(command, "\t");
        String cm = tk.nextToken().toLowerCase();
        String message = "Thành công!";
        switch (cm) {
            case "register" -> {
                if (tk.countTokens() != 3) {
                    message = "Thiếu tham số!";
                    break;
                }
                String name = tk.nextToken(), birthDay = tk.nextToken(), address = tk.nextToken();
                message = checkBirthDay(birthDay);
                if (!message.equals("1")) break;
                id = writeId();
                writeFullName(name);
                Server.writeString(file, birthDay, 10);
                writeAddress(id, address);
                file.write(new byte[1024*100]);
                message = "Mã số của thí sinh là: " + id;
            }
            case "view" -> {
                message = showInfo(tk.nextToken());
            }
            case "foto" -> {
                message = writeFile();
            }
            case "update" -> {
                if (tk.countTokens() != 2) {
                    message = "Lỗi tham số!";
                    break;
                }
                message = writeAddress(Integer.parseInt(tk.nextToken()), tk.nextToken());
            }
            default -> {
                message = "Lỗi cú pháp!";
            }
        }


        out.println(message);
        return true;
    }

    private int writeId() throws IOException {
        file.seek(0);
        if(file.length() < 4) {
            file.writeInt(0);
        }
        file.seek(0);
        int id = file.readInt();
        file.seek(file.length());
        file.writeInt(id);
        return id;
    }

    private String showInfo(String value) {
        try {
            int pos = 4 + (124 + 1024*100) * Integer.parseInt(value);
            file.seek(pos);
            int id = file.readInt();
            String name = Server.readString(file, 25),
                    birthDay = Server.readString(file, 10),
                    address = Server.readString(file, 25);
            return id + "\t" + name + "\t" + birthDay + "\t" + address;
        } catch (IOException e) {
            return "Thí sinh không tồn tại!";
        } catch (NumberFormatException nfe) {
            return "Lỗi tham số!";
        }
    }

    private int getClientId() throws IOException {
        file.seek(0);
        if (file.length() < 4) file.writeInt(0);
        file.seek(0);
        return file.readInt();
    }

    private void writeFullName(String value) throws IOException {
        Server.writeString(file, value, 25);
    }

    private String checkBirthDay(String value) throws IOException {
        try {
            int year = Integer.parseInt(value.substring(value.lastIndexOf("/") + 1, value.length()));
            if (LocalDate.now().getYear() - year <= 10) {
                return "1";
            }
            return "Tuổi không phù hợp!";
        } catch (NumberFormatException nfe) {
            return "Lỗi tham số!";
        }
    }

    private String writeAddress(int id, String value) {
        try {
            file.seek(4 + (124 + 1024*100) * id + 74);
            Server.writeString(file, value, 25);
            return "Thành công!";
        } catch (IOException e) {
            return "Thí sinh không tồn tại!";
        }
    }

    private String writeFile() throws IOException {
        try {
            file.seek(4+(124 + 1024*100)*id + 124);
            int fileSize = Integer.parseInt(in.readLine());
            out.println("1");
            if (fileSize > 1024 * 100) out.println("Lỗi kích thước file");
            else out.println("1");
            byte[] buffer = new byte[1024 * 100];
            int data = fileIn.read(buffer);
            while (fileSize > 0) {
                file.write(buffer, 0, data);
                fileSize -= data;
            }
        } catch (NumberFormatException nfe) {
            out.println("Lỗi tham số!");
        }

        return "Thành công!";
    }
}
