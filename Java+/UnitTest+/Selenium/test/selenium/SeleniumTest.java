package selenium;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SeleniumTest {
    @Test
    void getTitle() {
        try (var driver = new WebDriverAutoClosable()) {
            driver.get("https://selenium.dev");
            var title = driver.getTitle();
            assertThat(title).isEqualTo("Selenium");
        }
    }

}
