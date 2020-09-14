package com.megha.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.megha.finalproject.Adapter.EventListAdapter;
import com.megha.finalproject.Common.Const;
import com.megha.finalproject.Entities.Activities;
import com.megha.finalproject.Entities.ActivitiesRequest;
import com.megha.finalproject.Entities.ApiClient;
import com.megha.finalproject.Service.Bachhpan;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://megharajpara.com/collegeProject/Api/";
    private Toolbar toolbar;
    TextView username;
    String uname;
    private String loginusername;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        toolbar = (Toolbar)findViewById(R.id.toolbar_actionbar);
//        setSupportActionBar(toolbar);
//        toolbar.setLogo(android.R.drawable.ic_menu_info_details);

        recyclerView = findViewById(R.id.events_listview);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        SharedPreferences sharedPreferences = getSharedPreferences(Const.IsLogin, Context.MODE_PRIVATE);
        uname = sharedPreferences.getString("username", "");
//        Toast.makeText(this, "name :" + uname, Toast.LENGTH_SHORT).show();

        username = findViewById(R.id.username_display);
        //MenuItem item = Menu.findItem(R.id.username_display);
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            loginusername = intent.getStringExtra("data");
            Log.e("Setuser", loginusername);
        }

        CallAPitoGetEventList();


    }

    public void CallAPitoGetEventList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Bachhpan bachhpan = retrofit.create(Bachhpan.class);

        Call<List<Activities>> allActivities = bachhpan.getActivities();

        allActivities.enqueue(new Callback<List<Activities>>() {
            //private ListView listView = findViewById(R.id.events_listview);

            @Override
            public void onResponse(Call<List<Activities>> call, Response<List<Activities>> response) {
                List<Activities> activitie = response.body();

                EventListAdapter listAdapter = new EventListAdapter(getApplicationContext(), activitie, MainActivity.this);
                recyclerView.setAdapter(listAdapter);

                Log.e("AllActivites", "Success");
            }

            @Override
            public void onFailure(Call<List<Activities>> call, Throwable t) {
                Log.e("AllActivites", t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem usernamemenu = menu.findItem(R.id.username_display);
        Log.e("menuitem", loginusername);
        usernamemenu.setTitle(loginusername);
        return true;
    }

    /*** Menu select ***/

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem itemSelected) {
        switch (itemSelected.getItemId()){
            case R.id.addevent:
                AlertDialog.Builder customBuilder = new AlertDialog.Builder(this);
                customBuilder.setTitle("Name");

                View customDialog = getLayoutInflater().inflate(R.layout.activity_add_event, null);
                customBuilder.setView(customDialog);

                customBuilder.setPositiveButton("Add Event", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialog customView = (AlertDialog)dialogInterface;
                        EditText eventName = customView.findViewById(R.id.event_name);
                        EditText address = customView.findViewById(R.id.address);
                        EditText description = customView.findViewById(R.id.description);
                        Log.e("Add event",eventName.getText().toString());
                        ActivitiesRequest activityRequest = new ActivitiesRequest();
                        activityRequest.setAddress(address.getText().toString());
                        activityRequest.setName(eventName.getText().toString());
                        activityRequest.setDescription(eventName.getText().toString());

                        RequestBody requestBody = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("name",activityRequest.getName())
                                .addFormDataPart("address",activityRequest.getAddress())
                                .addFormDataPart("description",activityRequest.getDescription())
                                .build();
                        Call<Activities> activityresponse = ApiClient.getActivityService().addActivity(requestBody);
                        activityresponse.enqueue(new Callback<Activities>() {
                            @Override
                            public void onResponse(Call<Activities> call, Response<Activities> response) {
                                if(response.isSuccessful()){
                                    Activities acResponse = response.body();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.d("Add event","Added");

//                                            Intent in = new Intent(Login.this,MainActivity.class);
//                                            in.putExtra("data",loginResponse.getUsername());
//                                            startActivity(in);
                                        }
                                    },700);
                                    Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this,"Login failed",Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Activities> call, Throwable t) {

                            }
                        });

                    }
                });

                customBuilder.setNegativeButton("Refuse", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                customBuilder
                        .create()
                        .show();
                break;

        }
        return true;
    }
}