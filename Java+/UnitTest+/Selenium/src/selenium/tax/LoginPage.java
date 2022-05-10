package selenium.tax;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    public void login(WebDriver driver, String loginUrl, String loginUsername, String loginPassword) {
        driver.get(loginUrl);
        var loginForm = driver.findElement(By.className("form"));
        var usernameField = loginForm.findElement(By.xpath("div[1]/div[2]/input"));
        var passwordField = loginForm.findElement(By.xpath("div[2]/div[2]/input[5]"));
        var buttonField = loginForm.findElement(By.tagName("button"));
        usernameField.sendKeys(loginUsername);
        passwordField.sendKeys(loginPassword);
        buttonField.click();
    }

}
