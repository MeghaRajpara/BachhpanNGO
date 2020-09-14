package com.megha.finalproject.Entities;

import com.megha.finalproject.Service.Bachhpan;
import com.megha.finalproject.Service.LoginService;

import retrofit2.Retrofit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit getRetrofit(){

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("https://megharajpara.com/collegeProject/Api/")
                        .client(okHttpClient)
                        .build();
        return retrofit;
    }

    public static LoginService getLoginService(){
        LoginService loginService = getRetrofit().create(LoginService.class);

        return loginService;
    }

    public static Bachhpan getActivityService(){
        Bachhpan activityService = getRetrofit().create(Bachhpan.class);

        return activityService;
    }
}
