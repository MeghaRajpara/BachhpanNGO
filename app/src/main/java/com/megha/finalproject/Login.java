package com.megha.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.megha.finalproject.Common.Const;
import com.megha.finalproject.Common.Sharedpref;
import com.megha.finalproject.Entities.ApiClient;
import com.megha.finalproject.Entities.LoginRequest;
import com.megha.finalproject.Entities.LoginResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    TextInputEditText username, password;
    Button btnLogin;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.edUsername);
        password = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnLogin);

        preferences = getSharedPreferences(Const.IsLogin, Context.MODE_PRIVATE);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString())) {
                    Toast.makeText(Login.this, "Username / Password Required", Toast.LENGTH_SHORT).show();
                } else {
                    login();
                }
            }
        });
    }

    public void login() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username.getText().toString());
        loginRequest.setPassword(password.getText().toString());

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", loginRequest.getUsername())
                .addFormDataPart("password", loginRequest.getPassword())
                .build();

        Call<LoginResponse> loginResponseCall = ApiClient.getLoginService().userLogin(requestBody);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("login", loginResponse.getUsername());

                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putBoolean(Const.IsUSERLogin, true);
                            editor.putString("username", loginRequest.getUsername());
                            editor.putString("password", loginRequest.getPassword());
                            editor.commit();

                            Intent in = new Intent(Login.this, MainActivity.class);
                            in.putExtra("data", loginResponse.getUsername());
                            startActivity(in);
                        }
                    }, 700);
                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Login.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences(Const.IsLogin, Context.MODE_PRIVATE);

        String uname = sharedPreferences.getString("username", "");
        String pwd = sharedPreferences.getString("password", "");

        if (!uname.equals("") && !pwd.equals("")) {
            username.setText(uname);
            password.setText(pwd);
        }

//
//        if (preferences.contains(Const.IsUSERLogin)) {
//            Intent intent = new Intent(Login.this, MainActivity.class);
//            startActivity(intent);
//        }


    }
}