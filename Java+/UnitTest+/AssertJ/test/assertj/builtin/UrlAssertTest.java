package assertj.builtin;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

class UrlAssertTest {
    @Test
    void has() throws MalformedURLException {
        var url = URI.create("http://mail.com:4040/the/path#page2").toURL();
        assertThat(url)
                .hasHost("mail.com")
                .hasPort(4040)
                .hasPath("/the/path")
                .hasAnchor("page2")
                .hasProtocol("http");
    }

    @Test
    void hasNo() throws MalformedURLException {
        var url = URI.create("http://:").toURL();
        assertThat(url)
                .hasNoHost()
                .hasNoPort()
                .hasNoPath()
                .hasNoAnchor()
                .hasNoQuery()
                .hasNoParameters()
                .hasNoUserInfo();
    }

    @Test
    void matches() throws MalformedURLException {
        var url = URI.create("file:///home/user1/folder1/file.txt").toURL();
        assertThat(url).matches(url1 -> url1.toString().endsWith("folder1/file.txt"));
    }

    @Test
    void asString() throws MalformedURLException {
        var url = URI.create("file:///home/user1/folder1/file.txt").toURL();
        assertThat(url).asString().endsWith("folder1/file.txt");
    }

    @Test
    void hasToString() throws MalformedURLException {
        var url = URI.create("file:///home/user1/folder1/file.txt").toURL();
        assertThat(url).hasToString("file:/home/user1/folder1/file.txt");
    }

    @Test
    void hasCondition() throws MalformedURLException {
        var url = URI.create("file:///home/user1/folder1/file.txt").toURL();
        assertThat(url).has(new Condition<>() {
            @Override
            public boolean matches(URL value) {
                return value.toString().endsWith("folder1/file.txt");
            }
        });
    }
}
