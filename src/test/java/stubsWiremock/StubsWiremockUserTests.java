package stubsWiremock;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@SpringBootTest
public class StubsWiremockUserTests extends BaseTest {

    @Test
    @DisplayName("create user")
    public void createUserTest() {
        stubs.stubCreateUser();

        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .body("{\n" +
                        "    \"name\": \"Marina\",\n" +
                        "    \"job\": \"QA\"\n" +
                        "}")
                .post(stubs.getBaseUrl() + "/users")
                .then()
                .extract().response();

        Assertions.assertEquals(201, response.statusCode());
        System.out.println(response.getBody().prettyPrint());
        Assertions.assertEquals("Marina", response.jsonPath().getString("name"));
        Assertions.assertEquals("QA", response.jsonPath().getString("job"));
        Assertions.assertNotNull(response.jsonPath().getString("id"));
        Assertions.assertNotNull(response.jsonPath().getString("createdAt"));

    }

    @Test
    @DisplayName("update user")
    public void updateUserTest() {
        stubs.stubUpdateUser();
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}")
                .put(stubs.getBaseUrl() + "/users/2")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        System.out.println(response.getBody().prettyPrint());
        Assertions.assertEquals("morpheus", response.jsonPath().getString("name"));
        Assertions.assertEquals("zion resident", response.jsonPath().getString("job"));
        Assertions.assertNotNull(response.jsonPath().getString("updatedAt"));

    }

    @Test
    @DisplayName("delete user")
    public void deleteUserTest() {
        stubs.stubDeleteUser();
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .delete(stubs.getBaseUrl() + "/users/2")
                .then()
                .extract().response();

        Assertions.assertEquals(204, response.statusCode());
        System.out.println(response.getBody().prettyPrint());
    }

    @Test
    @DisplayName("get user - classwork")
    void getUserTest() {
        stubs.stubGetUser();
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
//				.get("https://reqres.in/api/users/2")
          //      .get("http://localhost:5050/api/users/2")
                .get(stubs.getBaseUrl() + "/users/2")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        System.out.println(response.getBody().prettyPrint());
        Assertions.assertEquals("Janet", response.jsonPath().getString("data.first_name"));
        Assertions.assertEquals("Weaver", response.jsonPath().getString("data.last_name"));
        Assertions.assertTrue(response.jsonPath().getString("").contains("reqres.in"));
    }

}
