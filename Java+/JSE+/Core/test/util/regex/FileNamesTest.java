package util.regex;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Регулярные выражения над именами файлов.
 */
class FileNamesTest {

    /**
     * Удалить расширение от имени файла.
     */
    @Test
    void cutFileExtension() {
        var file = new File("/home/user/data.txt");
        var name = file.getName().replaceFirst("\\..*$", "");
        assertThat(name).isEqualTo("data");
    }
}
