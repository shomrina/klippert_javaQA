import config.ServerConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OpenMainPageTest {

    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(OpenMainPageTest.class);
    private ServerConfig conf = ConfigFactory.create(ServerConfig.class);  //создание переменной, которую можно использовать для получения параметров из конфиг.пропертис

    @Test
    public void verifyAddress() {
        logger.info("Test {} started", getCurrentMethodName());
        String expectedResult = "125167, г. Москва, Нарышкинская аллея., д. 5, стр. 2, тел. +7 499 938-92-02";
        openPage(conf.mainUrl());                                                                                                           //openMainPage
        driver.findElement(By.cssSelector("[title='Контакты']")).click();                                                                   //goto Contact page
        logger.info("Осуществлен переход на вкладку Контакты");
        Assert.assertEquals(expectedResult, driver.findElement(By.cssSelector("div.styles__Block-c0qfa0-1:nth-child(3) > div:nth-child(2)")).getText());
        logger.info("Проверка адреса успешно завершена");
    }

    @Test
    public void verifyTitleMainPage() {
        logger.info("Test {} started", getCurrentMethodName());
        String expectedTitle = "Онлайн‑курсы для профессионалов, дистанционное обучение современным профессиям";
        driver.manage().window().maximize();
        openPage(conf.mainUrl());
        assertEquals(expectedTitle, driver.getTitle());
        logger.info("Проверка Title страницы завершена с успешным результатом");
    }

    @Test
    public void searchNumberTele2() {
        logger.info("Test {} started", getCurrentMethodName());
        String tele2Address = "https://msk.tele2.ru/shop/number";
        openPage(tele2Address);
        driver.findElement(By.name("searchNumber")).sendKeys("97");
        logger.info("Введено значение в поиск");
        assertTrue(driver.findElement(By.className("bundles-row")).isDisplayed());
        assertTrue(driver.findElement(By.className("bundles-row")).isEnabled());
        driver.findElement(By.cssSelector("div.bundles-column:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > span:nth-child(1) > span:nth-child(1)")).click();
        logger.info("Поиск завершен. Результаты появились");
    }

    @Test
    public void verifyFaq() {
        logger.info("Test {} started", getCurrentMethodName());
        String expectedResult = "Программу курса в сжатом виде можно увидеть на странице курса после блока с преподавателями. Подробную программу курса можно скачать кликнув на “Скачать подробную программу курса”";
        openPage(conf.mainUrl());
        driver.findElement(By.cssSelector("a[href='/faq/']")).click();
        logger.info("Открыта страница FAQ");
        By locatorQuestion = By.cssSelector("div.js-faq-linked-data:nth-child(2) > div:nth-child(4) > div:nth-child(1)");
        WebElement question = driver.findElement(locatorQuestion);
        question.click();
        logger.info("Выбран вопрос '{}'", question.getText());
        String actualResult = driver.findElement(By.cssSelector("div.js-faq-linked-data:nth-child(2) > div:nth-child(4) > div:nth-child(2)")).getText();
        assertEquals(expectedResult, actualResult);
        logger.info("Проверка текста завершена успешно");
    }

    @Test
    public void verifySubscriptions() {
        logger.info("Test {} started", getCurrentMethodName());
        String expectedResult = "Вы успешно подписались";
        openPage(conf.mainUrl());

        driver.findElement(By.name("email")).sendKeys("dofoye9196@hype68.com");
        driver.findElement(By.cssSelector(".footer2__subscribe-button")).click();
        logger.info("Ввели почту и нажали Подписаться");
        assertEquals(expectedResult, driver.findElement(By.cssSelector(".subscribe-modal__success")).getText());
        logger.info("Проверка текста завершена успешно");
    }

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        logger.info("Драйвер поднят");
    }

    @After
    public void shutDown() {
        if (driver != null)
            driver.quit();
        logger.info("Драйвер остановлен");
    }

    private void openPage(String url) {
        driver.get(url);
        logger.info("Открыта страница {}", url);
    }

    private String getCurrentMethodName() {
        return Thread.currentThread().getStackTrace()[1].getMethodName();
    }


}
