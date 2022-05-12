package selenium.tax;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class IncomeSourceDiv {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final WebElement element;

    public IncomeSourceDiv(WebElement div) {
        this.element = div;
    }

    public BigDecimal getId() {
        var text = element.findElement(By.xpath(".//span[starts-with(@class, 'IncomeSources_spoilerSum')]")).getText();
        text = text.replace(", ", "");
        return new BigDecimal(text);
    }

    /**
     * Наименование.
     */
    public void setName(String name) {
        var input = findNameElement();
        input.clear();
        input.sendKeys(name);
    }

    /**
     * Код дохода.
     */
    public void setIncomeCode() {
        chooseComboBox("incomeTypeCode", "1010");
    }

    /**
     * Предоставить налоговый вычет.
     */
    public void setProvideTaxDeduction() {
        chooseComboBox("taxDeductionCode", "Не");
    }

    /**
     * Сумма дохода в валюте.
     */
    public void setIncomeAmountCurrency(String sumInCurrency) {
        var input = element.findElement(By.xpath(".//input[contains(@id, 'incomeAmountCurrency')]"));
        input.clear();
        input.sendKeys(sumInCurrency);
    }

    /**
     * Сумма налога в иностранной валюте.
     */
    public void setPaymentAmountCurrency(String amount) {
        var input = element.findElement(By.xpath(".//input[contains(@id, 'paymentAmountCurrency')]"));
        input.clear();
        input.sendKeys(amount);
    }

    /**
     * Дата получения дохода.
     */
    public void setIncomeDate(LocalDate incomeDate) {
        var input = element.findElements(By.xpath(".//input[contains(@class, 'DateField_dateField')]")).get(0);
        input.clear();
        var formatted = formatter.format(incomeDate);
        input.sendKeys(formatted);
    }

    /**
     * Дата уплаты налога.
     */
    public void setTaxPayDate(LocalDate taxPayDate) {
        var input = element.findElements(By.xpath(".//input[contains(@class, 'DateField_dateField')]")).get(1);
        input.clear();
        var formatted = formatter.format(taxPayDate);
        input.sendKeys(formatted);
    }

    /**
     * Определить курс автоматически.
     */
    public void setGetCurrencyRateOnline() {
        var input = element.findElement(By.xpath(".//input[@name='getCurrencyOnline']"));
        if (!input.isSelected()) {
            element.findElement(By.xpath(".//label[@class='form-checkbox']")).click();
        }
    }

    /**
     * Страна источника выплаты.
     */
    public void setIncomeCountryName(String isin) {
        if (isin.startsWith("IE")) {
            chooseComboBox("oksmIst", "372"); //Ireland
        } else if (isin.startsWith("US") || isin.startsWith("AN")) {
            chooseComboBox("oksmIst", "840"); //USA
        } else {
            throw new IllegalArgumentException("Unsupported ISIN: " + isin);
        }
    }

    /**
     * Наименование валюты.
     */
    public void setIncomeCurrencyCode() {
        chooseComboBox("currencyCode", "840");
    }

    public void expandElement() {
        if (!findNameElement().isDisplayed()) {
            element.click();
        }
    }

    private WebElement findNameElement() {
        return element.findElement(By.xpath(".//input[contains(@id, 'incomeSourceName')]"));
    }

    private void chooseComboBox(String id, String value) {
        var oksmIstElement = element.findElement(By.xpath(".//div[contains(@id,'" + id + "')]"));

        var list = oksmIstElement.findElement(By.xpath(".//span[@class='Select-arrow-zone']"));
        list.click();

        var input = oksmIstElement.findElement(By.xpath(".//div[@class='Select-input']/input"));
        input.clear();
        input.sendKeys(value);

        var select = oksmIstElement.findElement(By.xpath(".//div[@class='Select-menu-outer']"));
        select.click();
    }

    @Override
    public String toString() {
        return String.format("%s-%s", getClass().getSimpleName(), getId());
    }
}
