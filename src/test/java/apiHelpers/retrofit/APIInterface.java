package apiHelpers.retrofit;

import apiHelpers.retrofit.pojo.createUser.NewUserBody;
import apiHelpers.retrofit.pojo.createUser.NewUserResponse;
import apiHelpers.retrofit.pojo.getUser.User;
import retrofit2.Call;
import retrofit2.http.*;

public interface APIInterface {

    @GET("users/2") //хвостик от запроса
    Call<User> getUser();

    @GET("users/23")
    Call<User> getNotFoundUser();

    @POST("users")
    Call<NewUserResponse> createUser(@Body NewUserBody newUserBody);




}
