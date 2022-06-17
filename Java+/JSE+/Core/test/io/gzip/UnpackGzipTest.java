package io.gzip;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

import static org.assertj.core.api.Assertions.assertThat;


class UnpackGzipTest {

    @Test
    void unpack() throws IOException {
        var is = Objects.requireNonNull(getClass().getResource("content.txt.gz")).openStream();
        var gzip = new GZIPInputStream(is);
        var content = new BufferedReader(new InputStreamReader(gzip)).lines().collect(Collectors.joining());
        assertThat(content).isEqualTo("file_content");
    }

    @Test
    void packedFileName() {
        var archiveFileName = "content.txt.gz";
        var lastDot = archiveFileName.lastIndexOf(".");
        var packedFileName = archiveFileName.substring(0, lastDot);
        assertThat(packedFileName).isEqualTo("content.txt");
    }
}