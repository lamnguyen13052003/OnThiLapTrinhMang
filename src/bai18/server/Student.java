package bai18.server;

public class Student {
    private String id, name;
    private int age;
    private double socre;

    public Student(String id, String name, int age, double socre) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.socre = socre;
    }

    public Student() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public double getSocre() {
        return socre;
    }

    public void setSocre(double socre) {
        this.socre = socre;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", socre=" + socre +
                '}';
    }
}
