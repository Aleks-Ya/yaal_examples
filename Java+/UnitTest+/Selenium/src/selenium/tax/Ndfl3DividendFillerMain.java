package selenium.tax;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import selenium.WebDriverAutoClosable;

import java.io.File;
import java.time.Duration;

class Ndfl3DividendFillerMain {
    private static final Logger log = LoggerFactory.getLogger(Ndfl3DividendFillerMain.class);

    public static void main(String[] args) {
        var config = Config.load(new File(System.getProperty("user.home"), ".nalog/nalogru.properties"));
        var taxSources = TaxSourcesTsvReader.read("selenium/tax/tax_sources.tsv");
        try (var driver = new WebDriverAutoClosable()) {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            var login = new LoginPage(driver);
            login.login(config.loginUrl, config.loginUsername, config.loginPassword);
            var declarationPage = new DeclarationPage(driver);
            var ndflForm = declarationPage.getNdflForm(config.declarationUrl);
            var incomeSources = ndflForm.getIncomeSources();
            log.info("IncomeSource number: {}", incomeSources.size());
            for (TaxSource taxSource : taxSources) {
                var incomeSourceDiv = ndflForm.addNewIncomeSourceDiv();
                incomeSourceDiv.expandElement();
                ndflForm.fillIncomeSourceDiv(incomeSourceDiv, taxSource);
            }
            while (true) {
                System.out.println("Click 'ДАЛЕЕ' button to save the result");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
