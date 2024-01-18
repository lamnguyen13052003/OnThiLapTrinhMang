package de_22_23.de4.bai1;

import java.io.*;

public class FileManager {
    public static void pack(String pathFolder, String destFile) throws IOException {
        File srcFolder = new File(pathFolder);
        RandomAccessFile rf = new RandomAccessFile(destFile, "rw");
        File[] files = srcFolder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile();
            }
        });

        if (files == null || files.length == 0) return;

        long tick = 0;
        int index = 0;
        int data = 0;
        byte[] buffer = new byte[1024 * 100];
        DataInputStream readFile = null;
        for (File file : files) {
            tick = rf.getFilePointer();
            rf.writeLong(0);
            rf.writeUTF(file.getName());
            rf.writeLong(file.length());
            rf.seek(tick);
            if (index == files.length) rf.writeLong(-1);
            else {
                long nextPost = rf.length() + file.length();
                rf.writeLong(nextPost);
            }
            rf.seek(rf.length());
            readFile = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
            while ((data = readFile.read(buffer)) != -1) {
                rf.write(buffer, 0, data);
            }
            readFile.close();
        }
        rf.close();
        System.out.println("done...");
    }

    public static void unPack(String packFile, String extraFile, String destFile) throws IOException {
        RandomAccessFile rf = new RandomAccessFile(packFile, "r");
        DataOutputStream writeFile = null;
        long nextEntryPost = 0;
        long fileSize;
        byte[] buffer = new byte[1024 * 100];
        int data = 0;
        String fileName;
        while (true) {
            rf.seek(nextEntryPost);
            nextEntryPost = rf.readLong();
            fileName = rf.readUTF();
            fileSize = rf.readLong();
            if (fileName.equals(extraFile)) {
                writeFile = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(destFile)));
                while (fileSize > 0) {
                    data = rf.read(buffer, 0, (int) Math.min(fileSize, buffer.length));
                    writeFile.write(buffer, 0, data);
                    fileSize -= data;
                }

                writeFile.close();
                rf.close();
                System.out.println("done...");
                return;
            }

            if(nextEntryPost == -1) break;
        }

        rf.close();
        System.out.println("file not found...");
    }

    public static void main(String[] args) throws IOException {
        String destFolder = "folder_test";
        String packDest = "pack_file";
        String extraFile = "Lịch sử đảng- Nhóm 10.pptx";
        String destFile = "Lịch sử đảng- Nhóm 10 coppy de 4.pptx";
        pack(destFolder, packDest);
        unPack(packDest, extraFile, destFile);
    }
}
