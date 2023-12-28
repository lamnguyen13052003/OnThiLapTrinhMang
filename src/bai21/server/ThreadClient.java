package bai21.server;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.StringTokenizer;

public class ThreadClient extends Thread {
    private PrintWriter out;
    private BufferedReader in;
    private User user;
    private String username;

    public ThreadClient(Socket accept) throws IOException {
        out = new PrintWriter(new OutputStreamWriter(accept.getOutputStream(), "UTF-8"), true);
        in = new BufferedReader(new InputStreamReader(accept.getInputStream(), "UTF-8"));
    }

    @Override
    public void run() {
        try {
            out.println("Welcome to my server...");
            String command;
            while (true) if (!action(in.readLine())) break;
            in.close();
            out.close();
        } catch (IOException e) {
            out.println("Server Lỗi!");
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            out.println("Server Lỗi!");
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            out.println("Server Lỗi!");
            System.out.println(e.getMessage());
        }
    }

    public boolean action(String command) throws SQLException, ClassNotFoundException {
        if (command.toLowerCase().startsWith("quit")) return false;
        StringTokenizer tk = new StringTokenizer(command, " ");
        String result = "Lỗi cú pháp!";
        String cm = tk.nextToken();
        if (user == null) {
            switch (cm) {
                case "user" -> {
                    result = checkUsername(tk.nextToken());
                }
                case "password" -> {
                    result = login(tk.nextToken());
                }
            }

            out.println(result);
        } else {
            List<Student> students = null;
            try {
                switch (cm) {
                    case "fbid" -> {
                        students = StudentDAO.getInstence().findById(Integer.parseInt(tk.nextToken()));
                    }
                    case "fbn" -> {
                        students = StudentDAO.getInstence().findByName(tk.nextToken());
                    }
                    case "fba" -> {
                        students = StudentDAO.getInstence().findByAge(Integer.parseInt(tk.nextToken()));
                    }
                    case "fbs" -> {
                        students = StudentDAO.getInstence().findByScore(Double.parseDouble(tk.nextToken()));
                    }
                }

                out.println("1");
                sendListStudent(students);
            } catch (NumberFormatException nfe) {
                out.println("-1");
            }
        }

        return true;
    }

    private String login(String password) throws SQLException, ClassNotFoundException {
        user = UserDAO.getInstence().login(username, password);
        if (user != null) {
            return "Đăng nhập thành công!";
        }

        return "Mật khẩu không chính xác!";
    }

    private String checkUsername(String username) throws SQLException, ClassNotFoundException {
        if (UserDAO.getInstence().hasUsername(username)) {
            this.username = username;
            return "Thành công!";
        }

        return "Username không tồn tại!";
    }

    private void sendListStudent(List<Student> students) {
        out.println(students.size());
        for (Student st : students) {
            out.println(st);
        }
    }
}
