package com.megha.finalproject.Service;

import com.megha.finalproject.Entities.Activities;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Bachhpan {

    @GET("listactivity")
    public Call<List<Activities>> getActivities();

    @POST("addactivity")
    public Call<Activities> addActivity(@Body RequestBody activityRequest);
}
