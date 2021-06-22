package pages;

import config.ServerConfig;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage extends AbstractPage {

    public MainPage(WebDriver driver) {
        super(driver);
    }

    private Logger logger = LogManager.getLogger(MainPage.class);
    private ServerConfig conf = ConfigFactory.create(ServerConfig.class);

    private By electronicLocator = By.cssSelector("[href*='/catalog--elektronika/'] > span");

    public MainPage openMainPage() {
        driver.get(conf.mainUrl());
        logger.info("Открыта страница {}", conf.mainUrl());
        return new MainPage(driver);
    }

    public ElectronicPage openElectronicPage() {
        driver.findElement(electronicLocator).click();
        logger.info("Перешли  в раздел Электроника");
        return new ElectronicPage(driver);
    }
}
