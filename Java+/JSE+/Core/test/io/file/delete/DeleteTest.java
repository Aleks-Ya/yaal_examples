package io.file.delete;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

class DeleteTest {

    @Test
    void deleteNotExistsFile() {
        var f = new File("not exists");
        assertThat(f.delete()).isFalse();
    }
}