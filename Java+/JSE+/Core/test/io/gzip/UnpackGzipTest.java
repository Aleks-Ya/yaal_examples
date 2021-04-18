package io.gzip;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UnpackGzipTest {

    @Test
    public void unpack() throws IOException {
        InputStream is = getClass().getResource("content.txt.gz").openStream();
        GZIPInputStream gzip = new GZIPInputStream(is);
        String content = new BufferedReader(new InputStreamReader(gzip)).lines().collect(Collectors.joining());
        assertThat(content, equalTo("file_content"));
    }

    @Test
    public void packedFileName() throws IOException {
        String archiveFileName = "content.txt.gz";
        int lastDot = archiveFileName.lastIndexOf(".");
        String packedFileName = archiveFileName.substring(0, lastDot);
        assertThat(packedFileName, equalTo("content.txt"));
    }
}