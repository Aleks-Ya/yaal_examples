package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.stream.Collectors;

public class NetUtil {
    private NetUtil() {
    }

    public static String urlContentToString(String urlStr) {
        try {
            return urlContentToString(new URL(urlStr));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String urlContentToString(URI uri) {
        try {
            return urlContentToString(uri.toURL());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String urlContentToString(URL url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            BufferedReader bis = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            return bis.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}
