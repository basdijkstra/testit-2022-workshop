package seleniumdemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RequestLoanResultPage extends BasePage {

    private final By textlabelLoanApplicationResult = By.id("loanStatus");
    private final By textlabelLoanProvider = By.id("loanProviderName");
    private final By textfieldInternalErrorHasOccurred =
            By.xpath("//p[contains(text(),'An internal error has occurred and has been logged.')]");

    public RequestLoanResultPage(WebDriver driver) {
        super(driver);
    }

    public String getLoanApplicationResult() {
        return getElementText(textlabelLoanApplicationResult);
    }

    public String getLoanProviderName() {
        return getElementText(textlabelLoanProvider);
    }

    public boolean internalErrorMessageIsVisible() {
        return isDisplayed(textfieldInternalErrorHasOccurred);
    }

}

