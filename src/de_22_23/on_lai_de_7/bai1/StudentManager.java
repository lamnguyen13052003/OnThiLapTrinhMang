package de_22_23.on_lai_de_7.bai1;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StudentManager {
	private RandomAccessFile raf;

	public StudentManager() throws IOException {
		raf = new RandomAccessFile("student_manager", "rw");
		if (raf.length() == 0)
			raf.writeInt(0);
	}

	public void add(Student st) throws IOException {
		raf.seek(0);
		int size = raf.readInt();
		raf.seek(0);
		raf.writeInt(size + 1);
		raf.seek(raf.length());
		st.add(raf);
	}

	public boolean delete(int stId) throws IOException {
		raf.seek(0);
		int id;
		int size = raf.readInt();
		int nextPos;
		for (int i = 0; i < size; i++) {
			nextPos = 4 + (4 + 100 + 4 + 8 + 1) * i;
			raf.seek(nextPos);
			id = raf.readInt();
			if (id == stId) {
				raf.seek(4 + (4 + 100 + 4 + 8 + 1) * i + 4 + 100 + 4 + 8);
				raf.writeBoolean(true);
				return true;
			}
		}
		return false;
	}

	public List<Student> list() throws IOException {
		raf.seek(0);
		List<Student> students = new ArrayList<>();
		int size = raf.readInt();
		for (int i = 0; i < size; i++) {
			Student student = new Student(raf.readInt(), Student.readString(raf), raf.readInt(), raf.readDouble(),
					raf.readBoolean());
				if(!student.isDelete()) students.add(student);
		}
		return students;
	}

	public Student get(int index) throws IOException {
		raf.seek(0);
		List<Student> students = new ArrayList<>();
		int size = raf.readInt();
		if (index >= size)
			return null;
		raf.seek(4 + index * (4 + 100 + 4 + 8 + 1));
		Student student = new Student(raf.readInt(), Student.readString(raf), raf.readInt(), raf.readDouble(),
				raf.readBoolean());
		if (!student.isDelete())
			return student;
		else
			return null;
	}

	public boolean update(int stId, Student newSt) throws IOException {
		raf.seek(0);
		int id;
		int size = raf.readInt();
		int nextPos;
		for (int i = 0; i < size; i++) {
			nextPos = 4 + (4 + 100 + 4 + 8 + 1) * i;
			raf.seek(nextPos);
			id = raf.readInt();
			if (id == stId) {
				raf.seek(4 + (4 + 100 + 4 + 8) * i);
				boolean delete = raf.readBoolean();
				if (delete)
					return false;
				nextPos = 4 + (4 + 100 + 4 + 8 + 1) * i;
				raf.seek(nextPos);
				newSt.add(raf);
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) throws IOException {
		String[] names = { "Nguyễn Văn Anh", "Trần Thị Bình", "Lê Văn Chí", "Phạm Thị Dung", "Hoàng Văn Em",
				"Ngô Thị Phúc", "Đặng Văn Giang", "Bùi Thị Hoa", "Đỗ Văn Ích", "Hồ Thị Kim", "Vũ Văn Lâm",
				"Đinh Thị Mai", "Lý Văn Ngọc", "Mai Thị Oanh", "Trương Văn Phong", "Hoàng Thị Quỳnh", "Võ Văn Rồng",
				"Trần Thị Sương", "Nguyễn Văn Tài", "Lê Thị Uyên" };
		StudentManager studentManager = new StudentManager();
		Random random = new Random(System.currentTimeMillis());
		int index = 0;
		while (index < 100) {
			String name = names[random.nextInt(0, 20)];
			int age = random.nextInt(18, 25);
			double score = ((int) (random.nextDouble(0, 10) * 100)) / 100.0;

			Student student = new Student(index++, name, age, score);
			studentManager.add(student);
		}
		Student lam = new Student(21130416, "Nguyen Dinh Lam", 20, 8.0);
		System.out.println("delete student id 15");
		System.out.println(studentManager.delete(15));
		System.out.println("update student id 30");
		System.out.println(studentManager.update(30, lam));
		System.out.println("get student index 13");
		System.out.println(studentManager.get(13));
		System.out.println("----------------------------");
		for (Student student : studentManager.list()) {
			System.out.println(student);
		}
	}
}
