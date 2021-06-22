package steps;

import config.ServerConfig;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;


public class SearchPhoneModelStepDefs {
    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(SearchPhoneModelStepDefs.class);
    private ServerConfig conf = ConfigFactory.create(ServerConfig.class);

    By electronicLocator = By.cssSelector("[href*='/catalog--elektronika/'] > span");
    By mobileLocator = By.cssSelector("[href*='/catalog--smartfony/']");
    By makerLocator = By.cssSelector("fieldset[data-autotest-id='7893318']");
    By overlaySearchResultLocator = By.cssSelector("div[data-tid='67d9be0a']");
    By resultListLocator = By.cssSelector("div[data-zone-name='snippetList'] > article[data-autotest-id='product-snippet']");
    By modelNameLocator = By.cssSelector("[data-zone-name='title'] a");

    By ascOrderFilterLocator = By.cssSelector("button[data-autotest-id='dprice']");
    By pricePhoneLocator = By.cssSelector("span[data-autotest-value]");


    @Given("Open yandex.market page")
    public void openYandexMarketPage() {
        driver.get(conf.mainUrl());
        logger.info("Открыта страница {}", conf.mainUrl());

        System.out.println("test yandex market page");
    }

    @And("Go to eleсtronics page")
    public void goToEleсtronicsPage() {
        driver.findElement(electronicLocator).click();
        logger.info("Перешли  в раздел Электроника");

        System.out.println("go to el page");
    }

    @And("Go to smartphones page")
    public void goToSmartphonesPage() {
        WebElement mobile =  waitVisibilityElement(mobileLocator, 10);
        mobile.click();

        System.out.println("go to phone page");
    }

    @When("User search by maker {string}")
    public void userSearchByMaker(String makerName) {
        WebElement makerList = waitVisibilityElement(makerLocator, 10);
        makerList.findElement(By.xpath(".//span[contains(text(), '" + makerName + "')]")).click();
        waitInvisibilityOf(overlaySearchResultLocator, 5);
        System.out.println("sort by maker");
    }

    @Then("The list contains only sorted maker {string}")
    public void theListContainsOnlySortedMaker(String makerName) {
        List<WebElement> resultsList = driver.findElements(resultListLocator);

        for (int i = 0; i < resultsList.size(); i++) {
            WebElement phoneItem = resultsList.get(i);
            String modelName = phoneItem.findElement(modelNameLocator).getAttribute("title");      //получение имени модели в списке
            logger.debug("{} Получено имя модели в списке: {}", i, modelName);
            Assert.assertTrue("Попался элемент, который не соответсвует условиям фильтра", modelName.contains(makerName));
        }

        System.out.println("assert");
    }

    //Hooks cucumber
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

    public WebElement waitVisibilityElement(By locator, int timeInSec) {
        WebDriverWait wait = new WebDriverWait(driver, timeInSec);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitInvisibilityOf(By locator, int timeInSec) {
        WebDriverWait wait = new WebDriverWait(driver, timeInSec);
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(locator)));
    }

    @When("User click by prise one time")
    public void userClickByPriseOneTime() {
        driver.findElement(ascOrderFilterLocator).click();
        waitInvisibilityOf(overlaySearchResultLocator, 5);
        logger.info("Список отсортирован по цене");
    }

    @Then("The list contains phones are sorted by price ascending")
    public void theListContainsPhonesAreSortedByPriceAscending() {
        //получить результат
        List<WebElement> resultsList = driver.findElements(resultListLocator);
        List<Integer> prices = new ArrayList<>();

        //получение массива цен
        for (WebElement phoneItem : resultsList) {
            String modelPriceString = phoneItem.findElement(pricePhoneLocator).getText();
            Integer modelPrice = Integer.valueOf(modelPriceString.trim().replaceAll(" ", "").replaceAll("₽", ""));
            logger.debug("price model: {}", modelPrice);
            prices.add(modelPrice);
        }

        //проверка что отсортировано по возрастанию
        for (int j = 0; j < prices.size() - 1; j++) {
            Assert.assertTrue("Сортировка по возрастанию некорректна: ",prices.get(j) <= prices.get(j + 1));
            logger.debug("j: {}, j+1: {}", prices.get(j), prices.get(j + 1));
        }
    }
}
