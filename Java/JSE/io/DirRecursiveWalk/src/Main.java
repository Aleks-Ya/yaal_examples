import java.io.File;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        DirFileFilter dirFileFilter = new DirFileFilter();
        RegExpFilenameFilter regExpFilenameFilter = new RegExpFilenameFilter();

        File rootDir = new File("resources/dirForWalk");
        System.out.println(rootDir.getAbsolutePath());
        System.out.println(Arrays.deepToString(rootDir.listFiles(dirFileFilter)));
        System.out.println(Arrays.deepToString(rootDir.listFiles(regExpFilenameFilter)));
    }
}