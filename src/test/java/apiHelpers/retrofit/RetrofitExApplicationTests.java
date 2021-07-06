package apiHelpers.retrofit;

import apiHelpers.retrofit.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import retrofit2.Response;

import java.io.IOException;

@SpringBootTest
public class RetrofitExApplicationTests {

    @Test
    void retrofitTest() throws IOException {

        Response<User> response;

        //Endpoint client for send request
        APIInterface service = APIClientHelper.getClient().create(APIInterface.class);

        //исполняем гет-запрос
        response = service.getUser().execute();

        assert response.isSuccessful();
        assert response.body() != null;
        System.out.println(response.body().getData());
        
    }
}
