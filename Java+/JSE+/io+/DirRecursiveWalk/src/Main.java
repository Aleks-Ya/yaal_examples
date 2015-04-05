import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        File rootDir = new File("resources");
        System.out.println(rootDir.getAbsolutePath());
        {
            List<File> files = RecursiveDirectoryList.findFiles(rootDir, ".*.xml");
            System.out.println(files);
        }
        {
            ContentDirectoryList directoryList = new ContentDirectoryList(rootDir, ".*.xml");
            directoryList.process();
            List<File> files = directoryList.getResult();
            System.out.println(files);
        }
    }
}