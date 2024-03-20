package io;

import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;


class FilenameUtilsTest {

    @Test
    void concat() {
        var file = FilenameUtils.concat("tmp", "data.txt");
        var expPath = Paths.get("tmp", "data.txt");
        assertThat(file).isEqualTo(expPath.toString());
    }

    @Test
    void getBaseName() {
        assertThat(FilenameUtils.getBaseName("/tmp/dir/data.txt")).isEqualTo("data");
    }
}