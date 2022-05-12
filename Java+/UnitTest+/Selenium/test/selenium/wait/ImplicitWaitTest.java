package selenium.wait;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import selenium.WebDriverAutoClosable;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static util.ResourceUtil.resourceToUrl;

class ImplicitWaitTest {
    private final String url = resourceToUrl("selenium/wait/wait.html").toString();

    @Test
    void webDriverWait() {
        try (var driver = new WebDriverAutoClosable()) {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.get(url);
            var element = driver.findElement(By.tagName("p"));
            assertThat(element.getText()).isEqualTo("Hello from JavaScript!");
        }
    }

}
