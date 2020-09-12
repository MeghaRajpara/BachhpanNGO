package com.megha.finalproject.Service;

import com.megha.finalproject.Entities.Activities;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Bachhpan {

    @GET("listactivity")
    public Call<List<Activities>> getActivities();
}
