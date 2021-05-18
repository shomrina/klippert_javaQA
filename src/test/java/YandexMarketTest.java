
import config.ServerConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class YandexMarketTest {

    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(YandexMarketTest.class);
    private ServerConfig conf = ConfigFactory.create(ServerConfig.class);  //создание переменной, которую можно использовать для получения параметров из конфиг.пропертис

    By electronicLocator = By.cssSelector("[href*='/catalog--elektronika/'] > span");
    By mobileLocator = By.cssSelector("[href*='/catalog--smartfony/']");

    By modelNameLocator = By.cssSelector("[data-zone-name='title'] a");
    By compareWidgetLocator = By.cssSelector("[data-apiary-widget-id='/content/popupInformer'] > div");
    By unitNameInWidgetLocator = By.cssSelector("div > div:nth-child(2) > div:first-child");
    By resultListLocator = By.cssSelector("[data-zone-name='snippetList'] > article");
   // By productListLocator = By.cssSelector("article[data-autotest-id='product-snippet']");
    By ascOrderFilterLocator = By.cssSelector("button[data-autotest-id='dprice']");
    By makerLocator = By.cssSelector("fieldset[data-autotest-id='7893318']");

    By compareUnitListLocator = By.cssSelector("div[data-apiary-widget-id='/content/compareContent'] img");
    By overlaySearchResultLocator = By.cssSelector("div[data-tid='67d9be0a']");
    By buttonCompareInWidget = By.linkText("Сравнить");

    String compareTitle = "Сравнение товаров — Яндекс.Маркет";

    @Test
    public void addToCompare() throws InterruptedException {
       String model1 = "Samsung";
       String model2 = "Xiaomi";

        //открыть яндекс маркет
        driver.get(conf.mainUrl());
        logger.info("Открыта страница {}", conf.mainUrl());
        //Перейти Элекроника - Смартфоны
        driver.findElement(electronicLocator).click();
        logger.info("Перешли  в раздел Электроника");
        WebElement mobile =  waitVisibilityElement(mobileLocator, 10);
        mobile.click();
        logger.info("Перешли в раздел Смартфоны");

        //Отсортировать список товаров Samsung и Xiaomi
        filterByMaker(model1);
        filterByMaker(model2);
        logger.info("Отсортировано по моделям");

        //Отсортирован список товаров по цене
        sortByPriceAscending();

        //Добавить первый в списке Samsung
        List<WebElement> resultsList = driver.findElements(resultListLocator);
        logger.debug("Получен список результатов. Размер массива = {}", resultsList.size());

        addFirstToCompare(resultsList, model1);
        //Добавить первый в списке Xiaomi
        addFirstToCompare(resultsList, model2);

        //перейти к сравнению
        WebElement compareWidget = driver.findElement(compareWidgetLocator);
        WebElement compareButton = compareWidget.findElement(buttonCompareInWidget);
        compareButton.click();
        waitTitleIs(compareTitle, 10);
        logger.info("Перешли в раздел 'Сравнить'");

        //Проверить, что в списке товаров две позиции
        List<WebElement> compareUnitsList = driver.findElements(compareUnitListLocator);
        assertEquals(2, compareUnitsList.size());


    }

    private void addFirstToCompare(List<WebElement> resultsList, String model) {
        for (int i = 0; i < resultsList.size(); i++) {
            By currentCompareLocator = By.cssSelector("div[data-zone-name='snippetList'] article:nth-child(" + (i + 1) + ") div[aria-label*='сравнению']");
            WebElement phoneItem = resultsList.get(i);
            String modelName = phoneItem.findElement(modelNameLocator).getAttribute("title");
            logger.debug("{} Получено имя модели в списке: {}", i, modelName);
            if (modelName.contains(model)) {
                Actions action2 = new Actions(driver);
                action2.moveToElement(phoneItem).build().perform();      //наведение мышки на элемент списка с найденным телефоном, чтобы появилась кнопка "Добавить к сравнению"
                WebElement currentCompareButton = phoneItem.findElement(currentCompareLocator);
                waitElementToBeClickable(currentCompareButton, 10).click();

                //работа с виджетом
                WebElement compareWidget =  waitVisibilityElement(compareWidgetLocator, 10);//wait.until(ExpectedConditions.visibilityOfElementLocated(compareWidgetLocator));
                String phoneNameInPopup = compareWidget.findElement(unitNameInWidgetLocator).getAttribute("innerText");
                logger.info("Текст в плашке сравнения: {}", phoneNameInPopup);
                assertTrue(phoneNameInPopup.contains(model));
                assertTrue(phoneNameInPopup.equals("Товар " + modelName + " добавлен к сравнению"));
                break;
            }
        }
    }

    public WebElement waitVisibilityElement(By locator, int timeInSec) {
        WebDriverWait wait = new WebDriverWait(driver, timeInSec);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitElementToBeClickable(WebElement webElement, int timeInSec) {
        WebDriverWait wait = new WebDriverWait(driver, timeInSec);
        return wait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    public void waitTitleIs(String title, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.until(ExpectedConditions.titleIs(title));
    }

    public void waitInvisibilityOf(By locator, int timeInSec) {
        WebDriverWait wait = new WebDriverWait(driver, timeInSec);
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(locator)));
    }

    public void filterByMaker(String makerName) {
        WebElement makerList = waitVisibilityElement(makerLocator, 10);
        makerList.findElement(By.xpath(".//span[contains(text(), '" + makerName + "')]")).click();
        waitInvisibilityOf(overlaySearchResultLocator, 5);
    }

    //сортировка по возрастанию
    public void sortByPriceAscending() {
        driver.findElement(ascOrderFilterLocator).click();
        waitInvisibilityOf(overlaySearchResultLocator, 5);
        logger.info("Список отсортирован по цене");
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
