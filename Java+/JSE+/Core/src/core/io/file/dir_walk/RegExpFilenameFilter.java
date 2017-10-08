package core.io.file.dir_walk;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

/**
 * Файловый фильтр пропускает файлы (не папки), имена которых удовлетворяют регулярному выражению.
 */
class RegExpFilenameFilter implements FileFilter {
    private Pattern pattern = Pattern.compile(".*");

    public void setPattern(String filenamePattern) {
        pattern = Pattern.compile(filenamePattern);
    }

    @Override
    public boolean accept(File pathname) {
        return !pathname.isDirectory() && pattern.matcher(pathname.getName()).matches();
    }
}
