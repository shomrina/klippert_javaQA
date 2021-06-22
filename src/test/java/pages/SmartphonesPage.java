package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SmartphonesPage extends AbstractPage {

    public SmartphonesPage(WebDriver driver) {
        super(driver);
    }

    private Logger logger = LogManager.getLogger(SmartphonesPage.class);

    private By makerLocator = By.cssSelector("fieldset[data-autotest-id='7893318']");
    private By overlaySearchResultLocator = By.cssSelector("div[data-tid='67d9be0a']");
    private By ascOrderFilterLocator = By.cssSelector("button[data-autotest-id='dprice']");
    private By resultListLocator = By.cssSelector("div[data-zone-name='snippetList'] > article[data-autotest-id='product-snippet']");
    private By modelNameLocator = By.cssSelector("[data-zone-name='title'] a");
    private By pricePhoneLocator = By.cssSelector("span[data-autotest-value]");

    public void searchByMaker(String makerName) {
        WebElement makerList = waitVisibilityElement(makerLocator, 10);
        makerList.findElement(By.xpath(".//span[contains(text(), '" + makerName + "')]")).click();
        waitInvisibilityOf(overlaySearchResultLocator, 5);
    }

    public void sortByPriceAsc() {
        driver.findElement(ascOrderFilterLocator).click();
        waitInvisibilityOf(overlaySearchResultLocator, 5);
        logger.info("Список отсортирован по цене");
    }

    public void checkTheListContainsOnleSortedMaker(String makerName, List<WebElement> resultsList) {
        for (int i = 0; i < resultsList.size(); i++) {
            WebElement phoneItem = resultsList.get(i);
            String modelName = phoneItem.findElement(modelNameLocator).getAttribute("title");      //получение имени модели в списке
            logger.debug("{} Получено имя модели в списке: {}", i, modelName);
            Assert.assertTrue("Попался элемент, который не соответсвует условиям фильтра", modelName.contains(makerName));
        }
    }

    public List<WebElement> gerResultList() {
        return driver.findElements(resultListLocator);
    }

    public void getResultListOfPrices(List<WebElement> resultsList, List<Integer> prices) {
        for (WebElement phoneItem : resultsList) {
            String modelPriceString = phoneItem.findElement(pricePhoneLocator).getText();
            Integer modelPrice = Integer.valueOf(modelPriceString.trim().replaceAll(" ", "").replaceAll("₽", ""));
            logger.debug("price model: {}", modelPrice);
            prices.add(modelPrice);
        }
    }

    public void checkPriceAsc(List<Integer> prices) {
        for (int j = 0; j < prices.size() - 1; j++) {
            logger.debug("j: {}, j+1: {}", prices.get(j), prices.get(j + 1));
            Assert.assertTrue("Сортировка по возрастанию некорректна: ", prices.get(j) <= prices.get(j + 1));
        }
    }
}
