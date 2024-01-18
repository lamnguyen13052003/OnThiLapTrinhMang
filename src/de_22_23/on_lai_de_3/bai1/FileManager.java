package de_22_23.on_lai_de_3.bai1;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileManager {
	public static void pack(String folder, String extension, String packedFile) throws IOException {
		File fol = new File(folder);
		if (!fol.exists()) return;
		File[] files = fol.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				return pathname.getName().endsWith("." + extension) && pathname.isFile();
			}
		});

		if (files == null || files.length == 0)
			return;

		RandomAccessFile raf = new RandomAccessFile(packedFile, "rw");
		long savePos = 0;
		File file;
		DataInputStream dis;
		int data = 0;
		byte[] buffer = new byte[1024 * 100];
		for (int i = 0; i < files.length; i++) {
			file = files[i];
			savePos = raf.getFilePointer();
			raf.writeLong(0);
			raf.writeLong(file.length());
			raf.writeUTF(file.getName());
			dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
			while ((data = dis.read(buffer)) != -1)
				raf.write(buffer, 0, data);
			dis.close();
			raf.seek(savePos);
			if (i == files.length - 1)
				raf.writeLong(0);
			else
				raf.writeLong(raf.length());
			raf.seek(raf.length());
		}

		raf.close();
	}

	public static void unPack(String packedFile, String extracFile, String destFile) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(packedFile, "r");
		long nextPos = 0, fileSize = 0;
		String fileName = "";
		DataOutputStream dos;
		int data = 0;
		byte[] buffer = new byte[1024 * 100];
		while (true) {
			raf.seek(nextPos);
			nextPos = raf.readLong();
			fileSize = raf.readLong();
			try {
				fileName = raf.readUTF();
			} catch (Exception e) {
				return;
			}
			if (fileName.equals(extracFile)) {
				dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(destFile)));
				while (fileSize > 0) {
					data = raf.read(buffer, 0, (int) Math.min(buffer.length, fileSize));
					dos.write(buffer, 0, data);
					fileSize -= data;
				}
				dos.close();
				break;
			}

			if (nextPos == 0)
				break;
		}

		raf.close();
	}

	public static void main(String[] args) throws IOException {
		String folder = "folder_test";
		String packedFile = "folder_test_packed_file";
		String extenstion = "docx";
		String destFile = "thông tin extra.docx";
		String extraFile = "thông tin.docx";
		pack(folder, extenstion, packedFile);
		unPack(packedFile, extraFile, destFile);
	}
}
