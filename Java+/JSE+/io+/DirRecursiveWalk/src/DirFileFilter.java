import java.io.File;
import java.io.FileFilter;

/**
 * Файловый фильтр пропускает только папки.
 */
public class DirFileFilter implements FileFilter {
    @Override
    public boolean accept(File pathname) {
        return pathname.isDirectory();
    }
}
