package bai3;

import java.io.File;

public class DirTree {

    public static void displayDirTree(String path) {
        File file = new File(path);
        if (!file.exists()) return;

        if (file.isDirectory()) displayDirTreeHelper(path, 0);
        else System.out.println(displayFile(file, 0));;
    }

    public static void displayDirTreeHelper(String path, int level) {
        File file = new File(path);
        if (!file.exists()) return;

        if (!file.isDirectory()) System.out.println(displayFile(file, level));
        if (file.isDirectory()) {
            System.out.println(displayDir(file, level));
            File[] files = file.listFiles();
            for (File f : files) displayDirTreeHelper(f.getAbsolutePath(), level + 1);
        }
    }

    public static String displayFile(File file, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append("   |");
        }
        sb.append("+ ");
        sb.append(file.getName());
        return sb.toString();
    }

    public static String displayDir(File file, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append("   |");
        }
        sb.append("- ");
        sb.append(file.getName());
        return sb.toString();
    }

    public static void main(String[] args) {
        displayDirTree("C:\\Users\\Tu\\OneDrive\\Hình ảnh");
    }
}
