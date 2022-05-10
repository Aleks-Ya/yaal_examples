package selenium.tax;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class IncomeSourceListDiv {
    private final WebElement form;

    public IncomeSourceListDiv(WebElement form) {
        this.form = form;
    }

    public List<IncomeSourceDiv> getIncomeSources(WebDriver driver) {
        return new ArrayList<>();
    }

    public void addNewIncomeSource() {

    }
}
