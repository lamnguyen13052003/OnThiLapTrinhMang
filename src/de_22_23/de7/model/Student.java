package de_22_23.de7.model;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Student {
	private final int LENGTH_NAME = 50;
	private int id, age;
	private String name;
	private double grade;
	private boolean delete = false;

	public Student() {
		super();
	}

	public Student(int id, String name, int age, double grade) {
		super();
		this.id = id;
		this.age = age;
		this.name = name;
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

	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public void writeName(RandomAccessFile rf) throws IOException {
		int index = 0;
		for (; index < LENGTH_NAME && index < name.length(); index++) {
			rf.writeChar(name.charAt(index));
		}
		for (; index < LENGTH_NAME; index++) {
			rf.writeChar(0);
		}
	}

	public void readName(RandomAccessFile rf) throws IOException {
		char character;
		name = "";
		for (int i = 0; i < LENGTH_NAME; i++) {
			character = rf.readChar();
			name += character != 0 ? character : "";
		}
	}

	public void add(RandomAccessFile rf) throws IOException {
		rf.writeInt(id);
		writeName(rf);
		rf.writeInt(age);
		rf.writeDouble(grade);
		rf.writeBoolean(delete);
	}

	public Student get(RandomAccessFile rf) throws IOException {
		this.id = rf.readInt();
		readName(rf);
		this.age = rf.readInt();
		this.grade = rf.readDouble();
		this.delete = rf.readBoolean();
		return delete ? null : this;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", age=" + age + ", grade=" + grade + "]";
	}

}
