package static_content;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

/**
 * Jetty provides static content from disk.
 */
public class StaticContentTest {

    private static void assertUrlContent(String urlStr, String expectedContent) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.connect();
        BufferedReader bis = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        String content = bis.lines().collect(Collectors.joining("\n"));
        conn.disconnect();
        assertThat(content, containsString(expectedContent));
    }

    @Test
    public void main() throws Exception {
        ResourceHandler handler = new ResourceHandler();
        String staticContentPath = new File("static_content").getAbsolutePath();
        handler.setResourceBase(staticContentPath);
        handler.setDirectoriesListed(true);

        int port = 8080;
        Server server = new Server(port);
        server.setHandler(handler);
        server.start();

        assertUrlContent("http://localhost:" + port, "Hi, HTML!");
        assertUrlContent("http://localhost:" + port + "/nested/info.json", "{\"a\": 1}");

        server.stop();
    }
}