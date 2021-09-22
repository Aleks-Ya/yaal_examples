package io.file.delete.delete_on_exit;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Использование метода File#deleteOnExit() для удаления временных тестовых папок.
 */
class DeleteOnExit {

    /**
     * Файл, созданный File#createTempFile().
     */
    @Test
    void file1() throws IOException {
        var file = File.createTempFile("DeleteOnExit_FILE_1_", ".tmp");
        file.deleteOnExit();
        System.out.printf("%s exists=%s %n", file.getAbsolutePath(), file.exists());
    }

    /**
     * Файл, созданный Files#createTempFile().
     */
    @Test
    void file2() throws IOException {
        var file = Files.createTempFile("DeleteOnExit_FILE_2_", ".tmp").toFile();
        file.deleteOnExit();
        System.out.printf("%s exists=%s %n", file.getAbsolutePath(), file.exists());
    }

    /**
     * Пустая папка удаляется без проблем.
     */
    @Test
    void emptyDir() throws IOException {
        var dir = Files.createTempDirectory("DeleteOnExit_EMPTY_DIR_").toFile();
        dir.deleteOnExit();
        System.out.printf("%s exists=%s %n", dir.getAbsolutePath(), dir.exists());
    }

    /**
     * Чтобы удалилась папка, все вложенные файлы должны быть помечены к удалению.
     */
    @Test
    void dirWithFile() throws IOException {
        var dir = Files.createTempDirectory("DeleteOnExit_NOT_EMPTY_DIR_").toFile();
        dir.deleteOnExit();
        var nestedFile = new File(dir, "nested_file.txt");
        nestedFile.deleteOnExit();
        var nestedCreated = nestedFile.createNewFile();
        System.out.printf("%s exists=%s, nested created=%s %n", dir.getAbsolutePath(), dir.exists(), nestedCreated);
    }
}