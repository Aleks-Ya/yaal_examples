package selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CookiesTest {
    @Test
    void getCookies() {
        try (var driver = new WebDriverAutoClosable()) {
            driver.get("http://httpbin.org/cookies/set?freeform=abc");
            var freeform = driver.manage().getCookieNamed("freeform");
            assertThat(freeform.getValue()).isEqualTo("abc");
        }
    }

    @Test
    void serializeDeserializeCookie() throws IOException, ClassNotFoundException {
        try (var driver = new WebDriverAutoClosable()) {
            driver.get("http://httpbin.org/cookies/set?user=John&day=Monday");
            var cookies = driver.manage().getCookies();
            assertThat(cookies).hasSize(2);
            var baos = new ByteArrayOutputStream();
            var oos = new ObjectOutputStream(baos);
            oos.writeObject(cookies);
            var bytes = baos.toByteArray();
            var ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            var actCookie = (Set<Cookie>) ois.readObject();
            assertThat(actCookie).isEqualTo(cookies);
        }
    }

}
