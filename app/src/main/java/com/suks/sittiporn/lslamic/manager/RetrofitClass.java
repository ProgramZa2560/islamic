package com.suks.sittiporn.lslamic.manager;


import com.suks.sittiporn.lslamic.manager.http.ApiService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClass {

    private static final String BASE_URL = "http://164.115.27.232:9982/api/Ship/";
    private static Retrofit retrofit;
    private static Retrofit getRetrofitInstance(){

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        return retrofit;
    }

    public static ApiService getApiService(){

        return getRetrofitInstance().create(ApiService.class);

    }
}
