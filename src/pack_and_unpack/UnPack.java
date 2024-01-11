package pack_and_unpack;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class UnPack {
	public void action(String pathFilePack, String fileNameFound, String pathDest) throws IOException {
		RandomAccessFile rfIn = new RandomAccessFile(pathFilePack, "r");
		int amountFileInPack = rfIn.readInt();
		String fileName;
		long positionReadFile = 0;
		long fileSize = 0;
		for (int i = 0; i < amountFileInPack; i++) {
			fileName = rfIn.readUTF();
			if (fileName.equals(fileNameFound)) {
				positionReadFile = rfIn.readLong();
				fileSize = rfIn.readLong();
				break;
			}
			rfIn.readLong();
			rfIn.readLong();
		}

		if (positionReadFile == 0) {
			System.out.println("Don't have file...");
		} else {
			System.out.println("File is exist...");
		}

		rfIn.seek(positionReadFile);
		byte[] buffer = new byte[1024 * 100];
		int data = 0;
		DataOutputStream fileOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(pathDest)));
		while (fileSize > 0) {
			data = rfIn.read(buffer, 0, (int)Math.min(fileSize, buffer.length));
			fileSize -= data;
			fileOut.write(buffer, 0, data);
		}

		fileOut.close();
		rfIn.close();
		System.out.println("done");
	}
}
