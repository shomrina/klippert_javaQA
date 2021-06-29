package createUser;


import dto.User;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class GetUserByName extends UserBaseTest {


    @Test
    @DisplayName("проверка получения по имени существующего юзера")
    public void getExistingUserById() {
        Response response;
        User user;
        String expectedUserName = "Marina";

        //подготовка: создание юзера
        user = User.builder()
                .firstName("FirstName")
                .lastName("LastName")
                .password("Password")
                .username(expectedUserName)
                .build();
        userApi.createUser(user);

        //сам тест
        response = userApi.getUserByName(expectedUserName);
        response
                .then()
                .log().all()
                .statusCode(200)
                .body("username", equalTo(expectedUserName));

        //OR
        String actualName = response.jsonPath().get("username");
        Assertions.assertEquals(expectedUserName, actualName, "get user by name works incorrect");

        //очистка данных
        userApi.deleteUser(expectedUserName);
    }

    @Test
    @DisplayName("проверка получения по имени несуществующего юзера")
    public void getNonExistingUserById() {
        Response response;
        String userName = "Marina1234598";

        //тест
        response = userApi.getUserByName(userName);
        response
                .then()
                .log().all()
                .statusCode(404)
                .body("message", equalTo("User not found"));

    }
}
