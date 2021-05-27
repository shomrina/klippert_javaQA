import config.ServerConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MainPage;

import static org.junit.Assert.assertEquals;

public class MainPageTest extends BaseTest {

    private Logger logger = LogManager.getLogger(MainPageTest.class);

    @Ignore
    @Test
    public void verifyTitleMainPage() {
        MainPage mainPage = new MainPage(driver);
        assertEquals(mainPage.getTitleMainPage(), mainPage.getTitleThisPage());
        logger.info("Проверка Title страницы завершена с успешным результатом");
    }
}
