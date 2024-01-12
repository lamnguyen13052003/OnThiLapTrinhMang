package de_22_23_de7.bai1;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de_22_23_de7.model.Student;

public class StudentManager {
	private RandomAccessFile rf;

	public StudentManager() throws UnsupportedEncodingException, IOException {
		this.rf = new RandomAccessFile("students_manager.txt", "rw");
		rf.writeInt(0);
	}

	private void add(Student st) throws IOException {
		rf.seek(0);
		int totalStudent = rf.readInt();
		rf.seek(0);
		rf.writeInt(totalStudent + 1);
		rf.seek(rf.length());
		st.add(rf);
	}

	private boolean delete(int stId) throws IOException {
		// id = 4, name = 100, age = 4, grade = 8, delete = 1;
		rf.seek(0);
		int totalStudent = rf.readInt();
		for (int i = 0; i < totalStudent; i++) {
			rf.seek(4 + 117 * i);
			int id = rf.readInt();
			if (stId == id) {
				rf.seek(4 + 117 * i + 116);
				rf.writeBoolean(true);
				return true;
			}
		}
		return false;
	}

	private List<Student> list() throws IOException {
		rf.seek(0);
		int totalStudent = rf.readInt();
		List<Student> result = new ArrayList<>();
		for (int i = 0; i < totalStudent; i++) {
			Student student = get(i);
			if (student != null)
				result.add(student);
		}
		return result;
	}

	private Student get(int index) throws IOException {
		rf.seek(0);
		int totalStudent = rf.readInt();
		if(index >= totalStudent) return null;
		rf.seek(4 + 117 * index);
		Student student = new Student();
		student = student.get(rf);
		return student;
	}

	private boolean update(int stId, Student newStudent) throws IOException {
		rf.seek(0);
		int totalStudent = rf.readInt();
		for (int i = 0; i < totalStudent; i++) {
			rf.seek(4 + 117 * i);
			int id = rf.readInt();
			if (stId == id) {
				rf.seek(4 + 117 * i + 116);
				if (rf.readBoolean())
					return false;
				rf.seek(4 + 117 * i);
				newStudent.add(rf);
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
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
