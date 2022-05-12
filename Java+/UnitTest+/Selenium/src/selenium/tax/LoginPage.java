package selenium.tax;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import selenium.WebDriverAutoClosable;

class LoginPage {
    private final WebDriverAutoClosable driver;

    public LoginPage(WebDriverAutoClosable driver) {
        this.driver = driver;
    }

    public void login(String loginUrl, String loginUsername, String loginPassword) {
        driver.get(loginUrl);
        closeNotificationPopUp();
        tryLogin(driver, loginUsername, loginPassword);
        if (!isMainPage()) {
            tryLogin(driver, loginUsername, loginPassword);
            if (!isMainPage()) {
                throw new AssertionError("Login was failed");
            }
        }
    }

    private void tryLogin(WebDriver driver, String loginUsername, String loginPassword) {
        var loginForm = driver.findElement(By.className("form"));
        var usernameField = loginForm.findElement(By.xpath("div[1]/div[2]/input"));
        var passwordField = loginForm.findElement(By.xpath("div[2]/div[2]/input[5]"));
        var buttonField = loginForm.findElement(By.tagName("button"));
        usernameField.sendKeys(loginUsername);
        passwordField.sendKeys(loginPassword);
        buttonField.click();
    }

    private boolean isLoginPage() {
        try {
            driver.findElement(By.tagName("button"));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isMainPage() {
        try {
            driver.findElement(By.className("menu__main"));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private void closeNotificationPopUp() {
        driver.findElementOpt(By.id("info")).ifPresent(element -> element.findElement(By.tagName("button")).click());
    }

}
