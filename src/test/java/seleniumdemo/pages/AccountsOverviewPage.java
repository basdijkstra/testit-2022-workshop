package seleniumdemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountsOverviewPage {

    private WebDriver driver;

    public AccountsOverviewPage(WebDriver driver) {

        this.driver = driver;
    }

    public void selectMenuItem(String menuItemText) {

        this.driver.findElement(By.linkText(menuItemText)).click();
    }
}
