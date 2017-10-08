package core.net.url.connection.file;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import sun.net.www.protocol.file.FileURLConnection;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class FileURLConnectionTest {

    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    @Test
    public void correct() throws IOException {
        URL url = temp.newFile().toURI().toURL();
        FileURLConnection conn = (FileURLConnection) url.openConnection();
        Assert.assertThat(conn.getContentLength(), Matchers.equalTo(0));
        Map<String, List<String>> headerMap = conn.getHeaderFields();
        Assert.assertThat(headerMap, Matchers.anEmptyMap());
    }
}
