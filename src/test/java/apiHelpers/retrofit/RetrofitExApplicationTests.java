package apiHelpers.retrofit;

import apiHelpers.retrofit.pojo.updateUser.UpdateUserResponse;
import apiHelpers.retrofit.pojo.createUser.NewUserBody;
import apiHelpers.retrofit.pojo.createUser.NewUserResponse;
import apiHelpers.retrofit.pojo.getUser.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.DELETE;

import java.io.IOException;


@SpringBootTest
public class RetrofitExApplicationTests {
    APIInterface service;

    @BeforeEach
    public void setApiService() {
        //Endpoint client for send request
        service = APIClientHelper.getClient().create(APIInterface.class);
    }


    @Test
    @DisplayName("create new user")
    public void createUserTest() throws IOException {
        String testName = "Marina";
        String testJob = "developer";

        Response<NewUserResponse> response;
        NewUserBody newUserBody = new NewUserBody();
        newUserBody.setName(testName);
        newUserBody.setJob(testJob);

        Call<NewUserResponse> user = service.createUser(newUserBody);
        response = user.execute();
        System.out.println(response.body().toString());

        assert response.isSuccessful();
        Assertions.assertEquals(testName, response.body().getName());
        Assertions.assertEquals(testJob, response.body().getJob());
        Assertions.assertNotNull(response.body().getId());
        Assertions.assertNotNull(response.body().getCreatedAt());
        Assertions.assertEquals(201, response.code());

    }

    @Test
    @DisplayName("update user")
    public void updateUserTest() throws IOException {
        String testName = "Marina";
        String changeJob = "QA";

        Response<UpdateUserResponse> response;
        NewUserBody updatedBody = new NewUserBody();
        updatedBody.setName(testName);
        updatedBody.setJob(changeJob);

        response = service.updateUser(updatedBody).execute();
        System.out.println(response.body().toString());

        assert response.isSuccessful();
        Assertions.assertEquals(testName, response.body().getName());
        Assertions.assertEquals(changeJob, response.body().getJob());
        Assertions.assertNotNull(response.body().getUpdatedAt());
        Assertions.assertEquals(200, response.code());
    }

    @Test
    @DisplayName("")
    public void deleteUserTest() throws IOException {
        Response<User> response;
        response = service.deleteUser().execute();

        assert response.isSuccessful();
        Assertions.assertNull(response.body());
        Assertions.assertEquals(204, response.code());
    }

    @Test
    @DisplayName("get not existing user")
    public void getNotFoundUserTest() throws IOException {
        Response<User> response;
        response = service.getNotFoundUser().execute();

        Assertions.assertNull(response.body());
        Assertions.assertEquals(404, response.code());

    }



    @Test
    @DisplayName("get single user (classwork)")
    void retrofitTest() throws IOException {
        Response<User> response;

        //исполняем гет-запрос
        response = service.getUser().execute();

        assert response.isSuccessful();
        assert response.body() != null;
        Assertions.assertEquals("Janet", response.body().getData().getFirstName());
        System.out.println(response.body());
    }

}
