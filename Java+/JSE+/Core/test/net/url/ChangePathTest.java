package net.url;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasToString;

class ChangePathTest {

    @Test
    void replacePath() throws MalformedURLException {
        var oldUrl = new URL("http://ya.ru/path/to/resource?a=b&c=d#frag");
        var urlStr = oldUrl.toString();

        var oldPath = oldUrl.getPath();
        var newPath = oldPath.toUpperCase();

        var newUrlStr = urlStr.replace(oldPath, newPath);
        var newUrl = new URL(newUrlStr);

        assertThat(newUrl, hasToString(equalTo(newUrlStr)));
    }
}
