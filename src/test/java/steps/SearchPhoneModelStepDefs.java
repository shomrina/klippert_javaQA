package steps;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.ElectronicPage;
import pages.MainPage;
import pages.SmartphonesPage;
import utils.WebDriverFactory;

import java.util.ArrayList;
import java.util.List;


public class SearchPhoneModelStepDefs {
    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(SearchPhoneModelStepDefs.class);

    private MainPage mainPage;
    private ElectronicPage electronicPage;
    private SmartphonesPage smartphonesPage;


    @Given("Open yandex.market page")
    public void openYandexMarketPage() {
        mainPage = new MainPage(driver);
        mainPage.openMainPage();
    }

    @And("Go to electronics page")
    public void goToElectronicsPage() {
        electronicPage = mainPage.openElectronicPage();
    }

    @And("Go to smartphones page")
    public void goToSmartphonesPage() {
        smartphonesPage = electronicPage.openSmartphonesPage();
    }

    @When("User search by maker {string}")
    public void userSearchByMaker(String makerName) {
        smartphonesPage.searchByMaker(makerName);
    }

    @Then("The list contains only sorted maker {string}")
    public void theListContainsOnlySortedMaker(String makerName) {
        List<WebElement> resultsList = smartphonesPage.gerResultList();
        smartphonesPage.checkTheListContainsOnleSortedMaker(makerName, resultsList);
    }

    @When("User click by prise one time")
    public void userClickByPriseOneTime() {
        smartphonesPage.sortByPriceAsc();
    }


    @Then("The list contains phones are sorted by price ascending")
    public void theListContainsPhonesAreSortedByPriceAscending() {
        //получить результат
        List<WebElement> resultsList = smartphonesPage.gerResultList();

        //получение массива цен
        List<Integer> prices = new ArrayList<>();
        smartphonesPage.getResultListOfPrices(resultsList, prices);

        //проверка что отсортировано по возрастанию
        smartphonesPage.checkPriceAsc(prices);
    }

    //Hooks cucumber
    @Before
    public void setUp() {
        driver = WebDriverFactory.create(System.getProperty("browser"), System.getProperty("options"));
        logger.info("Драйвер поднят");
    }

    @After
    public void shutDown() {
        if (driver != null)
            driver.quit();
        logger.info("Драйвер остановлен");
    }

}
