import com.sun.security.sasl.ntlm.FactoryImpl;
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

    By firstAddToCompareLocator = By.cssSelector("div[data-zone-name='snippetList'] > article:nth-child(1) div:nth-of-type(5)"); //By.cssSelector("div[aria-label='Добавить к сравнению']");
    //article._1_IxNTwqll:nth-child(1) > div:nth-child(2) > div:nth-child(2) > div:nth-child(1)
    //By.cssSelector("div[data-zone-name='snippetList'] > article:nth-child(1) div[aria-label*='сравнению']");  By.cssSelector("div[data-zone-name='snippetList'] > article:nth-child(1) div[data-tid='сравнению'] > div");
    By modelNameLocator = By.cssSelector("[data-zone-name='title'] a");
    By compareWidgetLocator = By.cssSelector("[data-apiary-widget-id='/content/popupInformer'] > div");
    By unitNameInWidgetLocator = By.cssSelector("div > div:nth-child(2) > div:first-child");
    By resultListLocator = By.cssSelector("[data-zone-name='snippetList'] > article");
    By productListLocator = By.cssSelector("article[data-autotest-id='product-snippet']");
    By ascOrderFilterLocator = By.cssSelector("button[data-autotest-id='dprice']");
    By makerLocator = By.cssSelector("fieldset[data-autotest-id='7893318']");

    By compareUnitListLocator = By.cssSelector("div[data-apiary-widget-id='/content/compareContent'] img");

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
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement mobile =  wait.until(ExpectedConditions.visibilityOfElementLocated(mobileLocator));
        mobile.click();
        logger.info("Перешли в раздел Смартфоны");

        //Отсортировать список товаров Samsung и Xiaomi
        WebElement makerList = wait.until(ExpectedConditions.visibilityOfElementLocated(makerLocator));  //makerLocator
        makerList.findElement(By.xpath(".//span[contains(text(), 'Samsung')]")).click();
        makerList.findElement(By.xpath(".//span[contains(text(), 'Xiaomi')]")).click();
        logger.info("Отсортировано по моделям");

        //Отсортирован список товаров по цене
        driver.findElement(ascOrderFilterLocator).click();
        wait.until(ExpectedConditions.elementToBeClickable(productListLocator));  //productListLocator


        Actions action = new Actions(driver);
        action.moveToElement(driver.findElements(productListLocator).get(0)).build().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstAddToCompareLocator));

        //Добавить первый в списке Samsung
        List<WebElement> resultsList = driver.findElements(resultListLocator);
        logger.info("Получен список результатов. Размер массива = {}", resultsList.size());
        addFirstToCompare(resultsList, wait, model1);
        //Добавить первый в списке Xiaomi
        addFirstToCompare(resultsList, wait, model2);

        //перейти к сравнению
        WebElement compareWidget = driver.findElement(compareWidgetLocator);
         WebElement compareButton = compareWidget.findElement(By.linkText("Сравнить"));
         compareButton.click();
        wait.until(ExpectedConditions.titleIs("Сравнение товаров — Яндекс.Маркет"));
        logger.info("Перешли в раздел 'Сравнить'");

        //Проверить, что в списке товаров две позиции
        List<WebElement> compareUnitsList = driver.findElements(compareUnitListLocator);
        assertEquals(2, compareUnitsList.size());
        logger.info("Проверка, что в списке товаров две позиции успешно завершена");




    }

    private void addFirstToCompare(List<WebElement> resultsList, WebDriverWait wait, String model) {
        for (int i = 0; i < resultsList.size(); i++) {
            WebElement phoneItem = resultsList.get(i);
            String modelName = phoneItem.findElement(modelNameLocator).getAttribute("title");
            logger.info("{} Получено имя модели в списке: {}", i, modelName);
            if (modelName.contains(model)) {
                Actions action2 = new Actions(driver);
                action2.moveToElement(phoneItem).build().perform();      //наведение мышки на элемент списка с найденным телефоном, чтобы появилась кнопка "Добавить к сравнению"

                By currentCompareLocator = By.cssSelector("div[data-zone-name='snippetList'] article:nth-child(" + (i + 1) + ") div[aria-label*='сравнению']");

                wait.until(ExpectedConditions.elementToBeClickable(phoneItem.findElement(currentCompareLocator))).click();

                //работа с виджетом
                wait.until(ExpectedConditions.visibilityOfElementLocated(compareWidgetLocator));
                WebElement compareWidget =  wait.until(ExpectedConditions.visibilityOfElementLocated(compareWidgetLocator));
                String phoneNameInPopup = compareWidget.findElement(unitNameInWidgetLocator).getAttribute("innerText");
                logger.info("Текст в плашке сравнения: {}", phoneNameInPopup);
                assertTrue(phoneNameInPopup.contains(model));
                assertTrue(phoneNameInPopup.equals("Товар " + modelName + " добавлен к сравнению"));
                break;
            }
        }
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
