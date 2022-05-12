package selenium;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static util.ResourceUtil.resourceToUrl;

class UseLocalHtmlFileTest {
    @Test
    void getTitle() {
        try (var driver = new WebDriverAutoClosable()) {
            var url = resourceToUrl("selenium/UseLocalHtmlFileTest.html").toString();
            driver.get(url);
            var title = driver.getTitle();
            assertThat(title).isEqualTo("Local HTML Page");
        }
    }

}
