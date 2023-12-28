package bai18.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;

public class Server {
    private ServerSocket server;
    private Map<String, String> users;
    private List<Student> students;

    public Server(int port) throws IOException {
        this.server = new ServerSocket(port);
        init();
        run();
    }

    private void init() {
        users = new HashMap<>();
        users.put("user1", "123");
        users.put("user2", "abc");
        students = new ArrayList<Student>();
        String[] names = {
                "Nguyễn Văn Anh",
                "Trần Thị Bình",
                "Lê Văn Chí",
                "Phạm Thị Dung",
                "Hoàng Văn Em",
                "Ngô Thị Phúc",
                "Đặng Văn Giang",
                "Bùi Thị Hoa",
                "Đỗ Văn Ích",
                "Hồ Thị Kim",
                "Vũ Văn Lâm",
                "Đinh Thị Mai",
                "Lý Văn Ngọc",
                "Mai Thị Oanh",
                "Trương Văn Phong",
                "Hoàng Thị Quỳnh",
                "Võ Văn Rồng",
                "Trần Thị Sương",
                "Nguyễn Văn Tài",
                "Lê Thị Uyên"
        };

        Random random = new Random(System.currentTimeMillis());
        while (students.size() < 100) {
            String name = names[random.nextInt(0, 20)];
            int age = random.nextInt(18, 25);
            double score = ((int) (random.nextDouble(0, 10) * 100)) / 100.0;

            Student student = new Student(String.valueOf(random.nextInt()), name, age, score);
            System.out.println(student);
            students.add(student);
        }
    }

    private void run() throws IOException {
        while (true) {
            ThreadClient client = new ThreadClient(server.accept(), users, students);
            client.start();
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(1305);
    }
}
