package bai4;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirTreeExtend {
    public static void displayDirTree(String path) {
        File file = new File(path);
        if (!file.exists()) return;

        if (file.isDirectory()) {
            List<String> displays = new ArrayList<String>();
            displayDirTreeHelper(path, 0, displays);
            for (int i = displays.size() - 1; i >= 0; i--) {
                System.out.println(displays.get(i));
            }
        } else System.out.println(displayFile(file, 0));
    }

    public static long displayDirTreeHelper(String path, int level, List<String> displays) {
        File file = new File(path);
        if (!file.exists()) return 0;

        if (!file.isDirectory()) {
            displays.add(displayFile(file, level));
            return file.length();
        }

        long size = 0;
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                size += displayDirTreeHelper(f.getAbsolutePath(), level + 1, displays);
            }

            displays.add(displayDir(file, level, size));
        }

        return size;
    }

    public static String displayFile(File file, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append("   |");
        }
        sb.append("+ ");
        sb.append(file.getName());
        sb.append(" --size: ");
        sb.append(file.length());
        sb.append(" KB");
        return sb.toString();
    }

    public static String displayDir(File file, int level, Long size) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append("   |");
        }
        sb.append("- ");
        sb.append(file.getName());
        sb.append(" --size: ");
        sb.append(size);
        sb.append(" KB");
        return sb.toString();
    }

    public static void main(String[] args) {
        displayDirTree("C:\\Users\\Tu\\OneDrive\\Hình ảnh");
    }
}
