package util.regex;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Регулярные выражения над именами файлов.
 */
public class FileNamesTest {

    /**
     * Удалить расширение от имени файла.
     */
    @Test
    public void cutFileExtension() {
        var file = new File("/home/user/data.txt");
        var name = file.getName().replaceFirst("\\..*$", "");
        assertThat(name, equalTo("data"));
    }
}
