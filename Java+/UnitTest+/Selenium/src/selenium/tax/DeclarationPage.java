package selenium.tax;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

class DeclarationPage {
    private final WebDriver driver;

    public DeclarationPage(WebDriver driver) {
        this.driver = driver;
    }

    public NdflForm getNdflForm(String declarationUrl) {
        driver.get(declarationUrl);
        var foreignIncomesTab = driver.findElement(By.xpath("//*[@id='react-tabs-2']"));
        foreignIncomesTab.click();
        var ndflForm = driver.findElement(By.id("3NDFL"));
        return new NdflForm(ndflForm);
    }
}
