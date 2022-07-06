package io;

import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;


class FilenameUtilsTest {

    @Test
    void concat() {
        var file = FilenameUtils.concat("tmp", "file.txt");
        var expPath = Paths.get("tmp", "file.txt");
        assertThat(file).isEqualTo(expPath.toString());
    }
}