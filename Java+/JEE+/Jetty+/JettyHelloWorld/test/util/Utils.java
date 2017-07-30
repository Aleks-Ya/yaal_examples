package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

/**
 * @author Aleksey Yablokov
 */
public class Utils {
    public static void assertUrlContent(String urlStr, String expectedContent) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.connect();
        BufferedReader bis = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        String content = bis.lines().collect(Collectors.joining("\n"));
        conn.disconnect();
        assertThat(content, containsString(expectedContent));
    }
}
