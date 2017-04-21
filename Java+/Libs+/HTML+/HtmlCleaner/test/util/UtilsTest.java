package util;

import org.htmlcleaner.Utils;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class UtilsTest {
    @Test
    public void fullUrl() {
        assertThat(Utils.fullUrl("http://ya.ru/site/page.html", "contact.html"),
                equalTo("http://ya.ru/site/contact.html"));

        assertThat(Utils.fullUrl("http://ya.ru/site/info", "contact.html"),
                equalTo("http://ya.ru/site/contact.html"));

        assertThat(Utils.fullUrl("http://ya.ru/site/page.html", "#contact"),
                equalTo("http://ya.ru/site/#contact"));
    }
}
