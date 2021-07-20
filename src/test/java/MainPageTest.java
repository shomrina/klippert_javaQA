import config.ServerConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
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


    @Test
    @Epic(value = "Otus")
    @Feature(value = "Главная страница")
    @Story(value = "Проверка тайтла главной страницы")
    @Description(value = "Тест проверяет текст в атрибуте Title для главной страницы")
    public void verifyTitleMainPage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        assertEquals(mainPage.getTitleMainPage(), mainPage.getTitleThisPage());
        logger.info("Проверка Title страницы завершена с успешным результатом");
    }
}
