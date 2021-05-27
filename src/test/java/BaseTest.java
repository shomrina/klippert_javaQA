import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

public class BaseTest {
    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(BaseTest.class);


    @Before
    public void setUp() {
        //ввод опций через пробел внутри кавычек(если опций несколько), например: mvn clean test -Dbrowser="chrome" -Doptions="window-size=1920,1080 incognito" (для линукса кавычки одинарные)
        //или пример mvn clean test -Dbrowser="chrome" -Doptions="start-maximized" -Dlogin="log" -Dpassword="pass"
        //mvn clean test -Dbrowser="chrome" -Doptions="start-maximized" -Dlogin="milagrous@gmail.com" -Dpassword="fJ!ntyy2wRg9Fdh"
        driver = WebDriverFactory.create(System.getProperty("browser"), System.getProperty("options"));
        logger.info("Драйвер поднят");
    }

    @After
    public void setDown() {
        if (driver != null) {
            driver.quit();
        }
        logger.info("Текущие сессия и браузер закрыты");
    }
}
