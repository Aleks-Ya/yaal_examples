package core.util.regex;

import org.junit.Test;

import java.io.File;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Регулярные выражения над именами файлов.
 */
public class FileNamesTest {

    /**
     * Удалить расширение от имени файла.
     */
    @Test
    public void cutFileExtension() {
        File file = new File("/home/user/data.txt");
        String name = file.getName().replaceFirst("\\..*$", "");
        assertThat(name, equalTo("data"));
    }
}
