package apiHelpers.retrofit;

import apiHelpers.retrofit.pojo.updateUser.UpdateUserResponse;
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

    @PUT("users/2")
    Call<UpdateUserResponse> updateUser(@Body NewUserBody userBody);

    @DELETE("users/2")
    Call<User> deleteUser();




}
