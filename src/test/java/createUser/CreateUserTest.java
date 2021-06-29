package createUser;

import dto.User;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class CreateUserTest extends UserBaseTest {

    @Test
    public void checkCreateUser() {
        Response response;
        User user;
        String expectedEmail = "Test@mail.ru";
        String actualType;
        String type = "unknown";
        String errorMessageType = "Incorrect userName";
        String expectedType = "unknown";
        Long id = 101L;

        user = User.builder()
                .email(expectedEmail)
                .firstName("FirstName")
                .id(id)
                .lastName("LastName")
                .password("Password")
                .phone("8-920-920-23-23")
                .username("Ivan")
                .userStatus(10L)
                .build();

        response = userApi.createUser(user);

        //1
        response
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .time(lessThan(5000L))
                .body("type", equalTo(expectedType))
                .body("message", comparesEqualTo(id.toString()));
        //2
        actualType = response.jsonPath().get("type");
        Assertions.assertEquals(type, actualType, errorMessageType);


        //3
       /* actualEmail = response.
                then()
                .extract()
                .body()
                .as(User.class).getMEmail();

        Assertions.assertEquals(expectedEmail, actualEmail, errorMessageEmail);*/
    }

    @Test
    @DisplayName("Проверка создания пользователя без айди")
    public void createdUserWithoutId() {
        User user =  User.builder()
                .email("test123@mail.ru")
                .firstName("Name1")
                .lastName("LastName2")
                .password("Password1")
                .phone("8-920-920-23-23")
                .username("Ivan2")
                .userStatus(10L)
                .build();

        Response response = userApi.createUser(user);
        response
                .then()
                .log().all()
                .statusCode(200)
                .body("id", notNullValue());  //обычно айди генерится автоматически
    }

    @Test
    @DisplayName("Создание пользователя без заполнения username")
    public void createdUserWithoutUsername() {
        User user = User.builder()
                .email("test1234@mail.ru")
                .firstName("Name2")
                .lastName("LastName3")
                .password("Password2")
                .phone("8-920-920-23-23")
                .id(102L)
                .userStatus(10L)
                .build();

        Response response = userApi.createUser(user);
        response
                .then()
                .log().all()
                .statusCode(400); //в этом апи очевидно что  username используется как уникальтный идентификатор. логично, что без него не должно создаваться
    }

    @Test
    @DisplayName("Создание юзеров с одинаковым username")
    public void createdTwoUsersWithTHeSameUsername() {
        String username = "kovaleriya";

        User user1 = User.builder()
                .email("test111@mail.ru")
                .firstName("Name11")
                .lastName("LastName11")
                .password("Password11")
                .phone("8-920-920-23-23")
                .username(username)
                .id(103L)
                .userStatus(10L)
                .build();

        userApi.createUser(user1);

        User user2 = User.builder()
                .email("test222@mail.ru")
                .firstName("Name222")
                .lastName("LastName333")
                .password("Password23")
                .phone("8-920-920-23-23")
                .username(username)
                .id(104L)
                .userStatus(10L)
                .build();

        Response response = userApi.createUser(user2);
        response
                .then()
                .log().all()
                .statusCode(400)
                .body("messale", contains("already exist"));  //поскольку username используется для удаления и получения, то полагаю должно быть уникально...
    }
}

