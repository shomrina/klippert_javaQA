package stubsWiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BaseTest {
    protected static WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.options().port(5050)); //стартуем сервер. при старте требуется порт ля размещения
    protected static Stubs stubs = new Stubs();;

    @BeforeAll
    public static void setUpMockServer() {
        wireMockServer.start();

    }

    @AfterAll
    public static void tearDownMockServer() {
        wireMockServer.stop();
    }


}
