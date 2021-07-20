package stubsWiremock;

import com.github.tomakehurst.wiremock.client.WireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class Stubs {

    private static final String PATH = "/api";
    private static final String HOST = "localhost";
    private static final int PORT = 5050;

    private static String BASE_URL;

    public Stubs() {
        BASE_URL = "http://" + HOST + ":" + PORT + PATH;
        WireMock.configureFor(HOST, PORT); //конфигурация. при настрйоке надо предеать хост и порт
    }

    public String getBaseUrl() {
        return BASE_URL;
    }

    public void stubGetUser() {
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo(PATH + "/users/2"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withBody("{\n" +
                                "    \"data\": {\n" +
                                "        \"id\": 2,\n" +
                                "        \"email\": \"janet.weaver@reqres.in\",\n" +
                                "        \"first_name\": \"Janet\",\n" +
                                "        \"last_name\": \"Weaver\",\n" +
                                "        \"avatar\": \"https://reqres.in/img/faces/2-image.jpg\"\n" +
                                "    },\n" +
                                "    \"support\": {\n" +
                                "        \"url\": \"https://reqres.in/#support-heading\",\n" +
                                "        \"text\": \"To keep ReqRes free, contributions towards server costs are appreciated!\"\n" +
                                "    }\n" +
                                "}")));
    }

    public void stubCreateUser() {
        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo(PATH + "/users"))
                .withHeader("Content-Type", matching("application/json"))
                .withRequestBody(equalToJson("{\n" +
                                                "    \"name\": \"Marina\",\n" +
                                                "    \"job\": \"QA\"\n" +
                                                "}"))
                .willReturn(WireMock.aResponse()
                        .withStatus(201)
                        .withBody("{\n" +
                                "    \"name\": \"Marina\",\n" +
                                "    \"job\": \"QA\",\n" +
                                "    \"id\": \"877\",\n" +
                                "    \"createdAt\": \"2021-07-07T19:47:51.921Z\"\n" +
                                "}")
                )
        );

    }

    public void stubUpdateUser() {
        WireMock.stubFor(WireMock.put(WireMock.urlEqualTo(PATH + "/users/2"))
                .withRequestBody(equalToJson("{\n" +
                                                "    \"name\": \"morpheus\",\n" +
                                                "    \"job\": \"zion resident\"\n" +
                                                "}"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withBody("{\n" +
                                "    \"name\": \"morpheus\",\n" +
                                "    \"job\": \"zion resident\",\n" +
                                "    \"updatedAt\": \"2021-07-07T20:38:26.625Z\"\n" +
                                "}")
                )
        );

    }

    public void stubDeleteUser() {
        WireMock.stubFor(WireMock.delete(WireMock.urlEqualTo(PATH + "/users/2"))
                .willReturn(WireMock.aResponse()
                        .withStatus(204)
                )
        );
    }



}
