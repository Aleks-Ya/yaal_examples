package core.io.gzip;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class UnpackGzipTest {

    @Test
    public void unpack() throws IOException {
        InputStream is = getClass().getResource("content.txt.gz").openStream();
        GZIPInputStream gzip = new GZIPInputStream(is);
        String content = new BufferedReader(new InputStreamReader(gzip)).lines().collect(Collectors.joining());
        Assert.assertThat(content, Matchers.equalTo("file_content"));
    }

    @Test
    public void packedFileName() throws IOException {
        String archiveFileName = "content.txt.gz";
        int lastDot = archiveFileName.lastIndexOf(".");
        String packedFileName = archiveFileName.substring(0, lastDot);
        Assert.assertThat(packedFileName, Matchers.equalTo("content.txt"));
    }
}