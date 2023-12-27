package bai11;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Student {
    private int id, age;
    private String name, address;
    private double grade;

    public Student() {
    }

    public Student(int id, String name, int age, String address, double grade) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.address = address;
        this.grade = grade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", grade=" + grade +
                '}';
    }

    public void write(RandomAccessFile raf, int lengthString) throws IOException {
        raf.writeInt(this.id); /*4 byte*/
        this.writeString(raf, this.name, lengthString); /*100 byte*/
        raf.writeInt(this.age); /*4 byte*/
        this.writeString(raf, this.address, lengthString); /*100 byte*/
        raf.writeDouble(this.getGrade()); /*8 byte*/
        /*Tổng mỗi lần dùng 216byte*/
    }

    private void writeString(RandomAccessFile raf, String string, int lengthString) throws IOException {
        int i = 0;
        for (; i < lengthString && i < string.length(); i++) {
            raf.writeChar(string.charAt(i)); /*mỗi lần write sẽ là 2 byte*/
        }

        for (; i < lengthString; i++) {
            raf.writeChar(0);
        }

        /*Tổng mỗi lần dùng thằng này sẽ bằng lengthString*2*/
    }

    public Student read(RandomAccessFile raf, int lengthString) throws IOException {
        this.setId(raf.readInt());
        this.setName(readString(raf, lengthString));
        this.setAge(raf.readInt());
        this.setAddress(readString(raf, lengthString));
        this.setGrade(raf.readDouble());
        return this;
    }

    private String readString(RandomAccessFile raf, int lengthString) throws IOException {
        String result = "";
        char character;
        for (int i = 0; i < lengthString; i++) {
            character = raf.readChar();
            result += character != 0 ? character : "";
        }

        return result;
    }
}
