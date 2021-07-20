package pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.io.ByteArrayInputStream;

public class MainPage extends AbstractPage {
    private Logger logger = LogManager.getLogger(MainPage.class);

    private String baseURL = "https://otus.ru/";
    private String titleMainPage = "Онлайн‑курсы для профессионалов, дистанционное обучение современным профессиям";

    private By avatarLocator = By.cssSelector(".ic-blog-default-avatar");

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "button.header2__auth")
    private WebElement buttonEnterLK;

    @FindBy(css = "a[href='/lk/biography/personal/'] > div > b")
    private WebElement myProfileButton;

    @Step("Открыта главная страница")
    public void open() {
        driver.get(baseURL);
        logger.info("Открыта страница отус {}", baseURL);
    }

    public String getTitleMainPage() {
        return titleMainPage;
    }

    @Step("Переход на страницу логина")
    public LoginPage openLoginPage() {
        buttonEnterLK.click();
        return new LoginPage(driver);
    }

    //сделать отдельный класс под главную страницу с авторизацией? или так оставить?
    @Step("Переход в личный кабинет")
    public LKpersonalDataPage enterLK() {
        WebElement avatar = waitVisibilityOfElement(avatarLocator, 5);
        Actions actions = new Actions(driver);
        actions.moveToElement(avatar).build().perform();
        myProfileButton.click();                                                                    //click by MY PROFILE
        logger.info("Перешли в личный кабинет");

        Allure.addAttachment("Личный кабинет " + Math.random(), new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));

        return new LKpersonalDataPage(driver);
    }

}
