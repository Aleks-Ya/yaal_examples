package io.file.delete;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class DeleteTest {

    @Test
    public void deleteNotExistsFile() throws IOException {
        File f = new File("not exists");
        assertFalse(f.delete());
    }
}