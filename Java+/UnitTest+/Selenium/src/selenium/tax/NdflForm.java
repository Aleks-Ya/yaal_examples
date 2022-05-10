package selenium.tax;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class NdflForm {
    private final WebElement form;
    private final WebDriver driver;

    public NdflForm(WebElement form, WebDriver driver) {
        this.form = form;
        this.driver = driver;
    }

    public List<IncomeSourceDiv> getIncomeSources() {
        return new ArrayList();
    }

    public void addNewIncomeSource() {

    }
}
