package selenium.tax;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

class NdflForm {
    private final WebElement form;

    public NdflForm(WebElement form) {
        this.form = form;
    }

    public List<IncomeSourceDiv> getIncomeSources() {
        var incomeSourceHeaderDivs = form
                .findElement(By.xpath(".//div[starts-with(@class, 'IncomeSources_incomeSources')]"))
                .findElements(By.xpath("./div[starts-with(@class, 'Spoiler_spoiler')]"));
        return incomeSourceHeaderDivs.stream().map(IncomeSourceDiv::new).toList();
    }

    private WebElement getAddNewIncomeButton() {
        var addButtonContainerDiv = form.findElement(By.xpath("//div[starts-with(@class, 'IncomeSources_addButtonContainer')]"));
        return addButtonContainerDiv.findElement(By.tagName("button"));
    }

    public IncomeSourceDiv addNewIncomeSourceDiv() {
        var addNewIncomeButton = getAddNewIncomeButton();
        addNewIncomeButton.click();
        var incomeSources = getIncomeSources();
        return incomeSources.get(incomeSources.size() - 1);
    }

    public void fillIncomeSourceDiv(IncomeSourceDiv incomeSourceDiv, TaxSource taxSource) {
        incomeSourceDiv.setName(taxSource.emitter());
        incomeSourceDiv.setIncomeCountryName(taxSource.ISIN());
        incomeSourceDiv.setIncomeCode();
        incomeSourceDiv.setProvideTaxDeduction();
        incomeSourceDiv.setIncomeAmountCurrency(taxSource.sumTotal().toString());
        incomeSourceDiv.setIncomeDate(taxSource.payDate());
        incomeSourceDiv.setTaxPayDate(taxSource.payDate());
        incomeSourceDiv.setIncomeCurrencyCode();
        incomeSourceDiv.setGetCurrencyRateOnline();
        incomeSourceDiv.setPaymentAmountCurrency(taxSource.sumPaid().toString());
    }
}
