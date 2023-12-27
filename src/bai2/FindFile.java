package bai2;

import java.io.File;

public class FindFile {
    public static boolean findFirst(String path, String pattern) {
        File file = new File(path);
        if (!file.exists()) return false;

        if (file.getAbsolutePath().contains(pattern)) return true;
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) findFirst(f.getAbsolutePath(), pattern);
        }

        return false;
    }


}
