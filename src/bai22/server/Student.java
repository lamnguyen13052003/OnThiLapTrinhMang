package bai22.server;

import java.io.Serializable;

public class Student implements Serializable{
	private int id, age;
	private String name;
	private double grade;

	public Student(int id, String name, int age, double grade) {
		super();
		this.id = id;
		this.age = age;
		this.name = name;
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", age=" + age + ", name=" + name + ", grade=" + grade + "]";
	}
}
