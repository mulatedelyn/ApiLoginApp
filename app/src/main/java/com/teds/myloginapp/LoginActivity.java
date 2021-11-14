package com.teds.myloginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.teds.myloginapp.api.RequestPlaceholder;
import com.teds.myloginapp.api.RetrofitBuilder;
import com.teds.myloginapp.pojos.Login;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public RetrofitBuilder retrofitBuilder;
    public RequestPlaceholder requestPlaceholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView username =(TextView) findViewById(R.id.username);
        TextView password =(TextView) findViewById(R.id.password);
        MaterialButton  loginbtn = (MaterialButton) findViewById(R.id.loginbtn);

        retrofitBuilder = new RetrofitBuilder();
        requestPlaceholder = retrofitBuilder.getRetrofit().create(RequestPlaceholder.class);

        //admin and admin

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText() !=null && password.getText() !=null) {
                    Call<Login> loginCall = requestPlaceholder.login(new Login(null, username.getText().toString(), null, null, password.getText().toString()));

                    loginCall.enqueue(new Callback<Login>() {
                        @Override
                        public void onResponse(Call<Login> call, Response<Login> response) {
                            if (!response.isSuccessful()) {
                                if (response.code() == 404) {
                                    Toast.makeText(LoginActivity.this, "User not found!", Toast.LENGTH_SHORT).show();
                                    Log.e("LOGING ERR", response.message());

                                }else{
                                    Toast.makeText(LoginActivity.this, "There is an error upon logging in to the API", Toast.LENGTH_SHORT).show();
                                    Log.e("LOGING ERR", response.message());

                                }
                                Toast.makeText(LoginActivity.this, "There is an error upon logging in to the API", Toast.LENGTH_SHORT).show();
                                Log.e("LOGING ERR", response.message());
                            }else{
                                if(response.code() == 200) {
                                    Login loginResponse = response.body();
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    intent.putExtra("USERID", loginResponse.getId());
                                    intent.putExtra("USERNAME", loginResponse.getUsername());
                                    intent.putExtra("TOKEN", loginResponse.getToken());

                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Login> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "There is an error upon logging in to the API", Toast.LENGTH_SHORT).show();
                            Log.e("LOGING ERR", t.getMessage());

                        }
                    });
                }else{
                    Toast.makeText(LoginActivity.this, "Please supply all the fields to login!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}