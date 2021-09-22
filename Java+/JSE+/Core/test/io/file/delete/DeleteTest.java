package io.file.delete;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertFalse;

class DeleteTest {

    @Test
    void deleteNotExistsFile() {
        var f = new File("not exists");
        assertFalse(f.delete());
    }
}