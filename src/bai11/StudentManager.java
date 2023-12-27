package bai11;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StudentManager {
    public static void save(List<Student> students, String dest) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(dest, "rw");
        raf.writeInt(students.size()); //Số lượng sinh viên
        Student st = null;

        /*tạo header
        * tạo header với cho mỗi tinh viên gồm index sinh viên trong file và byte bắt đầu
        * lấy thoong tin sinh viên đó trong file*/
        for (int i = 0; i < students.size(); i++) {
            raf.writeInt(i); /*index*/
            raf.writeInt(i + 1); /*Cái byte để seek  đến thông tin sv trong file*/
        }
        /*xong header*/

        /*Ghi thông tin sv*/
        for (int i = 0; i < students.size(); i++) {
            raf.seek(4 + 8 * i + 4); /*Quay lại*/
            raf.writeInt((int) raf.length()); /*Ghi lại vị trí bắt đầu ghi thông tin sv*/
            raf.seek(raf.length()); /*Qua lại cuối file để ghi thông tin*/
            st = students.get(i);
            raf.writeInt(st.getId());
            raf.writeUTF(st.getName());
            raf.writeInt(st.getAge());
            raf.writeUTF(st.getAddress());
            raf.writeDouble(st.getGrade());
            raf.writeInt((int) raf.length());
        }
        raf.close();
    }

    public static void saveExtend(List<Student> students, String dest, int lengthString) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(dest, "rw");
        raf.writeInt(students.size()); /*SLSV*/
        raf.writeInt(lengthString); /*Độ dài các String*/
        /*file size = 8 byte*/

        for (Student student : students) {
            student.write(raf, lengthString);
        }

        raf.close();
    }

    public static Student readStudentByIndex(String path, int index) throws IOException {
        File file = new File(path);
        if (!file.exists()) return null;
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        int size = raf.readInt(); /*SLSV*/
        if (index >= size) return null; /*So danh index co nam trong danh sach hk*/

        Student st = new Student();
        raf.seek(4 + 8 * index + 4); /*Nhay den vi tri danh dau cua sv index*/
        raf.seek(raf.readInt()); /*raf.readInt() đọc byte đánh dấu vị trí đọc thông tin viên viên*/
        st.setId(raf.readInt());
        st.setName(raf.readUTF());
        st.setAge(raf.readInt());
        st.setAddress(raf.readUTF());
        st.setGrade(raf.readDouble());
        return st;
    }

    public static Student readStudentByIndexExtend(String path, int index) throws IOException {
        File file = new File(path);
        if (!file.exists()) return null;
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        int size = raf.readInt();
        int lengthString = raf.readInt();
        if (index >= size) return null;
        raf.seek(8 + (4 * 2 + lengthString * 4 + 8) * index);
        return new Student().read(raf, lengthString);
    }

    public static boolean editStudentByIndex(String path, int index, Student st) throws IOException {
        File file = new File(path);
        if (!file.exists()) return false;
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        int size = raf.readInt();
        int lengthString = raf.readInt();
        if (index >= size) return false;

        raf.seek(8 + (4 * 2 + 8 + lengthString * 2 * 2) * index);
        st.write(raf, lengthString);
        return true;
    }

    public static void main(String[] args) throws IOException {
        String destExtend = "D:\\Tai_lieu_hoc_tap\\LapTrinhMang\\Code\\OnThi\\file\\raf-student-manager-extend.txt";
        List<Student> students = new ArrayList<>();
        Random random = new Random(System.currentTimeMillis());
        Student st = null;
        for (int i = 0; i < 10; i++) {
            st = new Student(random.nextInt(1000), "Nguyễn Đình " + (char) (65 + i), random.nextInt(0, 25), "Địa chỉ " + i, random.nextDouble() * 10);
            students.add(st);
            System.out.println(st);
        }
        saveExtend(students, destExtend, 50);
        System.out.println("Read file");
        for (int i = 0; i <= 10; i++) {
            System.out.println(readStudentByIndexExtend(destExtend, i));
        }

        st = new Student(21130416, "Nguyễn Đình Lam", 20, "Bình Đức, Phan Hiện, Bắc Bình, Bình Thuận", 8.5);
        editStudentByIndex(destExtend, 3, st);
        System.out.println("after edit file");
        for (int i = 0; i <= 10; i++) {
            System.out.println(readStudentByIndexExtend(destExtend, i));
        }
    }
}
