package apiHelpers.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class APIClientHelper {
    public static final String BASE_URL = "https:/reqres.in/api/";  //для ретрофита важн, чтобы стрингазаканчивалась /!!!


    private static Retrofit retrofit = null;

    static Retrofit getClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())  //джексон-конвертер для работы с джавой
                .build();

        return retrofit;
    }
}