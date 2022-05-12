package selenium.wait;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.WebDriverAutoClosable;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static util.ResourceUtil.resourceToUrl;

class ExplicitWaitTest {
    private final String url = resourceToUrl("selenium/wait/wait.html").toString();

    @Test
    void webDriverWait() {
        try (var driver = new WebDriverAutoClosable()) {
            driver.get(url);
            var element = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.presenceOfElementLocated(By.tagName("p")));
            assertThat(element.getText()).isEqualTo("Hello from JavaScript!");
        }
    }

}
