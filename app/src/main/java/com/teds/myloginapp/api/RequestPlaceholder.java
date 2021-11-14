package com.teds.myloginapp.api;

import com.teds.myloginapp.pojos.Login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestPlaceholder {

    @POST("login")
    Call<Login> login(@Body Login login);
}
