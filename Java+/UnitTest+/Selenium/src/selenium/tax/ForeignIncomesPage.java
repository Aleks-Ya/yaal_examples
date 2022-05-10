package selenium.tax;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ForeignIncomesPage {
    private final WebDriver driver;
    private final WebElement page;

    public ForeignIncomesPage(WebDriver driver, WebElement page) {
        this.driver = driver;
        this.page = page;
    }

    public NdflForm getNdflForm(String declarationUrl) {
        return new NdflForm(driver.findElement(By.id("3NDFL")), driver);
    }
}
