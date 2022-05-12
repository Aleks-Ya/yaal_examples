package selenium.wait;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import selenium.WebDriverAutoClosable;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static util.ResourceUtil.resourceToUrl;

class NoWaitTest {
    private final String url = resourceToUrl("selenium/wait/wait.html").toString();

    @Test
    void noSuchElement() {
        try (var driver = new WebDriverAutoClosable()) {
            driver.get(url);
            assertThatThrownBy(() -> driver.findElement(By.tagName("p"))).isInstanceOf(NoSuchElementException.class);
        }
    }

}
