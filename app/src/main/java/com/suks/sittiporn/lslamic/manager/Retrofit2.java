package com.suks.sittiporn.lslamic.manager;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.suks.sittiporn.lslamic.manager.http.ApiService;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit2 {

//    public static String BASE_URL = "http://sittiporn-suks.com/api_rongphai/";
    public static String BASE_URL = "https://lslamicplace.com/apiIslamic/";
    private static Retrofit retrofit;
    private static Retrofit getRetrofitInstance(){

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30,TimeUnit.SECONDS)
                .connectTimeout(30,TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws java.io.IOException {
                        Request newRequest = chain.request().newBuilder()
//                        .addHeader("Authorization",URL.BEARER_TOKEN)
                                .addHeader("accept", "application/json")
                                .build();
                        return chain.proceed(newRequest);
                    }
                }).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        return retrofit;
    }

    public static ApiService getApiService(){

        return getRetrofitInstance().create(ApiService.class);

    }
}
