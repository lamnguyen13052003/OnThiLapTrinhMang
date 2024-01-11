package pack_and_unpack;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Pack {
	public void action(String pathFolder, String dest) throws IOException {
		File folder = new File(pathFolder);
		if (!folder.exists() || folder.isFile())
			return;
		File[] files = folder.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isFile();
			}
		});

		if (files == null) return;

		RandomAccessFile rfOut = new RandomAccessFile(dest, "rw");
		rfOut.writeInt(files.length);
		long[]position = new long[files.length];
		int index = 0;
		for (File file : files) {
			rfOut.writeUTF(file.getName());
			position[index++] = rfOut.getFilePointer();
			rfOut.writeLong(0);
			rfOut.writeLong(file.length());
		}

		byte[] buffer = new byte[1024*100];
		int data = 0;
		index = 0;
		for (File file : files) {
			rfOut.seek(position[index++]);
			rfOut.writeLong(rfOut.length());
			rfOut.seek(rfOut.length());
			
			DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
			while((data = dis.read(buffer)) != -1) {
				rfOut.write(buffer, 0, data);
			}
			dis.close();
		}
		
		rfOut.close();
		System.out.println("done...");
	}
	
	public static void main(String[] args) throws IOException {
		String folderPack = "folder_test";
		String filePack = "file_pack.dat";
		String fileNameFound = "Lịch sử đảng- Nhóm 10.pptx";
		String fileNameFoundDest = "Lịch sử đảng- Nhóm 10_found.pptx";
		Pack pack = new Pack();
		pack.action(folderPack, filePack);
		UnPack unPack = new UnPack();
		unPack.action(filePack, fileNameFound, fileNameFoundDest);
	}
}
