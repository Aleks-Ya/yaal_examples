package util;

import org.htmlcleaner.Utils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UtilsTest {
    @Test
    void fullUrl() {
        assertThat(Utils.fullUrl("http://ya.ru/site/page.html", "contact.html"))
                .isEqualTo("http://ya.ru/site/contact.html");

        assertThat(Utils.fullUrl("http://ya.ru/site/info", "contact.html"))
                .isEqualTo("http://ya.ru/site/contact.html");

        assertThat(Utils.fullUrl("http://ya.ru/site/page.html", "#contact"))
                .isEqualTo("http://ya.ru/site/#contact");
    }
}
