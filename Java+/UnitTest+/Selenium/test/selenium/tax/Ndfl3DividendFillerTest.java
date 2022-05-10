package selenium.tax;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import selenium.WebDriverAutoClosable;

import java.io.File;

class Ndfl3DividendFillerTest {
    private static final Logger log = LoggerFactory.getLogger(Ndfl3DividendFillerTest.class);

    @Test
    void getTitle() {
        var file = new File(System.getProperty("user.home"), ".nalog/nalogru.properties");
        var config = Config.load(file);
        try (var driver = new WebDriverAutoClosable()) {
            var login = new LoginPage();
            login.login(driver, config.loginUrl, config.loginUsername, config.loginPassword);
            var declarationPage = new DeclarationPage(driver);
            var ndflForm = declarationPage.getNdflForm(config.declarationUrl);
            var incomeSources = ndflForm.getIncomeSources();
            log.info("IncomeSource number: {}", incomeSources.size());
            ndflForm.addNewIncomeSource();
//        assertThat(title).isEqualTo("Selenium");
        }
    }

}
