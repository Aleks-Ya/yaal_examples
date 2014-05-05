import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Возвращает папки, в которых содержится файл, удовлетворяющий регулярному выражению.
 */
public class ContentDirectoryList {
    private static final DirFileFilter dirFileFilter = new DirFileFilter();
    private final RegExpFilenameFilter regExpFilenameFilter = new RegExpFilenameFilter();
    private final File rootDir;
    private final List<File> searchResult = new ArrayList<>();
    private boolean processed = false;

    /**
     * Задаем параметры поиска.
     *
     * @param rootDir         Корневая папка.
     * @param filenamePattern Маска имени файла.
     */
    public ContentDirectoryList(File rootDir, String filenamePattern) {
        this.rootDir = rootDir;
        regExpFilenameFilter.setPattern(filenamePattern);
    }

    /**
     * Получить результат поиска.
     */
    public List<File> getResult() {
        if (processed) {
            return searchResult;
        } else {
            throw new RuntimeException("Previously process search: #process()");
        }
    }

    /**
     * Выполнить поиск.
     */
    public void process() {
        process(rootDir);
        processed = true;
    }

    private void process(File dir) {
        File[] matchedFiles = dir.listFiles(regExpFilenameFilter);
        if (matchedFiles.length > 0) {
            searchResult.add(dir);
        }

        File[] subDirs = dir.listFiles(dirFileFilter);
        for (File subDir : subDirs) {
            process(subDir);
        }
    }
}