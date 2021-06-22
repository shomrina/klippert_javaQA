package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ElectronicPage extends AbstractPage {

    public ElectronicPage(WebDriver driver) {
        super(driver);
    }

    private Logger logger = LogManager.getLogger(ElectronicPage.class);

    private By mobileLocator = By.cssSelector("[href*='/catalog--smartfony/']");

    public SmartphonesPage openSmartphonesPage() {
        WebElement mobile =  waitVisibilityElement(mobileLocator, 10);
        mobile.click();
        return new SmartphonesPage(driver);
    }
}
