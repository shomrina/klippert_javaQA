package apiHelpers.retrofit;

import apiHelpers.retrofit.pojo.User;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {

    @GET("2") //хвостик от запроса
    Call<User> getUser();
}
