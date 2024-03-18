package io;

import com.google.common.io.Files;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ParseFileNameTest {
    private static final String FULL_NAME = "/tmp/data.txt";

    @Test
    void getFileExtension() {
        assertThat(Files.getFileExtension(FULL_NAME)).isEqualTo("txt");
    }

    @Test
    void getNameWithoutExtension() {
        assertThat(Files.getNameWithoutExtension(FULL_NAME)).isEqualTo("data");
    }
}