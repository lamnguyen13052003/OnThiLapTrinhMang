package bai7;

import com.sun.source.tree.Tree;

import java.io.*;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class FileSplitterAndJoiner {
    public static void fileSplitterByFileSize(String path, int size) throws IOException {
        File source = new File(path);
        if (!source.exists()) return;

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(source));
        int index = 1;
        String dest = getFileName(source.getAbsolutePath(), index);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dest));
        while (fileCopy(bis, bos, size)) {
            bos.close();
            index++;
            dest = getFileName(source.getAbsolutePath(), index);
            bos = new BufferedOutputStream(new FileOutputStream(dest));
        }
        bis.close();
    }

    public static void fileSplitterByQuantityFile(String path, int quantity) throws IOException {
        File source = new File(path);
        if (!source.exists()) return;

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(source));
        int index = 1;
        int size = 0, sizeFileLast = 0;
        String dest = null;
        BufferedOutputStream bos = null;
        if (source.length() % quantity == 0) {
            size = (int) source.length() / quantity;

            while (index <= quantity) {
                dest = getFileName(source.getAbsolutePath(), index);
                bos = new BufferedOutputStream(new FileOutputStream(dest));
                fileCopy(bis, bos, size);
                bos.close();
                index++;
            }
        } else {
            size = (int) source.length() / (quantity - 1);
            sizeFileLast = (int) source.length() - (quantity - 1) * size;

            while (index <= quantity - 1) {
                dest = getFileName(source.getAbsolutePath(), index);
                bos = new BufferedOutputStream(new FileOutputStream(dest));
                fileCopy(bis, bos, size);
                bos.close();
                index++;
            }

            dest = getFileName(source.getAbsolutePath(), index);
            bos = new BufferedOutputStream(new FileOutputStream(dest));
            fileCopy(bis, bos, sizeFileLast);
            bos.close();
        }

        bis.close();
    }

    public static boolean fileCopy(BufferedInputStream bis, BufferedOutputStream bos, int fileSize) throws IOException {
        byte[] buffer = new byte[1024 * 100];
        int data = 0;
        int size = 0;
        size = fileSize < buffer.length ? fileSize : buffer.length;
        while ((data = bis.read(buffer, 0, size)) != -1 && fileSize > 0) {
            bos.write(buffer, 0, data);
            fileSize -= data;
            size = fileSize < buffer.length ? fileSize : buffer.length;
        }

        return data != -1;
    }

    public static String getFileName(String fileName, int index) {
        int indexDot = fileName.lastIndexOf(".");
        indexDot = indexDot == -1 ? fileName.length() : indexDot;
        String dau = fileName.substring(0, indexDot);
        String dui = indexDot == -1 ? "" : fileName.substring(indexDot, fileName.length());
        switch (String.valueOf(index).length()) {
            case 1 -> {
                return dau + "-00" + index + dui;
            }
            case 2 -> {
                return dau + "-0" + index + dui;
            }
            case 3 -> {
                return dau + "-" + index + dui;
            }
        }
        return dau + "-" + index + dui;
    }

    public static void joinerFile(String path) throws IOException {
        int indexDot = path.lastIndexOf("."), index = 1, data = 0;
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        byte[] buffer = new byte[1024 * 100];

        String dest = indexDot == -1 ? path.substring(0, path.length() - 4) : path.substring(0, indexDot - 4) + path.substring(indexDot, path.length());
        bos = new BufferedOutputStream(new FileOutputStream(dest));
        String source = null;
        source = getFileName(dest, index);
        File sourceFile = new File(source);
        while (sourceFile.exists()) {
            bis = new BufferedInputStream(new FileInputStream(sourceFile));
            fileCopy(bis, bos, (int) sourceFile.length());
            bis.close();
            index++;
            source = getFileName(dest, index);
            sourceFile = new File(source);
        }
    }


    public static void main(String[] args) throws IOException {
        String path = "D:\\Tai_lieu_hoc_tap\\LapTrinhMang\\Code\\OnThi\\file\\Tiều-luận-môn-nhập-môn-AI.docx";
        String pathJoiner = "D:\\Tai_lieu_hoc_tap\\LapTrinhMang\\Code\\OnThi\\file\\Tiều-luận-môn-nhập-môn-AI-003.docx";
        int fileSize = 1024 * 1000;
        int quantityFile = 12;
//        fileSplitterByFileSize(path, fileSize);
//        fileSplitterByQuantityFile(path, quantityFile);

        joinerFile(pathJoiner);
    }
}
