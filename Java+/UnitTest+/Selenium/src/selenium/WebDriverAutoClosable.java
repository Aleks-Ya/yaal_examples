package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Optional;

public class WebDriverAutoClosable extends FirefoxDriver implements AutoCloseable {
    static {
        System.setProperty("webdriver.gecko.driver", "/home/aleks/installed/WebDriver/geckodriver");
    }

    @Override
    public void close() {
        quit();
    }

    public Optional<WebElement> findElementOpt(By by) {
        var size = findElements(by).size();
        if (size == 0) {
            return Optional.empty();
        } else if (size == 1) {
            return Optional.of(findElement(by));
        } else {
            throw new IllegalStateException("Found more than 1 element: " + size);
        }
    }
}
