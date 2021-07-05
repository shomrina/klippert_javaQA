package citrus.tests;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.testng.TestNGCitrusTestRunner;

import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.springframework.http.HttpStatus.*;

public class FirstTest extends TestNGCitrusTestRunner {

    @Autowired  //аннотация автосвязывания. Цитрус понимает что поле клиенат связывается с бином. указанным в цитрус-контексте (xml) Так мы получаем экземпляр бина
    private HttpClient restClient; //чтобы посылать запрос нужен клиент о цитруса
    private TestContext context; //для работы с контекстом нужно цитрусовое


    //Homework
    @Test(description = "Get not existing user")
    @CitrusTest
    public void userNotFoundTest() {
        http(httpActionBuilder -> httpActionBuilder
                .client(restClient)
                .send()
                .get("/users/" + context.getVariable("notExistingUserId"))
        );

        http(httpActionBuilder -> httpActionBuilder
                .client(restClient)
                .receive()
                .response()
                .status(NOT_FOUND)
                .payload("{}")
        );

    }

    @Test(description = "create user")
    @CitrusTest
    public void createUserTest() {
        http(httpActionBuilder -> httpActionBuilder
                .client(restClient)
                .send()
                .post("/users")
                .payload("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"leader\"\n" +
                        "}")
        );

        http(httpActionBuilder -> httpActionBuilder
                .client(restClient)
                .receive()
                .response()
                .messageType(MessageType.JSON)
                .status(CREATED)
                .payload("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"leader\",\n" +
                        "    \"id\": \"@isNumber()@\",\n" +
                        "    \"createdAt\": \"@assertThat(notNullValue())@\"\n" +
                 "}")
        );

    }

    @Test(description = "update user")
    @CitrusTest
    public void updateUserTest() {
        http(httpActionBuilder -> httpActionBuilder
                .client(restClient)
                .send()
                .put("/users/" + context.getVariable("userId"))
                .payload("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}")
        );

        http(httpActionBuilder -> httpActionBuilder
                .client(restClient)
                .receive()
                .response()
                .messageType(MessageType.JSON)
                .status(OK)
                .payload("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"zion resident\",\n" +
                        "    \"updatedAt\": \"@assertThat(notNullValue())@\"\n" +
                        "}")
        );

    }

    @Test(description = "delete user")
    @CitrusTest
    public void deleteUserTest() {
        http(httpActionBuilder -> httpActionBuilder
                .client(restClient)
                .send()
                .delete("/users/" + context.getVariable("userId"))
        );

        http(httpActionBuilder -> httpActionBuilder
                .client(restClient)
                .receive()
                .response()
                .status(NO_CONTENT)
        );

    }

    @BeforeMethod
    @CitrusTest
    public void setContext() {
        this.context = citrus.createTestContext();      //контекст будет виден методу
    }

    //classwork
    @Test(description = "get information about user")
    @CitrusTest   //необходимо чтобы цитрус распознал это как цитрус-тест
    public void getTestsAction() {
     //   this.context = citrus.createTestContext();      //контекст будет виден методу

        //достать и залогировать вериэблс. Пример работы с переменными цитруса!
        context.setVariable("value", "!!!!!!!!!!!");
        echo(context.getVariable("value"));
        echo(context.getVariable("var"));   //получение переменной по цитрус-контекст
        echo("${var}" + "123456789");       //еще один способ получения переменной
      //  echo("${var}" + "123456789");

        //сформируем экшн с помощью вызова билдера хттп
        http(httpActionBuilder -> httpActionBuilder
                .client(restClient)
                .send()
                //.get("users/2") // + user)
                .get("users/" + context.getVariable("userId"))
        );

        //клиент принимает респонс . пейлоад - это текстовое содержимое
        http(httpActionBuilder -> httpActionBuilder
                .client(restClient)
                .receive()
                .response()
                .messageType(MessageType.JSON)
                .payload("{\n" +
                        "   \"data\":{\n" +
                        "      \"id\":2,\n" +
                        "      \"email\":\"janet.weaver@reqres.in\",\n" +
                        "      \"first_name\":\"Janet\",\n" +
                        "      \"last_name\":\"Weaver\",\n" +
                        "      \"avatar\":\"https://reqres.in/img/faces/2-image.jpg\"\n" +
                        "   },\n" +
                        "   \"support\":{\n" +
                        "      \"url\":\"https://reqres.in/#support-heading\",\n" +
                        "      \"text\":\"To keep ReqRes free, contributions towards server costs are appreciated!\"\n" +
                        "   }\n" +
                        "}")
        );

    }

    @Test(description = "get information about user - fail")
    @CitrusTest   //необходимо чтобы цитрус распознал это как цитрус-тест
    public void getTestsAction2() {

        //сформируем экшн с помощью вызова билдера хттп (request)
        http(httpActionBuilder -> httpActionBuilder
                .client(restClient)
                .send()
                .get("users/2") // + user)
        );

        //клиент принимает респонс . пейлоад - это текстовое содержимое (response, стнхронный ответ)
        http(httpActionBuilder -> httpActionBuilder
                .client(restClient)
                .receive()
                .response()
                .messageType(MessageType.JSON)
                .payload("{\n" +
                        "   \"data\":{\n" +
                        "      \"id\":2,\n" +
                        "      \"email\":\"janet.weaver@reqres.in\",\n" +
                        "      \"first_name\":\"Janete\",\n" +
                        "      \"last_name\":\"Weaver\",\n" +
                        "      \"avatar\":\"https://reqres.in/img/faces/2-image.jpg\"\n" +
                        "   },\n" +
                        "   \"support\":{\n" +
                        "      \"url\":\"https://reqres.in/#support-heading\",\n" +
                        "      \"text\":\"To keep ReqRes free, contributions towards server costs are appreciated!\"\n" +
                        "   }\n" +
                        "}")
        );

    }

}
