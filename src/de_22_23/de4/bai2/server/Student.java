package de_22_23.de4.bai2.server;

public class Student {
    private int id, age;
    private String name;
    private double score;

    public Student() {
    }

    public Student(int id, String name, int age, double score) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.score = score;
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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}
