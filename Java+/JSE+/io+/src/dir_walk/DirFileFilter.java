package dir_walk;

import java.io.File;
import java.io.FileFilter;

/**
 * Файловый фильтр пропускает только папки.
 */
class DirFileFilter implements FileFilter {
    @Override
    public boolean accept(File pathname) {
        return pathname.isDirectory();
    }
}
