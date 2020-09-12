package com.megha.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.megha.finalproject.Entities.Activities;
import com.megha.finalproject.Service.Bachhpan;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://megharajpara.com/collegeProject/Api/";

    TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            String passedUsername = intent.getStringExtra("data");
            username.setText("Welcome "+passedUsername);
        }

        Retrofit retrofit= new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

        Bachhpan bachhpan = retrofit.create(Bachhpan.class);

        Call<List<Activities>> allActivities = bachhpan.getActivities();

        allActivities.enqueue(new Callback<List<Activities>>() {
            @Override
            public void onResponse(Call<List<Activities>> call, Response<List<Activities>> response) {
                //List<Activities> activitie = response.body();
                Log.e("AllActivites","Success");
            }

            @Override
            public void onFailure(Call<List<Activities>> call, Throwable t) {
                Log.e("AllActivites",t.getMessage());
            }
        });
    }
}