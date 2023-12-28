package bai17.server;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class ThreadClient extends Thread {
    private Map<String, String> users;
    private List<Student> students;
    private BufferedReader in;
    private PrintWriter out;
    private User user;
    private String userName;

    public ThreadClient(Socket client, Map<String, String> users, List<Student> students) throws IOException {
        this.users = users;
        this.students = students;
        in = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
        out = new PrintWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"), true);
    }

    @Override
    public void run() {
        try {
            out.println("Welcome to my server...");
            String command;
            while (true) {
                command = in.readLine();
                if (command == null || command.contains("quit")) break;
                StringTokenizer tk = new StringTokenizer(command, " ");
                String cm = tk.nextToken().toLowerCase();
                String value;
                try {
                    value = tk.nextToken();
                } catch (NoSuchElementException nsee) {
                    out.println("Lỗi cú pháp");
                    continue;
                }

                if (user == null) {
                    switch (cm) {
                        case "user" -> {
                            setUser(value);
                        }
                        case "pass" -> {
                            setPassword(value);
                        }
                        default -> {
                            out.println("Lỗi cú pháp!");
                        }
                    }
                } else {
                    List<Student> result = null;
                    try {
                        switch (cm) {
                            case "fbn" -> {
                                value = command.substring(3, command.length());
                                result = findByName(value);
                            }
                            case "fba" -> {
                                result = findByAge(Integer.parseInt(value));
                            }
                            case "fbs" -> {
                                result = findByScore(Double.parseDouble(value));
                            }
                            default -> {
                                out.println("Lỗi cú pháp!");
                                continue;
                            }
                        }

                        out.println("1");
                        writeListStudent(result);
                    } catch (NumberFormatException nfe) {
                        out.println("Lỗi cú pháp!");
                    }
                }
            }

            in.close();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setUser(String userName) {
        if (users.containsKey(userName)) {
            this.userName = userName;
            out.println("Thành công!");
        } else {
            out.println("User Name không tồn tại");
        }
    }

    private void setPassword(String password) {
        if (users.get(userName).equals(password)) {
            this.user = new User(userName, password);
            out.println("Đăng nhập thành công!");
        } else {
            out.println("Mật khẩu không chính xác!");
        }
    }

    private void writeListStudent(List<Student> students) {
        out.println(students.size());
        for (Student student : students) {
            out.println(student);
        }
    }

    private List<Student> findByName(String name) {
        List<Student> result = new ArrayList<>();
        for (Student student : this.students) {
            if (student.getName().endsWith(name)) result.add(student);
        }
        return result;
    }

    private List<Student> findByAge(int age) {
        List<Student> result = new ArrayList<>();
        for (Student student : this.students) {
            if (student.getAge() == age) result.add(student);
        }
        return result;
    }

    private List<Student> findByScore(double score) {
        List<Student> result = new ArrayList<>();
        for (Student student : this.students) {
            if (Double.compare(student.getSocre(), score) == 0) result.add(student);
        }
        return result;
    }
}
