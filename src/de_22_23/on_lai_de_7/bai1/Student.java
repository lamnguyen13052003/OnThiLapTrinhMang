package de_22_23.on_lai_de_7.bai1;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Student {
	private int mssv, tuoi;
	private double diem;
	private String ten;
	private boolean delete;
	
	
	
	public Student(int mssv, String ten, int tuoi, double diem, boolean delete) {
		super();
		this.mssv = mssv;
		this.tuoi = tuoi;
		this.diem = diem;
		this.ten = ten;
		this.delete = delete;
	}
	
	public Student(int mssv, String ten, int tuoi, double diem) {
		super();
		this.mssv = mssv;
		this.tuoi = tuoi;
		this.diem = diem;
		this.ten = ten;
		this.delete = false;
	}
	
	

	/**
	 * @return the delete
	 */
	public boolean isDelete() {
		return delete;
	}



	void add(RandomAccessFile raf) throws IOException {
		raf.writeInt(mssv);
		writeString(raf, ten);
		raf.writeInt(tuoi);
		raf.writeDouble(diem);
		raf.writeBoolean(false);
	}
	
	private void writeString(RandomAccessFile raf, String value) throws IOException {
		int index = 0;
		for(; index < value.length() && index < 50; index++)
			raf.writeChar(value.charAt(index));
		for(;index < 50; index++)
			raf.writeChar(0);
	}
	
	public static String readString(RandomAccessFile raf) throws IOException {
		String result = "";
		char charater;
		for(int i = 0; i < 50; i++) {
			charater = raf.readChar();
			if(charater != 0) result += charater;
		}
		return result;
	}

	@Override
	public String toString() {
		return "Student [mssv=" + mssv + ", tuoi=" + tuoi + ", diem=" + diem + ", ten=" + ten + "]";
	}
	
	
}
