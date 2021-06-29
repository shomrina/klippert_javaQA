package services;

import dto.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UserApi {
    private static final String BASE_URI = "https://petstore.swagger.io/v2";
    private static final String USER = "/user";
    private RequestSpecification spec;

    public UserApi () {
        spec = given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON);
    }

    public Response createUser(User user) {
        return
                given(spec)
                        .body(user)
                        .when()
                        .log().all()
                        .post(USER);
    }

    public Response getUserByName(String userName) {
        return
                given(spec)
                        .when()
                        .log().all()
                        .get(USER + "/" + userName);
    }

    public Response deleteUser(String userName) {
        return
                given(spec)
                .when()
                .log().all()
                .delete(USER + "/" + userName);
    }
}
