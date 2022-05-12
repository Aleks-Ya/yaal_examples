package selenium.wait;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import selenium.WebDriverAutoClosable;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static util.ResourceUtil.resourceToUrl;

class FluentWaitTest {
    private final String url = resourceToUrl("selenium/wait/wait.html").toString();

    @Test
    void webDriverWait() {
        try (var driver = new WebDriverAutoClosable()) {
            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                    .withTimeout(Duration.ofSeconds(30))
                    .pollingEvery(Duration.ofSeconds(5))
                    .ignoring(NoSuchElementException.class);
            driver.get(url);
            var element = wait.until(driver1 -> driver1.findElement(By.tagName("p")));
            assertThat(element.getText()).isEqualTo("Hello from JavaScript!");
        }
    }

}
