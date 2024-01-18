package de_22_23.on_lai_de_3.bai2;

public class Student {
	private int mssv, tuoi;
	private String ten;
	private double diem;

	public Student(int mssv, String ten, int tuoi, double diem) {
		super();
		this.mssv = mssv;
		this.tuoi = tuoi;
		this.ten = ten;
		this.diem = diem;
	}

	public Student() {
		super();
	}

	public int getMssv() {
		return mssv;
	}

	public void setMssv(int mssv) {
		this.mssv = mssv;
	}

	public int getTuoi() {
		return tuoi;
	}

	public void setTuoi(int tuoi) {
		this.tuoi = tuoi;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public double getDiem() {
		return diem;
	}

	public void setDiem(double diem) {
		this.diem = diem;
	}

	@Override
	public String toString() {
		return "Student [mssv=" + mssv + ", tuoi=" + tuoi + ", ten=" + ten + ", diem=" + diem + "]";
	}
	
	
}
