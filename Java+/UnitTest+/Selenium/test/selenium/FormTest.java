package selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.assertj.core.api.Assertions.assertThat;

class FormTest {
    @Test
    void fillForm() {
        try (var driver = new WebDriverAutoClosable()) {
            driver.get("http://httpbin.org/forms/post");
            var form = driver.findElement(By.tagName("form"));
            form.findElement(By.name("custname")).sendKeys("John");
            form.submit();
            var pageSource = driver.getPageSource();
            assertThat(pageSource).contains("John");
        }
    }

}
