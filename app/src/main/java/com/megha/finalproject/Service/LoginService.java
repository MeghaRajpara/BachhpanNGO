package com.megha.finalproject.Service;

import com.megha.finalproject.Entities.LoginRequest;
import com.megha.finalproject.Entities.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {

    @POST("volunteerlogin")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);
}
