package selenium;

import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverAutoClosable extends FirefoxDriver implements AutoCloseable {
    static {
        System.setProperty("webdriver.gecko.driver", "/home/aleks/installed/WebDriver/geckodriver");
    }

    @Override
    public void close() {
        quit();
    }
}
