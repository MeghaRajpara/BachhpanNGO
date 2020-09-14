package com.megha.finalproject.Service;

import com.megha.finalproject.Entities.Activities;
import com.megha.finalproject.Entities.VolunteerRequest;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Bachhpan {

    @GET("listactivity")
    public Call<List<Activities>> getActivities();

    @POST("addactivity")
    public Call<Activities> addActivity(@Body RequestBody activityRequest);

    @FormUrlEncoded
    @POST("increaseLikes")
    Call<Boolean> IncreaseLike(@Field("activity_id") String activity_id);

    @POST("approvevolunteer")
    Call<VolunteerRequest> addVolunterr(@Body RequestBody volunteerRequest);
}
