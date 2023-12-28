package bai16.server;

import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class ThreadClient extends Thread {
    private BufferedReader in;
    private PrintWriter out;

    public ThreadClient(Socket client) throws IOException {
        in = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
        out = new PrintWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"), true);
    }

    @Override
    public void run() {
        try {
            out.println("Welcome to my server....");
            String command;
            while (true) {
                command = in.readLine();
                try {
                    if (command == null || command.toLowerCase().contains("quit")) break;
                    action(command);
                } catch (NoSuchElementException nsee) {
                    out.println(nsee.getMessage());
                } catch (NumberFormatException nfe) {
                    out.println(nfe.getMessage());
                } catch (ArithmeticException ae) {
                    out.println(ae.getMessage());
                }
            }

            System.out.println("Have client disconnect...");
            in.close();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void action(String command) {
        StringTokenizer tk = new StringTokenizer(command, "+-*/=", true);
        if (tk.countTokens() != 3) throw new NoSuchElementException("Lỗi cú pháp!");
        double x, y;
        String phepTinh = "";
        try {
            x = Double.parseDouble(tk.nextToken());
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException("Lỗi tham số x!");
        }

        phepTinh = tk.nextToken();

        try {
            y = Double.parseDouble(tk.nextToken());
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException("Lỗi tham số x!");
        }

        switch (phepTinh) {
            case "+" -> {
                out.println(x + " + " + y + " = " + (x + y));
            }
            case "-" -> {
                out.println(x + " - " + y + " = " + (x - y));
            }
            case "*" -> {
                out.println(x + " * " + y + " = " + (x * y));
            }
            case "/" -> {
                if (Double.compare(y, 0) == 0)
                    throw new ArithmeticException("Lỗi chi cho 0!");

                out.println(x + " / " + y + " = " + (x / y));
            }
            default -> {
                throw new NoSuchElementException("Lỗi phép tính!");
            }
        }
    }
}
