package bai21.server;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Student {
    private String name;
    private int id, age;
    private double score;

    public Student(int id, String name, int age, double socre) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.score = socre;
    }

    public Student(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id");
        this.name = resultSet.getString("name");
        this.age = resultSet.getInt("age");
        this.score = resultSet.getDouble("score");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", socre=" + score +
                '}';
    }
}
