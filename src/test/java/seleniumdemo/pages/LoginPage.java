package seleniumdemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By textfieldUsername = By.name("username");
    private final By textfieldPassword = By.name("password");
    private final By buttonDoLogin = By.xpath("//input[@value='Log In']");

    public LoginPage(WebDriver driver) {

        super(driver);
        openUrl("http://localhost:8080/parabank");
    }

    public LoginPage loginAs(String username, String password) {
        sendKeys(textfieldUsername, username);
        sendKeys(textfieldPassword, password);
        click(buttonDoLogin);
        return this;
    }
}
