package bai1;

import java.io.File;
import java.io.FileFilter;

public class DeleteFile {
    public static boolean delete(String path) {
        File file = new File(path);
        if (file.isFile()) file.delete();

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) delete(f.getAbsolutePath());

            return file.delete();
        }

        return file.delete();
    }

    public static boolean deleteExtend(String path) {
        File file = new File(path);
        boolean isDelete = true;
        if (file.isFile()) isDelete = file.delete();

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                isDelete = isDelete & deleteExtend(f.getAbsolutePath());
            }
        }

        return isDelete;
    }

    public static void main(String[] args) {
//        System.out.println(delete("D:\\test1"));
        System.out.println(deleteExtend("D:\\test2"));
    }
}
