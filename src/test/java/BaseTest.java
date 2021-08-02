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
        //mvn clean test -Dbrowser="chrome" -Doptions="start-maximized headless" -Dlogin="milagrous@gmail.com" -Dpassword="_evD@DicbSkua5g"
        driver = WebDriverFactory.create(System.getProperty("browser"), System.getProperty("options"));
        logger.info("Драйвер поднят");
        logger.info("browser = {}, options = {}", System.getProperty("browser"), System.getProperty("options"));
    }

    @After
    public void setDown() {
        if (driver != null) {
            driver.quit();
        }
        logger.info("Текущие сессия и браузер закрыты");
    }
}
