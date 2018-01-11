package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class NetUtil {
    private NetUtil() {
    }

    public static String urlContentToString(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            BufferedReader bis = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String content = bis.lines().collect(Collectors.joining("\n"));
            conn.disconnect();
            return content;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
