package com.megha.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.megha.finalproject.Adapter.EventListAdapter;
import com.megha.finalproject.Entities.Activities;
import com.megha.finalproject.Service.Bachhpan;

import java.util.EventListener;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://megharajpara.com/collegeProject/Api/";
    private Toolbar toolbar;
    TextView username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        toolbar = (Toolbar)findViewById(R.id.toolbar_actionbar);
//        setSupportActionBar(toolbar);
//        toolbar.setLogo(android.R.drawable.ic_menu_info_details);


        //username = findViewById(R.id.username);

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            String passedUsername = intent.getStringExtra("data");
Log.d("passed",passedUsername);
            //username.setText("Welcome "+passedUsername);
        }


        Retrofit retrofit= new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

        Bachhpan bachhpan = retrofit.create(Bachhpan.class);

        Call<List<Activities>> allActivities = bachhpan.getActivities();

        allActivities.enqueue(new Callback<List<Activities>>() {
            private ListView listView = findViewById(R.id.events_listview);

            @Override
            public void onResponse(Call<List<Activities>> call, Response<List<Activities>> response) {
                List<Activities> activitie = response.body();
                EventListAdapter listAdapter = new EventListAdapter(MainActivity.this,activitie);
                listView.setAdapter(listAdapter);
                Log.e("AllActivites","Success");
            }

            @Override
            public void onFailure(Call<List<Activities>> call, Throwable t) {
                Log.e("AllActivites",t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
}