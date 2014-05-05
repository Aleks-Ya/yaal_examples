import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        File rootDir = new File("resources");

        List<File> files = RecursiveDirectoryList.findFiles(rootDir, ".*.xml");

        System.out.println(rootDir.getAbsolutePath());
        System.out.println(files);
    }
}