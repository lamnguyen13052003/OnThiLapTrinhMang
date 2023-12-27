package bai10;

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
}
