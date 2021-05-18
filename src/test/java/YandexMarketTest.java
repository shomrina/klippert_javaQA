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
    By ascOrderFilterLocator = By.cssSelector("button[data-autotest-id='dprice']");
    By makerLocator = By.cssSelector("fieldset[data-autotest-id='7893318']");
    By compareUnitListLocator = By.cssSelector("div[data-apiary-widget-id='/content/compareContent'] img");
    By overlaySearchResultLocator = By.cssSelector("div[data-tid='67d9be0a']");
    By buttonCompareInWidget = By.linkText("Сравнить");

    String compareTitle = "Сравнение товаров — Яндекс.Маркет";

    @Test
    public void addToCompare() {
       String model1 = "Samsung";
       String model2 = "Xiaomi";

        //1. открыть яндекс маркет
        openMainPage();

        //2. Перейти Элекроника - Смартфоны
        goToElectronicPage();
        goToSmartphonesPage();
        logger.info("Перешли в раздел Смартфоны");

        //3. Отсортировать список товаров Samsung и Xiaomi
        filterByMaker(model1);
        filterByMaker(model2);
        logger.info("Отсортировано по моделям");

        //4. Отсортирован список товаров по цене
        sortByPriceAscending();

        //5. Добавить первый в списке Samsung
        List<WebElement> resultsList = getAllResults();
        logger.debug("Получен список результатов. Размер массива = {}", resultsList.size());

        String modelName1 = addFirstToCompare(resultsList, model1);
        String phoneNameInPopup1 = getTextPhoneNameInCompareWidget();  //получить полное название модели из виджета сравнения
        logger.info("Текст в плашке сравнения: {}", phoneNameInPopup1);
        //Проверить, что отобразилась плашка "Товар {имя товара} добавлен к сравнению"
        assertTrue(phoneNameInPopup1.equals("Товар " + modelName1 + " добавлен к сравнению"));

        //6. Добавить первый в списке Xiaomi
        String modelName2 = addFirstToCompare(resultsList, model2);
        String phoneNameInPopup2 = getTextPhoneNameInCompareWidget();  //получить полное название модели из виджета сравнения
        logger.info("Текст в плашке сравнения: {}", phoneNameInPopup2);
        //Проверить, что отобразилась плашка "Товар {имя товара} добавлен к сравнению"
        assertTrue(phoneNameInPopup2.equals("Товар " + modelName2 + " добавлен к сравнению"));

        //7. перейти к сравнению
        goToComparePage();

        //8. Проверить, что в списке товаров две позиции
        assertQuantityItemsInComparePage(2);
    }

    private String addFirstToCompare(List<WebElement> resultsList, String model) {
        for (int i = 0; i < resultsList.size(); i++) {
            By currentCompareLocator = By.cssSelector("div[data-zone-name='snippetList'] article:nth-child(" + (i + 1) + ") div[aria-label*='сравнению']"); //получение локатора для конкретного товара в списке
            WebElement phoneItem = resultsList.get(i);
            String modelName = phoneItem.findElement(modelNameLocator).getAttribute("title");      //получение имени модели в списке
            logger.debug("{} Получено имя модели в списке: {}", i, modelName);
            if (modelName.contains(model)) {
                Actions action2 = new Actions(driver);
                action2.moveToElement(phoneItem).build().perform();                                     //наведение мышки на элемент списка с найденным телефоном, чтобы появилась кнопка "Добавить к сравнению"
                WebElement currentCompareButton = phoneItem.findElement(currentCompareLocator);         // получение элемента "Добавить к сравнению" у нужной модели
                waitElementToBeClickable(currentCompareButton, 10).click();                    //ожидание и клик по кнопке "Добавить к сравнению"
                return modelName;
            }
        }
        return null;
    }

    public void openMainPage() {
        driver.get(conf.mainUrl());
        logger.info("Открыта страница {}", conf.mainUrl());
    }

    public void goToSmartphonesPage() {
        WebElement mobile =  waitVisibilityElement(mobileLocator, 10);
        mobile.click();
        logger.info("Перешли в раздел Смартфоны");
    }

    public void goToElectronicPage() {
        driver.findElement(electronicLocator).click();
        logger.info("Перешли  в раздел Электроника");
    }

    //получить список поискового результата после фильтрации
    public List<WebElement> getAllResults() {
        return driver.findElements(resultListLocator);
    }

    //проверить, что на вкладке Сравнения находится заданное число объектов (товаров)
    public void assertQuantityItemsInComparePage(int count) {
        List<WebElement> compareUnitsList = driver.findElements(compareUnitListLocator);
        assertEquals(count, compareUnitsList.size());
    }

    public void goToComparePage() {
        WebElement compareWidget =  waitVisibilityElement(compareWidgetLocator, 10);
        WebElement compareButton = waitElementToBeClickable(compareWidget.findElement(buttonCompareInWidget), 5);
        compareButton.click();
        waitTitleIs(compareTitle, 10);
        logger.info("Перешли в раздел 'Сравнить'");
    }

    //получить полное наименование телефона в виджете
    public String getTextPhoneNameInCompareWidget() {
        WebElement compareWidget =  waitVisibilityElement(compareWidgetLocator, 10);            //ожидание появление виджета
        return compareWidget.findElement(unitNameInWidgetLocator).getAttribute("innerText");
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

    //поиск по определенной модели телефона
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
