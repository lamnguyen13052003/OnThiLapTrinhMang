package bai5;

import java.io.*;

public class FileCopy {
    public static boolean fileCopy(String sFile, String destFile, boolean moved) throws IOException {
        File sourceFile = new File(sFile);
        if (!sourceFile.exists()) return false;
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sourceFile));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFile));
        byte[] buffer = new byte[1024 * 100];
        int data = 0;
        while ((data = bis.read(buffer)) != -1) {
            bos.write(buffer, 0, data);
        }

        bis.close();
        bos.close();
        if (moved) sourceFile.delete();
        return true;
    }

    public static void main(String[] args) throws IOException {
        String sFile = "D:\\Tai_lieu_hoc_tap\\LapTrinhMang\\Code\\OnThi\\file\\Tiều-luận-môn-nhập-môn-AI.docx";
        String destFile = "D:\\Tai_lieu_hoc_tap\\LapTrinhMang\\Code\\OnThi\\file\\Tiều-luận-môn-nhập-môn-AI-copy.docx";
        boolean moved = true;
        System.out.println(fileCopy(destFile, sFile, moved));
    }
}
