package bai6;

import java.io.*;

public class FolderCopy {
    public static boolean folderCopy(String sFolder, String destFolder, boolean moved) throws IOException {
        File source = new File(sFolder);
        if (!source.exists()) return false;
        File[] files = source.listFiles();
        File dest = new File(destFolder);
        if (!dest.exists()) dest.mkdirs();
        else fileCopy(sFolder, destFolder + "-coppy", moved);
        boolean result = true;
        for (File file : files) {
            if (!file.isDirectory()) result &= fileCopy(file.getAbsolutePath(), destFolder + "\\" + file.getName(), moved);
            if (file.isDirectory()) {
                result &= folderCopy(file.getAbsolutePath(), destFolder + "\\" + file.getName(), moved);
            }
        }

        if (moved) source.delete();
        return result;
    }

    public static boolean fileCopy(String sFile, String destFile, boolean moved) throws IOException {
        System.out.println(sFile);
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
        if (moved) return sourceFile.delete();
        return true;
    }

    public static void main(String[] args) throws IOException {
        String sFolder = "D:\\test";
        String destFolder = "D:\\test3";
        boolean moved = true;
        System.out.println(folderCopy(destFolder, sFolder, moved));
    }
}
