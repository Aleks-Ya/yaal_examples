package io.file.delete.delete_on_exit;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Использование метода File#deleteOnExit() для удаления временных тестовых папок.
 */
public class DeleteOnExit {

    /**
     * Файл, созданный File#createTempFile().
     */
    @Test
    public void file1() throws IOException {
        File file = File.createTempFile("DeleteOnExit_FILE_1_", ".tmp");
        file.deleteOnExit();
        System.out.printf("%s exists=%s %n", file.getAbsolutePath(), file.exists());
    }

    /**
     * Файл, созданный Files#createTempFile().
     */
    @Test
    public void file2() throws IOException {
        File file = Files.createTempFile("DeleteOnExit_FILE_2_", ".tmp").toFile();
        file.deleteOnExit();
        System.out.printf("%s exists=%s %n", file.getAbsolutePath(), file.exists());
    }

    /**
     * Пустая папка удаляется без проблем.
     */
    @Test
    public void emptyDir() throws IOException {
        File dir = Files.createTempDirectory("DeleteOnExit_EMPTY_DIR_").toFile();
        dir.deleteOnExit();
        System.out.printf("%s exists=%s %n", dir.getAbsolutePath(), dir.exists());
    }

    /**
     * Чтобы удалилась папка, все вложенные файлы должны быть помечены к удалению.
     */
    @Test
    public void dirWithFile() throws IOException {
        File dir = Files.createTempDirectory("DeleteOnExit_NOT_EMPTY_DIR_").toFile();
        dir.deleteOnExit();
        File nestedFile = new File(dir, "nested_file.txt");
        nestedFile.deleteOnExit();
        boolean nestedCreated = nestedFile.createNewFile();
        System.out.printf("%s exists=%s, nested created=%s %n", dir.getAbsolutePath(), dir.exists(), nestedCreated);
    }
}