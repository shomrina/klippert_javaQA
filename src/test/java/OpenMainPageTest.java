import config.ServerConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertEquals;

public class OpenMainPageTest {

    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(OpenMainPageTest.class);
    private ServerConfig conf = ConfigFactory.create(ServerConfig.class);  //создание переменной, которую можно использовать для получения параметров из конфиг.пропертис

    @Test
    public void verifyTitleMainPage() {
        String expectedTitle = "Онлайн‑курсы для профессионалов, дистанционное обучение современным профессиям";
        driver.get(conf.mainUrl());
        logger.info("Открыта страница {}", conf.mainUrl());
        assertEquals(expectedTitle, driver.getTitle());
        logger.info("Проверка Title страницы завершена с успешным результатом");
    }

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        logger.info("Драйвер поднят");
    }

    @After
    public void shutDown() {
        if (driver != null)
            driver.quit();
        logger.info("Драйвер остановлен");
    }
}
