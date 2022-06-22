package net.url;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

class ChangePathTest {

    @Test
    void replacePath() throws MalformedURLException {
        var oldUrl = new URL("http://ya.ru/path/to/resource?a=b&c=d#frag");
        var urlStr = oldUrl.toString();

        var oldPath = oldUrl.getPath();
        var newPath = oldPath.toUpperCase();

        var newUrlStr = urlStr.replace(oldPath, newPath);
        var newUrl = new URL(newUrlStr);

        assertThat(newUrl).hasToString(newUrlStr);
    }
}
