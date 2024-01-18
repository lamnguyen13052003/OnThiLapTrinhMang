package de_22_23.de4.bai2.server;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.StringTokenizer;

public class ThreadServer extends Thread {
    private PrintWriter out;
    private BufferedReader in;
    private Socket client;
    private boolean isLogin;
    private UserDAO userDAO;
    private StudentDAO studentDAO;
    private String username;

    public ThreadServer(Socket client) throws IOException, SQLException {
        this.client = client;
        isLogin = false;
        out = new PrintWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"), true);
        in = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
        userDAO = new UserDAO();
        studentDAO = new StudentDAO();
        out.println("Welcome to Search center...");
    }

    @Override
    public void run() {
        String line;
        try {
            while (true) {
                line = in.readLine();
                if (line == null || line.toLowerCase().equals("quit")) break;
                action(line);
            }
            out.println("Thành công!");
            out.close();
            in.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            out.println("Lỗi I/o");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            out.println("Lỗi DB");
        }
    }

    private void action(String line) throws SQLException {
        StringTokenizer tk = new StringTokenizer(line, " ");
        String command = tk.nextToken().toLowerCase();
        if (!isLogin) {
            switch (command) {
                case "ten" -> {
                    if (!checkAmountTk(tk, 1, false)) return;

                    String username = tk.nextToken();
                    if (userDAO.hasUser(username)) {
                        this.username = username;
                        out.println("Thành công!");
                    } else out.println("Tên đăng nhập không tồn tại!");
                }
                case "mat_khau" -> {
                    if (!checkAmountTk(tk, 1, false)) return;

                    if (username == null) out.println("Chưa set tên đăng nhập!");
                    String password = tk.nextToken();
                    if ((isLogin = userDAO.login(username, password))) out.println("Thành công!");
                    else out.println("Tên đăng nhập không tồn tại!");
                }
                default -> out.println("Lỗi cú pháp!");
            }
        } else {
            switch (command) {
                case "ten" -> {
                    if (!checkAmountTk(tk, 1, false)) return;

                    String name = line.substring(command.length() + 1, line.length());
                    List<Student> result = studentDAO.findByName(name);
                    if (result.isEmpty()) out.println("Không tìm thấy");
                    else sendList(result);
                }
                case "mssv" -> {
                    if (!checkAmountTk(tk, 1, false)) return;

                    try {
                        int id = Integer.parseInt(tk.nextToken());
                        List<Student> result = studentDAO.findByScore(id);
                        if (result.isEmpty()) out.println("Không tìm thấy");
                        else sendList(result);
                    } catch (NumberFormatException e) {
                        out.println("Lỗi tham số!");
                    }
                }
                case "tuoi" -> {
                    if (!checkAmountTk(tk, 1, false)) return;

                    try {
                        int age = Integer.parseInt(tk.nextToken());
                        List<Student> result = studentDAO.findByAge(age);
                        if (result.isEmpty()) out.println("Không tìm thấy");
                        else sendList(result);
                    } catch (NumberFormatException e) {
                        out.println("Lỗi tham số!");
                    }
                }
                case "diem" -> {
                    if (!checkAmountTk(tk, 1, false)) return;

                    try {
                        double score = Double.parseDouble(tk.nextToken());
                        List<Student> result = studentDAO.findByScore(score);
                        if (result.isEmpty()) out.println("Không tìm thấy");
                        else sendList(result);
                    } catch (NumberFormatException e) {
                        out.println("Lỗi tham số!");
                    }
                }
                case "sua" -> {
                    if (!checkAmountTk(tk, 2, false)) return;

                    try {
                        int id = Integer.parseInt(tk.nextToken());
                        double score = Double.parseDouble(tk.nextToken());
                        boolean checkk = studentDAO.updateScore(id, score);
                        if (!checkk) out.println("Không tìm thấy");
                        else out.println("Thành công!");
                    } catch (NumberFormatException e) {
                        out.println("Lỗi tham số!");
                    }
                }
                default -> out.println("Lỗi cú pháp!");
            }
        }
    }

    public boolean checkAmountTk(StringTokenizer tk, int amount, boolean equals) {
        if (equals) {
            if (tk.countTokens() != amount) {
                out.println("Lỗi số lượng tham số");
                return false;
            }
        } else {
            if (tk.countTokens() < amount) {
                out.println("Lỗi số lượng tham số");
                return false;
            }
        }

        return true;
    }

    private void sendList(List<Student> list) {
        for (Student student : list) out.println(student.toString());
    }
}
