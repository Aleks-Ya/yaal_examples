import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Поддерживает рекурсивный поиск файлов по папке.
 */
public class RecursiveDirectoryList {
    private static final DirFileFilter dirFileFilter = new DirFileFilter();
    private final RegExpFilenameFilter regExpFilenameFilter = new RegExpFilenameFilter();

    private RecursiveDirectoryList() {
    }

    public static List<File> findFiles(File rootDir, String pattern) {
        RecursiveDirectoryList list = new RecursiveDirectoryList();
        list.regExpFilenameFilter.setPattern(pattern);
        return list.process(rootDir);
    }

    private List<File> process(File dir) {
        List<File> result = new ArrayList<>();

        result.addAll(Arrays.asList(dir.listFiles(regExpFilenameFilter)));

        File[] subDirs = dir.listFiles(dirFileFilter);
        for (File subDir : subDirs) {
            result.addAll(process(subDir));
        }

        return result;
    }
}