package server;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okio.Buffer;
import org.junit.jupiter.api.Test;
import util.ResourceUtil;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class DownloadFileTest {

    public static File downloadFileFromUrl(URL url, File outputDir) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            var contentDispositionHeader = "Content-Disposition";
            var contentDisposition = conn.getHeaderField(contentDispositionHeader);
            if (contentDisposition == null) {
                throw new IllegalStateException("Header not found: " + contentDispositionHeader);
            }
            var p = Pattern.compile("^attachment; filename=\"(.+)\"$");
            var m = p.matcher(contentDisposition);
            if (!m.matches()) {
                throw new IllegalStateException(String.format("Cannot parse %s header: %s",
                        contentDispositionHeader, contentDisposition));
            }
            var filename = m.group(1);
            var outputFile = new File(outputDir, filename).toPath();
            Files.copy(conn.getInputStream(), outputFile);
            return outputFile.toFile();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    @Test
    void download() throws IOException {
        try (var server = new MockWebServer()) {
            var buffer = new Buffer();
            var expFilename = "HDFS_CLIENT-configs.tar.gz";
            var bytes = ResourceUtil.resourceToBytes(getClass(), expFilename);
            buffer.write(bytes);
            server.enqueue(new MockResponse()
                    .setBody(buffer)
                    .setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", expFilename))
                    .setHeader("Content-Type", "application/x-ustar;charset=utf-8")
                    .setHeader("Content-Length", String.valueOf(bytes.length))
            );

            server.start();

            var baseUrl = server.url("/v1/chat/");

            var outDir = Files.createTempDirectory(getClass().getSimpleName()).toFile();
            var outFile = downloadFileFromUrl(new URL(baseUrl.url(), "messages/"), outDir);
            assertThat(outFile).hasName(expFilename);
            var expFile = ResourceUtil.resourceToFile(getClass(), expFilename);
            assertThat(outFile).hasSameBinaryContentAs(expFile);
        }
    }
}
