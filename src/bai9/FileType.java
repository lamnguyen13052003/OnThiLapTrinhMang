package bai9;

public class FileType {
    public static String type(String path){
        int indexDot = path.lastIndexOf(".");
        return indexDot == -1 ? "" : path.substring(indexDot + 1, path.length());
    }
}
