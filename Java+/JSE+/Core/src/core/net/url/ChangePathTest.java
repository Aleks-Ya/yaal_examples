package core.net.url;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasToString;
import static org.junit.Assert.assertThat;

public class ChangePathTest {

    @Test
    public void replacePath() throws MalformedURLException {
        URL oldUrl = new URL("http://ya.ru/path/to/resource?a=b&c=d#frag");
        String urlStr = oldUrl.toString();

        String oldPath = oldUrl.getPath();
        String newPath = oldPath.toUpperCase();

        String newUrlStr = urlStr.replace(oldPath, newPath);
        URL newUrl = new URL(newUrlStr);

        Assert.assertThat(newUrl, Matchers.hasToString(Matchers.equalTo(newUrlStr)));
    }
}
